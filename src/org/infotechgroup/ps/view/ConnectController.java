package org.infotechgroup.ps.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.infotechgroup.ps.MainApp;
import org.infotechgroup.ps.model.GeoConnect;

/**
 * Created by User on 11.07.2017.
 */
public class ConnectController {

    private Stage dialogStage;
    private MainApp mainApp;

    @FXML
    private TextField host;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label status;

    @FXML
    private void connect(){
        GeoConnect g = GeoConnect.getInstance();
        String buffer = host.getText();
        if(buffer.isEmpty())
            buffer = host.getPromptText();
        g.setHOST(buffer);

        buffer = username.getText();
        if(buffer.isEmpty())
            buffer = username.getPromptText();
        g.setUSERNAME(buffer);

        buffer = password.getText();
        if(buffer.isEmpty())
            buffer = password.getPromptText();
        g.setPASSWORD(buffer);
      try{
            fillLabel(g.connect());

        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public ConnectController(){
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void initialize(){
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public MainApp getMainApp() {
        return mainApp;
    }


    public void fillLabel(String text){
        status.setText(text);
    }

}
