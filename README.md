# JUnit 5 Reporter for Testomat.io

This library is a JUnit 5 extension that reports test execution metadata and results to [Testomat.io](https://testomat.io). It enables seamless integration by using custom annotations to describe and identify tests.

---

## 📦 Installation

To use this library in your Maven project:

### 1. Add the custom S3 Maven repository

```xml
<repositories>
  <repository>
    <id>my-s3-repo</id>
    <url>s3://tomato.io/build/io/testomat/junit5-reporter/</url>
  </repository>
</repositories>
```

### 2. Add the dependency

```xml
<dependencies>
  <dependency>
    <groupId>io.testomat</groupId>
    <artifactId>junit-reporter</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

---

## ✅ Usage Example

Annotate your test class with `@EnableTestomatReporting`, and each test method with `@TestId` and `@Title`:

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

### 🔍 What do the annotations do?

- `@EnableTestomatReporting` – enables the Testomat.io integration for the test class.
- `@TestId("...")` – assigns a unique Testomat.io test ID to the test method.
- `@Title("...")` – provides a human-readable name for the test.

---

## 🚀 Running & Reporting Tests

### 1. Requirements

- Git Bash (or compatible terminal)
- NodeJS ≥ 14
- Maven

### 2. Install the Testomat reporter CLI globally:

```bash
npm i -g @testomatio/reporter
```

If you get a `command not found` error, install NodeJS from:  
🔗 https://nodejs.org/en/download

### 3. Run tests and generate the report:

```bash
mvn clean package
```

### 4. Push results to Testomat.io:

```bash
TESTOMATIO=tstmt_jZ3vHuylkuwXN2IRQ_hnR85JYNgy92FXAw1748344420 \
npx report-xml "/path/to/xml-reports/**.xml" --lang=""
```

Make sure to replace `/path/to/xml-reports/**.xml` with the actual path to your generated JUnit XML reports.

---

## 🧠 Summary

This library simplifies the connection between your JUnit 5 tests and Testomat.io. With just a few annotations and setup steps, your test results and metadata can be automatically synchronized and visualized inside Testomat.io’s test management system.

---
