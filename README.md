# GroupDocs Redaction Java – Redact RTF, TXT, and ODT

## Overview
This showcase project demonstrates how to use **GroupDocs Redaction** (Java) to replace an exact phrase in RTF, TXT, and OpenDocument (ODT) files and save each result in its original format. It is a minimal, runnable Maven project that helps developers understand how to redact text in these formats without converting them to PDF.

## Prerequisites
- **Java Development Kit (JDK) 8** or higher
- **Apache Maven** 3.5+ installed and added to your `PATH`
- An **Internet connection** for Maven to download dependencies

## License
GroupDocs Redaction requires a license file to work without evaluation limitations (trial badge, one opened document per process, and so on).

1. **Obtain a temporary license** – Visit https://purchase.groupdocs.com/temporary-license/ to get a free 30-day temporary license.
2. **Place the license file** – Save the downloaded `GroupDocs.Redaction.Java.lic` file in the **project root directory** (the same level where `pom.xml` resides).
3. **Without a license** – The library runs in evaluation mode, which adds a trial badge and opens only one document per process.

## Project Structure
```
redact-rtf-txt-odt-groupdocs-redaction-java/
│   pom.xml
│   README.md
│   GroupDocs.Redaction.Java.lic   (optional – place your license here)
│
├───src
│   └───main
│       └───java
│           └───com
│               └───groupdocs
│                   └───redaction
│                       └───examples
│                           └───RedactRtfTxtOdtExample.java
│
├───resources
│   ├───input
│   │   ├───sample.rtf             (sample RTF – replace with your own)
│   │   ├───sample.txt             (sample TXT – replace with your own)
│   │   └───sample.odt             (sample ODT – replace with your own)
│   └───output                     (redacted files will be saved here)
```

## Setup & Run
1. **Clone or copy the project**
   ```bash
   git clone https://github.com/your-repo/redact-rtf-txt-odt-groupdocs-redaction-java.git
   cd redact-rtf-txt-odt-groupdocs-redaction-java
   ```
2. **Add your license file** (optional but recommended) – copy `GroupDocs.Redaction.Java.lic` into the project root.
3. **Place RTF, TXT, and ODT files** you want to redact inside `resources/input/`. The project already contains `sample.rtf`, `sample.txt`, and `sample.odt`, which you can replace.
4. **Build the project**
   ```bash
   mvn clean compile
   ```
5. **Run the example**
   - Using Maven Exec plugin:
     ```bash
     mvn exec:java
     ```
   In evaluation mode only the first file is opened. Redact one sample by passing its extension:
   ```bash
   mvn exec:java -Dexec.args="rtf"
   mvn exec:java -Dexec.args="txt"
   mvn exec:java -Dexec.args="odt"
   ```

## What Happens When You Run It?
- The program loads the license (if present).
- It checks `resources/input/sample.rtf`, `sample.txt`, and `sample.odt`. A missing file is reported and skipped.
- An output directory `resources/output/` is created if it does not already exist.
- `ExactPhraseRedaction` replaces `Lorem ipsum` with `[REDACTED]`.
- Rasterization is turned off, so each file is saved in its original format as `sample_redacted.rtf`, `sample_redacted.txt`, and `sample_redacted.odt`.
- In evaluation mode the run stops after the first document, because the trial opens only one document per process.
- Console output informs you about the progress and any possible errors.

## Customization
- **Redact another phrase** – change `PHRASE` and `REPLACEMENT` in `RedactRtfTxtOdtExample`.
- **Make the match case-sensitive** – use `new ExactPhraseRedaction(PHRASE, true, new ReplacementOptions(REPLACEMENT))`.
- **Hide the match with a color box** – pass `new ReplacementOptions(java.awt.Color.BLACK)` instead of a replacement string.
- **Process other files** – replace the samples in `resources/input/`, or change `INPUT_FILES` in the source.

## Notes
- The included samples contain the phrase `Lorem ipsum`, so the replacement can be checked immediately.
- `Redactor.save()` with no arguments writes a rasterized PDF. This example disables rasterization so the output keeps the source extension.
- Ensure the `resources/input/` and `resources/output/` directories are at the same level as `pom.xml` as shown in the structure above.

---

*This is a showcase project for GroupDocs Redaction. It is intended for demonstration and educational purposes.*
