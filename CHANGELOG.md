# ZameenMitra Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]
### Added
- **OCR Document Parser Mock:** Added `parser.go` to the Go backend. It simulates extracting state, district, and Khasra/Survey numbers from uploaded PDFs/images using regex and NLP.
- **Govt Land Record API Mock:** Added `validator.go`. Simulates querying a B2B Aggregator (like Surepass or Karza) with the extracted survey number to return verified owner names and encumbrance statuses.
- **Backend Verification Pipeline:** Updated `main.go` `/upload` route to automatically pipe incoming documents through the parser and the validator, returning an enriched JSON response containing both extracted data and verification results.
### Added
- **Android Gradle Configurations:** Generated Gradle wrapper and configured `local.properties` and `gradle.properties`.
- **KMP Root Build Script:** Configured Android/Kotlin plugins and dependencies in root `build.gradle.kts` and `settings.gradle.kts`.
- **Android App Compile Options:** Added Java 1.8 compatibility to `client/androidApp/build.gradle.kts` to resolve Kotlin JVM target mismatches.

### Fixed
- **Android Manifest Errors:** Removed missing `@mipmap/ic_launcher` icons from `client/androidApp/src/main/AndroidManifest.xml` to allow successful compilation.

### Verified
- Android APK now successfully builds via `./gradlew :androidApp:assembleDebug`.
