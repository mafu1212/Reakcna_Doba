package base;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class login_controller {

    @FXML
    public TextField login;

    Main main = new Main();

    public void onContinueButtonClick() throws IOException {

        main.player = login.getText();
        main.ImportRecords();


        FXMLLoader login = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = login.load();

        Main.primary.setScene(new Scene(root, 428, 227));
        Main.primary.setMinWidth(428);
        Main.primary.setMinHeight(227);
    }
}
