package ua.netc.popov.task4.db.logic;

import java.sql.SQLException;
import java.sql.Statement;
import ua.netc.popov.task4.db.exception.DBException;
import ua.netc.popov.task4.db.manager.DBManager;
import ua.netc.popov.task4.db.exception.TableNameIsEmptyException;

public class SQLiteDBLogic implements DBLogic {

    private DBManager dBManager;
    private String tableName = "";

    public SQLiteDBLogic(DBManager dBManager) {
        this.dBManager = dBManager;
    }

    public SQLiteDBLogic() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void createTable(String tableName, int count) throws DBException {
        String createTableSql = "CREATE TABLE " + tableName + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE";
        for (int i = 0; i < count; i++) {
            createTableSql += ", Field" + i + " VARCHAR(500)";
        }
        createTableSql += " );";
        dBManager.executeUpdate(createTableSql);
    }

    public void insertValue(String tableName, String... values) throws DBException {
        String insertRecordSQL = "INSERT INTO " + tableName + " VALUES($next_id";
        for (String s : values) {
            insertRecordSQL += ", \"" + s + "\"";
        }
        insertRecordSQL += " );";
        dBManager.executeUpdate(insertRecordSQL);
    }

    public void insertValue(String... values) throws DBException, TableNameIsEmptyException {
        if (this.tableName.equals("")) {
            throw new TableNameIsEmptyException("Name of the table empty");
        } else {
            String insertRecordSQL = "INSERT INTO " + this.tableName + " VALUES($next_id";
            for (String s : values) {
                insertRecordSQL += ", \"" + s + "\"";
            }
            dBManager.executeUpdate(insertRecordSQL);
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
}