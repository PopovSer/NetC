package com.netc.task3.server.db.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DBManager {

    public void executeUpdate(String sql) throws DBException;

    public ResultSet execSQL(String sql) throws DBException;
    
    public PreparedStatement prepareStatement(String sql) throws DBException;

    public void close() throws DBException;
}