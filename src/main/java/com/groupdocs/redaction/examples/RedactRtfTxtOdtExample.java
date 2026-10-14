package com.groupdocs.redaction.examples;

import com.groupdocs.redaction.RedactionStatus;
import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.RedactorChangeLog;
import com.groupdocs.redaction.exceptions.TrialLimitationsException;
import com.groupdocs.redaction.licensing.License;
import com.groupdocs.redaction.options.RasterizationOptions;
import com.groupdocs.redaction.redactions.ExactPhraseRedaction;
import com.groupdocs.redaction.redactions.ReplacementOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Demonstrates how to redact an exact phrase in RTF, TXT, and ODT documents using GroupDocs Redaction.
 * <p>
 * The example expects <code>sample.rtf</code>, <code>sample.txt</code>, and <code>sample.odt</code> in the
 * <code>resources/input/</code> directory (project root). Redacted files are saved to <code>resources/output/</code>
 * in the original format.
 * </p>
 */
public class RedactRtfTxtOdtExample {

    private static final String INPUT_DIR = "resources/input/";
    private static final String OUTPUT_DIR = "resources/output/";
    private static final String LICENSE_FILE = "GroupDocs.Redaction.Java.lic";
    private static final String PHRASE = "Lorem ipsum";
    private static final String REPLACEMENT = "[REDACTED]";
    private static final String[] INPUT_FILES = {
            INPUT_DIR + "sample.rtf",
            INPUT_DIR + "sample.txt",
            INPUT_DIR + "sample.odt"
    };

    /**
     * Loads the GroupDocs Redaction license if the license file exists.
     * <p>
     * To get a temporary license, visit:
     * <a href="https://purchase.groupdocs.com/temporary-license/">https://purchase.groupdocs.com/temporary-license/</a>.
     * Place the downloaded {@code GroupDocs.Redaction.Java.lic} file in the project root directory.
     * Without a license the library works in evaluation mode with a trial badge and a limit of one opened document per process.
     * </p>
     *
     * @param licensePath path to the license file relative to the project root
     */
    public static void loadLicense(String licensePath) {
        File licenseFile = new File(licensePath);
        if (licenseFile.exists()) {
            try {
                License license = new License();
                license.setLicense(licensePath);
                System.out.println("GroupDocs Redaction license loaded successfully.");
            } catch (Exception e) {
                System.err.println("Failed to load GroupDocs Redaction license: " + e.getMessage());
            }
        } else {
            System.out.println("License file not found at " + licensePath + ". Running in evaluation mode.");
        }
    }

    /**
     * Replaces <code>Lorem ipsum</code> with <code>[REDACTED]</code> and saves each document in its original format.
     * <p>
     * {@link ExactPhraseRedaction} takes the phrase and a {@link ReplacementOptions} value.
     * Rasterization is disabled, so RTF, TXT, and ODT are not written as PDF.
     * Output files use the pattern <code>sample_redacted.ext</code>. A missing input file is reported and skipped.
     * </p>
     *
     * @param inputFiles paths of the documents to redact
     */
    public static void redactDocuments(String[] inputFiles) {
        Path outDirPath = Paths.get(OUTPUT_DIR);
        try {
            if (!Files.exists(outDirPath)) {
                Files.createDirectories(outDirPath);
                System.out.println("Created output directory: " + outDirPath.toAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Failed to create output directory: " + e.getMessage());
            return;
        }

        ExactPhraseRedaction redaction = new ExactPhraseRedaction(PHRASE, new ReplacementOptions(REPLACEMENT));
        // save() with no arguments writes a rasterized PDF. Disable rasterization to keep the original format.
        RasterizationOptions rasterOptions = new RasterizationOptions();
        rasterOptions.setEnabled(false);

        for (String inputFile : inputFiles) {
            File inputFileObj = new File(inputFile);
            if (!inputFileObj.exists()) {
                System.err.println("Input file not found: " + inputFile);
                continue;
            }

            String fileName = inputFileObj.getName();
            int dot = fileName.lastIndexOf('.');
            String outputFile = outDirPath.resolve(fileName.substring(0, dot) + "_redacted" + fileName.substring(dot)).toString();

            try (Redactor redactor = new Redactor(inputFile)) {
                RedactorChangeLog result = redactor.apply(redaction);
                if (result.getStatus() == RedactionStatus.Failed) {
                    System.err.println("Redaction failed for " + inputFile + ": " + result.getStatus());
                    continue;
                }

                try (FileOutputStream stream = new FileOutputStream(outputFile)) {
                    redactor.save(stream, rasterOptions);
                }
                System.out.println("Redacted file saved: " + Paths.get(outputFile).toAbsolutePath()
                        + " (status: " + result.getStatus() + ")");
            } catch (TrialLimitationsException e) {
                System.out.println("Evaluation mode allows only one document in a process. "
                        + "Add " + LICENSE_FILE + " to the project root, or redact one file, for example: mvn exec:java -Dexec.args=\"txt\"");
                break;
            } catch (Exception e) {
                System.err.println("Error processing " + inputFile + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Resolves a command-line token to a path under <code>resources/input/</code>.
     * <p>
     * <code>txt</code> becomes <code>sample.txt</code>. <code>sample.odt</code> is used as a file name.
     * A token that already contains a slash is left unchanged.
     * </p>
     */
    private static String resolveInput(String token) {
        if (token.indexOf('/') >= 0 || token.indexOf('\\') >= 0) {
            return token;
        }
        if (token.indexOf('.') < 0) {
            return INPUT_DIR + "sample." + token;
        }
        return INPUT_DIR + token;
    }

    public static void main(String[] args) {
        // Load license if available
        loadLicense(LICENSE_FILE);
        // Perform the redaction demonstration
        if (args.length == 0) {
            redactDocuments(INPUT_FILES);
            return;
        }
        String[] selected = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            selected[i] = resolveInput(args[i]);
        }
        redactDocuments(selected);
    }
}
