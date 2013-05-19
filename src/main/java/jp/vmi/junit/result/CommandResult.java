package jp.vmi.junit.result;

public class CommandResult {

    public final String command;
    public final String arg1;
    public final String arg2;

    public boolean isSuccess = true;

    public CommandResult(String command, String arg1, String arg2) {
        this.command = command;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }
}
