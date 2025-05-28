package testomat.implementation;

import java.io.IOException;

public class TestRunClient {

    private final String baseUrl;
    private final String apiKey;

    public TestRunClient(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public String createTestRun(String runName) throws IOException {
        String body = "{\"title\":\"" + runName + "\"}";
        return HttpUtils.post(buildUrl(""), body);
    }

    public void finishTestRun(String uid, double duration) throws IOException {
        String body = "{\"status_event\":\"finish\",\"duration\":" + duration + "}";
        HttpUtils.put(buildUrl("/" + uid), body);
    }

    public void sendTestReport(String runUid, TestReport report) throws IOException {
        String url = buildUrl("/" + runUid + "/testrun");
        String jsonBody = report.toJson();
        HttpUtils.post(url, jsonBody);
    }

    private String buildUrl(String path) {
        return baseUrl + path + "?api_key=" + apiKey;
    }
}
