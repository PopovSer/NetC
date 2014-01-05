/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.netc.popov.task4.ui;

import java.io.IOException;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jsoup.nodes.Document;
import ua.netc.popov.task4.Connector;
import ua.netc.popov.task4.entity.HtmlTag;
import ua.netc.popov.task4.exception.WrongDomainException;
import ua.netc.popov.task4.ui.model.AttributesTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.netc.popov.task4.PageParser;
import ua.netc.popov.task4.db.exception.DBException;
import ua.netc.popov.task4.db.logic.DBLogic;
import ua.netc.popov.task4.db.logic.SQLiteDBLogic;
import ua.netc.popov.task4.db.manager.DBManager;
import ua.netc.popov.task4.db.manager.SQLiteDBManager;

/**
 *
 * @author Ser
 */
public class MainController {

    private static MainController instance = null;
    private Document doc = null;
    private String domain = null;
    private static DBLogic dBLogic;
    String tableName;
    
    private static final String DB_CONNECTED_INFO = "DB connected";
    private static final String DB_PATH = "jdbc:sqlite:./db/database.sqlite";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private MainController() {
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public Document connect(String url) throws IOException, WrongDomainException {
        Connector connector = new Connector(url);
        domain = connector.getDomain();
        doc = connector.connect();
        return doc;
    }

    public String getDomain() {
        return domain;
    }

    public AttributesTableModel getInfoAboutNode(DefaultMutableTreeNode node) {
        try {
            Object nodeInfo = node.getUserObject();
            if (nodeInfo instanceof HtmlTag) {
                HtmlTag tag = (HtmlTag) nodeInfo;
                AttributesTableModel tModel = new AttributesTableModel(tag.getAttrs());
                tModel.makeRowsAttributes();
                tModel.addRow(new Object[]{"Text", tag.getText()});
                return tModel;
            }

        } catch (ClassCastException ex) {
            LOGGER.error(ex.toString());
        }
        return null;
    }

    private void connectToDB() {
        dBLogic = null;
        try {
            DBManager dbm = new SQLiteDBManager(DB_PATH);
            LOGGER.info(DB_CONNECTED_INFO);
            dBLogic = new SQLiteDBLogic(dbm);
        } catch (DBException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public void findTag(List<HtmlTag> tagsAttrs) {
        PageParser pp = new PageParser(doc);
        List< List<HtmlTag>> list = pp.findTag(tagsAttrs);
        int size = getSizeBiggestList(list);
        try {
            for (int i = 0; i < size; i++) {
                String[] stringArr = new String[list.size()];
                for (int j = 0; j < list.size(); j++) {
                    try {
                        stringArr[j] = list.get(j).get(i).getText();
                    } catch (IndexOutOfBoundsException e) {
                        stringArr[j] = " - ";
                    }
                }
                dBLogic.insertValue(tableName, stringArr);

            }
        } catch (DBException ex) {
            LOGGER.error(ex.toString());
        }
    }

    public void createNewTable(String tableName, int countField) throws DBException {
        dBLogic.createTable(tableName, countField);
        this.tableName = tableName;
    }

    private static int getSizeBiggestList(List< List<HtmlTag>> list) {
        int size = 0;
        for (List<HtmlTag> l : list) {
            if (size < l.size()) {
                size = l.size();
            }
        }
        return size;
    }
}
