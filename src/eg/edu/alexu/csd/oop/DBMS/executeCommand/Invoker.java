package eg.edu.alexu.csd.oop.DBMS.executeCommand;

import eg.edu.alexu.csd.oop.DBMS.Objects.commandState;
import eg.edu.alexu.csd.oop.DBMS.executeCommand.Command.Command;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;

public class Invoker {
    private Command command;
    public void setCommand(Command command){
        this.command=command;
    }

    public commandState excuteCommand(queryParsedData data) throws SQLException {
        commandState commandstate = command.execute(data);
        return commandstate;
    }
}
