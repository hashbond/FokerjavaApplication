/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author HASH
 */
public class chat extends Application{
    private boolean isServer = true;
    
    private TextArea message = new TextArea();
    private NetworkConnection connection = isServer ? createServer() : createClient();
    
    private Parent createContend()
    {
        message.setPrefHeight(550);
        TextField input = new TextField();
        input.setOnAction(event -> {
            String message = isServer ? "Server": "Client";
            message +=input.getText();
            input.clear();
            
            this.message.appendText("\n"+"message");
            try {
                connection.send(message);
            } catch (Exception e) {
                this.message.appendText("Exception"+"\n");
            }
        });
        VBox root = new VBox(20,message,input);
        root.setPrefSize(600,600);
        return root;
        
    }
    private Server createServer()
    {
        return new Server(55555, data ->{
           Platform.runLater(()->{
            message.appendText(data.toString() +"\n");
        });    
        });
    }
    private Client createClient()
    {
       return new Client("127.0.0.1",55555, data ->{
           Platform.runLater(()->{
            message.appendText(data.toString() +"\n");
        });    
        });
    
    }
    public void init() throws Exception
    {
        connection.startConnection();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContend()));
        primaryStage.show();
    }
     public void stop() throws Exception
    {
        connection.closeConnection();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
