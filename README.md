# JUnit5 Reporter Integration with Testomat.io

This project demonstrates how to integrate JUnit5 tests with the custom `junit-reporter` library and upload test results to [Testomat.io](https://testomat.io) using a custom XML reporter.

## 🧩 Maven Setup

Add the following to your `pom.xml` file to include the reporter dependency and repository:

```xml
<repositories>
  <repository>
    <id>my-s3-repo</id>
    <url>s3://tomato.io/build/io/testomat/junit5-reporter/</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>io.testomat</groupId>
    <artifactId>junit-reporter</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

## 🔖 Annotating Your Tests

To enable reporting, annotate your test class and test methods like this:

```java
package org.example;

import org.junit.jupiter.api.Test;
import testomat.anotations.EnableTestomatReporting;
import testomat.anotations.TestId;
import testomat.anotations.Title;

@EnableTestomatReporting
public class TomatoTests {

    @Test
    @TestId("1")
    @Title("Test 1")
    void additionTest() {
        assert 2 + 2 == 4 : "1";
    }

    @Test
    @TestId("2")
    @Title("Test 2")
    void subtractionTest() {
        assert 5 - 3 == 2 : "2";
    }

    @Test
    @TestId("3")
    @Title("Test 3")
    void multiplicationTest() {
        assert 3 * 3 == 9 : "3";
    }

    @Test
    @TestId("4")
    @Title("Test 4")
    void divisionTest() {
        assert 10 / 2 == 5 : "4";
    }
}
```

## ⚙️ How It Works

- `@EnableTestomatReporting`: Enables the reporter to hook into the test lifecycle.
- `@TestId`: Assigns a unique ID to the test for linking in Testomat.io.
- `@Title`: Adds a human-readable name for display in the report.

## 🚀 Run and Report

You will need:
- Git Bash or terminal
- Maven installed
- Node.js >= 14 installed

Install the reporter globally:

```bash
npm i -g @testomatio/reporter
```

Run your tests and generate the XML report:

```bash
mvn clean package
```

Upload the XML report to Testomat.io:

```bash
TESTOMATIO=tstmt_jZ3vHuylkuwXN2IRQ_hnR85JYNgy92FXAw1748344420 npx report-xml "target/surefire-reports/*.xml" --lang=""
```

If you get `command not found`, install Node.js from [https://nodejs.org/en/download/](https://nodejs.org/en/download/).

## 📊 After Upload

After successful execution, you will see logs like:

```
🤩 Testomat.io XML Reporter v1.6.17
[TESTOMATIO] Pipes: Testomatio Reporter
[TESTOMATIO] Testomatio Reporter v1.6.17
[TESTOMATIO] Parsed target\surefire-reports\TEST-org.example.{report name}.xml
[TESTOMATIO] 📊 Report created. Report ID: Test ID
[TESTOMATIO] 📊 Report Saved. Report URL: https://app.testomat.io/projects/{Test}/runs/{Test id}/report
```

You can follow the link to see the full test report on [Testomat.io](https://testomat.io).

---

Happy Testing! 🎉
