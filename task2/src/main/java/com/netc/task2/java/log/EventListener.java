/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netc.task2.java.log;

import javax.swing.JTextArea;


/**
 *
 * @author Ser
 */
public class EventListener  implements Observer{
    private JTextArea jTextArea;
    private static String event;

    public EventListener(JTextArea jTextArea) {
        this.jTextArea=jTextArea;
    }
    
    public void showEvent(String str){
        jTextArea.append(str);
    }
    
    public void clearTextArea(){
        jTextArea.setText("");
    }

    @Override
    public void changes(String name, String event) {
        showEvent(name+' '+event+'\n');
    } 
}



