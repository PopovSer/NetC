package com.netc.task3.commons;

public class EmptyParameter extends Exception{

    public EmptyParameter() {
        super();
    }

    public EmptyParameter(String message) {
        super(message);
    }
}
