package com.msb.xml.line;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * description  XMLTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 12:55  2022/9/19
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class XMLTest {

    public static void main(String[] args) throws IOException, SAXException {
        String xmlString = "\n"

                + " \n"

                + " Hello World!\n"

                + " \n"

                + "";

        InputStream is = new ByteArrayInputStream(xmlString.getBytes());

        Document doc = PositionalXmlParser.readXML(is);

        is.close();

        Node node = doc.getElementsByTagName("moo").item(0);

        System.out.println("Line number: " + node.getUserData("lineNumber"));
    }
}
