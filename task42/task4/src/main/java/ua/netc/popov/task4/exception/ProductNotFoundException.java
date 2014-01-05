/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.netc.popov.task4.exception;

/**
 *
 * @author Ser
 */
public class ProductNotFoundException extends NullPointerException {

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String s) {
        super(s);
    }
    
    
}
