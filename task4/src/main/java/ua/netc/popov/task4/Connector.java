/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.netc.popov.task4;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.netc.popov.task4.exception.WrongDomainException;

/**
 *
 * @author Ser
 */
public class Connector {

    private String url;
    private String domain;
    private Document doc;
    
    private static final String VALID_DOMAIN_REGEX = "^(https?://)?([a-zA-Z0-9\\.\\-\\?=_]+)/?";
    private static final String EXC_WRONG_DOMAIN = "Wrong domain name";
    private static final String EXC_URL_EMPTY = "URL is empty";
    private static final String EXC_CAN_NOT_CONNECT = "Can't connect to server";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Connector.class);

    public Connector(String url) throws WrongDomainException {
        this.url = url;
        if (url.isEmpty()) {
            throw new WrongDomainException(EXC_URL_EMPTY);
        }
        domain();
    }

    private void domain() throws WrongDomainException {
        domain = new String();
        Pattern pattern = Pattern.compile(VALID_DOMAIN_REGEX);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String s1 = matcher.group(1);
            String s2 = matcher.group(2);
            if (matcher.group(1).isEmpty()) {
                url = "http://" + url;
            }
            domain = matcher.group(1) + matcher.group(2);
        } else {
            throw new WrongDomainException(domain + " is wrong");
        }
    }

    public String getUrl() {
        return url;
    }

    public String getDomain() {
        return domain;
    }

    public Document connect() throws IOException, WrongDomainException {
        try {
            doc = Jsoup.connect(url).get();
            return doc;
        } catch (IllegalArgumentException e) {
            throw new WrongDomainException(EXC_WRONG_DOMAIN);
        } catch (IOException e) {
            throw new IOException(EXC_CAN_NOT_CONNECT);
        }
    }
}
