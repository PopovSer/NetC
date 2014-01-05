package ua.netc.popov.task4.db.logic;

import ua.netc.popov.task4.db.exception.DBException;
import ua.netc.popov.task4.db.exception.TableNameIsEmptyException;

public interface DBLogic {

    public void insertValue(String tableName, String... values) throws DBException;

    public void insertValue(String... values) throws DBException, TableNameIsEmptyException;

    public void createTable(String tableName, int countFields) throws DBException;

    public void close() throws DBException;
}