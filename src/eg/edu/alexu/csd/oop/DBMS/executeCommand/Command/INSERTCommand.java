package eg.edu.alexu.csd.oop.DBMS.executeCommand.Command;

import eg.edu.alexu.csd.oop.DBMS.Objects.MyTable;
import eg.edu.alexu.csd.oop.DBMS.Objects.commandState;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;
public class INSERTCommand implements Command {

    MyTable table;


    public INSERTCommand(MyTable table) {
        this.table = table ;
    }


    @Override
    public commandState execute(queryParsedData data) throws SQLException {
            return table.insert(data);
    }
}
