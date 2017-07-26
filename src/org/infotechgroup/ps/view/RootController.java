package org.infotechgroup.ps.view;

import javafx.fxml.FXML;
import org.infotechgroup.ps.MainApp;

public class RootController {

    private static boolean isTabOpen = false;
    private MainApp mainApp;

    @FXML
    private void connectController() {
        if (mainApp != null) {                  //FIXED: tabPane call this method after RootController creating, mainApp is null at that moment
                mainApp.showConnectOverview();
        }
    }

    @FXML
    private void layersController() {
        if (!isTabOpen) {
            mainApp.showLayersOverview();
            isTabOpen = true;
        } else
            isTabOpen = false;
    }

    @FXML
    private void tasksController() {
        if (!isTabOpen) {
            mainApp.showTasksOverview();
            isTabOpen = true;
        }else
            isTabOpen =false;
    }

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
