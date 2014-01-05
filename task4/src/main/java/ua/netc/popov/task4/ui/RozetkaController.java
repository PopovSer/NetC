package ua.netc.popov.task4.ui;

import java.io.IOException;
import ua.netc.popov.task4.exception.ProductNotFoundException;
import ua.netc.popov.task4.exception.WrongDomainException;
import ua.netc.popov.task4.rozetka.RozetkaParser;
import ua.netc.popov.task4.ui.model.RozetkaProductsTableModel;

public class RozetkaController {

    RozetkaParser rozetkaParser;

    public RozetkaController() {
    }

    public void connect(String url) throws IOException, WrongDomainException {
        rozetkaParser = new RozetkaParser(url);
    }

    public RozetkaProductsTableModel getProductsInfo() throws ProductNotFoundException {
        RozetkaProductsTableModel tModel = new RozetkaProductsTableModel(rozetkaParser.searchProduct());
        tModel.makeRowsProducts();
        return tModel;
    }
}
