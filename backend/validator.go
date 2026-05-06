package main

import (
	"log"
	"time"
)

// GovtValidationResult holds the verified data from the Govt/B2B API
type GovtValidationResult struct {
	IsVerified        bool   `json:"is_verified"`
	RegisteredOwner   string `json:"registered_owner"`
	PlotAreaSqFt      int    `json:"plot_area_sq_ft"`
	EncumbranceStatus string `json:"encumbrance_status"`
	APISource         string `json:"api_source"`
}

// VerifyWithGovtAPI simulates querying a B2B Aggregator (like Surepass or Karza)
// with the extracted Survey Number to get the real registered ownership.
func VerifyWithGovtAPI(record PropertyRecord) GovtValidationResult {
	log.Printf("Querying Govt API (via B2B Aggregator) for Survey Number: %s in %s...", record.SurveyNumber, record.State)
	
	// Simulate network latency for API call
	time.Sleep(1500 * time.Millisecond)

	result := GovtValidationResult{
		IsVerified:        true,
		APISource:         "Surepass_Land_Record_API_Mock",
		EncumbranceStatus: "Clear",
	}

	// Mock database logic based on the extracted survey number
	switch record.SurveyNumber {
	case "45/2A":
		result.RegisteredOwner = "Rahul Sharma"
		result.PlotAreaSqFt = 2500
	case "102/1":
		result.RegisteredOwner = "Priya Patel"
		result.PlotAreaSqFt = 1200
		result.EncumbranceStatus = "Active Bank Loan (HDFC)" // Example of encumbrance
	default:
		result.IsVerified = false
		result.RegisteredOwner = "Record Not Found"
		result.PlotAreaSqFt = 0
		result.EncumbranceStatus = "Unknown"
	}

	return result
}
