package com.netc.task3.server;

import com.netc.task3.commons.User;
import com.netc.task3.server.db.logic.DBLogic;
import com.netc.task3.server.db.logic.SQLiteDBLogic;
import com.netc.task3.server.db.manager.DBException;
import com.netc.task3.server.db.manager.DBManager;
import com.netc.task3.server.db.manager.SQLiteDBManager;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static List<User> clientList = new ArrayList<User>();
    private Socket userSocket;
    private static DBLogic dBLogic;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Server(Socket userSocket) throws IOException {
        this.userSocket = userSocket;
        ois = new ObjectInputStream(userSocket.getInputStream());
        oos = new ObjectOutputStream(userSocket.getOutputStream());
    }

    public static void main(String[] args) {
        LOGGER.info("Server start");
        dBLogic = null;
        try {
            DBManager dbm = new SQLiteDBManager("jdbc:sqlite:./db/chat_users.sqlite");
            LOGGER.info("DB connected");
            dBLogic = new SQLiteDBLogic(dbm);
            try {
                ServerSocket serverSocket = new ServerSocket(9098);
                while (true) {
                    Socket uSocket = serverSocket.accept();
                    new Server(uSocket).start();
                }
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
            } finally {
                dbm.close();
            }
        } catch (DBException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } finally {
            try {
                dBLogic.close();
            } catch (DBException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void run() {
        String message;
        try {
            User user = (User) ois.readObject();
            if (authentication(user)) {

                LOGGER.info(user.getNickname()+" connected");
                clientList.add(user);
                oos.writeObject(clientList);
            } else {
                userSocket.close();
                ois.close();
                oos.close();
                return;
            }
            
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
            while(true){
                message = reader.readLine();
                for (User client : clientList) {
                    writer.write("\""+client.getNickname()+"\": "+message);
                    writer.flush();
                   
                }
            }
            
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            //LOGGER.info();
            return;
//            if (user!=null)
//            clientList.remove(user);
            
        }

    }

    private boolean authentication(User user) {
        try {
            if (dBLogic.findClient(user)) {
                return true;
            } else {
                return false;
            }
        } catch (DBException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return false;
    }
}
