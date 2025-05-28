package testomat.anotations;

import testomat.implementation.TestomatReporterExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(TestomatReporterExtension.class)
public @interface EnableTestomatReporting {
}
