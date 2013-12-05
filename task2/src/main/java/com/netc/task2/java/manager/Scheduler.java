package com.netc.task2.java.manager;

import com.netc.task2.java.log.Observed;
import com.netc.task2.java.log.Observer;
import com.netc.task2.java.entity.Task;
import java.util.ArrayList;
import java.lang.System;
import java.util.ConcurrentModificationException;
import javax.swing.JTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scheduler  extends  Thread implements Observed
{
    private boolean flag = true;
    private String state = "off"; 
    private ArrayList<Observer> listeners = new ArrayList<Observer>();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);    
    
    public Scheduler() {
        this.setName("Scheduler");
    }
    
    public String getScedState(){
        return state;
    }
    
    public void startTask(Task task){
        if (flag)
        if (task.getRunCount()>0)
        {   
            task.decNumberRepeating();
            task.setCurrentTime(0);
            task.thread = new Thread(task);
            task.thread.start();
        }
        else closeTask(task);
    }
    
    public void closeTask(Task task){
        SAXPars.list.remove(task); 
        SAXPars.list.trimToSize();
    }
    
    public void run()
    {
       LOGGER.info("Scheduler is running");
       state="on";
       informListener();
        while(true)
        {
            if (SAXPars.list.isEmpty()){
                LOGGER.info("Scheduler off.");
                state = "off";
                informListener();
                return;
            } 
                            
            try {
            for (Task task: SAXPars.list)
            {
                if (task.thread == null)
                {
                    if (task.getRunCount()==0) 
                    { 
                        closeTask(task);
                        continue;
                    }
                    if (task.getDelayFirstStart()==0) 
                    {
                        startTask(task);
                        continue;
                    }
                    else                 
                    if (task.getDelayFirstStart()>0) 
                        continue;
                }
                else
                    {
                if (task.thread.isAlive())
                {
                    
                    if (task.getOperationTime()>60)
                    {
                        flag=false;
                        if (task.getCurrentTime()>=task.getOperationTime())
                        {
                            task.thread.interrupt();
                            flag=true;
                            startTask(task);
                            }
                        else 
                            task.incCurrentTime();
                    }
                    else
                    {
                        if (task.getCurrentTime()>=task.getOperationTime())
                        {
                            task.thread.interrupt();
                            startTask(task);
                            continue;
                        }
                        else 
                            task.incCurrentTime();
                    }
                    
                }
                else
                    if (task.getDelayFirstStart()==0)
                        startTask(task);
                    }
            }
            }
            catch(ConcurrentModificationException e){ }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                continue;
            }
            for (Task task: SAXPars.list)
            {
                if (task.getDelayFirstStart()>0)
                    task.decDelayFirstStart();
            }  
        }
    }

    @Override
    public void addListener(Observer ts) {
        listeners.add(ts);
    }

    @Override
    public void removeListener(Observer ts) {
        listeners.remove(ts);
        listeners.trimToSize();
    }

    @Override
    public void informListener() {
        for (Observer c: listeners){
            c.changes(this.getName(), state);
        }
    }
    
}
