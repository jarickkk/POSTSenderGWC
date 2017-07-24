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
import java.util.Map;

/**
 * Created by User on 10.07.2017.
 */
public class LayersController {
    @FXML
    private TableView<Layer> layerTableView;
    @FXML
    private TableColumn<Layer, String> column;
    @FXML
    private TableView<Layer> groupTableView;
    @FXML
    private TableColumn<Layer, String> column1;
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
    private Label paramName1;
    @FXML
    private Label paramName2;
    @FXML
    private Label paramName3;
    @FXML
    private Label paramName4;
    @FXML
    private Label paramName5;

    @FXML
    private Label modifiableParameters;
    private ArrayList<Label> listOfLabels;

    @FXML
    private ChoiceBox<String> modifiableParam1;
    @FXML
    private ChoiceBox<String> modifiableParam2;
    @FXML
    private ChoiceBox<String> modifiableParam3;
    @FXML
    private ChoiceBox<String> modifiableParam4;
    @FXML
    private ChoiceBox<String> modifiableParam5;
    private ArrayList<ChoiceBox<String>> listOfBoxes;

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
    @FXML
    private ScrollPane scrollPane;
    private HashMap<String, ArrayList<String>> selectedMap;

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

            if(layer.getParameters()!=null && layer.getParameters().size() > 0){
                clearModParams();
                int counter = 0;
                for(Map.Entry<String, ObservableList<String>> KeyValue : layer.getParameters().entrySet()){
                    Label l = listOfLabels.get(counter);
                    l.setText(KeyValue.getKey().replace("parameter_", ""));
                    l.setVisible(true);
                    ChoiceBox<String> c = listOfBoxes.get(counter);
                    c.setItems(KeyValue.getValue());
                    c.setVisible(true);
                    c.getSelectionModel().selectFirst();
                    counter++;
                }
                modifiableParameters.setVisible(true);
                scrollPane.setVisible(true);
            }else{
                disableModParams();
            }


        } else {
            ObservableList<String> nul = FXCollections.observableArrayList("");
            numberOfTasks.setItems(nul) ;
            typeOfOperation.setItems(nul);
            gridSet.setItems(nul);
            format.setItems(nul);
            zoomStart.setItems(nul);
            zoomStop.setItems(nul);
            disableModParams();
        }
    }

    private void disableModParams(){
        modifiableParameters.setVisible(false);
        scrollPane.setVisible(false);
        ObservableList<String> nul = FXCollections.observableArrayList("");
        for(ChoiceBox c : listOfBoxes){
          c.setItems(nul);
          c.setVisible(false);
        }
        for (Label l : listOfLabels){
            l.setText("");
            l.setVisible(false);
        }
    }
    private void clearModParams(){
        ObservableList<String> nul = FXCollections.observableArrayList("");
        for(ChoiceBox c : listOfBoxes){
            c.setItems(nul);
            c.setVisible(false);
        }
        for (Label l : listOfLabels){
            l.setText("");
            l.setVisible(false);
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
        listOfLabels = new ArrayList<>();
        {
            listOfLabels.add(paramName1);
            listOfLabels.add(paramName2);
            listOfLabels.add(paramName3);
            listOfLabels.add(paramName4);
            listOfLabels.add(paramName5);
        }
        listOfBoxes = new ArrayList<>();
        {
            listOfBoxes.add(modifiableParam1);
            listOfBoxes.add(modifiableParam2);
            listOfBoxes.add(modifiableParam3);
            listOfBoxes.add(modifiableParam4);
            listOfBoxes.add(modifiableParam5);
        }
        column1.setCellValueFactory(cellData -> cellData.getValue().layerNameProperty());
        column.setCellValueFactory(cellData -> cellData.getValue().layerNameProperty());
        showLayerChoices(null);
        layerTableView.getSelectionModel().selectedItemProperty().addListener(
               ((observable, oldValue, newValue) -> showLayerChoices(newValue))
        );
        groupTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showLayerChoices(newValue))
        );
        gridSet.getSelectionModel().selectedItemProperty().addListener(
               ((observable, oldValue, newValue) -> setMaxBounds(newValue))
        );

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        groupTableView.setItems(GeoConnect.getInstance().getGroupsList());
        layerTableView.setItems(GeoConnect.getInstance().getLayersList());

    }

    @FXML
    private void submit(){
        Task task = new Task(GeoConnect.getInstance());
        task.setLayerName(layerName);
        task.setNumberOfTasksToUse(numberOfTasks.getValue());
        task.setTypeOfOperation(typeOfOperation.getValue());
        task.setGridSet(gridSet.getValue());
        task.setFormat(format.getValue());
        task.setZoomStart(zoomStart.getValue());
        task.setZoomStop(zoomStop.getValue());

        HashMap<String, String> mapOfParameters = new HashMap<>();
        for(int i = 0; i < 5; i++){
            if(listOfBoxes.get(i).isVisible()){
                mapOfParameters.put(listOfLabels.get(i).getText(), listOfBoxes.get(i).getValue());
            }
        }
        task.setModParameters(mapOfParameters);

        String buffer = minX.getText();
        if(buffer.equals("")) {
            task.setMinX(null);
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
