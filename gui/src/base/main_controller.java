package base;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class main_controller {

    Main main = new Main();
    Random rn = new Random();

    @FXML
    TableView<Record> tabulka;
    @FXML private TableColumn<Record, String> poradieColumn;
    @FXML private TableColumn<Record, String> nameColumn;
    @FXML private TableColumn<Record, String> scoreColumn;

    @FXML
    Label scoreLabel;

    boolean running = false;
    long startTime = -1;

    @FXML
    public Label label;

    @FXML
    public Button click;

    public void onStartButtonAction() throws IOException {
        FXMLLoader gameview = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = gameview.load();
        Main.primary.setScene(new Scene(root));
        Main.primary.show();

        main_controller controller = gameview.getController();

        controller.label.setText("Ready?");

        Main.primary.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.ENTER) {
                if (running) {
                    long time = System.currentTimeMillis() - startTime;
                    if (startTime == -1) {
                        time = 1000000;
                    }
                    main.currentRecord = main.Sort(main.player, time);

                    Main.SaveRecords(main.currentRecord);
                    running = false;
                    System.out.println(Main.currentRecord.record);

                    startTime = -1;

                } else {
                    controller.label.setText("Set...");

                    Task<Void> sleeper = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            running = true;
                            try {
                                Thread.sleep((long)(0.5 + rn.nextDouble() * (3 - 0.5)) * 1000);
                            } catch (InterruptedException e) {
                            }
                            return null;
                        }
                    };
                    sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            controller.label.setText("GO!");
                            startTime = System.currentTimeMillis();
//                                running = true;
                        }
                    });
                    new Thread(sleeper).start();
                }
            }
        });
    }

    public void onQuitButtonAction() {
        System.exit(0);
    }

    public void onResultsButtonAction() {

        try {
            FXMLLoader tabulkaview = new FXMLLoader(getClass().getResource("tabulka.fxml"));
            Parent rooterino = tabulkaview.load();
            Main.primary.setScene(new Scene(rooterino));
            Main.primary.show();

            main_controller controllerino = tabulkaview.getController();
            controllerino.fullTabulka();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    void fullTabulka() {

        poradieColumn.setCellValueFactory(new PropertyValueFactory<>("poradie"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        ObservableList<Record> data = FXCollections.observableArrayList();
        for (int i = 0; i < Main.tabulka.size(); i++) {
            Record rec = Main.tabulka.get(i);
            rec.poradie = i + 1;
            data.add(rec);
            if (i == 9) break;
        }

        tabulka.getItems().setAll(data);
        scoreLabel.setText("Score: " + Main.currentRecord.record);

    }

    @FXML
    void onOkClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Main.primary.setScene(new Scene(root));
            Main.primary.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
