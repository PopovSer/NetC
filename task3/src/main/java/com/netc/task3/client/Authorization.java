/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netc.task3.client;

import com.netc.task3.commons.User;
import com.netc.task3.commons.EmptyParameter;
import com.netc.task3.ui.ClientUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.nio.ch.SocketAdaptor;

/**
 *
 * @author Ser
 */
public class Authorization {

    private User user;
    private Socket userSocket;
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public Authorization(String userName, String userPass) throws EmptyParameter {
        user = new User(userName, userPass);
    }

    public Authorization(User user) {
        this.user = new User(user);
    }

    public Socket getUserSocket() {
        return userSocket;
    }
    

    public List<User> connectToServer() {
        try {
            Socket userSocket = new Socket(InetAddress.getByName("localhost"), 9098);
            this.userSocket = userSocket;
            ObjectOutputStream oos = new ObjectOutputStream(userSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(userSocket.getInputStream());
            oos.writeObject(user);
            List<User> list = (List) ois.readObject();
            return list;
        } catch (SocketException ex) {
            LOGGER.error(ex.toString());
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex.toString());
        } catch (UnknownHostException ex) {
            LOGGER.error(ex.toString());
        } catch (IOException ex) {
            LOGGER.error(ex.toString());
        }
        return null;
    }
}
