/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netc.task3.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import javax.swing.JTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ser
 */
public class Client extends Thread{
private BufferedReader in;
private BufferedWriter out;
private Socket userSocket;
private JTextArea textArea=null;



private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    
    public Client(Socket userSocket) throws IOException {
        this.userSocket = userSocket;
        in = new BufferedReader(new InputStreamReader(this.userSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(this.userSocket.getOutputStream()));
    }
    
    public void sendMessage(String text) throws IOException{
        out.write(text);
       // out.flush();
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
    
    
    public void showMessage(String text){
        if (textArea == null)
            System.out.println(text);
        else   
            textArea.append(text);
    }

    @Override
    public void run() {
        LOGGER.info("Client start");
        String message;
        while(true){
            try {
                message = in.readLine();
                showMessage(message);
            } catch (IOException ex) {
                LOGGER.error(ex.toString());
                return;
            }
        }
            
    }
    
    
    
    
}
