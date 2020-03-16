package eg.edu.alexu.csd.oop.DBMS.executeCommand.Command;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;
import eg.edu.alexu.csd.oop.DBMS.Objects.commandState;

import java.sql.SQLException;

public interface  Command {

    public commandState execute(queryParsedData data) throws SQLException;
}
