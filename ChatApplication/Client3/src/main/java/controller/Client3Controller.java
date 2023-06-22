package controller;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Client3Controller {
    public TextField txtMassage;
    public Label lblName;
    public VBox vBox2;
    public ScrollPane scrollPaneId;
    public Pane darkimagePane;
    public ImageView darkImage;
    public ImageView lightImageview;
    public ImageView theamChange;
    public ImageView sendBtn;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Socket socket;
    String massage = "";
    static String sendMassage;
    public AnchorPane imogiPane;
    public AnchorPane gifPAne;

    public void initialize() {
        lblName.setText(Client3logController.userName);
        scrollPaneId.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneId.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        txtMassage.setStyle("-fx-background-color: transparent; -fx-text-box-border: transparent; -fx-focus-color: transparent;");
        vBox2.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        scrollPaneId.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        imogiPane.setVisible(false);
        gifPAne.setVisible(false);
        lightImageview.setVisible(false);
        sendBtn.setVisible(false);
        txtMassage.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                sendBtn.setVisible(true);
            }
        });
        Platform.runLater(() -> {
            scrollPaneId.lookup(".viewport").setStyle("-fx-background-color: transparent;");
            scrollPaneId.lookup(".scroll-bar").setStyle("-fx-background-color: transparent;");
            scrollPaneId.lookup(".scroll-bar:vertical").setStyle("-fx-background-color: transparent;");

        });
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3008);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream.writeUTF(Client3logController.userName);
                dataOutputStream.flush();

                while (socket.isConnected()) {
                    massage = dataInputStream.readUTF();

                    if (massage.startsWith("img")) {

                        setImage(massage);

                    } else {
                        setMessage(massage);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void exist(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }
    private void setImage(String massage) {
        Platform.runLater(() -> {

            String[] paths = massage.split("`");
            Image image1 = new Image(paths[1], 100, 300, true, true);
            ImageView image = new ImageView(image1);
            final Group root = new Group();
            final GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);
            gridpane.setVgap(10);
            gridpane.minHeight(30);
            gridpane.maxHeight(200);
            GridPane.setHalignment(image, HPos.CENTER);
            gridpane.add(image, 0, 0);
            gridpane.setAlignment(Pos.CENTER_LEFT);

            root.getChildren().add(gridpane);

            vBox2.getChildren().add(gridpane);

        });
    }

    private void setMessage(String massage) {
        Platform.runLater(() -> {
            TextFlow tempFlow = new TextFlow();
            Text txtName = new Text();
            tempFlow.setStyle("-fx-background-radius: 10;" +
                    "-fx-background-color: rgba(113, 144, 224, 0.85);" +
                    "-fx-font-family: \"Arial Rounded MT Bold\";" +
                    "-fx-font-size: 15px; -fx-padding: 8px; -fx-start-margin: 200px;" +
                    "-fx-text-fill:  #ffffff;");

            txtName.setStyle("-fx-fill: #fff;");
            txtName.setText(massage);
            tempFlow.getChildren().add(txtName);
            tempFlow.setPadding(new Insets(3, 10, 3, 10));

            final Group root = new Group();
            final GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(5));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.minHeight(30);
            txtName.maxHeight(200);
            gridPane.maxHeight(200);

            GridPane.setHalignment(tempFlow, HPos.CENTER);
            gridPane.add(tempFlow, 0, 0);
            gridPane.setAlignment(Pos.CENTER_LEFT);

            root.getChildren().add(gridPane);
            vBox2.getChildren().add(gridPane);

        });

    }
    public void onActionImageSend(MouseEvent mouseEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            String path = file.toURI().toString();
            sendImage(path);
        }
    }

    public void onActionSendMassage(MouseEvent mouseEvent) throws IOException {
        sendMassage = txtMassage.getText();
        Platform.runLater(() -> {

            TextFlow tempFlow = new TextFlow();
            Text txtName = new Text();
            tempFlow.setStyle("-fx-background-radius: 10;" +
                    "-fx-background-color: rgba(0, 201, 167, 0.85);" +
                    "-fx-font-family: \"Arial Rounded MT Bold\";" +
                    "-fx-font-size: 15; -fx-padding: 8; -fx-start-margin: 200 ;" +
                    "-fx-text-fill:  #fff;");

            txtName.setStyle("-fx-fill: #fff;");
            txtName.setText(sendMassage);
            tempFlow.getChildren().add(txtName);
            tempFlow.setPadding(new Insets(3, 10, 3, 10));

            final Group root = new Group();
            final GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(5));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.minHeight(30);
            txtName.maxHeight(200);
            gridPane.maxHeight(200);

            GridPane.setHalignment(tempFlow, HPos.CENTER);
            gridPane.add(tempFlow, 0, 0);
            gridPane.setAlignment(Pos.CENTER_RIGHT);

            root.getChildren().add(gridPane);
            vBox2.getChildren().add(gridPane);

        });

        dataOutputStream.writeUTF(txtMassage.getText());
        dataOutputStream.flush();
        txtMassage.clear();

    }

    public void emojiOnAction(MouseEvent mouseEvent) {
        gifPAne.setVisible(false);
        imogiPane.setVisible(true);

    }

    public void hideEmoji(MouseEvent mouseEvent) {
        imogiPane.setVisible(false);
        gifPAne.setVisible(false);
    }

    public void sunglass(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128526));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void verySad(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128555));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void sad(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128543));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void lovesmile(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128522));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void cute(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128525));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void kissed(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128538));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void exited(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128521));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void shok(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128562));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void emotion2(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128519));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void ghost(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128123));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void angry(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128544));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void smile(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128514));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void largesmile(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128513));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void silly(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128540));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void heart(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128150));
        txtMassage.setText(emoji);
        imogiPane.setVisible(false);
    }

    public void smileHeart2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-1a931e9af3.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void smile2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-56c35d999.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void sad2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-1a931e9af3.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void sunglass2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-15e251dad3.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void shok2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-6ace4e5710.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void angry2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-44cce16f7f.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void emostional2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-56c35d9996.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void verysad2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-4e652a75f8.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void heart2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-2168a6832c.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void laugh2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-0472cf6031.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void exited2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-1739dcae0f.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void gost2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-fc05abb5fb.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void kiss2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-8750852c7e.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void silly2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-dfc46071d0.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }
    public void gifemotion2(MouseEvent mouseEvent) throws IOException {
        File file = new File("D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\gif\\ezgif-2-80c8b54cd1.gif");
        if (file.exists()) {
            String path = file.toURI().toString();
            sendImage(path);
        } else {
            System.out.println("File does not exist.");
        }
    }
    public void gifOnAction(MouseEvent mouseEvent) {
        imogiPane.setVisible(false);
        gifPAne.setVisible(true);

    }

    public void sendImage(String path) throws IOException {

        if (path != null) {
            Image image1 = new Image(path, 100, 300, true, true);
            ImageView image = new ImageView(image1);

            final Group root = new Group();
            final GridPane gridpane = new GridPane();

            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);
            gridpane.setVgap(10);
            gridpane.minHeight(30);
            gridpane.maxHeight(200);
            GridPane.setHalignment(image, HPos.CENTER);
            gridpane.add(image, 0, 0);
            gridpane.setAlignment(Pos.CENTER_RIGHT);

            root.getChildren().add(gridpane);
            vBox2.getChildren().add(gridpane);
            dataOutputStream.writeUTF("img`" + path);
            dataOutputStream.flush();

        }
    }

    public void lightOnAction(MouseEvent mouseEvent) {
        String imagePath = "D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\image\\background.jpg";
        changeImageFromPath(theamChange, imagePath);
        darkimagePane.setVisible(true);
        darkImage.setVisible(true);
        lightImageview.setVisible(false);
    }

    public void darkOnAction(MouseEvent mouseEvent) {
        String imagePath = "D:\\Chat\\ChatApplication\\Client1\\src\\main\\resources\\view\\image\\dark.png";
        changeImageFromPath(theamChange, imagePath);
        darkimagePane.setVisible(false);
        darkImage.setVisible(false);
        lightImageview.setVisible(true);
    }
    public void changeImageFromPath(ImageView imageView, String imagePath) {
        try {
            Image image = new Image(new File(imagePath).toURI().toString());
            imageView.setImage(image);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        }
    }

    public void keyTyping(KeyEvent keyEvent) {
        String text = txtMassage.getText();
        if (!text.isBlank()) {
            sendBtn.setVisible(true);

        }else{
            sendBtn.setVisible(false);
        }
    }
}
