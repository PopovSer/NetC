package com.netc.task3.server.db.logic;

import com.netc.task3.commons.User;
import com.netc.task3.commons.EmptyParameter;
import com.netc.task3.server.db.manager.DBException;
import com.netc.task3.server.db.manager.DBManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 2013.11.05
 * @author netcracker
 */
public class SQLiteDBLogic implements DBLogic {

    protected static final String ADD_USER_SQL = "INSERT INTO CHAT_USERS VALUES($next_id, ?, ?, ?)";
    protected static final String UPDATE_USER_SQL = "UPDATE CHAT_USERS SET NICKNAME  = ?, EMAIL = ?, PASS =? WHERE NICKNAME  = ?";
    protected static final String DELETE_USER_SQL = "DELETE FROM CHAT_USERS WHERE NICKNAME = ?";
    protected static final String DELETE_ALL_USERS_SQL = "DELETE FROM CHAT_USERS";
    protected static final String LOAD_USERS_SQL = "SELECT NICKNAME, EMAIL, PASS FROM CHAT_USERS";
    protected static final String CLIENT_SEARCH_SQL = "SELECT NICKNAME FROM CHAT_USERS WHERE NICKNAME = ? AND PASS = ?";
    private DBManager dBManager;

    public SQLiteDBLogic(DBManager dBManager) {
        this.dBManager = dBManager;
    }

    @Override
    public int updateUser(User client) throws DBException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = dBManager.prepareStatement(UPDATE_USER_SQL);
            preparedStatement.setString(1, client.getNickname());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.setString(4, client.getOldNickname());

            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public void insertUser(User client) throws DBException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = dBManager.prepareStatement(ADD_USER_SQL);
            preparedStatement.setString(1, client.getNickname());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        } finally {
            close(preparedStatement);
        }

    }

    @Override
    public void deleteUser(User client) throws DBException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = dBManager.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setString(1, client.getNickname());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public void deleteAllUsers() throws DBException {
        dBManager.executeUpdate(DELETE_ALL_USERS_SQL);
    }

    private void close(ResultSet resultSet) throws DBException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                throw new DBException(ex.getMessage(), ex);
            }
        }
    }

    private void close(Statement statement) throws DBException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                throw new DBException(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void close() throws DBException {
        dBManager.close();
    }

    public List<User> loadUsers() throws DBException, EmptyParameter {
        List<User> users = new ArrayList<User>();
        ResultSet resultSet = null;

        try {
            resultSet = dBManager.execSQL(LOAD_USERS_SQL);
            User user;
            while (resultSet.next()) {
                user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        } finally {
            close(resultSet);
        }

        return users;
    }

    public boolean findClient(User client) throws DBException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = dBManager.prepareStatement(CLIENT_SEARCH_SQL);
            preparedStatement.setString(1, client.getNickname());
            preparedStatement.setString(2, client.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.getString(1).isEmpty()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            throw new DBException(ex.getMessage(), ex);
        } finally {
            close(resultSet);
        }
    }
}