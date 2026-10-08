package com.groupdocs.redaction.examples;

import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.redactions.ExactPhraseRedaction;
import com.groupdocs.redaction.options.SaveOptions;
import com.groupdocs.redaction.licensing.License;

import java.io.File;
import java.io.IOException;

/**
 * Demonstrates redaction of sensitive data from RTF, TXT, and ODT documents using GroupDocs Redaction for Java.
 * <p>
 * This example:
 * <ul>
 *   <li>Applies exact-phrase redaction to replace sensitive text (e.g., "John Doe") with a placeholder.</li>
 *   <li>Supports RTF, TXT, and ODT input formats.</li>
 *   <li>Saves the redacted document in its original format.</li>
 * </ul>
 * <p>
 * Ensure input files exist in {@code resources/input/} and output will be written to {@code resources/output/}.
 * <p>
 * For a temporary license, see: https://purchase.groupdocs.com/temporary-license
 * For full documentation: https://docs.groupdocs.com/redaction/java/
 */
public class RedactRtfTxtOdtExample {

    /**
     * Sets up a temporary evaluation license (if available) or falls back to evaluation mode.
     */
    private static void setupLicense() {
        String licensePath = "src/main/resources/GroupDocs.Redaction.Java.lic";
        File licenseFile = new File(licensePath);
        License license = new License();
        if (licenseFile.exists()) {
            try {
                license.setLicense(licensePath);
            } catch (Exception e) {
                System.out.println("License file found but could not be applied: " + e.getMessage());
            }
        } else {
            System.out.println("No license file found. Running in evaluation mode.");
        }
    }

    /**
     * Main entry point.
     */
    public static void main(String[] args) {
        setupLicense();
        run();
    }

    /**
     * Demonstrates redaction of RTF, TXT, and ODT documents.
     */
    public static void run() {
        // -----------------------------------------------------------------
        // Step 1. Define input and output paths.
        // -----------------------------------------------------------------
        String[] inputFiles = {
            "src/main/resources/input/sample.rtf",
            "src/main/resources/input/sample.txt",
            "src/main/resources/input/sample.odt"
        };

        String outputDir = "src/main/resources/output/";
        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        // -----------------------------------------------------------------
        // Step 2. Create redaction policy: replace "John Doe" with [REDACTED].
        // -----------------------------------------------------------------
        ExactPhraseRedaction redaction = new ExactPhraseRedaction("John Doe", "[REDACTED]");

        // -----------------------------------------------------------------
        // Step 3. Process each input file.
        // -----------------------------------------------------------------
        for (String inputFile : inputFiles) {
            File inputFileObj = new File(inputFile);
            if (!inputFileObj.exists()) {
                System.out.println("Input file not found: " + inputFile + " — skipping.");
                continue;
            }

            String fileName = inputFileObj.getName();
            String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
            String outputFile = outputDir + baseName + "_redacted." + fileName.substring(fileName.lastIndexOf('.') + 1);

            try (Redactor redactor = new Redactor(inputFile)) {
                // -----------------------------------------------------------------
                // Step 4. Apply the redaction.
                // -----------------------------------------------------------------
                redactor.apply(redaction);

                // -----------------------------------------------------------------
                // Step 5. Save the redacted document in original format.
                // -----------------------------------------------------------------
                redactor.save(outputFile);

                System.out.println("Redacted file saved: " + outputFile);
            } catch (Exception e) {
                System.err.println("Error processing " + inputFile + ": " + e.getMessage());
            }
        }

        // -----------------------------------------------------------------
        // Step 6. Cleanup is handled automatically by try-with-resources.
        // -----------------------------------------------------------------
    }
}
