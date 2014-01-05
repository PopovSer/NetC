package ua.netc.popov.task4.db.manager;

import ua.netc.popov.task4.db.exception.DBException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DBManager {

    public void executeUpdate(String sql) throws DBException;

    public ResultSet execSQL(String sql) throws DBException;
    
    public PreparedStatement prepareStatement(String sql) throws DBException;

    public void close() throws DBException;
}