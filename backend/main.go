package main

import (
	"log"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/cors"
	"github.com/gofiber/fiber/v2/middleware/logger"
)

type VaultUploadResponse struct {
	Message          string               `json:"message"`
	DocumentID       string               `json:"document_id"`
	OriginalName     string               `json:"original_name"`
	Status           string               `json:"status"`
	ExtractedRecord  PropertyRecord       `json:"extracted_record"`
	ValidationResult GovtValidationResult `json:"validation_result"`
}

func main() {
	// Initialize Fiber app
	app := fiber.New(fiber.Config{
		AppName: "ZameenMitra Vault API v1.0",
	})

	// Middleware
	app.Use(cors.New())
	app.Use(logger.New())

	// Health check
	app.Get("/health", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{"status": "ok", "service": "vault-api"})
	})

	// API Route Group
	api := app.Group("/api/v1")
	vault := api.Group("/vault")

	vault.Post("/upload", func(c *fiber.Ctx) error {
		filename := "sale_deed.pdf"
		if file, err := c.FormFile("document"); err == nil {
			filename = file.Filename
		}

		// 1. Parse the document using OCR/NLP mock
		record := ParseDocument(filename, []byte("mock file data"))

		// 2. Validate with Govt API mock
		validation := VerifyWithGovtAPI(record)

		return c.Status(fiber.StatusOK).JSON(VaultUploadResponse{
			Message:          "Document processed and verified successfully.",
			DocumentID:       "doc_12345abcde",
			OriginalName:     filename,
			Status:           "verified",
			ExtractedRecord:  record,
			ValidationResult: validation,
		})
	})

	log.Println("Starting ZameenMitra API on port 8080...")
	log.Fatal(app.Listen(":8080"))
}
