package jp.vmi.html;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.SystemUtils;
import org.apache.html.dom.HTMLDocumentImpl;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLDocument;

@SuppressWarnings("javadoc")
public class SimpleHtmlBuilder {

    abstract public static class For<R, E> {

        abstract public R apply(E elem);

        public boolean isAcceptable(R result) {
            return result != null;
        }

        public List<R> each(E... elems) {
            List<R> results = new ArrayList<R>(elems.length);
            for (E elem : elems) {
                R result = apply(elem);
                if (isAcceptable(result))
                    results.add(result);
            }
            return results;
        }

        public List<R> each(Collection<E> elems) {
            List<R> results = new ArrayList<R>(elems.size());
            for (E elem : elems) {
                R result = apply(elem);
                if (isAcceptable(result))
                    results.add(result);
            }
            return results;
        }
    }

    public static interface Elem {
        void build(HTMLDocument document, Node parent);
    }

    public static class Tag implements Elem {
        public final String name;
        public final List<Attr> attrList = new ArrayList<Attr>();
        public final List<Elem> childList = new ArrayList<Elem>();

        public Tag(String name, Object... elems) {
            this.name = name;
            add(elems);
        }

        public Tag add(Object... elems) {
            for (Object elem : elems) {
                if (elem == null) {
                    // ignore
                } else if (elem instanceof Attr) {
                    attrList.add((Attr) elem);
                } else if (elem instanceof Object[]) {
                    add((Object[]) elem);
                } else if (elem instanceof List) {
                    for (Object elem2 : (List<?>) elem)
                        add(elem2);
                } else if (elem instanceof Elem) {
                    childList.add((Elem) elem);
                } else {
                    childList.add(new Text(elem.toString()));
                }
            }
            return this;
        }

        @Override
        public void build(HTMLDocument document, Node parent) {
            Element elem = document.createElement(name);
            for (Attr attr : attrList)
                elem.setAttribute(attr.name, attr.value);
            for (Elem child : childList)
                child.build(document, elem);
            parent.appendChild(elem);
        }
    }

    public static class Text implements Elem {
        public final String text;

        public Text(String text) {
            this.text = text;
        }

        @Override
        public void build(HTMLDocument document, Node parent) {
            Node node = document.createTextNode(text);
            parent.appendChild(node);
        }
    }

    public static class Attr {
        public final String name;
        public final String value;

        public Attr(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    private final HTMLDocument document = new HTMLDocumentImpl();
    protected final String title;

    public SimpleHtmlBuilder(String title) {
        this.title = title;
    }

    public <W extends Writer> W build(W writer) {
        Tag html = html();
        Tag head = head();
        Tag body = body();
        html(html);
        head(head);
        body(body);
        html.add(head, body);
        html.build(document, document);
        try {
            writer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            writer.write(SystemUtils.LINE_SEPARATOR);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public String build() {
        return build(new StringWriter()).toString();
    }

    public void html(Tag html) {
    }

    public void head(Tag head) {
    }

    public void body(Tag body) {
    }

    public Text __text(String text) {
        return new Text(text);
    }

    public Object[] __elems(Object... elems) {
        return elems;
    }

    public Tag createTag(String name, Object... elems) {
        return new Tag(name, elems);
    }

    public Attr createAttr(String name, boolean value) {
        return value ? new Attr(name, name) : null;
    }

    public Attr createAttr(String name, String value) {
        return new Attr(name, value);
    }

    // BEGINNING OF AUTO GENERATED METHODS.

    /**
     * Tag of "a".
     *
     * @param elems array of elements.
     * @return "a" Tag object.
     */
    public Tag a(Object... elems) {
        return createTag("a", elems);
    }

    /**
     * Tag of "abbr".
     *
     * @param elems array of elements.
     * @return "abbr" Tag object.
     */
    public Tag abbr(Object... elems) {
        return createTag("abbr", elems);
    }

    /**
     * Tag of "acronym".
     *
     * @param elems array of elements.
     * @return "acronym" Tag object.
     */
    public Tag acronym(Object... elems) {
        return createTag("acronym", elems);
    }

    /**
     * Tag of "address".
     *
     * @param elems array of elements.
     * @return "address" Tag object.
     */
    public Tag address(Object... elems) {
        return createTag("address", elems);
    }

    /**
     * Tag of "applet".
     *
     * @param elems array of elements.
     * @return "applet" Tag object.
     */
    public Tag applet(Object... elems) {
        return createTag("applet", elems);
    }

    /**
     * Tag of "area".
     *
     * @param elems array of elements.
     * @return "area" Tag object.
     */
    public Tag area(Object... elems) {
        return createTag("area", elems);
    }

    /**
     * Tag of "b".
     *
     * @param elems array of elements.
     * @return "b" Tag object.
     */
    public Tag b(Object... elems) {
        return createTag("b", elems);
    }

    /**
     * Tag of "base".
     *
     * @param elems array of elements.
     * @return "base" Tag object.
     */
    public Tag base(Object... elems) {
        return createTag("base", elems);
    }

    /**
     * Tag of "basefont".
     *
     * @param elems array of elements.
     * @return "basefont" Tag object.
     */
    public Tag basefont(Object... elems) {
        return createTag("basefont", elems);
    }

    /**
     * Tag of "bdo".
     *
     * @param elems array of elements.
     * @return "bdo" Tag object.
     */
    public Tag bdo(Object... elems) {
        return createTag("bdo", elems);
    }

    /**
     * Tag of "big".
     *
     * @param elems array of elements.
     * @return "big" Tag object.
     */
    public Tag big(Object... elems) {
        return createTag("big", elems);
    }

    /**
     * Tag of "blockquote".
     *
     * @param elems array of elements.
     * @return "blockquote" Tag object.
     */
    public Tag blockquote(Object... elems) {
        return createTag("blockquote", elems);
    }

    /**
     * Tag of "body".
     *
     * @param elems array of elements.
     * @return "body" Tag object.
     */
    public Tag body(Object... elems) {
        return createTag("body", elems);
    }

    /**
     * Tag of "br".
     *
     * @param elems array of elements.
     * @return "br" Tag object.
     */
    public Tag br(Object... elems) {
        return createTag("br", elems);
    }

    /**
     * Tag of "button".
     *
     * @param elems array of elements.
     * @return "button" Tag object.
     */
    public Tag button(Object... elems) {
        return createTag("button", elems);
    }

    /**
     * Tag of "caption".
     *
     * @param elems array of elements.
     * @return "caption" Tag object.
     */
    public Tag caption(Object... elems) {
        return createTag("caption", elems);
    }

    /**
     * Tag of "center".
     *
     * @param elems array of elements.
     * @return "center" Tag object.
     */
    public Tag center(Object... elems) {
        return createTag("center", elems);
    }

    /**
     * Tag of "cite".
     *
     * @param elems array of elements.
     * @return "cite" Tag object.
     */
    public Tag cite(Object... elems) {
        return createTag("cite", elems);
    }

    /**
     * Tag of "code".
     *
     * @param elems array of elements.
     * @return "code" Tag object.
     */
    public Tag code(Object... elems) {
        return createTag("code", elems);
    }

    /**
     * Tag of "col".
     *
     * @param elems array of elements.
     * @return "col" Tag object.
     */
    public Tag col(Object... elems) {
        return createTag("col", elems);
    }

    /**
     * Tag of "colgroup".
     *
     * @param elems array of elements.
     * @return "colgroup" Tag object.
     */
    public Tag colgroup(Object... elems) {
        return createTag("colgroup", elems);
    }

    /**
     * Tag of "dd".
     *
     * @param elems array of elements.
     * @return "dd" Tag object.
     */
    public Tag dd(Object... elems) {
        return createTag("dd", elems);
    }

    /**
     * Tag of "del".
     *
     * @param elems array of elements.
     * @return "del" Tag object.
     */
    public Tag del(Object... elems) {
        return createTag("del", elems);
    }

    /**
     * Tag of "dfn".
     *
     * @param elems array of elements.
     * @return "dfn" Tag object.
     */
    public Tag dfn(Object... elems) {
        return createTag("dfn", elems);
    }

    /**
     * Tag of "dir".
     *
     * @param elems array of elements.
     * @return "dir" Tag object.
     */
    public Tag dir(Object... elems) {
        return createTag("dir", elems);
    }

    /**
     * Tag of "div".
     *
     * @param elems array of elements.
     * @return "div" Tag object.
     */
    public Tag div(Object... elems) {
        return createTag("div", elems);
    }

    /**
     * Tag of "dl".
     *
     * @param elems array of elements.
     * @return "dl" Tag object.
     */
    public Tag dl(Object... elems) {
        return createTag("dl", elems);
    }

    /**
     * Tag of "dt".
     *
     * @param elems array of elements.
     * @return "dt" Tag object.
     */
    public Tag dt(Object... elems) {
        return createTag("dt", elems);
    }

    /**
     * Tag of "em".
     *
     * @param elems array of elements.
     * @return "em" Tag object.
     */
    public Tag em(Object... elems) {
        return createTag("em", elems);
    }

    /**
     * Tag of "fieldset".
     *
     * @param elems array of elements.
     * @return "fieldset" Tag object.
     */
    public Tag fieldset(Object... elems) {
        return createTag("fieldset", elems);
    }

    /**
     * Tag of "font".
     *
     * @param elems array of elements.
     * @return "font" Tag object.
     */
    public Tag font(Object... elems) {
        return createTag("font", elems);
    }

    /**
     * Tag of "form".
     *
     * @param elems array of elements.
     * @return "form" Tag object.
     */
    public Tag form(Object... elems) {
        return createTag("form", elems);
    }

    /**
     * Tag of "frame".
     *
     * @param elems array of elements.
     * @return "frame" Tag object.
     */
    public Tag frame(Object... elems) {
        return createTag("frame", elems);
    }

    /**
     * Tag of "frameset".
     *
     * @param elems array of elements.
     * @return "frameset" Tag object.
     */
    public Tag frameset(Object... elems) {
        return createTag("frameset", elems);
    }

    /**
     * Tag of "h1".
     *
     * @param elems array of elements.
     * @return "h1" Tag object.
     */
    public Tag h1(Object... elems) {
        return createTag("h1", elems);
    }

    /**
     * Tag of "h2".
     *
     * @param elems array of elements.
     * @return "h2" Tag object.
     */
    public Tag h2(Object... elems) {
        return createTag("h2", elems);
    }

    /**
     * Tag of "h3".
     *
     * @param elems array of elements.
     * @return "h3" Tag object.
     */
    public Tag h3(Object... elems) {
        return createTag("h3", elems);
    }

    /**
     * Tag of "h4".
     *
     * @param elems array of elements.
     * @return "h4" Tag object.
     */
    public Tag h4(Object... elems) {
        return createTag("h4", elems);
    }

    /**
     * Tag of "h5".
     *
     * @param elems array of elements.
     * @return "h5" Tag object.
     */
    public Tag h5(Object... elems) {
        return createTag("h5", elems);
    }

    /**
     * Tag of "h6".
     *
     * @param elems array of elements.
     * @return "h6" Tag object.
     */
    public Tag h6(Object... elems) {
        return createTag("h6", elems);
    }

    /**
     * Tag of "head".
     *
     * @param elems array of elements.
     * @return "head" Tag object.
     */
    public Tag head(Object... elems) {
        return createTag("head", elems);
    }

    /**
     * Tag of "hr".
     *
     * @param elems array of elements.
     * @return "hr" Tag object.
     */
    public Tag hr(Object... elems) {
        return createTag("hr", elems);
    }

    /**
     * Tag of "html".
     *
     * @param elems array of elements.
     * @return "html" Tag object.
     */
    public Tag html(Object... elems) {
        return createTag("html", elems);
    }

    /**
     * Tag of "i".
     *
     * @param elems array of elements.
     * @return "i" Tag object.
     */
    public Tag i(Object... elems) {
        return createTag("i", elems);
    }

    /**
     * Tag of "iframe".
     *
     * @param elems array of elements.
     * @return "iframe" Tag object.
     */
    public Tag iframe(Object... elems) {
        return createTag("iframe", elems);
    }

    /**
     * Tag of "img".
     *
     * @param elems array of elements.
     * @return "img" Tag object.
     */
    public Tag img(Object... elems) {
        return createTag("img", elems);
    }

    /**
     * Tag of "input".
     *
     * @param elems array of elements.
     * @return "input" Tag object.
     */
    public Tag input(Object... elems) {
        return createTag("input", elems);
    }

    /**
     * Tag of "ins".
     *
     * @param elems array of elements.
     * @return "ins" Tag object.
     */
    public Tag ins(Object... elems) {
        return createTag("ins", elems);
    }

    /**
     * Tag of "isindex".
     *
     * @param elems array of elements.
     * @return "isindex" Tag object.
     */
    public Tag isindex(Object... elems) {
        return createTag("isindex", elems);
    }

    /**
     * Tag of "kbd".
     *
     * @param elems array of elements.
     * @return "kbd" Tag object.
     */
    public Tag kbd(Object... elems) {
        return createTag("kbd", elems);
    }

    /**
     * Tag of "label".
     *
     * @param elems array of elements.
     * @return "label" Tag object.
     */
    public Tag label(Object... elems) {
        return createTag("label", elems);
    }

    /**
     * Tag of "legend".
     *
     * @param elems array of elements.
     * @return "legend" Tag object.
     */
    public Tag legend(Object... elems) {
        return createTag("legend", elems);
    }

    /**
     * Tag of "li".
     *
     * @param elems array of elements.
     * @return "li" Tag object.
     */
    public Tag li(Object... elems) {
        return createTag("li", elems);
    }

    /**
     * Tag of "link".
     *
     * @param elems array of elements.
     * @return "link" Tag object.
     */
    public Tag link(Object... elems) {
        return createTag("link", elems);
    }

    /**
     * Tag of "map".
     *
     * @param elems array of elements.
     * @return "map" Tag object.
     */
    public Tag map(Object... elems) {
        return createTag("map", elems);
    }

    /**
     * Tag of "menu".
     *
     * @param elems array of elements.
     * @return "menu" Tag object.
     */
    public Tag menu(Object... elems) {
        return createTag("menu", elems);
    }

    /**
     * Tag of "meta".
     *
     * @param elems array of elements.
     * @return "meta" Tag object.
     */
    public Tag meta(Object... elems) {
        return createTag("meta", elems);
    }

    /**
     * Tag of "noframes".
     *
     * @param elems array of elements.
     * @return "noframes" Tag object.
     */
    public Tag noframes(Object... elems) {
        return createTag("noframes", elems);
    }

    /**
     * Tag of "noscript".
     *
     * @param elems array of elements.
     * @return "noscript" Tag object.
     */
    public Tag noscript(Object... elems) {
        return createTag("noscript", elems);
    }

    /**
     * Tag of "object".
     *
     * @param elems array of elements.
     * @return "object" Tag object.
     */
    public Tag object(Object... elems) {
        return createTag("object", elems);
    }

    /**
     * Tag of "ol".
     *
     * @param elems array of elements.
     * @return "ol" Tag object.
     */
    public Tag ol(Object... elems) {
        return createTag("ol", elems);
    }

    /**
     * Tag of "optgroup".
     *
     * @param elems array of elements.
     * @return "optgroup" Tag object.
     */
    public Tag optgroup(Object... elems) {
        return createTag("optgroup", elems);
    }

    /**
     * Tag of "option".
     *
     * @param elems array of elements.
     * @return "option" Tag object.
     */
    public Tag option(Object... elems) {
        return createTag("option", elems);
    }

    /**
     * Tag of "p".
     *
     * @param elems array of elements.
     * @return "p" Tag object.
     */
    public Tag p(Object... elems) {
        return createTag("p", elems);
    }

    /**
     * Tag of "param".
     *
     * @param elems array of elements.
     * @return "param" Tag object.
     */
    public Tag param(Object... elems) {
        return createTag("param", elems);
    }

    /**
     * Tag of "pre".
     *
     * @param elems array of elements.
     * @return "pre" Tag object.
     */
    public Tag pre(Object... elems) {
        return createTag("pre", elems);
    }

    /**
     * Tag of "q".
     *
     * @param elems array of elements.
     * @return "q" Tag object.
     */
    public Tag q(Object... elems) {
        return createTag("q", elems);
    }

    /**
     * Tag of "s".
     *
     * @param elems array of elements.
     * @return "s" Tag object.
     */
    public Tag s(Object... elems) {
        return createTag("s", elems);
    }

    /**
     * Tag of "samp".
     *
     * @param elems array of elements.
     * @return "samp" Tag object.
     */
    public Tag samp(Object... elems) {
        return createTag("samp", elems);
    }

    /**
     * Tag of "script".
     *
     * @param elems array of elements.
     * @return "script" Tag object.
     */
    public Tag script(Object... elems) {
        return createTag("script", elems);
    }

    /**
     * Tag of "select".
     *
     * @param elems array of elements.
     * @return "select" Tag object.
     */
    public Tag select(Object... elems) {
        return createTag("select", elems);
    }

    /**
     * Tag of "small".
     *
     * @param elems array of elements.
     * @return "small" Tag object.
     */
    public Tag small(Object... elems) {
        return createTag("small", elems);
    }

    /**
     * Tag of "span".
     *
     * @param elems array of elements.
     * @return "span" Tag object.
     */
    public Tag span(Object... elems) {
        return createTag("span", elems);
    }

    /**
     * Tag of "strike".
     *
     * @param elems array of elements.
     * @return "strike" Tag object.
     */
    public Tag strike(Object... elems) {
        return createTag("strike", elems);
    }

    /**
     * Tag of "strong".
     *
     * @param elems array of elements.
     * @return "strong" Tag object.
     */
    public Tag strong(Object... elems) {
        return createTag("strong", elems);
    }

    /**
     * Tag of "style".
     *
     * @param elems array of elements.
     * @return "style" Tag object.
     */
    public Tag style(Object... elems) {
        return createTag("style", elems);
    }

    /**
     * Tag of "sub".
     *
     * @param elems array of elements.
     * @return "sub" Tag object.
     */
    public Tag sub(Object... elems) {
        return createTag("sub", elems);
    }

    /**
     * Tag of "sup".
     *
     * @param elems array of elements.
     * @return "sup" Tag object.
     */
    public Tag sup(Object... elems) {
        return createTag("sup", elems);
    }

    /**
     * Tag of "table".
     *
     * @param elems array of elements.
     * @return "table" Tag object.
     */
    public Tag table(Object... elems) {
        return createTag("table", elems);
    }

    /**
     * Tag of "tbody".
     *
     * @param elems array of elements.
     * @return "tbody" Tag object.
     */
    public Tag tbody(Object... elems) {
        return createTag("tbody", elems);
    }

    /**
     * Tag of "td".
     *
     * @param elems array of elements.
     * @return "td" Tag object.
     */
    public Tag td(Object... elems) {
        return createTag("td", elems);
    }

    /**
     * Tag of "textarea".
     *
     * @param elems array of elements.
     * @return "textarea" Tag object.
     */
    public Tag textarea(Object... elems) {
        return createTag("textarea", elems);
    }

    /**
     * Tag of "tfoot".
     *
     * @param elems array of elements.
     * @return "tfoot" Tag object.
     */
    public Tag tfoot(Object... elems) {
        return createTag("tfoot", elems);
    }

    /**
     * Tag of "th".
     *
     * @param elems array of elements.
     * @return "th" Tag object.
     */
    public Tag th(Object... elems) {
        return createTag("th", elems);
    }

    /**
     * Tag of "thead".
     *
     * @param elems array of elements.
     * @return "thead" Tag object.
     */
    public Tag thead(Object... elems) {
        return createTag("thead", elems);
    }

    /**
     * Tag of "title".
     *
     * @param elems array of elements.
     * @return "title" Tag object.
     */
    public Tag title(Object... elems) {
        return createTag("title", elems);
    }

    /**
     * Tag of "tr".
     *
     * @param elems array of elements.
     * @return "tr" Tag object.
     */
    public Tag tr(Object... elems) {
        return createTag("tr", elems);
    }

    /**
     * Tag of "tt".
     *
     * @param elems array of elements.
     * @return "tt" Tag object.
     */
    public Tag tt(Object... elems) {
        return createTag("tt", elems);
    }

    /**
     * Tag of "u".
     *
     * @param elems array of elements.
     * @return "u" Tag object.
     */
    public Tag u(Object... elems) {
        return createTag("u", elems);
    }

    /**
     * Tag of "ul".
     *
     * @param elems array of elements.
     * @return "ul" Tag object.
     */
    public Tag ul(Object... elems) {
        return createTag("ul", elems);
    }

    /**
     * Tag of "var".
     *
     * @param elems array of elements.
     * @return "var" Tag object.
     */
    public Tag var(Object... elems) {
        return createTag("var", elems);
    }

    /**
     * Attribute of "abbr".
     *
     * @param value attribute value.
     * @return "abbr" Attr object.
     */
    public Attr _abbr(String value) {
        return createAttr("abbr", value);
    }

    /**
     * Attribute of "accept-charset".
     *
     * @param value attribute value.
     * @return "accept-charset" Attr object.
     */
    public Attr _accept_charset(String value) {
        return createAttr("accept-charset", value);
    }

    /**
     * Attribute of "accept".
     *
     * @param value attribute value.
     * @return "accept" Attr object.
     */
    public Attr _accept(String value) {
        return createAttr("accept", value);
    }

    /**
     * Attribute of "accesskey".
     *
     * @param value attribute value.
     * @return "accesskey" Attr object.
     */
    public Attr _accesskey(String value) {
        return createAttr("accesskey", value);
    }

    /**
     * Attribute of "action".
     *
     * @param value attribute value.
     * @return "action" Attr object.
     */
    public Attr _action(String value) {
        return createAttr("action", value);
    }

    /**
     * Attribute of "align".
     *
     * @param value attribute value.
     * @return "align" Attr object.
     */
    public Attr _align(String value) {
        return createAttr("align", value);
    }

    /**
     * Attribute of "alink".
     *
     * @param value attribute value.
     * @return "alink" Attr object.
     */
    public Attr _alink(String value) {
        return createAttr("alink", value);
    }

    /**
     * Attribute of "alt".
     *
     * @param value attribute value.
     * @return "alt" Attr object.
     */
    public Attr _alt(String value) {
        return createAttr("alt", value);
    }

    /**
     * Attribute of "archive".
     *
     * @param value attribute value.
     * @return "archive" Attr object.
     */
    public Attr _archive(String value) {
        return createAttr("archive", value);
    }

    /**
     * Attribute of "axis".
     *
     * @param value attribute value.
     * @return "axis" Attr object.
     */
    public Attr _axis(String value) {
        return createAttr("axis", value);
    }

    /**
     * Attribute of "background".
     *
     * @param value attribute value.
     * @return "background" Attr object.
     */
    public Attr _background(String value) {
        return createAttr("background", value);
    }

    /**
     * Attribute of "bgcolor".
     *
     * @param value attribute value.
     * @return "bgcolor" Attr object.
     */
    public Attr _bgcolor(String value) {
        return createAttr("bgcolor", value);
    }

    /**
     * Attribute of "border".
     *
     * @param value attribute value.
     * @return "border" Attr object.
     */
    public Attr _border(String value) {
        return createAttr("border", value);
    }

    /**
     * Attribute of "cellpadding".
     *
     * @param value attribute value.
     * @return "cellpadding" Attr object.
     */
    public Attr _cellpadding(String value) {
        return createAttr("cellpadding", value);
    }

    /**
     * Attribute of "cellspacing".
     *
     * @param value attribute value.
     * @return "cellspacing" Attr object.
     */
    public Attr _cellspacing(String value) {
        return createAttr("cellspacing", value);
    }

    /**
     * Attribute of "char".
     *
     * @param value attribute value.
     * @return "char" Attr object.
     */
    public Attr _char(String value) {
        return createAttr("char", value);
    }

    /**
     * Attribute of "charoff".
     *
     * @param value attribute value.
     * @return "charoff" Attr object.
     */
    public Attr _charoff(String value) {
        return createAttr("charoff", value);
    }

    /**
     * Attribute of "charset".
     *
     * @param value attribute value.
     * @return "charset" Attr object.
     */
    public Attr _charset(String value) {
        return createAttr("charset", value);
    }

    /**
     * Attribute of "checked".
     *
     * @param value attribute value.
     * @return "checked" Attr object.
     */
    public Attr _checked(boolean value) {
        return createAttr("checked", value);
    }

    /**
     * Attribute of "cite".
     *
     * @param value attribute value.
     * @return "cite" Attr object.
     */
    public Attr _cite(String value) {
        return createAttr("cite", value);
    }

    /**
     * Attribute of "class".
     *
     * @param value attribute value.
     * @return "class" Attr object.
     */
    public Attr _class(String value) {
        return createAttr("class", value);
    }

    /**
     * Attribute of "classid".
     *
     * @param value attribute value.
     * @return "classid" Attr object.
     */
    public Attr _classid(String value) {
        return createAttr("classid", value);
    }

    /**
     * Attribute of "clear".
     *
     * @param value attribute value.
     * @return "clear" Attr object.
     */
    public Attr _clear(String value) {
        return createAttr("clear", value);
    }

    /**
     * Attribute of "code".
     *
     * @param value attribute value.
     * @return "code" Attr object.
     */
    public Attr _code(String value) {
        return createAttr("code", value);
    }

    /**
     * Attribute of "codebase".
     *
     * @param value attribute value.
     * @return "codebase" Attr object.
     */
    public Attr _codebase(String value) {
        return createAttr("codebase", value);
    }

    /**
     * Attribute of "codetype".
     *
     * @param value attribute value.
     * @return "codetype" Attr object.
     */
    public Attr _codetype(String value) {
        return createAttr("codetype", value);
    }

    /**
     * Attribute of "color".
     *
     * @param value attribute value.
     * @return "color" Attr object.
     */
    public Attr _color(String value) {
        return createAttr("color", value);
    }

    /**
     * Attribute of "cols".
     *
     * @param value attribute value.
     * @return "cols" Attr object.
     */
    public Attr _cols(String value) {
        return createAttr("cols", value);
    }

    /**
     * Attribute of "colspan".
     *
     * @param value attribute value.
     * @return "colspan" Attr object.
     */
    public Attr _colspan(String value) {
        return createAttr("colspan", value);
    }

    /**
     * Attribute of "compact".
     *
     * @param value attribute value.
     * @return "compact" Attr object.
     */
    public Attr _compact(boolean value) {
        return createAttr("compact", value);
    }

    /**
     * Attribute of "content".
     *
     * @param value attribute value.
     * @return "content" Attr object.
     */
    public Attr _content(String value) {
        return createAttr("content", value);
    }

    /**
     * Attribute of "coords".
     *
     * @param value attribute value.
     * @return "coords" Attr object.
     */
    public Attr _coords(String value) {
        return createAttr("coords", value);
    }

    /**
     * Attribute of "data".
     *
     * @param value attribute value.
     * @return "data" Attr object.
     */
    public Attr _data(String value) {
        return createAttr("data", value);
    }

    /**
     * Attribute of "datetime".
     *
     * @param value attribute value.
     * @return "datetime" Attr object.
     */
    public Attr _datetime(String value) {
        return createAttr("datetime", value);
    }

    /**
     * Attribute of "declare".
     *
     * @param value attribute value.
     * @return "declare" Attr object.
     */
    public Attr _declare(boolean value) {
        return createAttr("declare", value);
    }

    /**
     * Attribute of "defer".
     *
     * @param value attribute value.
     * @return "defer" Attr object.
     */
    public Attr _defer(boolean value) {
        return createAttr("defer", value);
    }

    /**
     * Attribute of "dir".
     *
     * @param value attribute value.
     * @return "dir" Attr object.
     */
    public Attr _dir(String value) {
        return createAttr("dir", value);
    }

    /**
     * Attribute of "disabled".
     *
     * @param value attribute value.
     * @return "disabled" Attr object.
     */
    public Attr _disabled(boolean value) {
        return createAttr("disabled", value);
    }

    /**
     * Attribute of "enctype".
     *
     * @param value attribute value.
     * @return "enctype" Attr object.
     */
    public Attr _enctype(String value) {
        return createAttr("enctype", value);
    }

    /**
     * Attribute of "face".
     *
     * @param value attribute value.
     * @return "face" Attr object.
     */
    public Attr _face(String value) {
        return createAttr("face", value);
    }

    /**
     * Attribute of "for".
     *
     * @param value attribute value.
     * @return "for" Attr object.
     */
    public Attr _for(String value) {
        return createAttr("for", value);
    }

    /**
     * Attribute of "frame".
     *
     * @param value attribute value.
     * @return "frame" Attr object.
     */
    public Attr _frame(String value) {
        return createAttr("frame", value);
    }

    /**
     * Attribute of "frameborder".
     *
     * @param value attribute value.
     * @return "frameborder" Attr object.
     */
    public Attr _frameborder(String value) {
        return createAttr("frameborder", value);
    }

    /**
     * Attribute of "headers".
     *
     * @param value attribute value.
     * @return "headers" Attr object.
     */
    public Attr _headers(String value) {
        return createAttr("headers", value);
    }

    /**
     * Attribute of "height".
     *
     * @param value attribute value.
     * @return "height" Attr object.
     */
    public Attr _height(String value) {
        return createAttr("height", value);
    }

    /**
     * Attribute of "href".
     *
     * @param value attribute value.
     * @return "href" Attr object.
     */
    public Attr _href(String value) {
        return createAttr("href", value);
    }

    /**
     * Attribute of "hreflang".
     *
     * @param value attribute value.
     * @return "hreflang" Attr object.
     */
    public Attr _hreflang(String value) {
        return createAttr("hreflang", value);
    }

    /**
     * Attribute of "hspace".
     *
     * @param value attribute value.
     * @return "hspace" Attr object.
     */
    public Attr _hspace(String value) {
        return createAttr("hspace", value);
    }

    /**
     * Attribute of "http-equiv".
     *
     * @param value attribute value.
     * @return "http-equiv" Attr object.
     */
    public Attr _http_equiv(String value) {
        return createAttr("http-equiv", value);
    }

    /**
     * Attribute of "id".
     *
     * @param value attribute value.
     * @return "id" Attr object.
     */
    public Attr _id(String value) {
        return createAttr("id", value);
    }

    /**
     * Attribute of "ismap".
     *
     * @param value attribute value.
     * @return "ismap" Attr object.
     */
    public Attr _ismap(boolean value) {
        return createAttr("ismap", value);
    }

    /**
     * Attribute of "label".
     *
     * @param value attribute value.
     * @return "label" Attr object.
     */
    public Attr _label(String value) {
        return createAttr("label", value);
    }

    /**
     * Attribute of "lang".
     *
     * @param value attribute value.
     * @return "lang" Attr object.
     */
    public Attr _lang(String value) {
        return createAttr("lang", value);
    }

    /**
     * Attribute of "language".
     *
     * @param value attribute value.
     * @return "language" Attr object.
     */
    public Attr _language(String value) {
        return createAttr("language", value);
    }

    /**
     * Attribute of "link".
     *
     * @param value attribute value.
     * @return "link" Attr object.
     */
    public Attr _link(String value) {
        return createAttr("link", value);
    }

    /**
     * Attribute of "longdesc".
     *
     * @param value attribute value.
     * @return "longdesc" Attr object.
     */
    public Attr _longdesc(String value) {
        return createAttr("longdesc", value);
    }

    /**
     * Attribute of "marginheight".
     *
     * @param value attribute value.
     * @return "marginheight" Attr object.
     */
    public Attr _marginheight(String value) {
        return createAttr("marginheight", value);
    }

    /**
     * Attribute of "marginwidth".
     *
     * @param value attribute value.
     * @return "marginwidth" Attr object.
     */
    public Attr _marginwidth(String value) {
        return createAttr("marginwidth", value);
    }

    /**
     * Attribute of "maxlength".
     *
     * @param value attribute value.
     * @return "maxlength" Attr object.
     */
    public Attr _maxlength(String value) {
        return createAttr("maxlength", value);
    }

    /**
     * Attribute of "media".
     *
     * @param value attribute value.
     * @return "media" Attr object.
     */
    public Attr _media(String value) {
        return createAttr("media", value);
    }

    /**
     * Attribute of "method".
     *
     * @param value attribute value.
     * @return "method" Attr object.
     */
    public Attr _method(String value) {
        return createAttr("method", value);
    }

    /**
     * Attribute of "multiple".
     *
     * @param value attribute value.
     * @return "multiple" Attr object.
     */
    public Attr _multiple(boolean value) {
        return createAttr("multiple", value);
    }

    /**
     * Attribute of "name".
     *
     * @param value attribute value.
     * @return "name" Attr object.
     */
    public Attr _name(String value) {
        return createAttr("name", value);
    }

    /**
     * Attribute of "nohref".
     *
     * @param value attribute value.
     * @return "nohref" Attr object.
     */
    public Attr _nohref(boolean value) {
        return createAttr("nohref", value);
    }

    /**
     * Attribute of "noresize".
     *
     * @param value attribute value.
     * @return "noresize" Attr object.
     */
    public Attr _noresize(boolean value) {
        return createAttr("noresize", value);
    }

    /**
     * Attribute of "noshade".
     *
     * @param value attribute value.
     * @return "noshade" Attr object.
     */
    public Attr _noshade(boolean value) {
        return createAttr("noshade", value);
    }

    /**
     * Attribute of "nowrap".
     *
     * @param value attribute value.
     * @return "nowrap" Attr object.
     */
    public Attr _nowrap(boolean value) {
        return createAttr("nowrap", value);
    }

    /**
     * Attribute of "object".
     *
     * @param value attribute value.
     * @return "object" Attr object.
     */
    public Attr _object(String value) {
        return createAttr("object", value);
    }

    /**
     * Attribute of "onblur".
     *
     * @param value attribute value.
     * @return "onblur" Attr object.
     */
    public Attr _onblur(String value) {
        return createAttr("onblur", value);
    }

    /**
     * Attribute of "onchange".
     *
     * @param value attribute value.
     * @return "onchange" Attr object.
     */
    public Attr _onchange(String value) {
        return createAttr("onchange", value);
    }

    /**
     * Attribute of "onclick".
     *
     * @param value attribute value.
     * @return "onclick" Attr object.
     */
    public Attr _onclick(String value) {
        return createAttr("onclick", value);
    }

    /**
     * Attribute of "ondblclick".
     *
     * @param value attribute value.
     * @return "ondblclick" Attr object.
     */
    public Attr _ondblclick(String value) {
        return createAttr("ondblclick", value);
    }

    /**
     * Attribute of "onfocus".
     *
     * @param value attribute value.
     * @return "onfocus" Attr object.
     */
    public Attr _onfocus(String value) {
        return createAttr("onfocus", value);
    }

    /**
     * Attribute of "onkeydown".
     *
     * @param value attribute value.
     * @return "onkeydown" Attr object.
     */
    public Attr _onkeydown(String value) {
        return createAttr("onkeydown", value);
    }

    /**
     * Attribute of "onkeypress".
     *
     * @param value attribute value.
     * @return "onkeypress" Attr object.
     */
    public Attr _onkeypress(String value) {
        return createAttr("onkeypress", value);
    }

    /**
     * Attribute of "onkeyup".
     *
     * @param value attribute value.
     * @return "onkeyup" Attr object.
     */
    public Attr _onkeyup(String value) {
        return createAttr("onkeyup", value);
    }

    /**
     * Attribute of "onload".
     *
     * @param value attribute value.
     * @return "onload" Attr object.
     */
    public Attr _onload(String value) {
        return createAttr("onload", value);
    }

    /**
     * Attribute of "onmousedown".
     *
     * @param value attribute value.
     * @return "onmousedown" Attr object.
     */
    public Attr _onmousedown(String value) {
        return createAttr("onmousedown", value);
    }

    /**
     * Attribute of "onmousemove".
     *
     * @param value attribute value.
     * @return "onmousemove" Attr object.
     */
    public Attr _onmousemove(String value) {
        return createAttr("onmousemove", value);
    }

    /**
     * Attribute of "onmouseout".
     *
     * @param value attribute value.
     * @return "onmouseout" Attr object.
     */
    public Attr _onmouseout(String value) {
        return createAttr("onmouseout", value);
    }

    /**
     * Attribute of "onmouseover".
     *
     * @param value attribute value.
     * @return "onmouseover" Attr object.
     */
    public Attr _onmouseover(String value) {
        return createAttr("onmouseover", value);
    }

    /**
     * Attribute of "onmouseup".
     *
     * @param value attribute value.
     * @return "onmouseup" Attr object.
     */
    public Attr _onmouseup(String value) {
        return createAttr("onmouseup", value);
    }

    /**
     * Attribute of "onreset".
     *
     * @param value attribute value.
     * @return "onreset" Attr object.
     */
    public Attr _onreset(String value) {
        return createAttr("onreset", value);
    }

    /**
     * Attribute of "onselect".
     *
     * @param value attribute value.
     * @return "onselect" Attr object.
     */
    public Attr _onselect(String value) {
        return createAttr("onselect", value);
    }

    /**
     * Attribute of "onsubmit".
     *
     * @param value attribute value.
     * @return "onsubmit" Attr object.
     */
    public Attr _onsubmit(String value) {
        return createAttr("onsubmit", value);
    }

    /**
     * Attribute of "onunload".
     *
     * @param value attribute value.
     * @return "onunload" Attr object.
     */
    public Attr _onunload(String value) {
        return createAttr("onunload", value);
    }

    /**
     * Attribute of "profile".
     *
     * @param value attribute value.
     * @return "profile" Attr object.
     */
    public Attr _profile(String value) {
        return createAttr("profile", value);
    }

    /**
     * Attribute of "prompt".
     *
     * @param value attribute value.
     * @return "prompt" Attr object.
     */
    public Attr _prompt(String value) {
        return createAttr("prompt", value);
    }

    /**
     * Attribute of "readonly".
     *
     * @param value attribute value.
     * @return "readonly" Attr object.
     */
    public Attr _readonly(boolean value) {
        return createAttr("readonly", value);
    }

    /**
     * Attribute of "rel".
     *
     * @param value attribute value.
     * @return "rel" Attr object.
     */
    public Attr _rel(String value) {
        return createAttr("rel", value);
    }

    /**
     * Attribute of "rev".
     *
     * @param value attribute value.
     * @return "rev" Attr object.
     */
    public Attr _rev(String value) {
        return createAttr("rev", value);
    }

    /**
     * Attribute of "rows".
     *
     * @param value attribute value.
     * @return "rows" Attr object.
     */
    public Attr _rows(String value) {
        return createAttr("rows", value);
    }

    /**
     * Attribute of "rowspan".
     *
     * @param value attribute value.
     * @return "rowspan" Attr object.
     */
    public Attr _rowspan(String value) {
        return createAttr("rowspan", value);
    }

    /**
     * Attribute of "rules".
     *
     * @param value attribute value.
     * @return "rules" Attr object.
     */
    public Attr _rules(String value) {
        return createAttr("rules", value);
    }

    /**
     * Attribute of "scheme".
     *
     * @param value attribute value.
     * @return "scheme" Attr object.
     */
    public Attr _scheme(String value) {
        return createAttr("scheme", value);
    }

    /**
     * Attribute of "scope".
     *
     * @param value attribute value.
     * @return "scope" Attr object.
     */
    public Attr _scope(String value) {
        return createAttr("scope", value);
    }

    /**
     * Attribute of "scrolling".
     *
     * @param value attribute value.
     * @return "scrolling" Attr object.
     */
    public Attr _scrolling(String value) {
        return createAttr("scrolling", value);
    }

    /**
     * Attribute of "selected".
     *
     * @param value attribute value.
     * @return "selected" Attr object.
     */
    public Attr _selected(boolean value) {
        return createAttr("selected", value);
    }

    /**
     * Attribute of "shape".
     *
     * @param value attribute value.
     * @return "shape" Attr object.
     */
    public Attr _shape(String value) {
        return createAttr("shape", value);
    }

    /**
     * Attribute of "size".
     *
     * @param value attribute value.
     * @return "size" Attr object.
     */
    public Attr _size(String value) {
        return createAttr("size", value);
    }

    /**
     * Attribute of "span".
     *
     * @param value attribute value.
     * @return "span" Attr object.
     */
    public Attr _span(String value) {
        return createAttr("span", value);
    }

    /**
     * Attribute of "src".
     *
     * @param value attribute value.
     * @return "src" Attr object.
     */
    public Attr _src(String value) {
        return createAttr("src", value);
    }

    /**
     * Attribute of "standby".
     *
     * @param value attribute value.
     * @return "standby" Attr object.
     */
    public Attr _standby(String value) {
        return createAttr("standby", value);
    }

    /**
     * Attribute of "start".
     *
     * @param value attribute value.
     * @return "start" Attr object.
     */
    public Attr _start(String value) {
        return createAttr("start", value);
    }

    /**
     * Attribute of "style".
     *
     * @param value attribute value.
     * @return "style" Attr object.
     */
    public Attr _style(String value) {
        return createAttr("style", value);
    }

    /**
     * Attribute of "summary".
     *
     * @param value attribute value.
     * @return "summary" Attr object.
     */
    public Attr _summary(String value) {
        return createAttr("summary", value);
    }

    /**
     * Attribute of "tabindex".
     *
     * @param value attribute value.
     * @return "tabindex" Attr object.
     */
    public Attr _tabindex(String value) {
        return createAttr("tabindex", value);
    }

    /**
     * Attribute of "target".
     *
     * @param value attribute value.
     * @return "target" Attr object.
     */
    public Attr _target(String value) {
        return createAttr("target", value);
    }

    /**
     * Attribute of "text".
     *
     * @param value attribute value.
     * @return "text" Attr object.
     */
    public Attr _text(String value) {
        return createAttr("text", value);
    }

    /**
     * Attribute of "title".
     *
     * @param value attribute value.
     * @return "title" Attr object.
     */
    public Attr _title(String value) {
        return createAttr("title", value);
    }

    /**
     * Attribute of "type".
     *
     * @param value attribute value.
     * @return "type" Attr object.
     */
    public Attr _type(String value) {
        return createAttr("type", value);
    }

    /**
     * Attribute of "usemap".
     *
     * @param value attribute value.
     * @return "usemap" Attr object.
     */
    public Attr _usemap(String value) {
        return createAttr("usemap", value);
    }

    /**
     * Attribute of "valign".
     *
     * @param value attribute value.
     * @return "valign" Attr object.
     */
    public Attr _valign(String value) {
        return createAttr("valign", value);
    }

    /**
     * Attribute of "value".
     *
     * @param value attribute value.
     * @return "value" Attr object.
     */
    public Attr _value(String value) {
        return createAttr("value", value);
    }

    /**
     * Attribute of "valuetype".
     *
     * @param value attribute value.
     * @return "valuetype" Attr object.
     */
    public Attr _valuetype(String value) {
        return createAttr("valuetype", value);
    }

    /**
     * Attribute of "version".
     *
     * @param value attribute value.
     * @return "version" Attr object.
     */
    public Attr _version(String value) {
        return createAttr("version", value);
    }

    /**
     * Attribute of "vlink".
     *
     * @param value attribute value.
     * @return "vlink" Attr object.
     */
    public Attr _vlink(String value) {
        return createAttr("vlink", value);
    }

    /**
     * Attribute of "vspace".
     *
     * @param value attribute value.
     * @return "vspace" Attr object.
     */
    public Attr _vspace(String value) {
        return createAttr("vspace", value);
    }

    /**
     * Attribute of "width".
     *
     * @param value attribute value.
     * @return "width" Attr object.
     */
    public Attr _width(String value) {
        return createAttr("width", value);
    }

    // END OF AUTO GENERATED METHODS.
}
