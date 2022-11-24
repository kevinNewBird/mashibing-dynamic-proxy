package com.msb.xml.line;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

/**
 * description  PostionalXmlParser <BR>
 * <p>
 * author: zhao.song
 * date: created in 12:48  2022/9/19
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class PositionalXmlParser {

    final static String LINE_NUMBER_KEY_NAME = "lineNumber";

    public static Document readXML(final InputStream is) throws IOException, SAXException {
        final Document doc;

        SAXParser parser;

        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();

            parser = factory.newSAXParser();

            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            doc = docBuilder.newDocument();

        } catch (final ParserConfigurationException | org.xml.sax.SAXException e) {
            throw new RuntimeException("Can't create SAX parser / DOM builder.", e);

        }

        final Stack elementStack = new Stack();

        final StringBuilder textBuffer = new StringBuilder();

        final DefaultHandler handler = new DefaultHandler() {
            private Locator locator;

            @Override
            public void setDocumentLocator(final Locator locator) {
                this.locator = locator; // Save the locator, so that it can be used later for line tracking when traversing nodes.

            }

            @Override
            public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)

                    throws SAXException {
                addTextIfNeeded();

                final Element el = doc.createElement(qName);

                for (int i = 0; i < attributes.getLength(); i++) {
                    el.setAttribute(attributes.getQName(i), attributes.getValue(i));

                }

                el.setUserData(LINE_NUMBER_KEY_NAME, String.valueOf(this.locator.getLineNumber()), null);

                elementStack.push(el);

            }

            @Override

            public void endElement(final String uri, final String localName, final String qName) {
                addTextIfNeeded();

                final Element closedEl = (Element) elementStack.pop();

                if (elementStack.isEmpty()) { // Is this the root element?

                    doc.appendChild(closedEl);

                } else {
                    final Element parentEl = (Element) elementStack.peek();

                    parentEl.appendChild(closedEl);

                }

            }

            @Override

            public void characters(final char ch[], final int start, final int length) throws SAXException {
                textBuffer.append(ch, start, length);

            }

// Outputs text accumulated under the current node

            private void addTextIfNeeded() {
                if (textBuffer.length() > 0) {
                    final Element el = (Element) elementStack.peek();

                    final Node textNode = doc.createTextNode(textBuffer.toString());

                    el.appendChild(textNode);

                    textBuffer.delete(0, textBuffer.length());

                }

            }

        };

        parser.parse(is, handler);

        return doc;

    }
}
