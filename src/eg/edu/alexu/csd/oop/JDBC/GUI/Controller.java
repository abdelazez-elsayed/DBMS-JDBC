package eg.edu.alexu.csd.oop.JDBC.GUI;

import eg.edu.alexu.csd.oop.DBMS.GUI.FunctionFacasd;
import eg.edu.alexu.csd.oop.JDBC.Driver.DBDriverAdapter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static javafx.scene.input.KeyCode.ENTER;


public class Controller {
    @FXML
    private TextArea batchIn;
    @FXML
    private TextArea batchOut;
    @FXML
    private TextField urlIn;
    @FXML
    private TextField sqlIn;
    @FXML
    private Button connectButton;
    @FXML
    private Button executeButton;
    @FXML
    private Button addToBatchButton;
    @FXML
    private Button executeBatchButton;
    @FXML
    private Button DisconnectButton;
    @FXML
    private Label connectionLabel;
    @FXML
    private TableView tb;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button clearBatchButton;
    @FXML
    private TextField timeOut;
    @FXML
    private TextField Query;
    @FXML
    private TextArea area;
    @FXML
    private TextArea executeOutt;
    @FXML
    private TextArea logger;

    private Future<List<String>> future;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Driver driver = new DBDriverAdapter();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Statement st = null;
    private Connection cn = null;
    private boolean isPath = false;
    private String path = null;
    private FunctionFacasd fS = new FunctionFacasd();

    @FXML
    public void connect(){
        try {
            if (!isPath)
                this.path = urlIn.getText().substring(urlIn.getText().indexOf("/")+2,urlIn.getText().length());
            isPath = false;
            Properties prop = new Properties();
            File dbDir = new File(path);
            prop.put("path",dbDir);
            cn = driver.connect(urlIn.getText(),prop);
            st = cn.createStatement();
            connectionLabel.setText("Connected");
            connectionLabel.setTextFill(Color.GREEN);
            sqlIn.setEditable(true);
            clearBatchButton.setDisable(false);
            connectButton.setDisable(true);
            DisconnectButton.setDisable(false);
            executeButton.setDisable(false);
            addToBatchButton.setDisable(false);
            executeBatchButton.setDisable(false);

        } catch (SQLException e) {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Cannot Connect");
            alert.showAndWait();
        }
        catch (Exception e){
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Enter A Valid Input");
            alert.showAndWait();
        }
    }
    @FXML
    public void addToBatch(){
        if (sqlIn.getText().length()!=0){
            batchIn.appendText(sqlIn.getText().trim().concat("\n"));
            try {
                st.addBatch(sqlIn.getText().trim());
                sqlIn.setText("\n");
            } catch (SQLException e) {
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Can't add to Batch");
                alert.showAndWait();
            }
        }
        else {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Enter A Query to be added");
            alert.showAndWait();
        }
    }
    @FXML
    public void executeBatch(){
        try {
            st = cn.createStatement();
            sqlIn.setText("\n");
            int [] out = st.executeBatch();
            batchOut.appendText(out.length + " Query executed successfuly + \n");
            st.close();
        } catch (SQLException e) {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error Executing Batch");
            alert.showAndWait();
        }
    }
    @FXML
    public void disconnect(){
        try {
            cn.close();
            st.close();
            connectionLabel.setText("Disconnected");
            connectionLabel.setTextFill(Color.RED);
            sqlIn.setEditable(false);
            connectButton.setDisable(false);
            DisconnectButton.setDisable(true);
            executeButton.setDisable(true);
            addToBatchButton.setDisable(true);
            clearBatchButton.setDisable(true);
            executeBatchButton.setDisable(true);
        } catch (SQLException e) {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Cannot Close Connection");
            alert.showAndWait();
        }
    }
    @FXML
    public void execute(){
        try {
            st = cn.createStatement();
            st.setQueryTimeout(Integer.parseInt(timeOut.getText()));
            if(sqlIn.getText().trim().startsWith("s")||sqlIn.getText().trim().startsWith("S"))
                table(st.executeQuery(sqlIn.getText().trim()));
            else {
                boolean executed = st.execute(sqlIn.getText().trim());
                if (executed)
                    executeOutt.appendText("SQL Executed! \n");
                else
                    executeOutt.appendText("Failed To Execute Query\n");
            }
            sqlIn.setText("\n");
            st.close();
        } catch (SQLException e) {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error Executing Query");
            alert.showAndWait();
        }
    }
    @FXML
    public void fromFile(){
//        try {
            DirectoryChooser dc = new DirectoryChooser();
            dc.setTitle("Choose The DataBase Directory");
            File selectedFile = dc.showDialog(gridPane.getScene().getWindow());
            if (selectedFile != null) {
                Properties prop = new Properties();
                this.isPath = true;
                this.path = selectedFile.getAbsolutePath();
                File dbDir = new File(path);
                prop.put("path", dbDir);
                urlIn.setText("jdbc:DBName://"+path.substring(path.lastIndexOf("\\")+1));
            }
            else {
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Choose A Valid Directory");
                alert.showAndWait();
            }
    }
    @FXML
    public void clearBatch(){
        try {
            st.clearBatch();
            batchIn.setText("\n\n");
            batchOut.setText("\n\n");
            executeOutt.setText("\n\n");
        } catch (SQLException e) {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error Deleting Batch");
            alert.showAndWait();
        }
    }

    private void table(ResultSet rs) throws SQLException {
        tb.setEditable(false);
        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            tb.getColumns().addAll(col);
            System.out.println("Column ["+i+"] "+col.getColumns());
        }
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        while(rs.next()){
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                try {
                    row.add(rs.getString(i));
                }
                catch (Exception e){
                    row.add(String.valueOf(rs.getInt(i)));
                }
            }
            System.out.println("Row [1] added "+row );
            data.add(row);
        }

        //FINALLY ADDED TO TableView
        tb.setItems(data);
    }

    @FXML
    public void handle(KeyEvent event) {
        if(event.getCode() == ENTER) {
            run();
        }
    }

    private void run(){
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
    @FXML
    @SuppressWarnings("NestedAssignment")
    public void showFileLines() throws InterruptedException, ExecutionException {
        future = executorService.submit(new Callable<List<String>>() {
            public List<String> call() throws Exception {
                return read(new File("Mylog.out"));
            }
        });
        List<String> lines = future.get();
        executorService.shutdownNow();
        logger.clear();
        for (String line : lines ) {
            logger.appendText(line + "\n");
        }
    }

    @SuppressWarnings("NestedAssignment")
    private List<String> read(File file) {
        List<String> lines = new ArrayList<String>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return lines;
    }
}
