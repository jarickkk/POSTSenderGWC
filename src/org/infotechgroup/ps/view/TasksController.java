package org.infotechgroup.ps.view;


import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.infotechgroup.ps.MainApp;
import org.infotechgroup.ps.model.GeoConnect;
import org.infotechgroup.ps.model.TaskOnGWC;



/**
 * Created by User on 10.07.2017.
 */
public class TasksController {
    @FXML
    private TableView<TaskOnGWC> tasksTableView;
    @FXML
    private TableColumn<TaskOnGWC, String> column0;
    @FXML
    private TableColumn<TaskOnGWC, String> column1;
    @FXML
    private TableColumn<TaskOnGWC, String> column2;
    @FXML
    private TableColumn<TaskOnGWC, String> column3;
    @FXML
    private TableColumn<TaskOnGWC, String> column4;
    @FXML
    private TableColumn<TaskOnGWC, String> column5;
    @FXML
    private TableColumn<TaskOnGWC, String> column6;
    @FXML
    private TableColumn<TaskOnGWC, String> column7;
    @FXML
    private TableColumn<TaskOnGWC, String> column8;
    @FXML
    private TextField ID;
    private MainApp mainApp;



    public TasksController(){
    }

    @FXML
    public void initialize(){
        column0.setCellValueFactory(cellData -> cellData.getValue().taskIDProperty());
        column1.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        column2.setCellValueFactory(cellData -> cellData.getValue().taskStatusProperty());
        column3.setCellValueFactory(cellData -> cellData.getValue().taskTypeProperty());
        column4.setCellValueFactory(cellData -> cellData.getValue().taskEstNofTilesProperty());
        column5.setCellValueFactory(cellData -> cellData.getValue().taskTilesComplitedProperty());
        column6.setCellValueFactory(cellData -> cellData.getValue().taskTimeElpsProperty());
        column7.setCellValueFactory(cellData -> cellData.getValue().taskTimeRmnProperty());
        column8.setCellValueFactory(cellData -> cellData.getValue().taskTaskOfNProperty());
        tasksTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> setID(newValue))
        );
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        tasksTableView.setItems(GeoConnect.getInstance().getTasksList());
    }

    @FXML
    private void kill(){
        try {
            GeoConnect.getInstance().killTask(ID.getText());
            refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void killAll(){

        mainApp.showDialog(this);

    }

    @FXML
    public void refresh(){
        try{
            GeoConnect.getInstance().fillTasksList();
            String oldID = ID.getText();
            tasksTableView.setItems(GeoConnect.getInstance().getTasksList());
            ID.setText(oldID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setID(TaskOnGWC t){
        if(t != null)
        ID.setText(t.taskIDProperty().getValue());
        else
            ID.setText("");
    }

}
