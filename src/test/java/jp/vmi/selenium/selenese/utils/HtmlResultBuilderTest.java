package jp.vmi.selenium.selenese.utils;

import org.junit.Test;

import jp.vmi.html.SimpleHtmlBuilder;

@SuppressWarnings("javadoc")
public class HtmlResultBuilderTest {

    @Test
    public void testInit() {
        String html = new SimpleHtmlBuilder("test") {

            @Override
            public void head(Tag head) {
                head.add(title(title));
            }

            @Override
            public void body(Tag body) {
                body.add(
                    h1(title),
                    hr(),
                    p("<This>\n<is>\n<test>."));
            }

        }.build();
        System.out.println(html);
    }
}
