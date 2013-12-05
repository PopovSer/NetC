package com.netc.task3.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Authentication {
    
}
/*
ObjectInputStream ois = new ObjectInputStream(uSocket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(uSocket.getOutputStream());
                    try {
                        Client user = (Client) ois.readObject();
                        if (dBLogic.findClient(user)) {
                            clientList.add(user);
                            oos.writeObject(clientList);
                            ois.close();
                            oos.close();
                        } else {
                            uSocket.close();
                        }*/