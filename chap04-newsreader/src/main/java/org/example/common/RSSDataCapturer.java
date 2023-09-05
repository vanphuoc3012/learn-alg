package org.example.common;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RSSDataCapturer extends DefaultHandler {
    private final int IN_TITLE = 1;
    private final int IN_LINK = 2;
    private final int IN_DESCRIPTION = 3;
    private final int IN_PUBDATE = 4;
    private final int IN_GUID = 5;
    private int status = 0;
    private CommonInformationItem item;
    private List<CommonInformationItem> list;
    private SimpleDateFormat formatter;
    private StringBuffer buffer;
    private String name;

    public RSSDataCapturer(String name) {
        formatter = new SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss zzz", Locale.ENGLISH);
        buffer = new StringBuffer();
        this.name = name;
    }

    public List<CommonInformationItem> load(String resource) {
        list = new ArrayList<>();

        SAXParserFactory spf = SAXParserFactory.newInstance();

        try {
            SAXParser sp = spf.newSAXParser();
            sp.parse(resource, this);
        } catch (ParserConfigurationException e) {
            System.err.printf("%s\n", resource);
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.printf("%s\n", resource);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.printf("%s\n", resource);
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        /* System.out.println("****START ELEMENT****");
         System.out.println("Uri: " + uri);
         System.out.println("Local Name: " + localName);
         System.out.println("QName: " + qName);
         System.out.println("****START ELEMENT****"); */

        if ((item != null) && (qName.equalsIgnoreCase("title"))) {
            status = IN_TITLE;
            return;
        }
        if ((item != null) && (qName.equalsIgnoreCase("link"))) {
            status = IN_LINK;
            return;
        }
        if ((item != null) && (qName.equalsIgnoreCase("description"))) {
            status = IN_DESCRIPTION;
            return;
        }
        if ((item != null) && (qName.equalsIgnoreCase("pubdate"))) {
            status = IN_PUBDATE;
            return;
        }
        if ((item != null) && (qName.equalsIgnoreCase("guid"))) {
            status = IN_GUID;
            return;
        }
        if (qName.equalsIgnoreCase("item")) {
            item = new CommonInformationItem();
            item.setSource(name);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
       /* System.out.println("****END ELEMENT****");
        System.out.println("Uri: " + uri);
        System.out.println("Local Name: " + localName);
        System.out.println("QName: " + qName);
        System.out.println("****END ELEMENT****"); */
        if ((item != null) && (qName.equalsIgnoreCase("title"))) {
            item.setTitle(buffer.toString());
            status = 0;
        }
        if ((item != null) && (qName.equalsIgnoreCase("link"))) {
            item.setLink(buffer.toString());
            status = 0;
        }
        if ((item != null) && (qName.equalsIgnoreCase("description"))) {
            item.addDescripcion(buffer.toString());
            status = 0;
        }
        if ((item != null) && (qName.equalsIgnoreCase("pubdate"))) {
            item.setTxtDate(buffer.toString());
            try {
                item.setDate(formatter.parse(buffer.toString()));
            } catch (ParseException e) {
                item.setDate(new Date());
            }
            status = 0;
        }
        if ((item != null) && (qName.equalsIgnoreCase("guid"))) {
            item.setId(buffer.toString());
            status = 0;
        }
        if (qName.equalsIgnoreCase("item")) {
            if (item.getId() == null) {
                item.setId("" + item.getDescription().hashCode());
            }
            list.add(item);
            item = null;
        }
        buffer = new StringBuffer();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//        System.out.println("DEBUG: " + methodName + ": Text to: " + new String(ch, start, length));
        String txt = new String(ch, start, length);
        buffer.append(txt.trim());
    }
}
