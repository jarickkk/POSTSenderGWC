package org.infotechgroup.ps.model;


import java.util.HashMap;

/**
 * Created by User on 11.07.2017.
 */
public class Task {

    private String layerName;
    private String numberOfTasksToUse;
    private String typeOfOperation;
    private String gridSet;
    private String format;
    private String zoomStart;
    private String zoomStop;
    private HashMap<String, String> modParameters;
    private Double minX, minY, maxX, maxY;
    private GeoConnect geoConnect;

    public Task (GeoConnect geo) {
        try {
            this.geoConnect = geo;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(){
        try {
            geoConnect.setLayerToCache(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    String getLayerName() {
        return layerName;
    }

    String getNumberOfTasksToUse() {
        return numberOfTasksToUse;
    }

    String getTypeOfOperation() {
        return typeOfOperation;
    }

    String getGridSet() {
        return gridSet;
    }

    String getFormat() {
        return format;
    }

    String getZoomStart() {
        return zoomStart;
    }

    String getZoomStop() {
        return zoomStop;
    }

    HashMap<String, String> getModParameters() {
        return modParameters;
    }

    Double getMinX() {
        return minX;
    }

    Double getMinY() {
        return minY;
    }

    Double getMaxX() {
        return maxX;
    }

    Double getMaxY() {
        return maxY;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public void setNumberOfTasksToUse(String numberOfTasksToUse) {
        this.numberOfTasksToUse = numberOfTasksToUse;
    }

    public void setTypeOfOperation(String typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    public void setGridSet(String gridSet) {
        this.gridSet = gridSet;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setZoomStart(String zoomStart) {
        this.zoomStart = zoomStart;
    }

    public void setZoomStop(String zoomStop) {
        this.zoomStop = zoomStop;
    }

    public void setModParameters( HashMap<String, String> modParameters) {
        this.modParameters = modParameters;
    }

    public void setMinX(Double minX) {
        this.minX = minX;
    }

    public void setMinY(Double minY) {
        this.minY = minY;
    }

    public void setMaxX(Double maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(Double maxY) {
        this.maxY = maxY;
    }

}
