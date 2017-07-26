package org.infotechgroup.ps.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;

public class Layer {
    private StringProperty layerName;
    private String href;
    private boolean isGroup;
    private ObservableList<String> numberOfTasks;
    private ObservableList<String> typeOfOperation;
    private ObservableList<String> gridSet;
    private ObservableList<String> format;
    private ObservableList<String> zoomStart;
    private ObservableList<String> zoomStop;
    private HashMap<String, ObservableList<String>> parameters;
    private HashMap<String, ArrayList<String>> maxBoundsText;
    private boolean filled = false;

    public Layer(String name){
        this.href = name;
    }

    void setLayerName(String layerName) {
        this.layerName = new SimpleStringProperty(layerName);
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

    void setNumberOfTasks(ObservableList<String> numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public ObservableList<String> getTypeOfOperation() {
        return typeOfOperation;
    }

    void setTypeOfOperation(ObservableList<String> typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    public ObservableList<String> getGridSet() {
        return gridSet;
    }

    void setGridSet(ObservableList<String> gridSet) {
        this.gridSet = gridSet;
    }

    public ObservableList<String> getFormat() {
        return format;
    }

    void setFormat(ObservableList<String> format) {
        this.format = format;
    }

    public ObservableList<String> getZoomStart() {
        return zoomStart;
    }

    void setZoomStart(ObservableList<String> zoomStart) {
        this.zoomStart = zoomStart;
    }

    public ObservableList<String> getZoomStop() {
        return zoomStop;
    }

    void setZoomStop(ObservableList<String> zoomStop) {
        this.zoomStop = zoomStop;
    }

    public HashMap<String, ObservableList<String>> getParameters() {
        return parameters;
    }

    void setParameters(HashMap<String, ObservableList<String>> parameters) {
        this.parameters = parameters;
    }

    public HashMap<String, ArrayList<String>> getMaxBoundsText() {
        return maxBoundsText;
    }

    void setMaxBoundsText(HashMap<String, ArrayList<String>> maxBoundsText) {
        this.maxBoundsText = maxBoundsText;
    }

    public boolean isFilled() {
        return filled;
    }

    void setFilled(boolean filled) {
        this.filled = filled;
    }

    String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    boolean isGroup() {
        return isGroup;
    }

    void setGroup(boolean group) {
        isGroup = group;
    }
}
