package eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter;

import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;

public interface Interpreter {
    public queryParsedData interpret (String query) throws SQLException;
}
