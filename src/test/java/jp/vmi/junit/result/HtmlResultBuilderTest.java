package jp.vmi.junit.result;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class HtmlResultBuilderTest {

    @Test
    public void testInit() {
        String html = new HtmlResultBuilder("test") {
            @Override
            public void body() {
            }
        }.build();
        System.out.println(html);
    }
}
