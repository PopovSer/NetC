package ua.netc.popov.task4.ui.model;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import ua.netc.popov.task4.rozetka.entity.Product;

public class RozetkaProductsTableModel extends DefaultTableModel {

    private List<Product> products;
    private static final String COL_NAME_ID = "ID";
    private static final String COL_NAME_TITLE = "Title";
    private static final String COL_NAME_CHARACT = "Characteristics";

    public RozetkaProductsTableModel(List<Product> products) {
        this.products = products;
    }

    public void makeRowsProducts() {
        this.addColumn(COL_NAME_ID);
        this.addColumn(COL_NAME_TITLE);
        this.addColumn(COL_NAME_CHARACT);
        for (Product product : products) {
            this.addRow(new Object[]{product.getId(), product.getTitle(), product.getAttrs()});
        }
        
    }
}
