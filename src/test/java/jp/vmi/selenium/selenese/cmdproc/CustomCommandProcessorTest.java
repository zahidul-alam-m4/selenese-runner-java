package jp.vmi.selenium.selenese.cmdproc;

import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.*;

public class CustomCommandProcessorTest {

    @Test
    public void test() {
        HtmlUnitDriver driver = new HtmlUnitDriver(true);
        String htmlUrl = CustomCommandProcessorTest.class.getResource("dummy.html").toString();
        CustomCommandProcessor proc = new CustomCommandProcessor(htmlUrl, driver);
        proc.doCommand("open", new String[] { "/" });
        String script = "storedVars['logoutpresent'] ? storedVars['link_logout'] : storedVars['body']";
        proc.setVar("result-1", "link_logout");
        proc.setVar("result-2", "body");
        proc.setVar(true, "logoutpresent");
        String result;
        result = proc.doCommand("getEval", new String[] { script });
        assertEquals("result-1", result);
        proc.setVar(false, "logoutpresent");
        result = proc.doCommand("getEval", new String[] { script });
        assertEquals("result-2", result);
    }
}
