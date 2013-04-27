package jp.vmi.junit.result;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.SystemUtils;
import org.apache.html.dom.HTMLBuilder;
import org.openqa.selenium.io.IOUtils;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributeListImpl;

/**
 * Building HTML result.
 */
@SuppressWarnings("deprecation")
public abstract class HtmlResultBuilder extends HTMLBuilder {

    private final Deque<String> tags = new ArrayDeque<String>();

    protected final String title;

    /**
     * Constructor.
     *
     * @param title title of html.
     */
    public HtmlResultBuilder(String title) {
        super();
        this.title = title;
    }

    /**
     * Start of element.
     *
     * @param tag tag name.
     * @param attrs list of attribute name and value.
     */
    public void start(String tag, String... attrs) {
        AttributeListImpl attrList = null;
        if (attrs.length > 0) {
            attrList = new AttributeListImpl();
            for (int i = 0; i < attrs.length; i += 2) {
                String key = attrs[i];
                String value = attrs[i + 1];
                attrList.addAttribute(key, "CDATA", value);
            }
        }
        try {
            startElement(tag, attrList);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        tags.push(tag);
    }

    /**
     * End of element.
     */
    public void end() {
        String tag = tags.pop();
        try {
            endElement(tag);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Text of element.
     *
     * @param text text.
     */
    public void text(String text) {
        try {
            characters(text);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Table row.
     *
     * @param tds table columns.
     */
    public void tr(Object... tds) {
        start("tr");
        for (Object td : tds) {
            start("td");
            text(td.toString());
            end();
        }
        end();
    }

    /**
     * Generate head part.
     */
    public void head() {
        start("title");
        text(title);
        end();
        start("style", "type", "text/css");
        String css;
        try {
            css = IOUtils.readFully(getClass().getResourceAsStream("result.css"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        text("\n");
        text(css);
        end();
    }

    /**
     * Generate body part.
     */
    public abstract void body();

    /**
     * Build HTML text.
     *
     * @param writer writer for HTML.
     *
     * @return writer itself.
     */
    public Writer build(Writer writer) {
        try {
            startDocument();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        start("html");
        start("head");
        head();
        end();
        start("body");
        body();
        end();
        end();
        try {
            endDocument();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        try {
            writer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            writer.write(SystemUtils.LINE_SEPARATOR);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(getHTMLDocument()), new StreamResult(writer));
            IOUtils.closeQuietly(writer);
            return writer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Build HTML text.
     *
     * @return HTML text.
     */
    public String build() {
        return build(new StringWriter()).toString();
    }
}
