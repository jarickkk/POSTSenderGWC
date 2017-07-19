package org.infotechgroup.ps.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.infotechgroup.ps.model.GeoConnect;

/**
 * Created by User on 18.07.2017.
 */
public class DialogController {

    private Stage dialogStage;
    private boolean okClicked = false;
    private TasksController tsc;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage, TasksController tc) {
        tsc = tc;
        this.dialogStage = dialogStage;
    }


    @FXML
    private void handleOk() {
        try {
            GeoConnect.getInstance().killAll();
            tsc.refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
            okClicked = true;
            dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
