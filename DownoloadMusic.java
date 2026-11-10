package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class FindListen extends Application {

    private static final String MUSIC_URL = "https://www.w3schools.com/html/horse.mp3";
    private static final String SAVE_PATH = "C:\\Users\\nnn\\Desktop\\downloaded_music.mp3";

    @Override
    public void start(Stage primaryStage) {
        Label statusLabel = new Label("Нажмите кнопку, чтобы скачать");
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        Button btnDownload = new Button("Скачать музыку");

        btnDownload.setOnAction(event -> {
            new Thread(() -> {
                try {
                    downloadFile(MUSIC_URL, SAVE_PATH, statusLabel, progressBar);
                } catch (Exception e) {
                    Platform.runLater(() -> statusLabel.setText("Ошибка: " + e.getMessage()));
                }
            }).start();
        });

        VBox root = new VBox(15, btnDownload, statusLabel, progressBar);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 350, 150);
        primaryStage.setTitle("Music Downloader");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void downloadFile(String fileUrl, String fileName, Label status, ProgressBar progressBar) {
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            long fileSize = connection.getContentLengthLong();

            try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream out = new FileOutputStream(fileName)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                long totalRead = 0;

                Platform.runLater(() -> status.setText("Скачивание..."));

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    totalRead += bytesRead;

                    if (fileSize > 0) {
                        double progress = (double) totalRead / fileSize;
                        Platform.runLater(() -> progressBar.setProgress(progress));
                    }
                }
            }

            Platform.runLater(() -> {
                status.setText("Скачивание завершено!");
                progressBar.setProgress(1.0);
            });

        } catch (IOException e) {
            Platform.runLater(() -> status.setText("Ошибка скачивания"));
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
