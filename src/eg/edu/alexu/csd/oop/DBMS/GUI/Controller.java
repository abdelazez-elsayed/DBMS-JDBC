package eg.edu.alexu.csd.oop.GUI;

import eg.edu.alexu.csd.oop.DBMS.GUI.FunctionFacasd;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;

import static javafx.scene.input.KeyCode.ENTER;

public class Controller {
    @FXML
    private TextField Query;
    @FXML
    private TextArea area;

    @FXML
    public void handle(KeyEvent event) {
        if(event.getCode() == ENTER) {
            run();
        }
    }
    private FunctionFacasd fS = new FunctionFacasd();

    public void run(){
        fS.setQuery(Query.getText());
        try {
            area.appendText("\n"+">>"+Query.getText()+"\n"+fS.operate());
            Query.setText("");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input,Please enter a valid SQL Query");
            alert.showAndWait();
        }
    }


}
