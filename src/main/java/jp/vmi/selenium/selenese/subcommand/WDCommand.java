package jp.vmi.selenium.selenese.subcommand;

import org.openqa.selenium.internal.seleniumemulation.SeleneseCommand;

import com.thoughtworks.selenium.SeleniumException;

import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.command.ArgumentType;

/**
 * WDCP command with the information.
 */
public class WDCommand extends AbstractSubCommand<Object> {

    private final SeleneseCommand<?> seleneseCommand;
    private final String name;

    /**
     * Constructor.
     *
     * @param seleneseCommand Selenese command.
     * @param name command name.
     * @param argTypes argument types.
     */
    public WDCommand(SeleneseCommand<?> seleneseCommand, String name, ArgumentType... argTypes) {
        super(argTypes);
        this.seleneseCommand = seleneseCommand;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object execute(Context context, String... args) {
        try {
            return seleneseCommand.apply(context.getWrappedDriver(), args);
        } catch (RuntimeException e) {
            // for HtmlUnit
            if (!e.getClass().getSimpleName().contains("Script"))
                throw e;
            String message = e.getMessage().replaceFirst("\\s*\\([^()]+\\)$", "");
            throw new SeleniumException(message, e);
        }
    }
}