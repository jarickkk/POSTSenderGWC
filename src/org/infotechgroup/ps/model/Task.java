package org.infotechgroup.ps.model;

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
    private String modParameter;
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

    public String getLayerName() {
        return layerName;
    }

    public String getNumberOfTasksToUse() {
        return numberOfTasksToUse;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    public String getGridSet() {
        return gridSet;
    }

    public String getFormat() {
        return format;
    }

    public String getZoomStart() {
        return zoomStart;
    }

    public String getZoomStop() {
        return zoomStop;
    }

    public String getModParameter() {
        return modParameter;
    }

    public Double getMinX() {
        return minX;
    }

    public Double getMinY() {
        return minY;
    }

    public Double getMaxX() {
        return maxX;
    }

    public Double getMaxY() {
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

    public void setModParameter(String modParameter) {
        this.modParameter = modParameter;
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
