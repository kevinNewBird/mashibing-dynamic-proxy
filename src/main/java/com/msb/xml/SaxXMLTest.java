package com.msb.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * description  SaxXMLTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 14:23  2021/11/9
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class SaxXMLTest {

    /**
     * JAXP attribute used to configure the schema language for validation.
     */
    private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    /**
     * JAXP attribute value indicating the XSD schema language.
     */
    private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        // 创建xml解析工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);

        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        docBuilder.setEntityResolver(new MineEntityResolver());
//        docBuilder.setErrorHandler(null);
        Document document = docBuilder.parse(SaxXMLTest.class.getClassLoader().getResourceAsStream("saxxml.xml"));
    }
}
