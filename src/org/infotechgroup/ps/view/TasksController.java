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
    private TableColumn<TaskOnGWC, String> column;
    @FXML
    private TableColumn<TaskOnGWC, String> column1;
    @FXML
    private TextField ID;

    private MainApp mainApp;

    public TasksController(){

    }

    @FXML
    public void initialize(){
        column.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        column1.setCellValueFactory(cellData -> cellData.getValue().taskIDProperty());
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
        try {
            GeoConnect.getInstance().killAll("");
            refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void refresh(){
        try{
            GeoConnect.getInstance().fillTasksList();
            tasksTableView.setItems(GeoConnect.getInstance().getTasksList());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
