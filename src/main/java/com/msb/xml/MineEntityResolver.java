package com.msb.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * description  MineEntityResolver <BR>
 * <p>
 * author: zhao.song
 * date: created in 14:27  2021/11/9
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class MineEntityResolver implements EntityResolver {
    /**
     * Allow the application to resolve external entities.
     *
     * <p>The parser will call this method before opening any external
     * entity except the top-level document entity.  Such entities include
     * the external DTD subset and external parameter entities referenced
     * within the DTD (in either case, only if the parser reads external
     * parameter entities), and external general entities referenced
     * within the document element (if the parser reads external general
     * entities).  The application may request that the parser locate
     * the entity itself, that it use an alternative URI, or that it
     * use data provided by the application (as a character or byte
     * input stream).</p>
     *
     * <p>Application writers can use this method to redirect external
     * system identifiers to secure and/or local URIs, to look up
     * public identifiers in a catalogue, or to read an entity from a
     * database or other input source (including, for example, a dialog
     * box).  Neither XML nor SAX specifies a preferred policy for using
     * public or system IDs to resolve resources.  However, SAX specifies
     * how to interpret any InputSource returned by this method, and that
     * if none is returned, then the system ID will be dereferenced as
     * a URL.  </p>
     *
     * <p>If the system identifier is a URL, the SAX parser must
     * resolve it fully before reporting it to the application.</p>
     *
     * @param publicId The public identifier of the external entity
     *                 being referenced, or null if none was supplied.
     * @param systemId The system identifier of the external entity
     *                 being referenced.
     * @return An InputSource object describing the new input source,
     * or null to request that the parser open a regular
     * URI connection to the system identifier.
     * @throws SAXException Any SAX exception, possibly
     *                      wrapping another exception.
     * @throws IOException  A Java-specific IO exception,
     *                      possibly the result of creating a new InputStream
     *                      or Reader for the InputSource.
     * @see InputSource
     */
    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        return null;
    }
}
