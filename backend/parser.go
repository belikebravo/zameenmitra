package main

import (
	"log"
)

// PropertyRecord holds the extracted information from the uploaded document
type PropertyRecord struct {
	State        string `json:"state"`
	District     string `json:"district"`
	Village      string `json:"village"`
	SurveyNumber string `json:"survey_number"`
	DocumentName string `json:"document_name"`
}

// ParseDocument simulates calling AWS Textract / Google Cloud Vision
// and running regex/NLP to extract land record identifiers.
func ParseDocument(filename string, fileBytes []byte) PropertyRecord {
	log.Printf("Parsing document: %s (%d bytes) via OCR...", filename, len(fileBytes))
	
	// Mock OCR Extraction Logic
	// In a real implementation, we would extract text using Vision APIs and use Regex to find patterns like "Khasra No: \d+"
	
	record := PropertyRecord{
		State:        "Maharashtra",
		District:     "Pune",
		Village:      "Hinjawadi",
		SurveyNumber: "Unknown",
		DocumentName: filename,
	}

	// ---------------------------------------------------------
	// DYNAMIC DOCUMENT DETECTION (Simulated)
	// ---------------------------------------------------------
	// Instead of relying on the filename, a real system would:
	// 1. Run OCR (e.g., Google Vision) to get all text.
	// 2. Feed the text to an LLM or use Keyword Heuristics to classify the document.
	//
	// Example Heuristic Logic:
	// text := extractOCRText(fileBytes)
	// if strings.Contains(text, "7/12") || strings.Contains(text, "Village Form VII") {
	//     record.DocumentType = "7_12_Extract"
	// } else if strings.Contains(text, "Sale Deed") || strings.Contains(text, "Deed of Conveyance") {
	//     record.DocumentType = "Sale_Deed"
	// }

	// For this POC, we will assume the OCR engine classified it and found a Survey Number.
	record.SurveyNumber = "45/2A"

	return record
}
