package ua.gaponov.posterminal.utils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

/**
 * @author Andriy Gaponov
 */
public class XmlUtils implements AutoCloseable {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    public XmlUtils(InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is);
    }

    public XMLStreamReader getReader() {
        return reader;
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) { // empty
            }
        }
    }

    public boolean startElement(String element, String parent) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (parent != null && event == XMLEvent.END_ELEMENT
                    && parent.equals(reader.getLocalName())) {
                return false;
            }
            if (event == XMLEvent.START_ELEMENT
                    && element.equals(reader.getLocalName())) {
                return true;
            }
        }
        return false;
    }

    public String getStringAttribute(String name) throws XMLStreamException {
        return reader.getAttributeValue(null, name);
    }

    public String getText() throws XMLStreamException {
        return reader.getElementText();
    }

    public boolean getBooleanAttribute(String name) throws XMLStreamException {
        if ("1".equals(getStringAttribute(name))) {
            return true;
        } else {
            return false;
        }
    }

    public double getDoubleAttribute(String name) throws XMLStreamException {
        return Double.parseDouble(getStringAttribute(name));
    }

    public int getIntegerAttribute(String name) throws XMLStreamException {
        return Integer.parseInt(getStringAttribute(name));
    }
}
