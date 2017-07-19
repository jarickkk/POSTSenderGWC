package org.infotechgroup.ps.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by User on 12.07.2017.
 */
public class TaskOnGWC {

    private StringProperty taskID;
    private StringProperty taskName;
    private StringProperty status;
    private StringProperty type;
    private StringProperty estNofTiles;
    private StringProperty tilesComplited;
    private StringProperty timeElapsed;
    private StringProperty timeRemaining;
    private StringProperty taskOfN;



    public TaskOnGWC(String id, String name, String stat, String typ, String est, String coml, String elps, String remain, String ofN){
        this.taskID = new SimpleStringProperty(id);
        this.taskName = new SimpleStringProperty(name);
        this.status = new SimpleStringProperty(stat);
        this.type = new SimpleStringProperty(typ);
        this.estNofTiles = new SimpleStringProperty(est);
        this.tilesComplited = new SimpleStringProperty(coml);
        this.timeElapsed = new SimpleStringProperty(elps);
        this.timeRemaining = new SimpleStringProperty(remain);
        this.taskOfN = new SimpleStringProperty(ofN);
    }

    public TaskOnGWC(String id, String name){
        this.taskID = new SimpleStringProperty(id);
        this.taskName = new SimpleStringProperty(name);
        this.status = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
        this.estNofTiles = new SimpleStringProperty("");
        this.tilesComplited = new SimpleStringProperty("");
        this.timeElapsed = new SimpleStringProperty("");
        this.timeRemaining = new SimpleStringProperty("");
        this.taskOfN = new SimpleStringProperty("");
    }


    public String getTaskName() {
        return taskName.get();
    }

    public StringProperty taskNameProperty(){
        return taskName;
    }
    public StringProperty taskIDProperty(){
        return taskID;
    }
    public StringProperty taskStatusProperty(){
        return status;
    }
    public StringProperty taskTypeProperty(){
        return type;
    }
    public StringProperty taskEstNofTilesProperty(){
        return estNofTiles;
    }
    public StringProperty taskTilesComplitedProperty() {
        return tilesComplited;
    }
    public StringProperty taskTimeElpsProperty(){
        return timeElapsed;
    }
    public StringProperty taskTimeRmnProperty(){
        return timeRemaining;
    }
    public StringProperty taskTaskOfNProperty(){
        return taskOfN;
    }


    public void setTaskName(String tName) {
        this.taskName.set(tName);
    }

}

