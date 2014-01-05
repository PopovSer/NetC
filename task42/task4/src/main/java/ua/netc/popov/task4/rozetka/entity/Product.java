package ua.netc.popov.task4.rozetka.entity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product {

    private int id;
    private String title;
    private List<String> attrs;

    public Product() {
    }

    public Product(int id, String title, List<String> attrs) {
        this.id = id;
        this.title = title;
        this.attrs = attrs;
    }

    public Product(String id, String title, List<String> attrs) {
        this.id = strToInt(id);
        this.title = title;
        this.attrs = attrs;
    }

    public List<String> getAttrs() {
        return attrs;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    private static int strToInt(String str) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(str);
        String res = "";
        while (m.find()) {
            if (!m.group().equals("")) {
                res += m.group();
            }
        }
        return Integer.parseInt(res);
    }
}
