package testomat.implementation;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StackTraceUtils {
    public static String getStackTrace(Throwable throwable) {
        return Arrays.stream(throwable.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\\n"));
    }
}
