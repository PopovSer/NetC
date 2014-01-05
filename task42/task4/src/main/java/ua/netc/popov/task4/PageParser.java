package ua.netc.popov.task4;

import ua.netc.popov.task4.entity.HtmlTag;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParser {

    private Document doc;

    public PageParser(Document doc) {
        this.doc = doc;
    }

    public List< List<HtmlTag> > findTag(List<HtmlTag> tagsToSearch) {
        List< List<HtmlTag> > elements = new ArrayList< List<HtmlTag> >();

        for (HtmlTag tag : tagsToSearch) {
            Map<String, String> attrs = tag.getAttrs();
            String regex = "";
            for (Map.Entry<String, String> entry : attrs.entrySet()) {
                regex += "[" + entry.getKey() + "=" + entry.getValue() + "]";
            }

            Elements el = doc.select(tag.getTagName()+regex);
            List<HtmlTag> tagList = new ArrayList<HtmlTag>();
            for (Element currentElem : el) {
                tagList.add(new HtmlTag(currentElem.tagName(), currentElem.text(), attrs,0));
            }

            elements.add(tagList);
        }
        return elements;
    }
}
