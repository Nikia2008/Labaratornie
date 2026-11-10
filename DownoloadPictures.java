package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class FindListen extends Application {

    private static final String IMAGE_URL = "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=300&h=300&fit=crop";
    private static final String SAVE_PATH = "C:\\Users\\nnn\\Desktop\\downloaded_image.jpg";

    @Override
    public void start(Stage primaryStage) {

        ImageView coverImage = new ImageView();
        coverImage.setFitWidth(250);
        coverImage.setFitHeight(250);
        coverImage.setPreserveRatio(true);
        coverImage.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 5);");

        Label statusLabel = new Label("Нажмите кнопку, чтобы скачать картинку");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        Button btnDownload = new Button("Скачать картинку");
        btnDownload.setStyle("-fx-font-size: 14px; -fx-padding: 8 15 8 15; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-cursor: hand;");

        btnDownload.setOnAction(event -> {
            btnDownload.setDisable(true);
            statusLabel.setText("Скачивание...");

            new Thread(() -> {
                try {
                    downloadFile(IMAGE_URL, SAVE_PATH);

                    Image image = new Image(IMAGE_URL, true);

                    Platform.runLater(() -> {
                        coverImage.setImage(image);
                        statusLabel.setText("Скачано! Файл: " + SAVE_PATH);
                        btnDownload.setDisable(false);
                    });
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        statusLabel.setText("Ошибка скачивания!");
                        btnDownload.setDisable(false);
                    });
                }
            }).start();
        });

        VBox root = new VBox(20, coverImage, statusLabel, btnDownload);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30; -fx-background-color: #2b2b2b;");

        Scene scene = new Scene(root, 400, 450);
        primaryStage.setTitle("Скачивание картинки");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void downloadFile(String fileUrl, String fileName) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();

        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
             FileOutputStream out = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
