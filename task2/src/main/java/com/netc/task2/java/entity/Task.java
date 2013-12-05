package com.netc.task2.java.entity;

import com.netc.task2.java.log.Observed;
import com.netc.task2.java.log.Observer;
import java.util.ArrayList;
import javax.swing.JTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task implements Runnable, Observed
{
    private String name;
    private int delayFirstStart;
    private int operationTime;
    private int repeat;
    private int currentTime;
    public  Thread thread; 
    private ArrayList<Observer> listeners = new ArrayList<Observer>();
    private String state;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);
    
    public Task(String name, int delayFirstStart, int operationTime, int repeat) {
        this.name = name;
        this.delayFirstStart = delayFirstStart;
        this.operationTime = operationTime;
        this.repeat = repeat;
    }
    
    public void setCurrentTime(int number){
        currentTime=number;
    }
    
    
    public String getTaskName(){
        return name;
    }
    
    public int getDelayFirstStart(){
        return delayFirstStart;
    }
    
    public int getCurrentTime(){
        return currentTime;
    }
    
    public int getOperationTime(){
        return operationTime;
    }
    
    public int getRunCount(){
        return repeat;
    }
    
    public void decDelayFirstStart(){
        if (delayFirstStart>0)
            delayFirstStart--;
    }
    
    public void decNumberRepeating(){
        if (repeat>0)
            repeat--;
    }
    
    public void incCurrentTime(){
        if (currentTime>=0)
            currentTime++;
    }
    
    public String toString(){
    StringBuffer str = new StringBuffer();
            str.append(name+": "+delayFirstStart+"|"+operationTime+"|"+repeat);
        return str.toString();
    }
    
    public void addListener(Observer ts){
        listeners.add(ts);
    }
    
    public void removeListener(Observer ts){
        listeners.remove(ts);
        listeners.trimToSize();
    }
    
    public void informListener(){
        for (Observer c: listeners){
            c.changes(name, state);
        }
    }
    
    public void run()
    {
      Thread.currentThread().setName(name);
                
                    
                    synchronized(this) {
                        try
                {
                      state="start...";
                      this.informListener();
                      this.wait();
                      }
                catch(InterruptedException  ie)
                {        
                 state="stop.";
                 informListener();
                    //   LOGGER.error(Thread.currentThread().getName()+" is close");
                }
                    }
                    //Thread.currentThread().sleep(100000);
                
    }
}
