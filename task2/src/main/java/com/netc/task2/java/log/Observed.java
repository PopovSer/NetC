/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netc.task2.java.log;

/**
 *
 * @author Ser
 */
public interface Observed {
    void addListener(Observer ts);
    void removeListener(Observer ts);
    void informListener();
}
