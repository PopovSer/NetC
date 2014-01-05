package ua.netc.popov.task4.ui.model;

import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.netc.popov.task4.entity.HtmlTag;

public class HtmlTagTreeModel extends DefaultMutableTreeNode {

    private Document doc;

    public HtmlTagTreeModel(Document doc, String rootNodeName) {
        super(rootNodeName);
        this.doc = doc;
    }

    public void makeTreeOfHtml() {
        Elements el = doc.children();
        List<Element> e = el.subList(0, el.size());
        for (int i = 0; i < e.size(); i++) {
            model(e.get(i), this, i);
        }
    }

    private void model(Element e, DefaultMutableTreeNode node, int level) {
        int i = 0;
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new HtmlTag(e.tagName(), e.ownText(), e.attributes(), level)); //e.tagName() + "[" + e.attr("id") + "]"
        node.add(newNode);
        do {
            if (e.children().isEmpty()) {
                break;
            }
            model(e.children().get(i), newNode, level+1);
            i++;
        } while (i < e.children().size());

    }
}
