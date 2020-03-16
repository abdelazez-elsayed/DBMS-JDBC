package eg.edu.alexu.csd.oop.DBMS.executeCommand.Command;

import eg.edu.alexu.csd.oop.DBMS.Objects.MyDatabase;
import eg.edu.alexu.csd.oop.DBMS.Objects.commandState;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;
public class DROPDATABASECommand implements Command {

    MyDatabase database  ;

    public DROPDATABASECommand(MyDatabase database) {
        this.database = database;
    }

    @Override
    public commandState execute(queryParsedData data) throws SQLException {
        return database.drop(data);
    }
}
