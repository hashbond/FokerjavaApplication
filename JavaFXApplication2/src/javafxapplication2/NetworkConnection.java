/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialArray;

/**
 *
 * @author HASH
 */
public abstract class NetworkConnection {
    private ConnectionThread connThread = new ConnectionThread();
    private Consumer<Serializable> onReciveCallBack;
    
    public NetworkConnection(Consumer<Serializable> onReciveCallBack)
    {
        this.onReciveCallBack = this.onReciveCallBack;
        connThread.setDaemon(true);
    }
    public void startConnection()throws Exception
    {
        connThread.start();
    }
    public void send(Serializable data)throws Exception
    {
        connThread.out.writeObject(data);
    }
    public void closeConnection()throws Exception
    {
        connThread.socket.close();
    }
    protected abstract boolean IsServer();
    protected abstract String getIP();
    protected abstract int getPort();
    
    public class ConnectionThread extends Thread
    {
        private Socket socket;
        private ObjectOutputStream out;
        @Override
        public void run() {
           try(ServerSocket server = IsServer() ? new ServerSocket(getPort()) : null;
                   Socket socket = IsServer()? server.accept() : new Socket(getIP(), getPort());
                   ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                   ObjectInputStream in = new  ObjectInputStream(socket.getInputStream()))
           {
               this.socket = socket;
               this.out = out;
               socket.setTcpNoDelay(true);
               while (true) {                   
                   Serializable data = (Serializable)in.readObject();
                   onReciveCallBack.accept(data);
                    
               }
               
               
           } catch (IOException ex) {
                onReciveCallBack.accept("conncetion close");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NetworkConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
                
           
        }
        
    }
}
