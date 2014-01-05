package ua.netc.popov.task4.ui.model;

import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class AttributesTableModel extends DefaultTableModel {

    private Map<String, String> attrList;
    private final String COL_NAME_ATTR = "Attribute";
    private final String COL_NAME_VALUE = "Value";

    public AttributesTableModel(Map<String, String> attrs) {
        this.attrList = attrs;
    }

    public void makeRowsAttributes() {
        this.addColumn(COL_NAME_ATTR);
        this.addColumn(COL_NAME_VALUE);
        for (Map.Entry<String, String> a : attrList.entrySet()) {
            this.addRow(new Object[]{a.getKey(), a.getValue()});
        }
    }
}
