package org.infotechgroup.ps;
/**
 * Created by User on 10.07.2017.
 */

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.infotechgroup.ps.model.GeoConnect;
import org.infotechgroup.ps.view.*;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private boolean isTaskView = false;
    private Thread refreshThread ;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("GeoWebCache");
        this.primaryStage.getIcons().add( new Image("org/infotechgroup/ps/geowebcache_logo.png"));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(refreshThread != null)
                    refreshThread.interrupt();
            }
        });
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
            // Loading root controller from .fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            RootController controller = loader.getController();
            controller.setMainApp(this);

            // Show the Scene with RootLayout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

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
            isTaskView = false;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LayersOverview.fxml"));
            AnchorPane layersOverview = loader.load();
            rootLayout.setBottom(null);
            rootLayout.setLeft(null);
            rootLayout.setBottom(layersOverview);
            LayersController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showDialog(TasksController tasskCont) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Dialog.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Kill all");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            DialogController controller = loader.getController();
            controller.setDialogStage(dialogStage, tasskCont);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setTitle(String title){
        primaryStage.setTitle(title);
    }

    public void showConnectOverview(){
        try {
            isTaskView = false;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ConnectOverview.fxml"));
            AnchorPane connectOverview = loader.load();
            rootLayout.setBottom(null);
            rootLayout.setLeft(connectOverview);
            ConnectController connectController = loader.getController();
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
            rootLayout.setBottom(null);
            rootLayout.setLeft(null);
            rootLayout.setBottom(tasksOverview);
            TasksController tasksController = loader.getController();
            tasksController.setMainApp(this);
            isTaskView = true;
            refreshThread = new Thread(){
                public void run(){
                    while(isTaskView){
                        try {
                            tasksController.refresh();
                            sleep(3000);
                        }catch (InterruptedException ie){
                            ie.printStackTrace();
                            System.exit(0);
                        }
                    }
                }
            };
            refreshThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}