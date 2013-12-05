package com.netc.task3.server.db.logic;

import com.netc.task3.commons.User;
import com.netc.task3.commons.EmptyParameter;
import com.netc.task3.server.db.manager.DBException;
import java.util.List;

/**
 * @since 2013.11.05
 * @author netcracker
 */
public interface DBLogic {

    void insertUser(User client) throws DBException;

    int updateUser(User client) throws DBException;

    void deleteUser(User client) throws DBException;

    void deleteAllUsers() throws DBException;

    List<User> loadUsers() throws DBException, EmptyParameter;

    boolean findClient(User client) throws DBException;

    void close() throws DBException;
}