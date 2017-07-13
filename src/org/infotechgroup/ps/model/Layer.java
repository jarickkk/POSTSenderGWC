package org.infotechgroup.ps.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Layer {
    private StringProperty layerName;
    private StringProperty parameter;

    public Layer(String name){
        this.layerName = new SimpleStringProperty(name);
    }

    public String getLayerName() {
        return layerName.get();
    }

    public StringProperty layerNameProperty() {
        return layerName;
    }


    public void setLayerName(String layerName) {
        this.layerName.set(layerName);
    }

    public String getParameter() {
        return parameter.get();
    }

    public StringProperty parameterProperty() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter.set(parameter);
    }
}
