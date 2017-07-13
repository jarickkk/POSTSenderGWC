package org.infotechgroup.ps.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.infotechgroup.ps.MainApp;
import org.infotechgroup.ps.model.GeoConnect;
import org.infotechgroup.ps.model.Task;

/**
 * Created by User on 10.07.2017.
 */
public class LayersManualController {

    private Task task;

    private MainApp mainApp;

    @FXML
    private TextField layerName;
    @FXML
    private TextField numberOfTask;
    @FXML
    private TextField type;
    @FXML
    private TextField gridSet;
    @FXML
    private TextField format;
    @FXML
    private TextField zoomStart;
    @FXML
    private TextField zoomStop;
    @FXML
    private TextField modParametr;
    @FXML
    private TextField minX;
    @FXML
    private TextField minY;
    @FXML
    private TextField maxX;
    @FXML
    private TextField maxY;

    public LayersManualController(){

    }



    @FXML
    public void initialize(){

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    @FXML
    private void submit() {
        task = new Task(GeoConnect.getInstance());
        task.setLayerName(layerName.getText());
        task.setNumberOfTasksToUse(numberOfTask.getText());
        task.setTypeOfOperation(type.getText());
        task.setGridSet(gridSet.getText());
        task.setFormat(format.getText());
        task.setZoomStart(zoomStart.getText());
        task.setZoomStop(zoomStop.getText());
        task.setModParametr(modParametr.getText());

        String buffer = minX.getText();
        if(buffer.equals("")) {task.setMinX(null);
        } else task.setMinX(Float.parseFloat(minX.getText()));

        buffer = minY.getText();
        if(buffer.equals("")) task.setMinX(null);
        else task.setMinY(Float.parseFloat(minY.getText()));

        buffer = maxX.getText();
        if(buffer.equals("")) task.setMinX(null);
        else task.setMaxX(Float.parseFloat(maxX.getText()));

        buffer = maxY.getText();
        if(buffer.equals("")) task.setMinX(null);
        else task.setMaxY(Float.parseFloat(maxY.getText()));

        task.send();
        System.out.println("New Task Created");
    }



}
