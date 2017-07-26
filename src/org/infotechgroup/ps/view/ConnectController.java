package org.infotechgroup.ps.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.controlsfx.control.textfield.TextFields;
import org.infotechgroup.ps.MainApp;
import org.infotechgroup.ps.model.GeoConnect;

import java.io.*;
import java.util.ArrayList;

public class ConnectController {

    private MainApp mainApp;

    @FXML
    private TextField host;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label status;
    @FXML
    private Button disconnect;
    @FXML
    private ProgressIndicator loading;
    @FXML
    private TextArea textArea;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    private ArrayList<String> autocompletion;
    private static String connectionStatus = "";


    @FXML
    private void connect(){
        GeoConnect g = GeoConnect.getInstance();
        String buffer = host.getText();
        if(buffer.isEmpty())
            buffer = host.getPromptText();
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("hosts.txt", true));
            if(!autocompletion.contains(buffer))
                fileWriter.write("\n" + buffer);
            fileWriter.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        //buffer = buffer.split("//")[0];
        g.setHOST(buffer.trim());

        buffer = username.getText();
        if(buffer.isEmpty())
            buffer = username.getPromptText();
        g.setUSERNAME(buffer);

        buffer = password.getText();
        if(buffer.isEmpty())
            buffer = password.getPromptText();
        g.setPASSWORD(buffer);

        try {
            connectionStatus = g.connect();
        }catch (Exception e){ e.printStackTrace();}
        fillLabel(connectionStatus);
    }

    @FXML
    private void pressLoad(){
        setLoading(true);
    }

    private void setLoading(boolean b){
        status.setVisible(!b);
        loading.setVisible(b);
    }


    @FXML
    private void disconnect(){
        GeoConnect.getInstance().disconnect();
    }

    public ConnectController(){
    }

    @FXML
    public void initialize(){
        fillAutoCompletion();
        status.setText(connectionStatus);
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void fillLabel(String text){
        if(text.contains("Authorized")){
            status.setTextFill(Color.GREEN);
            disconnect.setDisable(false);
            mainApp.setTitle("GeoWebCache " + text);
        }else{
            status.setTextFill(Color.RED);
            mainApp.setTitle("GeoWebCache");
        }

        setLoading(false);
        status.setText(text);


    }

    @FXML
    private void edit(){
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("hosts.txt"));
            StringBuilder str = new StringBuilder();

            String line;
            while ((line = fileReader.readLine()) != null) {
                str.append(line).append("\n");
            }

            fileReader.close();
            System.out.println(str.toString());
            textArea.setText(str.toString());
            textArea.setVisible(true);
            saveButton.setVisible(true);
            editButton.setVisible(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void save(){
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("hosts.txt"));
            String str = textArea.getText();
            fileWriter.write(str);
            fileWriter.close();
            editButton.setVisible(true);
            saveButton.setVisible(false);
            textArea.clear();
            textArea.setVisible(false);
            mainApp.showConnectOverview();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void fillAutoCompletion(){
        BufferedReader file;
        try{
            file = new BufferedReader(new FileReader("hosts.txt"));
            autocompletion = new ArrayList<>();
            String line;
            while((line = file.readLine()) != null){
                autocompletion.add(line);
            }
            file.close();
            System.out.println(autocompletion);
            TextFields.bindAutoCompletion(host, autocompletion);

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
