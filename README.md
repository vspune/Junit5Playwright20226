# Playwright + JUnit POM-style test scaffold (moved)

This project contains a minimal scaffold for Playwright tests using the Page Object Model (pages) and JUnit 5 tests (tests).

Key points:
- POM structure: pages under `src/test/java/com/example/pages`, tests under `src/test/java/com/example/tests`.
- `BaseTest` creates independent Playwright/browser/page instances per test.
- Tests run for Chromium and Firefox by default; set `-Dbrowser=chrome` or `-Dbrowser=firefox` to run a single browser.
- JUnit parallel execution is enabled via `src/test/resources/junit-platform.properties`.
- Allure reporting is wired via dependencies and the `allure-maven` plugin.

Run tests:
```bash
cd JavaPlaywright
mvn test
```

Run tests for a single browser (Jenkins example):
```bash
mvn test -Dbrowser=firefox
```

Generate Allure report after tests:
```bash
mvn allure:report
```

Notes:
- Playwright Java will download browser binaries on first run. In CI (Jenkins) prefer pre-install or use `mvn -Dpw.download.browser=true test` as needed.
- Adjust Maven `pom.xml` versions and plugin configuration to match your CI/reporting preferences.
