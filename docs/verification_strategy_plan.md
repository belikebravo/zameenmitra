---
title: Verification Strategy & Implementation Plan - ZameenMitra
author: Vijendra
date_created: 2026-05-06
date_modified: 2026-05-06
status: Active
---

# ZameenMitra: Validation Sources, Cost Analysis & Implementation Plan

This document outlines the research and strategic plan for building the ZameenMitra application, specifically focusing on the core challenge: **validating land and property documents in India.**

## 1. Sources of Validation in India

India's land records are digitized under the **Digital India Land Records Modernization Programme (DILRMP)**. However, land is a state subject, meaning there is no single central database. Every state has its own portal (e.g., *Bhoomi* in Karnataka, *Bhulekh* in UP/Maharashtra, *AnyRoR* in Gujarat).

To validate a document, we have three main avenues:

### A. Direct Government Portals (Web Scraping / RPA)
- **What it is:** Building custom scrapers for each state's land record website to fetch Khata, Patta, 7/12 extracts, and Encumbrance Certificates (EC).
- **The Challenge:** These portals use heavy anti-scraping measures (CAPTCHAs, dynamic IP blocking, frequent UI changes) and operate in regional languages.

### B. B2B API Aggregators (Recommended)
- **What it is:** Third-party Fintech/Regtech companies that have already solved the integration and scraping challenges across multiple states.
- **Top Providers:**
  - **Karza Technologies (by Perfios):** The gold standard in India for KYC and property intelligence APIs.
  - **Surepass:** Offers specific Land Record Verification APIs.
  - **Signzy:** Strong in document AI and background checks.
  - **Digiverification:** Specializes in property, encumbrance, and RERA registration checks.

### C. Centralized Registries
- **CERSAI:** The Central Registry of Securitisation Asset Reconstruction and Security Interest of India. Used specifically to check if a property has an existing loan or equitable mortgage against it.

---

## 2. Cost Analysis of Available Options

There is no public "standard pricing" as B2B APIs use enterprise-negotiated tiers. However, based on industry standards, here is the estimated cost structure:

| Method | Estimated Unit Cost | Engineering/Setup Cost | Maintenance Cost |
| :--- | :--- | :--- | :--- |
| **Direct Gov Portals (Scraping)** | ₹0 per hit | **High** (₹5L - ₹10L to build state scrapers) | **High** (Constant fixes due to CAPTCHAs/UI changes) |
| **B2B APIs (Karza/Surepass)** | **₹10 - ₹50+ per hit** (volume dependent) | **Low** (Standard REST API integration) | **Low** (Managed by the vendor) |
| **Manual Verification (Lawyers)** | ₹2,000 - ₹10,000+ per property | **None** | **None** |
| **Basic Document OCR (AWS Textract)**| ₹0.10 - ₹1 per page | **Medium** (Parsing logic) | **Low** |

**Cost Strategy:** Because property verification APIs are expensive (₹10-₹50+ per hit compared to ₹1-₹2 for Aadhaar), we cannot verify every document upon free upload. Verification should be triggered by a user action (e.g., paying a "Token Verification Fee" or as part of a premium tier).

---

## 3. Real-World Challenges

1. **Data Fragmentation:** Supporting 28 states means dealing with 28 different data formats and regional languages (Hindi, Kannada, Marathi, Tamil).
2. **Legacy Data Quality:** Many older records have spelling mistakes (English names translated poorly to local languages), making string-matching algorithms fail.
3. **Legal Liability:** An API might return "Clear Title," but if fraud occurs, who is liable? ZameenMitra cannot act as a legal guarantor purely based on an API.
4. **Encumbrance Delays:** Encumbrance Certificates (ECs) sometimes take weeks to update in government systems after a transaction occurs.

---

## 4. Implementation Plan (Phased Approach)

To build ZameenMitra effectively while mitigating costs and technical risks, we will use a phased implementation plan.

### Phase 1: The "Vault & AI" MVP (Months 1-2)
*Focus on document management and basic intelligence, avoiding expensive API calls.*
- **Build:** The Document Vault (Next.js + Go + AWS S3).
- **Feature:** Implement AWS Textract or Google Document AI to extract text from uploaded deeds.
- **Value Add:** Auto-tagging, expiry alerts, and secure sharing links. No external validation yet; the user simply organizes their files.

### Phase 2: Tier-1 State API Integration (Months 3-4)
*Introduce automated validation for the most active real estate markets.*
- **Integration:** Partner with Surepass or Karza.
- **Rollout:** Only launch validation for top states first (e.g., Maharashtra, Karnataka, UP, Delhi).
- **Workflow:** 
  1. User uploads document.
  2. OCR extracts Survey/Gat Number.
  3. User clicks "Verify Record" and pays a micro-fee (e.g., ₹99).
  4. System calls the B2B API and returns government records matching the survey number.
  5. System flags discrepancies (e.g., "Uploaded deed says 'Ramesh', Gov API says 'Suresh'").

### Phase 3: "Human-in-the-Loop" Marketplace (Months 5-6)
*Solve the legal liability and data quality problem.*
- **Feature:** Create a "Lawyer Dashboard."
- **Workflow:** When the API fails due to bad data, or the user needs a legally binding Title Search Report (TSR), they click "Request Legal Review."
- **Execution:** ZameenMitra routes the documents (and API findings) to a vetted freelance lawyer on the platform who provides the final "Clear/Risk" stamp.

### Phase 4: Enterprise & Direct Government (Future)
- Once transaction volume is high, apply for direct API access with state governments (similar to how banks operate) to bypass aggregator costs entirely.
