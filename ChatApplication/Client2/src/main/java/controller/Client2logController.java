package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Client2logController {
    static String userName;
    public TextField txtUserName;
    public AnchorPane ancherpane;
    public  void initialize() {
        txtUserName.setStyle("-fx-background-color: transparent; -fx-text-box-border: transparent; -fx-focus-color: transparent;");
    }
    public void loginOnAction(MouseEvent mouseEvent) throws IOException {
        if(!txtUserName.getText().isBlank()){
            userName=txtUserName.getText ();

            Stage stage = (Stage) ancherpane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Client2.fxml"))));
            stage.setTitle("");
            stage.centerOnScreen();
            stage.show();
        }
    }
}
