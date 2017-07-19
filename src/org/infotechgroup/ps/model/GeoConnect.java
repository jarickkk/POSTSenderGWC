package org.infotechgroup.ps.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by User on 10.07.2017.
 */

public class GeoConnect {

    private static GeoConnect geoConnectSingleton;
    private List<String> cookies;
    private HttpURLConnection connection;
    private static String GWCLink;
    private static String GSREST;
    private static String GWCREST;
    private static String USERNAME;
    private static String PASSWORD;
    private static String HOST;
    private static int TIMEOUT = 5000;
    private final static String LOGFILE = "LayersTestLog.txt";
    private static boolean layersAvailable = false;
    private static ObservableList<Layer> layerstList;
    private static ObservableList<TaskOnGWC> tasksList;
    private static ArrayList<String> listOfGroupsNames = new ArrayList<>();
    private static ArrayList<String> listOfLayersNames = new ArrayList<>();

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";

    public static GeoConnect getInstance(){
        if(geoConnectSingleton == null)
            geoConnectSingleton = new GeoConnect();

        return geoConnectSingleton;
    }

    private GeoConnect(){
        System.out.println("Connecting");
    }

    public boolean disconnect(){
        return true;
    }

    public String connect() throws Exception {
        CookieHandler.setDefault(new CookieManager());

        if(!HOST.startsWith("http://")) HOST = "http://" + HOST;
        String URLink = HOST + "/geoserver/web/";                                                                            //URLink для получения первичного cookies  с сессией
        String loginGS = HOST + "/geoserver/j_spring_security_check";                                                       //для логина
        GWCLink = HOST + "/geoserver/gwc/rest/seed/";
        GSREST = HOST + "/geoserver/rest";
        GWCREST = HOST + "/geoserver/gwc/rest";

        String postParams = this.getFormParams();                                                                                           //устанавливаем параметры для авторизации

        System.out.println(HOST + "\n" + URLink + "\n" + GWCLink);

        String result = "No connection";

        this.getPageContent(URLink);                                                                                  // Посылаем GET запрос для получения сессии
        this.sendPost(loginGS, postParams);                                                                            // отправляем POST запрос для аутентификации
        fillLayersList();
        if(layersAvailable)
            result = "Connected to " + HOST;

        String layersLink = HOST + "/geoserver/web/wicket/bookmarkable/org.geoserver.web.data.layer.LayerPage";
        String res = this.getPageAuth(layersLink);                                                                   //метод GET после авторизации
        //System.out.println(res);

        return result;
    }

    boolean setLayerToCache( Task task) throws Exception{
        URL link = new URL(GWCLink + task.getLayerName());
        String postParams = setPostParams(task);
        System.out.println(link.toString());
        connection = (HttpURLConnection) link.openConnection();
        connection.setConnectTimeout(TIMEOUT);

        // создаем POST запрос на основе данных POST запроса из браузера (получено вручную)
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
        connection.setRequestProperty("Cache-Control", "max-age=0" );
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if(this.cookies != null && !this.cookies.isEmpty())
            connection.addRequestProperty("Cookie", this.cookies.get(0));
        else return false;

        connection.setRequestProperty("Upgrade-Insecure-Requests" , "1");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        connection.setDoOutput(true);
        connection.setDoInput(true);

        DataOutputStream connectionOut = new DataOutputStream(connection.getOutputStream());
        System.out.println(postParams);
        connectionOut.writeBytes(postParams);
        connectionOut.flush();
        connectionOut.close();

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        return true;
    }               //POST

    public void killAll() throws Exception{
        URL link = new URL(HOST + "/geoserver/gwc/rest/seed");
        String postParams = "kill_all=all";
        System.out.println(link.toString());
        connection.setConnectTimeout(TIMEOUT);
        connection = (HttpURLConnection) link.openConnection();

        connection.setUseCaches(false);
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");

        connection.setRequestProperty("Cache-Control", "max-age=0" );
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if(this.cookies != null && !this.cookies.isEmpty())
            connection.addRequestProperty("Cookie", this.cookies.get(0));
        else return;

        connection.setRequestProperty("Upgrade-Insecure-Requests" , "1");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        connection.setDoOutput(true);
        connection.setDoInput(true);

        // Отправляем POST
        DataOutputStream connectionOut = new DataOutputStream(connection.getOutputStream());
        System.out.println(postParams);
        connectionOut.writeBytes(postParams);
        connectionOut.flush();
        connectionOut.close();

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
    }  //kill all tasks

    public void killTask(String ID) throws Exception{
        URL link = new URL(GWCLink );
        if(ID.equals("Non"))
            return;
        String postParams = "kill_thread=1&thread_id="+ID;
        System.out.println(link.toString());
        connection.setConnectTimeout(TIMEOUT);
        connection = (HttpURLConnection) link.openConnection();

        connection.setUseCaches(false);
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");

        connection.setRequestProperty("Cache-Control", "max-age=0" );
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if(this.cookies != null && !this.cookies.isEmpty())
            connection.addRequestProperty("Cookie", this.cookies.get(0));
        else return;

        connection.setRequestProperty("Upgrade-Insecure-Requests" , "1");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        connection.setDoOutput(true);
        connection.setDoInput(true);

        // Отправляем POST
        DataOutputStream connectionOut = new DataOutputStream(connection.getOutputStream());
        System.out.println(postParams);
        connectionOut.writeBytes(postParams);
        connectionOut.flush();
        connectionOut.close();

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
    }

    private void sendPost(String url, String postParams) throws Exception {

        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setConnectTimeout(TIMEOUT);
        // создаем POST запрос на основе данных POST запроса из браузера (получено вручную)
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");


        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");

        connection.setRequestProperty("Cache-Control", "max-age=0" );
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if(this.cookies != null && !this.cookies.isEmpty())
            connection.addRequestProperty("Cookie", this.cookies.get(0));
        else return;

        connection.setRequestProperty("Upgrade-Insecure-Requests" , "1");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        connection.setDoOutput(true);
        connection.setDoInput(true);

        // Отправляем POST
        DataOutputStream connectionOut = new DataOutputStream(connection.getOutputStream());
        connectionOut.writeBytes(postParams);
        connectionOut.flush();
        connectionOut.close();

        int responseCode = connection.getResponseCode();


    }

    private String sendTaskPost(String url, String postParams) throws Exception {

        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setConnectTimeout(TIMEOUT);

        // создаем POST запрос на основе данных POST запроса из браузера (получено вручную)
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Cache-Control", "max-age=0" );
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        if (this.cookies != null && !this.cookies.isEmpty())
            connection.addRequestProperty("Cookie", this.cookies.get(0));
        else return "";

        connection.setDoOutput(true);
        connection.setDoInput(true);

        // Отправляем POST
        DataOutputStream connectionOut = new DataOutputStream(connection.getOutputStream());
        connectionOut.writeBytes(postParams);

        connectionOut.flush();
        connectionOut.close();

        StringBuffer response = new StringBuffer();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


        return response.toString();
    }

    private void getPageContent(String url) throws Exception {

        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setConnectTimeout(TIMEOUT);

        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        setCookies(connection.getHeaderFields().get("Set-Cookie"));                                                 // Get the response cookies

    }

    private String getFormParams() throws UnsupportedEncodingException {
        String post = "username=" + USERNAME + "&password=" + PASSWORD;
        System.out.println(post);
        return post;
    }

    private String getPageAuth(String url) throws Exception{         //GET запрос после авторизации (уже имеются cookies)

        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();

        connection.setConnectTimeout(TIMEOUT);

        connection.setRequestMethod("GET");

        connection.setUseCaches(false);


        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        if (cookies != null && !cookies.isEmpty()) {
            connection.setRequestProperty("Cookies" , this.cookies.get(0));

        }else {
            return "";
        }

        //int responseCode = connection.getResponseCode();
        //System.out.print("\nGET request to URLink : " + url);
        //System.out.println("Response Code: " + responseCode);

        BufferedReader in =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    private String setPostParams(Task task){
        StringBuilder builder = new StringBuilder("threadCount=").append(task.getNumberOfTasksToUse());
        builder.append("&type=").append(task.getTypeOfOperation());
        builder.append("&gridSetId=").append(task.getGridSet().replaceAll(":", "%3A"));
        builder.append("&format=").append(task.getFormat().replaceAll("/", "%2F"));
        builder.append("&zoomStart=").append(task.getZoomStart());
        builder.append("&zoomStop=").append(task.getZoomStop());
        if(!task.getModParameter().equals(""))
            builder.append("&parameter_STYLES=").append(task.getModParameter());
        builder.append("&minX=");
        if(task.getMinX() != null) builder.append(task.getMinX() );
        builder.append("&minY=");
        if(task.getMinY() != null) builder.append(task.getMinY());
        builder.append("&maxX=");
        if(task.getMaxX() != null) builder.append(task.getMaxX());
        builder.append("&maxY=");
        if(task.getMaxY() != null) builder.append(task.getMaxY());
        return builder.toString().trim();
    }

    private ObservableList<Layer> fillListsOfLayersGroups(){
        ObservableList<Layer> resultList = FXCollections.observableArrayList();

        if(listOfGroupsNames.size() > 0){
            if(listOfGroupsNames.get(0).equals("No Groups"))
                System.out.println("NO GROUPS");
            else {
                resultList.add(new Layer("GROUPS:"));
                for (String s : listOfGroupsNames) {
                    Layer newL = new Layer(s);
                    setUpLayersFields(newL);
                    resultList.add(newL);
                }
                resultList.add(new Layer("LAYERS:"));
            }
        }
        if(listOfLayersNames.size() > 0){

            for(String s : listOfLayersNames){
                boolean ok = true;
                for(String g : listOfGroupsNames){
                    if(s.equals(g)) ok = false;                   // show layer in Layers list if it NOT IN Groups list
                }
                if(ok) {
                    Layer newL = new Layer(s);
                    setUpLayersFields(newL);
                    resultList.add(newL);
                }
            }
        }
        return resultList;
    }

    private ArrayList<String> parseForGroups(String res){
        ArrayList<String> newList = new ArrayList<>();
        Document html = Jsoup.parse(res);

        Elements liS = html.select("li");
        if(liS.size() <= 0) {
            newList.add("No available groups");
            layersAvailable = false;
        }else{
            for(int zz = 0; zz < liS.size(); zz++){
                String string = liS.get(zz).text().trim();
                System.out.println(string);
                newList.add(string);
            }
            layersAvailable = true;

        }
        return newList;
    }

    private ArrayList<String> parseForLayers(String res){
        ArrayList<String> newList = new ArrayList<>();
        Document html = Jsoup.parse(res);

        Elements nameS = html.select("name");
        if(nameS.size() <= 0) {
            newList.add("No available layers");
            layersAvailable = false;
        }else{
            for(int zz = 0; zz < nameS.size(); zz++){
                String string = nameS.get(zz).text().trim();
                System.out.println(string);
                newList.add(string);
            }
            layersAvailable = true;
        }
        return newList;
    }

    private void setUpLayersFields(Layer l) {
        String layerURL = HOST + "/geoserver/gwc/rest/seed/" + l.getLayerName();
        try {
            String pageResult = getPageAuth(layerURL);

            //System.out.println(pageResult);
            Document html = Jsoup.parse(pageResult);

            Elements tbodyS = html.select("tbody");
            Elements rows;
            if(tbodyS.size() <= 0)
                return;
            Element tbody = tbodyS.get(1);

            if(tbody.select("tr").select("td").first().text().equals("Id"))
                tbody = tbodyS.get(2);
            rows = tbody.select("tr");

            for (int zz = 0; zz < 6; zz++) {
                ObservableList<String> resultList = FXCollections.observableArrayList();
                Elements options = rows.get(zz).select("select").select("option");
                for(Element option : options){
                    resultList.add(option.val());
                }
                switch (zz){
                    case 0 : l.setNumberOfTasks(resultList);
                        break;
                    case 1 : l.setTypeOfOperation(resultList);
                        break;
                    case 2 : l.setGridSet(resultList);
                        break;
                    case 3 : l.setFormat(resultList);
                        break;
                    case 4 : l.setZoomStart(resultList);
                        break;
                    case 5 : l.setZoomStop(resultList);
                }
            }
            if (rows.size() > 8) {
                ObservableList<String> resultList = FXCollections.observableArrayList();
                Elements options = rows.get(6).select("select").select("option");
                for(Element option : options){
                    resultList.add(option.val());

                }
                l.setParameter(resultList);
            }
            Elements ulS = html.select("ul");
            if(ulS.size() >= 3){
                Elements liS = ulS.get(2).select("li");
                HashMap<String, ArrayList<String>> hash = new HashMap<>();
                for(int i = 0 ; i < liS.size(); i++){
                    String s = "";
                    ArrayList<String> list = new ArrayList<>();
                    String[] arr = liS.get(i).text().split(":");

                    s+=arr[0]+":"+arr[1];
                    arr = arr[2].trim().split(",");
                    for(int yy = 0; yy < 4; yy++){
                        list.add(arr[yy]);
                    }
                    hash.put(s, list);
                }
                l.setMaxBoundsText(hash);
            }
            l.setFilled(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ObservableList parseForTasksInGWC(String res){
        ObservableList<TaskOnGWC> resultList = FXCollections.observableArrayList();

        Document html = Jsoup.parse(res);

        Elements tbodyS = html.select("tbody");
        if(tbodyS.size() < 2) {
            resultList.add(new TaskOnGWC("Non","No available tasks!"));
        }else{
            Element tbody = tbodyS.get(1);
            Elements rows = tbody.select("tr");
            if(!rows.get(0).select("td").first().text().equals("Id"))
                resultList.add(new TaskOnGWC("Non","No available tasks!"));
            else {
                if (rows.size() > 1) {
                    for(int zz = 1; zz < rows.size();zz+=2){
                        Elements tdS = rows.get(zz).select("td");
                        if(tdS.size() > 9){
                            TaskOnGWC t = new TaskOnGWC(tdS.get(0).text(), tdS.get(1).text(), tdS.get(2).text(), tdS.get(3).text(), tdS.get(4).text(), tdS.get(5).text(), tdS.get(6).text(), tdS.get(7).text(), tdS.get(8).text());
                            resultList.add(t);
                        }else{
                            System.out.println("ERROR IN TASK TABLE PARSING!");
                        }
                    }

                } else {

                    resultList.add(new TaskOnGWC("Non","No available tasks!"));
                }
            }
        }


        return resultList;
    }

    public ObservableList getTasksList(){
        return tasksList;
    }

    public void fillTasksList(){
        tasksList = FXCollections.observableArrayList();
        try {
            if (!listOfLayersNames.isEmpty()) {
                String tasksURL = HOST + "/geoserver/gwc/rest/seed/" + listOfLayersNames.get(0);
                String postParams = "list=all";
                String result = sendTaskPost(tasksURL, postParams);
                //System.out.println(result);
                tasksList = parseForTasksInGWC(result);
            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    private void fillLayersList(){
       layerstList = FXCollections.observableArrayList();
        try {
            listOfGroupsNames = parseForGroups(getPageAuth(GSREST+"/layergroups"));
            listOfLayersNames = parseForLayers(getPageAuth(GWCREST + "/layers"));
            layerstList = fillListsOfLayersGroups();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<Layer> getLayersList(){
        return layerstList;
    }

    public static void setUSERNAME(String USERNAME) {
        GeoConnect.USERNAME = USERNAME;
    }

    public static void setPASSWORD(String PASSWORD) {
        GeoConnect.PASSWORD = PASSWORD;
    }

    public static void setHOST(String HOST) {
        GeoConnect.HOST = HOST;
    }


}
