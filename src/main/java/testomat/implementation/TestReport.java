package testomat.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestReport {
    private String title;
    private String testId;
    private String suiteTitle;
    private String file;
    private String status;
    private String code;
    private String message;
    private String stack;


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTestId() { return testId; }
    public void setTestId(String testId) { this.testId = testId; }

    public String getSuiteTitle() { return suiteTitle; }
    public void setSuiteTitle(String suiteTitle) { this.suiteTitle = suiteTitle; }

    public String getFile() { return file; }
    public void setFile(String file) { this.file = file; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStack() { return stack; }
    public void setStack(String stack) { this.stack = stack; }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
