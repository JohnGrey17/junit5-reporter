package testomat.implementation;

import testomat.anotations.TestId;
import testomat.anotations.Title;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class TestomatReporterExtension implements BeforeAllCallback, AfterAllCallback,
        BeforeTestExecutionCallback, AfterTestExecutionCallback, TestExecutionExceptionHandler {

    private static final String API_KEY = System.getenv("TESTOMATIO");
    private static final String BASE_URL = "https://app.testomat.io/api/reporter";
    private static final AtomicReference<String> TEST_RUN_UID = new AtomicReference<>();
    private static Instant startTime;

    private final TestRunClient client = new TestRunClient(BASE_URL, API_KEY);
    private final TestMethodInfoExtractor extractor = new TestMethodInfoExtractor();

    @Override
    public void beforeAll(ExtensionContext context) {
        try {
            startTime = Instant.now();
            String runName = "JUnit 5 Run - " + new Date();
            String response = client.createTestRun(runName);
            String uid = extractor.parseJsonValue(response, "uid");
            TEST_RUN_UID.set(uid);
        } catch (Exception e) {
            System.err.println("Failed to create test run: " + e.getMessage());
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        try {
            double duration = Duration.between(startTime, Instant.now()).toMillis() / 1000.0;
            client.finishTestRun(TEST_RUN_UID.get(), duration);
        } catch (Exception e) {
            System.err.println("Failed to finish test run: " + e.getMessage());
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        reportTest(context, null);
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        reportTest(context, throwable);
        throw throwable;
    }

    private void reportTest(ExtensionContext context, Throwable throwable) {
        try {
            Method testMethod = context.getRequiredTestMethod();
            String status = (throwable == null && !context.getExecutionException().isPresent()) ? "passed" : "failed";

            TestReport report = new TestReport();
            report.setTitle(Optional.ofNullable(extractor.getAnnotationValue(testMethod, Title.class)).orElse(context.getDisplayName()));
            report.setTestId(extractor.getAnnotationValue(testMethod, TestId.class));
            report.setSuiteTitle(context.getRequiredTestClass().getSimpleName());
            report.setFile(report.getSuiteTitle() + ".java");
            report.setStatus(status);
            report.setCode(extractor.getTestMethodSource(testMethod));
            if (throwable != null) {
                report.setMessage(throwable.getMessage());
                report.setStack(StackTraceUtils.getStackTrace(throwable));
            }

            client.sendTestReport(TEST_RUN_UID.get(), report);

        } catch (Exception e) {
            System.err.println("Failed to report test result: " + e.getMessage());
        }
    }
}
