package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    static Stage primary;

    static String player;
    static Record currentRecord;

    static ArrayList<Record> database = new ArrayList<>();
    private static SortTheDatabase sorter = new SortTheDatabase();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primary = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primary.setScene(new Scene(root));
        primary.show();
    }

    public static void main(String[] args) {
        ImportRecords();
        launch(args);
    }

    static Record Sort(String who, long record){
        Record rec = new Record(who, record);
        database.add(rec);
        database.sort(sorter);
        return rec;
    }

    private static void ImportRecords(){
        try {
            BufferedReader bufferRead = new BufferedReader(new FileReader("database"));
            String line = bufferRead.readLine();

            while (line != null){
                String[] dataFromLine = line.split(":");
                database.add(new Record(dataFromLine[0], Integer.parseInt(dataFromLine[1])));
                line = bufferRead.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void SaveRecords(){
        try {
            BufferedWriter bufferWrite = new BufferedWriter(new FileWriter("database"));
            for (Record record: database) {
                bufferWrite.write(record.name + ":" + record.score + "\n");
            }
            bufferWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
