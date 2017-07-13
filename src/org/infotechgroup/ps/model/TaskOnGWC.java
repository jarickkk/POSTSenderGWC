package org.infotechgroup.ps.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by User on 12.07.2017.
 */
public class TaskOnGWC {

    private StringProperty taskID;
    private StringProperty taskName;



    public TaskOnGWC(String id, String name){
        this.taskID = new SimpleStringProperty(id);
        this.taskName = new SimpleStringProperty(name);
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
    public void setTaskName(String tName) {
        this.taskName.set(tName);
    }

}

