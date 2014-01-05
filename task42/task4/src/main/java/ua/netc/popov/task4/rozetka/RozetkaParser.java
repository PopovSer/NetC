package ua.netc.popov.task4.rozetka;

import ua.netc.popov.task4.rozetka.entity.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.netc.popov.task4.Connector;
import ua.netc.popov.task4.exception.ProductNotFoundException;
import ua.netc.popov.task4.exception.WrongDomainException;
import ua.netc.popov.task4.ui.RozetkaController;

public class RozetkaParser {

    private String url;
    private Document doc;
    private Element currentElem;
    private List<Product> productList;
    
    private static final String EXC_PRODUCT_NOT_FOUND = "Products not found";
    private static final String SELECT_DIV_CLASS_TITLE = "div[class=title]";
    private static final String SELECT_DIV_CLASS_GOODS_LIST = "div[class=goods list]";
    private static final String SELECT_TABEL_CLASS_ITEM = "table[class~=^item]";
    private static final String SELECT_SPAN_CLASS_CODE = "span[class=code]";
    private static final String SELECT_UL_CLASS_SH_DETAIL = "ul[class=short-detail]";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RozetkaController.class);


    public RozetkaParser(String url) throws WrongDomainException, IOException {
        Connector con = new Connector(url);
        doc = con.connect();
    }

    public List<Product> searchProduct() throws ProductNotFoundException {
        Elements elems = doc.select(SELECT_DIV_CLASS_GOODS_LIST).select(SELECT_TABEL_CLASS_ITEM);
        if (!elems.isEmpty()) {
            productList = new ArrayList<Product>();
            for (int i = 0; i < elems.size(); i++) {
                currentElem = elems.get(i);
                productList.add(new Product(searchProductId(), searchProductTitle(), searchProductAttrs()));
            }
            return productList;
        }
        throw new ProductNotFoundException(EXC_PRODUCT_NOT_FOUND);
    }

    private String searchProductTitle() {
        return currentElem.select(SELECT_DIV_CLASS_TITLE).select("a").first().ownText();
    }

    private String searchProductId() {
        return currentElem.select(SELECT_DIV_CLASS_TITLE).select(SELECT_SPAN_CLASS_CODE).first().ownText();
    }

    private List<String> searchProductAttrs() {
        List<String> attrs = new ArrayList<String>();
        for (Element el : currentElem.select(SELECT_UL_CLASS_SH_DETAIL).select("li")) {
            attrs.add(el.text());
        }
        return attrs;
    }
}
