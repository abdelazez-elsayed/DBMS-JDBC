package eg.edu.alexu.csd.oop.DBMS.executeCommand;

import eg.edu.alexu.csd.oop.DBMS.Objects.MyDatabase;
import eg.edu.alexu.csd.oop.DBMS.Objects.MyTable;
import eg.edu.alexu.csd.oop.DBMS.Objects.commandState;
import eg.edu.alexu.csd.oop.DBMS.executeCommand.Command.*;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;

public class ChooseCommand {
    Invoker invoker= new Invoker();
    MyDatabase dataBase = new MyDatabase();
    MyTable table = new MyTable();
    Command createDB = new CREATEDATABASECommand(dataBase);
    Command dropDB = new DROPDATABASECommand(dataBase);
    Command createTable = new CREATETABLECommand(table);
    Command dropTable = new DROPTABLECommand(table);
    Command insert = new INSERTCommand(table);
    Command select = new SELECTCommand(table);
    Command delete = new DELETEFROMTABLECommand(table);
    Command update = new UPDATECommand(table);
    private queryParsedData data;

    public ChooseCommand (queryParsedData data){
        this.data = data;
    }

    public commandState returnCommand () throws SQLException {
        String operation = data.getOperationName();
        switch (operation){
            case("CREATEDATABASE"):
                invoker.setCommand(createDB);
                break;
            case("CREATETABLE"):
                invoker.setCommand(createTable);
                break;
            case("DROPDATABASE"):
                invoker.setCommand(dropDB);
                break;
            case("DROPTABLE"):
                invoker.setCommand(dropTable);
                break;
            case("INSERT"):
                invoker.setCommand(insert);
                break;
            case("SELECT"):
                invoker.setCommand(select);
                break;
            case("UPDATE"):
                invoker.setCommand(update);
                break;
            case("DELETE"):
                invoker.setCommand(delete);
                break;
        }
        return invoker.excuteCommand(data);
    }
}
