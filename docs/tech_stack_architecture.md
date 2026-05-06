---
title: Tech Stack & Architecture Analysis - ZameenMitra
author: Vijendra
date_created: 2026-05-06
date_modified: 2026-05-06
status: Draft
---

# Tech Stack & Architecture Analysis

## 1. System Architecture Overview
The platform will follow an API-first, microservices-oriented architecture to ensure scalability, fault isolation, and ease of maintenance.

- **Client Layer**: Web application for general users and administration.
- **API Gateway**: Central entry point handling routing, rate limiting, and authentication.
- **Microservices**: Modular backend services (Auth, Document Service, Verification Engine, Notification).
- **Data Layer**: Relational database for structured data, object storage for files.

## 2. Proposed Technology Stack

### Frontend (Client Layer)
- **Framework**: Next.js (React) - Chosen for SEO benefits (if needed for public listings), performance, and rich ecosystem.
- **Styling**: Tailwind CSS - For rapid UI development and maintaining a consistent design system.
- **State Management**: Zustand or React Context for global state, React Query for server state management.

### Backend (Microservices / API Layer)
- **Language**: Go (Golang) - Provides high performance, excellent concurrency support, and compiles to lightweight binaries. Ideal for building robust microservices.
- **Framework**: Fiber or standard library `net/http` with a router like Chi.
- **API Protocol**: REST over HTTPS, with consideration for gRPC for internal service-to-service communication.

### Database & Storage
- **Relational Database**: PostgreSQL - Standard, highly reliable RDBMS suitable for relational transaction data, user management, and audit logs.
- **Object Storage**: AWS S3 (or Azure Blob Storage / Google Cloud Storage) - Used for the Property Document Vault to securely store PDFs, images, and raw files.
- **Caching**: Redis - Used for session management, fast retrieval of frequently accessed metadata, and rate-limiting.

### Document Processing & AI
- **OCR & Extraction**: AWS Textract or Google Cloud DocumentAI - Used to parse unstructured property documents and extract key entities (Survey Numbers, Names).
- **AI/ML Layer**: OpenAI API (or similar LLM) for intelligent classification and initial discrepancy detection in text.

### Infrastructure & DevOps
- **Containerization**: Docker - For consistent environments across dev, staging, and production.
- **Orchestration**: Kubernetes (K8s) or AWS ECS - For managing scaling and deployment of services.
- **CI/CD**: GitHub Actions - Automated testing, linting, and deployment pipelines.

## 3. Core Modules & Interactions

1. **Auth Service**: Manages JWTs, OAuth login, and OTP verifications.
2. **Vault Service**: Handles secure file upload to S3, returns presigned URLs for viewing, and updates PostgreSQL with document metadata.
3. **Verification Engine**: Processes documents via OCR, applies business rules for ownership chains, and calculates the "Property Risk Score".
4. **Workflow Service**: Manages the state machine of a property transaction (Shortlist -> Token -> Registry).
5. **Notification Service**: Listens to events (via Kafka, RabbitMQ, or Redis PubSub) and dispatches Emails/SMS.

## 4. Security Considerations
- **Encryption**: Files encrypted at rest in S3. All communication over HTTPS.
- **Access Control**: RBAC implemented via middleware to ensure a user can only view their involved transactions.
- **Audit Logging**: Every create, update, and delete operation on critical resources will trigger an append-only log entry.
