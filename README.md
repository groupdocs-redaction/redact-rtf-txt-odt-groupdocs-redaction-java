# Redact RTF, TXT, and ODT Documents with GroupDocs Redaction for Java

This repository demonstrates redacting sensitive data from specialized formats like RTF, TXT, and OpenDocument (ODT) using GroupDocs Redaction for Java. It provides out-of-the-box redaction for multiple document formats including RTF, TXT, and ODT with ready-to-use policies.

## Problem
Documents often contain personally identifiable information (PII) or other sensitive data in plain text. When sharing RTF, TXT, or ODT files, manually redacting such content is error-prone and time-consuming. Automated redaction ensures consistency, security, and compliance.

## What This Example Shows
This example shows how to programmatically redact exact phrases (e.g., "John Doe") from RTF, TXT, and ODT files using GroupDocs Redaction for Java. It supports batch processing, preserves original formatting, and saves redacted files in the same format as the input. The workflow is simple: load the document, apply redaction, and save — all in a few lines of Java code.

## Expected Outcome
After running this example, you’ll have redacted versions of your input RTF, TXT, and ODT files in the output directory, with all occurrences of the target phrase replaced by `[REDACTED]`.

## How it works

```mermaid
flowchart LR
    A[Input: sample.rtf/txt/odt] --> B[Redactor redactor = new Redactor(path)]
    B --> C[ExactPhraseRedaction("John Doe", "[REDACTED]")]
    C --> D[redactor.apply(redaction)]
    D --> E[redactor.save(outputPath)]
    E --> F[Output: *_redacted.rtf/txt/odt]
```

**Important:** Ensure input files exist in `src/main/resources/input/`. The example skips missing files with a warning.

## Topics covered
Redact data from specialized formats like RTF, TXT, and OpenDocument (ODT).

## Prerequisites
- Java 8 or higher
- Maven 3.x

## License
This example uses GroupDocs Redaction for Java. For a temporary license, visit [https://purchase.groupdocs.com/temporary-license](https://purchase.groupdocs.com/temporary-license). Full documentation is available at [https://docs.groupdocs.com/redaction/java/](https://docs.groupdocs.com/redaction/java/).

## Setup & Run

1. Clone the repository.
2. Place sample RTF, TXT, and ODT files in `src/main/resources/input/` (e.g., `sample.rtf`, `sample.txt`, `sample.odt`).
3. Run:
   ```bash
   mvn compile exec:java
   ```

Redacted files will appear in `src/main/resources/output/`.

## FAQ

**Q: Does this example support other formats like DOCX or PDF?**
A: Yes — GroupDocs Redaction for Java supports many formats. This example focuses on RTF, TXT, and ODT as per the use case, but the same API works for DOCX, PDF, XLSX, and more.

**Q: How does redaction affect document formatting?**
A: GroupDocs Redaction preserves the original layout and formatting of RTF, TXT, and ODT files during redaction.

**Q: Can I redact multiple phrases at once?**
A: Yes — call `redactor.apply()` multiple times with different redaction instances, or use `RedactionPolicy` to batch multiple redactions.

See the official documentation for more: [https://docs.groupdocs.com/redaction/java/](https://docs.groupdocs.com/redaction/java/).
