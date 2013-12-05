/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netc.task3.server.db.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @since 2013.11.05
 * @author netcracker
 */
public class SQLiteDBManager implements DBManager {

    private static final String DRIVER = "org.sqlite.JDBC";

    private Connection connection = null;
    private Statement statement = null;

    public SQLiteDBManager(String url) throws DBException {
        try {
            Class.forName(DRIVER).newInstance();
            createConnection(url); //"jdbc:sqlite:./db/chat_users.sqlite"
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Class " + DRIVER + " not found", ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException("Driver " + DRIVER + " class InstantiationException.", ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("IllegalAccess for " + DRIVER + " class.", ex);
        }
    }

    private void createConnection(String url) throws DBException {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        }
    }

    public void executeUpdate(String sql) throws DBException {
        try {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        }
    }

    public ResultSet execSQL(String sql) throws DBException {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        }
    }

    public synchronized void close() throws DBException {
        closeConnection();
    }

    private void closeConnection() throws DBException {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        }
    }

    public PreparedStatement prepareStatement(String sql) throws DBException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        }
    }
}
