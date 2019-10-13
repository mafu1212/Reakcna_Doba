package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.Random;

public class Controller {

    private Random random = new Random();
    private long start = -1;
    private boolean run = false;

    @FXML
    TextField loginName;
    @FXML
    Label mainText;
    @FXML
    Label scoreLabel;
    @FXML
    TableView<Record> database;
    @FXML private TableColumn<Record, String> orderColumn;
    @FXML private TableColumn<Record, String> nameColumn;
    @FXML private TableColumn<Record, String> scoreColumn;

    @FXML
    void onLoginClick() {
        Main.player = loginName.getText();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Main.primary.setScene(new Scene(root));
            Main.primary.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onOkClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Main.primary.setScene(new Scene(root));
            Main.primary.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onExitClick() {
        Main.primary.close();
    }

    @FXML
    void onChangeClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Main.primary.setScene(new Scene(root));
            Main.primary.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onTopClick() {
        try {
            FXMLLoader databaseView = new FXMLLoader(getClass().getResource("database.fxml"));
            Parent root = databaseView.load();
            Main.primary.setScene(new Scene(root));
            Main.primary.show();

            Controller controller = databaseView.getController();
            controller.fullDatabase();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onStartClick() {
        try {
            FXMLLoader gameView = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent root = gameView.load();
            Main.primary.setScene(new Scene(root));
            Main.primary.show();

            Controller control = gameView.getController();

            control.mainText.setText("Ready??");

            Main.primary.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                if(key.getCode()== KeyCode.ENTER) {
                    if (run) {
                        long time = System.currentTimeMillis() - start;
                        if (start == -1) {
                            time = 999999999;
                        }
                        Main.currentRecord = Main.Sort(Main.player, time);
                        Main.SaveRecords();
                        run = false;
                        System.out.println(Main.currentRecord.score);

                        try {
                            FXMLLoader databaseView = new FXMLLoader(getClass().getResource("database.fxml"));
                            Parent rootIn = databaseView.load();
                            Main.primary.setScene(new Scene(rootIn));
                            Main.primary.show();

                            Controller controllerIn = databaseView.getController();
                            controllerIn.userDatabase();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        start = -1;

                    } else {
                        control.mainText.setText("Set?");

                        Task<Void> sleep = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                run = true;
                                try {
                                    Thread.sleep((long)(0.5 + random.nextDouble() * (3 - 0.5)) * 1000);
                                } catch (InterruptedException e) {

                                }
                                return null;
                            }
                        };
                        sleep.setOnSucceeded(event -> {
                            control.mainText.setText("Start!");
                            start = System.currentTimeMillis();
//                                running = true;
                        });
                        new Thread(sleep).start();
                    }
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void userDatabase() {

        orderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        ObservableList<Record> data = FXCollections.observableArrayList();
        int index = Main.database.indexOf(Main.currentRecord);
        for (int i = 0; i < Main.database.size(); i++) {
            if (i <= index + 5 && i >= index - 5) {
                Record record = Main.database.get(i);
                record.order = index + i;
                data.add(record);
            }
        }

        database.getItems().setAll(data);
        scoreLabel.setText("Score: " + Main.currentRecord.score);

    }

    private void fullDatabase() {

        orderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        ObservableList<Record> data = FXCollections.observableArrayList();
        for (int i = 0; i < Main.database.size(); i++) {
            Record record = Main.database.get(i);
            record.order = i + 1;
            data.add(record);
            if (i == 9) break;
        }

        database.getItems().setAll(data);
        //scoreLabel.setText("Score: " + Main.currentRecord.score);

    }
}
