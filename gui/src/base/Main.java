package base;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    static Stage primary;
    static Record currentRecord;

    static String player;
    static BufferedWriter bf;

    static ArrayList<Record> tabulka = new ArrayList();
    static Sort sorter = new Sort();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primary = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Reakcna doba");
        primaryStage.setScene(new Scene(root, 343, 186));
        primaryStage.setMinHeight(186);
        primaryStage.setMinWidth(343);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public Main() {
        ImportRecords();
    }

    public void ImportRecords() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("records"));
            String line = br.readLine();

            while (line != null){
                String[] data = line.split(":");
                tabulka.add(new Record(data[0], Integer.parseInt(data[1])));
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Record Sort(String who, long record){
        Record rec = new Record(who, record);
        tabulka.add(rec);
        tabulka.sort(sorter);
        return rec;
    }


    public static void SaveRecords(Record record){
        for (int i = 0; i < tabulka.size(); i++) {
            Record rec = tabulka.get(i);
            String indStr = (i + 1) + ".";
            System.out.printf("%-6s %-25s %d\n", indStr, rec.name, rec.record);
            if (i == 9) break;
        }
        try {
            bf = new BufferedWriter(new FileWriter("records"));
            for (Record rec: tabulka) {
                bf.write(rec.name + ":" + rec.record + "\n");
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
