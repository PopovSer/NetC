package ua.netc.popov.task4.ui.model;

import java.util.List;
import javax.swing.DefaultListModel;
import ua.netc.popov.task4.entity.HtmlTag;

public class HtmlTagListModel extends DefaultListModel {

    private List<HtmlTag> list;

    public HtmlTagListModel(List<HtmlTag> list) {
        this.list = list;
    }

    public void makeElemFromTag() {
        for (HtmlTag th : list) {
            this.addElement(th.getTagName() + " [" + th.attrsToString() + "]");
        }

    }
}
