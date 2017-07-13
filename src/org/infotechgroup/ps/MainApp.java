package org.infotechgroup.ps;
/**
 * Created by User on 10.07.2017.
 */

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.infotechgroup.ps.model.GeoConnect;
import org.infotechgroup.ps.model.Layer;
import org.infotechgroup.ps.view.*;

public class MainApp extends Application {

    private GeoConnect geoConnect;
    private ObservableList<Layer> layers = FXCollections.observableArrayList();             //for test

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        layers.add(new Layer("No connect to Geoserver!"));                  //for test
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("GeoWebCache");

        initRootLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Init Stage - Base window
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Show List of Layers in LayerOverview
     */
    public void showLayersOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LayersOverview.fxml"));
            AnchorPane layersOverview = loader.load();

            rootLayout.setBottom(layersOverview);

            LayersController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showLayersManualOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LayersManualOverview.fxml"));
            AnchorPane layersOverview = loader.load();

            rootLayout.setBottom(layersOverview);
            LayersManualController layersManualController = new LayersManualController();
            layersManualController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showConnectOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ConnectOverview.fxml"));
            AnchorPane connectOverview = loader.load();

            rootLayout.setBottom(connectOverview);
            ConnectController connectController = new ConnectController();
            connectController.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTasksOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TasksOverview.fxml"));
            AnchorPane tasksOverview = loader.load();
            GeoConnect.getInstance().fillTasksList();
            rootLayout.setBottom(tasksOverview);
            TasksController tasksController = loader.getController();
            tasksController.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GeoConnect getGeoConnect() {
        return geoConnect;
    }

    public void setGeoConnect(GeoConnect geoConnect) {
        this.geoConnect = geoConnect;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setLayers(ObservableList list){
        this.layers = list;
    }

    public ObservableList<Layer> getLayers() {
        return layers;
    }
}