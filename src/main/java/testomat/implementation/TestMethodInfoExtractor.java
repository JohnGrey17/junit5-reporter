package testomat.implementation;

import testomat.anotations.TestId;
import testomat.anotations.Title;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestMethodInfoExtractor {

    public String getAnnotationValue(Method method, Class<?> annotationClass) {
        if (annotationClass == TestId.class && method.isAnnotationPresent(TestId.class)) {
            return method.getAnnotation(TestId.class).value();
        }
        if (annotationClass == Title.class && method.isAnnotationPresent(Title.class)) {
            return method.getAnnotation(Title.class).value();
        }
        return null;
    }

    public String getTestMethodSource(Method method) {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String path = "src/test/java/" + className.replace('.', '/') + ".java";
        File file = new File(path);

        if (!file.exists()) {
            System.err.println("Source file not found: " + path);
            return null;
        }

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder methodSource = new StringBuilder();
            boolean insideMethod = false;
            int braceCount = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!insideMethod && line.contains(methodName) && line.contains("(") && line.contains(")")) {
                    insideMethod = true;
                }

                if (insideMethod) {
                    methodSource.append(line).append("\n");
                    braceCount += countOccurrences(line, '{');
                    braceCount -= countOccurrences(line, '}');
                    if (braceCount == 0 && methodSource.length() > 0) {
                        break;
                    }
                }
            }

            return methodSource.toString().trim();

        } catch (IOException e) {
            System.err.println("Error reading source file: " + e.getMessage());
            return null;
        }
    }

    private int countOccurrences(String text, char ch) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == ch) count++;
        }
        return count;
    }

    public String parseJsonValue(String json, String key) {
        int start = json.indexOf("\"" + key + "\":\"") + key.length() + 4;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
