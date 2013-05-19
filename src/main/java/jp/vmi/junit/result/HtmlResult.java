package jp.vmi.junit.result;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.openqa.selenium.io.IOUtils;

import jp.vmi.html.SimpleHtmlBuilder;
import jp.vmi.selenium.webdriver.WebDriverManager;

class HtmlResult {

    static void generate(Writer writer, final TestSuiteResult suiteResult) {
        new SimpleHtmlBuilder(suiteResult.getName() + " results") {
            @Override
            public void head(Tag head) {
                String css;
                try {
                    css = IOUtils.readFully(getClass().getResourceAsStream("result.css"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                head.add(
                    title(title),
                    style(_type("text/css"), css)
                    );
            }

            public Object[] resultSummary(String result) {
                String ver = WebDriverManager.getSeleniumVersion();
                int lastDot = ver.lastIndexOf('.');
                String v, r;
                if (lastDot >= 0) {
                    v = ver.substring(0, lastDot);
                    r = ver.substring(lastDot);
                } else {
                    v = r = "<unknown>";
                }
                return __elems(
                    tr(td("result:"), td(result)),
                    tr(td("totalTime:"), td(suiteResult.getTime())),
                    tr(td("numTestTotal:"), td(suiteResult.getTests())),
                    tr(td("numTestPasses:"), td(suiteResult.getPassed())),
                    tr(td("numTestFailures:"), td(suiteResult.getFailures())),
                    tr(td("numCommandPasses:"), td(suiteResult.getCommandPassed())),
                    tr(td("numCommandFailures:"), td(suiteResult.getCommandFailures())),
                    tr(td("numCommandErrors:"), td(suiteResult.getCommandErrors())),
                    tr(td("Selenium Version:"), td(v)),
                    tr(td("Selenium Revision:"), td(r)));
            }

            public Tag testSuiteTable(String result) {
                return table(
                    _id("suiteTable"),
                    _class("selenium"),
                    _cellpadding("1"),
                    _cellspacing("1"),
                    _border("1"),
                    tbody(
                        tr(_class("title status_" + result), td(b(suiteResult.getName()))),
                        new For<Object, TestCaseResult>() {
                            private int count = 0;

                            @Override
                            public Object apply(TestCaseResult caseResult) {
                                String cr = caseResult.getPassed() > 0 ? "passed" : "failed";
                                return tr(
                                    _class("status_" + cr),
                                    td(a(_href("#testresult" + count++), caseResult.getName())));
                            }
                        }.each(suiteResult.getTestCaseResults())));
            }

            public List<Object> testCaseCommands(TestCaseResult caseResult) {
                return new For<Object, CommandResult>() {

                    @Override
                    public Object apply(CommandResult r) {
                        String status = r.isSuccess ? "done" : "failed";
                        return tr(_class("status_" + status), _style("cursor: pointer;"),
                            td(r.command), td(r.arg1), td(r.arg2));
                    }

                }.each(caseResult.getCommandResults());
            }

            public Object[] testCaseTable() {
                return __elems(table(new For<Object, TestCaseResult>() {
                    private int count = 0;

                    @Override
                    public Object apply(TestCaseResult caseResult) {
                        String cr = caseResult.getPassed() > 0 ? "passed" : "failed";
                        return tr(td(a(_name("testresult" + count++), caseResult.getName() + ".html"), br(),
                            div(table(_cellpadding("1"), _cellspacing("1"), _border("1"),
                                thead(tr(_class("title status_" + cr), td(_rowspan("1"), _colspan("3"), caseResult.getName()))),
                                tbody(testCaseCommands(caseResult))))), td("&nbsp;"));
                    }
                }.each(suiteResult.getTestCaseResults())),
                    pre());
            }

            @Override
            public void body(Tag body) {
                String result = suiteResult.isSuccess() ? "passed" : "failed";
                body.add(
                    h1(title),
                    table(
                        resultSummary(result),
                        tr(td(testSuiteTable(result), td("&nbsp;")))),
                    testCaseTable());
            }
        };
    }
}
