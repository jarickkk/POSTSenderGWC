package org.infotechgroup.ps.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.infotechgroup.ps.MainApp;
import org.infotechgroup.ps.model.GeoConnect;
import org.infotechgroup.ps.model.Layer;

/**
 * Created by User on 10.07.2017.
 */
public class LayersController {
    @FXML
    private TableView<Layer> layerTableView;
    @FXML
    private TableColumn<Layer, String> column;

    private MainApp mainApp;

    public LayersController(){
    }

    @FXML
    public void initialize(){
       column.setCellValueFactory(cellData -> cellData.getValue().layerNameProperty());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        layerTableView.setItems(GeoConnect.getInstance().getLayersList());

    }
}
