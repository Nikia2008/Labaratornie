import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Scanner;

    private static final Path SAVE_DIR = Path.of("downloads");

    private static final String[] MUSIC_URLS = {
            "https://archive.org/download/freepd/comedy/BeBop%20for%20Joey.mp3",
            "https://archive.org/download/freepd/comedy/Be%20Jammin.mp3",
            "https://archive.org/download/freepd/electronic/Backbeat.mp3",
            "https://archive.org/download/freepd/electronic/Goodnightmare.mp3"
    };

    private static final String[] IMAGE_URLS = {
            "https://i.pinimg.com/736x/ef/8f/b3/ef8fb37244ac949153931d82188e026d.jpg",
            "https://i.pinimg.com/736x/11/ae/57/11ae57b0625e69ee5a1d3fdb13d57a91.jpg",
            "https://i.pinimg.com/736x/9d/ab/7a/9dab7a3c82403f7943e052e49714d6d8.jpg"
    };

    void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("""
                1 - Картинка 1
                2 - Картинка 2
                3 - Картинка 3
                4 - BeBop for Joey
                5 - Be Jammin
                6 - Backbeat
                7 - Goodnightmare
                """);

        int choice = sc.nextInt();
        sc.nextLine();

        try {
            if (choice >= 1 && choice <= 3) {
                download(IMAGE_URLS[choice - 1], "image" + choice + ".jpg");
            } else if (choice >= 4 && choice <= 7) {
                download(MUSIC_URLS[choice - 4], "track" + choice + ".mp3");
            } else {
                System.err.println("Неверный номер.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static void download(String url, String filename) throws IOException, InterruptedException {
        Files.createDirectories(SAVE_DIR);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .timeout(Duration.ofSeconds(20))
                .GET()
                .build();

        HttpResponse<Path> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofFile(SAVE_DIR.resolve(filename))
        );

        if (response.statusCode() == 200) {
            System.out.println("Сохранено: " + filename);
        } else {
            throw new IOException("HTTP " + response.statusCode());
        }
    }
