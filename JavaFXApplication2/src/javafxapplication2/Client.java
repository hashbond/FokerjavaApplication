/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * @author HASH
 */
public class Client extends NetworkConnection{

    private String ip;
    private int port;
    public Client(int port,String ip,Consumer<Serializable> onReciveCallBack) {
        super(onReciveCallBack);
        this.port = port;
        this.ip = ip;
    }

    @Override
    protected boolean IsServer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }

   
    
}
