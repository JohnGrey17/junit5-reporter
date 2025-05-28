package testomat.implementation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpUtils {

    public static String post(String urlString, String body) throws IOException {
        return sendRequest("POST", urlString, body);
    }

    public static String put(String urlString, String body) throws IOException {
        return sendRequest("PUT", urlString, body);
    }

    private static String sendRequest(String method, String urlString, String body) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));

        Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name());
        return scanner.useDelimiter("\\A").next();
    }
}
