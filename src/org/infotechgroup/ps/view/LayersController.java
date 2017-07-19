package org.infotechgroup.ps.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.infotechgroup.ps.MainApp;
import org.infotechgroup.ps.model.GeoConnect;
import org.infotechgroup.ps.model.Layer;
import org.infotechgroup.ps.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 10.07.2017.
 */
public class LayersController {
    @FXML
    private TableView<Layer> layerTableView;
    @FXML
    private TableColumn<Layer, String> column;
    private String layerName;
    @FXML
    private ChoiceBox<String> numberOfTasks;
    @FXML
    private ChoiceBox<String> typeOfOperation;
    @FXML
    private ChoiceBox<String> gridSet;
    @FXML
    private ChoiceBox<String> format;
    @FXML
    private ChoiceBox<String> zoomStart;
    @FXML
    private ChoiceBox<String> zoomStop;
    @FXML
    private ChoiceBox<String> modifiableParam;
    @FXML
    private TextField minX;
    @FXML
    private TextField minY;
    @FXML
    private TextField maxX;
    @FXML
    private TextField maxY;
    @FXML
    private CheckBox checkBox;
    private HashMap<String, ArrayList<String>> selectedMap;
    private Task task;

    private MainApp mainApp;


    public LayersController(){
    }

    private void showLayerChoices(Layer layer) {
        if (layer != null && layer.isFilled()) {
            layerName = layer.getLayerName();
            selectedMap = layer.getMaxBoundsText();
            numberOfTasks.setItems(layer.getNumberOfTasks()) ;
            numberOfTasks.getSelectionModel().selectFirst();
            typeOfOperation.setItems(layer.getTypeOfOperation());
            typeOfOperation.getSelectionModel().selectFirst();
            gridSet.setItems(layer.getGridSet());
            gridSet.getSelectionModel().selectFirst();
            format.setItems(layer.getFormat());
            format.getSelectionModel().selectFirst();
            zoomStart.setItems(layer.getZoomStart());
            zoomStart.getSelectionModel().selectFirst();
            zoomStop.setItems(layer.getZoomStop());
            zoomStop.getSelectionModel().selectFirst();

            if(layer.getParameter()!=null) {
                modifiableParam.setItems(layer.getParameter());         //TODO: list of mod params
                modifiableParam.getSelectionModel().selectFirst();
                modifiableParam.setVisible(true);
            }else {
                modifiableParam.setValue("");
                modifiableParam.setVisible(false);
            }

        } else {
            ObservableList<String> nul = FXCollections.observableArrayList("");
            numberOfTasks.setItems(nul) ;
            typeOfOperation.setItems(nul);
            gridSet.setItems(nul);
            format.setItems(nul);
            zoomStart.setItems(nul);
            zoomStop.setItems(nul);
            modifiableParam.setItems(nul);
            modifiableParam.setVisible(false);
        }
    }

    private void setMaxBounds(String val){
        if(val != null && !val.equals("")) {
            if (selectedMap.containsKey(val)) {
                ArrayList<String> list = selectedMap.get(val);
                minX.setText(list.get(0));
                minY.setText(list.get(1));
                maxX.setText(list.get(2));
                maxY.setText(list.get(3));
            } else{
            minX.setText("");
            minY.setText("");
            maxX.setText("");
            maxY.setText("");
        }
        }else{
            minX.setText("");
            minY.setText("");
            maxX.setText("");
            maxY.setText("");
        }
    }

    @FXML
    public void initialize(){
       column.setCellValueFactory(cellData -> cellData.getValue().layerNameProperty());
       showLayerChoices(null);
       layerTableView.getSelectionModel().selectedItemProperty().addListener(
               ((observable, oldValue, newValue) -> showLayerChoices(newValue))
       );
       gridSet.getSelectionModel().selectedItemProperty().addListener(
               ((observable, oldValue, newValue) -> setMaxBounds(newValue))
       );

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        layerTableView.setItems(GeoConnect.getInstance().getLayersList());
    }

    @FXML
    private void submit(){
        task = new Task(GeoConnect.getInstance());
        task.setLayerName(layerName);
        task.setNumberOfTasksToUse(numberOfTasks.getValue());
        task.setTypeOfOperation(typeOfOperation.getValue());
        task.setGridSet(gridSet.getValue());
        task.setFormat(format.getValue());
        task.setZoomStart(zoomStart.getValue());
        task.setZoomStop(zoomStop.getValue());
        task.setModParameter(modifiableParam.getValue());

        String buffer = minX.getText();
        if(buffer.equals("")) {task.setMinX(null);
        } else task.setMinX(Double.parseDouble(buffer));

        buffer = minY.getText();
        if(buffer.equals("")) task.setMinX(null);
        else task.setMinY(Double.parseDouble(buffer));

        buffer = maxX.getText();
        if(buffer.equals("")) task.setMinX(null);
        else task.setMaxX(Double.parseDouble(buffer));

        buffer = maxY.getText();
        if(buffer.equals("")) task.setMinX(null);
        else task.setMaxY(Double.parseDouble(buffer));

        task.send();
        System.out.println("New Task Created");
    }

    @FXML
    private void refresh(){
        column.setCellValueFactory(cellData -> cellData.getValue().layerNameProperty());
        showLayerChoices(null);
        layerTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showLayerChoices(newValue))
        );
    }

    @FXML
    private void check(){
        if (checkBox.isSelected()){
            minX.setDisable(false);
            minY.setDisable(false);
            maxX.setDisable(false);
            maxY.setDisable(false);
        }
        else{
            minX.setDisable(true);
            minY.setDisable(true);
            maxX.setDisable(true);
            maxY.setDisable(true);
            setMaxBounds(gridSet.getValue());
        }
    }
}
