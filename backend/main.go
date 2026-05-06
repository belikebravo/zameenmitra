package main

import (
	"log"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/logger"
)

// VaultUploadResponse defines the shape of the successful upload response
type VaultUploadResponse struct {
	Message      string `json:"message"`
	DocumentID   string `json:"document_id"`
	OriginalName string `json:"original_name"`
	Status       string `json:"status"`
}

func main() {
	// Initialize Fiber app
	app := fiber.New(fiber.Config{
		AppName: "ZameenMitra Vault API v1.0",
	})

	// Middleware
	app.Use(logger.New())

	// Health check
	app.Get("/health", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{"status": "ok", "service": "vault-api"})
	})

	// API Route Group
	api := app.Group("/api/v1")
	vault := api.Group("/vault")

	// Endpoint to upload a document
	vault.Post("/upload", func(c *fiber.Ctx) error {
		// In a real implementation, we would use c.FormFile("document") to retrieve the file,
		// validate it, and upload it to AWS S3.
		// For the POC, we'll return a mock successful response.
		
		return c.Status(fiber.StatusOK).JSON(VaultUploadResponse{
			Message:      "Document successfully uploaded and queued for processing.",
			DocumentID:   "doc_12345abcde",
			OriginalName: "sale_deed.pdf",
			Status:       "pending_verification",
		})
	})

	log.Println("Starting ZameenMitra API on port 3000...")
	log.Fatal(app.Listen(":3000"))
}
