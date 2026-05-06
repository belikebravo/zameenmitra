---
title: Cross-Platform Architecture Strategy - ZameenMitra
author: Vijendra
date_created: 2026-05-06
date_modified: 2026-05-06
status: Active
---

# ZameenMitra: Cross-Platform Architecture Strategy

## 1. The Goal: "Minimum Disturbance"
To support iOS, Android, and Web without duplicating business logic, we need an architecture that allows us to write the "Core" (networking, data parsing, risk score calculation, validation rules) once, while keeping the flexibility to render platform-specific or shared UIs.

## 2. Analysis of Cross-Platform Options

### Recommended Approach: Kotlin Multiplatform (KMP)
Based on the existing tooling and background context, **Kotlin Multiplatform (KMP)** is the ideal fit for ZameenMitra to achieve "minimum disturbance" to core logic.
- **How it works:** All core business logic (networking via Ktor, databases via SQLDelight, view models, domain rules) is written in Kotlin within a `commonMain` module.
- **Web:** Compiles to Kotlin/JS or Kotlin/Wasm for the Web portal.
- **Android:** Compiles directly to JVM byte code, leveraging Jetpack Compose for the UI layer.
- **iOS:** Compiles to native Objective-C/Swift frameworks, allowing for pure SwiftUI interfaces.
- **Why it fits:** KMP is explicitly designed to share *logic* without forcing a shared *UI* layer if native performance or styling is preferred. It offers the highest performance on mobile devices.

### Alternative A: React Native + Expo (with React Native Web)
- **How it works:** The app is written in TypeScript/JavaScript. `react-native-web` translates React Native components into DOM elements.
- **Why it was considered:** High code-sharing percentage (UI + Logic) and rapid iteration. However, it forces a JavaScript paradigm across the board, which may not align with the preferred native mobile ecosystem.

### Alternative B: Flutter
- **How it works:** Written in Dart, utilizing a custom canvas rendering engine (Skia/Impeller) across all platforms.
- **Why it was considered:** 100% shared code for UI and logic. However, Flutter for Web can sometimes feel non-native (SEO challenges, scrolling differences), which is sub-optimal for a document-centric portal like ZameenMitra.

## 3. The Recommended Architecture (KMP)

By adopting Kotlin Multiplatform, the project repository will be structured as follows:

1. **`shared/core` (CommonMain):** 
   - **Network layer:** Ktor for API calls to the ZameenMitra Go backend.
   - **Models:** Shared data classes for `Property`, `Document`, `User`, `VerificationScore`.
   - **Logic:** Business rules for state machines, validations, and local repository management.
   - **Local DB:** SQLDelight for cross-platform offline caching.

2. **`androidApp`:**
   - Pure UI layer using Jetpack Compose.
   - Consumes the `shared` module natively.

3. **`iosApp`:**
   - Pure UI layer using SwiftUI.
   - Consumes the `shared` module as an XCFramework or via SPM.

4. **`webApp`:**
   - Utilizes Compose HTML/Web or Kotlin/Wasm to reuse the exact same `shared` module for the browser-based dashboard.
