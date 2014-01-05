package ua.netc.popov.task4.entity;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;

public class HtmlTag {

    private String tagName;
    private String text;
    private Map<String, String> attrs;
    private int level;

    public HtmlTag(String tagName, String Text, Attributes attrs, int level) {
        this.tagName = tagName;
        this.text = Text;
        attrs(attrs);
        this.level = level;
    }

    public HtmlTag(String tagName, String text, Map<String, String> attrs, int level) {
        this.tagName = tagName;
        this.text = text;
        this.attrs = attrs;
        this.level = level;
    }

    public HtmlTag(HtmlTag tag) {
        this.tagName = tag.getTagName();
        this.text = tag.getText();
        this.attrs = tag.getAttrs();
        this.level = tag.getLevel();
    }

    private void attrs(Attributes attributes) {
        attrs = new HashMap<String, String>(attributes.size());
        for (Attribute a : attributes) {
            attrs.put(a.getKey(), a.getValue());
        }
    }

    public HtmlTag() {
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public String getTagName() {
        return tagName;
    }

    public String getText() {
        return text;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return tagName+"["+ level + "]";
    }

    public String attrsToString() {
        String s = "";
        for (Map.Entry<String, String> entry : attrs.entrySet()) {
            s += entry.getKey() + "=\"" + entry.getValue() + "\" ";
        }
        return s;
    }
}
