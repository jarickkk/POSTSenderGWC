package org.infotechgroup.ps.view;

import javafx.fxml.FXML;
import org.infotechgroup.ps.MainApp;


/**
 * Created by User on 11.07.2017.
 */
public class RootController {

    private MainApp mainApp;

    @FXML
    private void connectController(){
        if(mainApp != null)                           //FIXED: tabPane call this method after RootController creating, mainApp is null at that moment
            mainApp.showConnectOverview();
    }
    @FXML
    private void layersController(){mainApp.showLayersOverview();}
    @FXML
    private void tasksController(){
        mainApp.showTasksOverview();}

    public RootController(){
    }

    @FXML
    public void initialize(){
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        connectController();
    }
}
