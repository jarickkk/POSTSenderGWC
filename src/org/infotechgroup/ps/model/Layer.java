package org.infotechgroup.ps.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;

public class Layer {
    private StringProperty layerName;
    private ObservableList<String> numberOfTasks;
    private ObservableList<String> typeOfOperation;
    private ObservableList<String> gridSet;
    private ObservableList<String> format;
    private ObservableList<String> zoomStart;
    private ObservableList<String> zoomStop;
    private ObservableList<String> parameter;
    private HashMap<String, ArrayList<String>> maxBoundsText;
    private boolean filled = false;

    public Layer(String name){
        this.layerName = new SimpleStringProperty(name);
    }

    public String getLayerName() {
        return layerName.get();
    }

    public StringProperty layerNameProperty() {
        return layerName;
    }

    public ObservableList<String> getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(ObservableList<String> numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public ObservableList<String> getTypeOfOperation() {
        return typeOfOperation;
    }

    public void setTypeOfOperation(ObservableList<String> typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    public ObservableList<String> getGridSet() {
        return gridSet;
    }

    public void setGridSet(ObservableList<String> gridSet) {
        this.gridSet = gridSet;
    }

    public ObservableList<String> getFormat() {
        return format;
    }

    public void setFormat(ObservableList<String> format) {
        this.format = format;
    }

    public ObservableList<String> getZoomStart() {
        return zoomStart;
    }

    public void setZoomStart(ObservableList<String> zoomStart) {
        this.zoomStart = zoomStart;
    }

    public ObservableList<String> getZoomStop() {
        return zoomStop;
    }

    public void setZoomStop(ObservableList<String> zoomStop) {
        this.zoomStop = zoomStop;
    }

    public ObservableList<String> getParameter() {
        return parameter;
    }

    public void setParameter(ObservableList<String> parameter) {
        this.parameter = parameter;
    }

    public HashMap<String, ArrayList<String>> getMaxBoundsText() {
        return maxBoundsText;
    }

    public void setMaxBoundsText(HashMap<String, ArrayList<String>> maxBoundsText) {
        this.maxBoundsText = maxBoundsText;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
