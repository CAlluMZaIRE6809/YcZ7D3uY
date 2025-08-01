// 代码生成时间: 2025-08-01 23:12:21
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A utility class for unzipping files using Java.
 */
public class UnzipTool {

    /**
     * Unzips a ZIP file to a specified directory.
     *
     * @param zipFilePath Path to the ZIP file.
     * @param destDirectory Destination directory to unzip the files.
     * @throws IOException If an I/O error occurs during unzipping.
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // Iterate over the entries in the ZIP file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // If the entry is a file, extract it
                extractFile(zipIn, filePath);
            } else {
                // If the entry is a directory, create it
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a file from a ZIP input stream.
     *
     * @param zipIn The ZIP input stream.
     * @param filePath Path of the file to extract.
     * @throws IOException If an I/O error occurs during extraction.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    /**
     * Main method to test the UnzipTool class.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        UnzipTool unzipper = new UnzipTool();
        try {
            // Replace with the path to your ZIP file and the destination directory
            String zipFilePath = "path/to/your/zipfile.zip";
            String destDirectory = "path/to/destination/directory";
            unzipper.unzip(zipFilePath, destDirectory);
            System.out.println("Unzipping complete.");
        } catch (IOException e) {
            // Handle exceptions here
            e.printStackTrace();
            System.out.println("An error occurred during unzipping.");
        }
    }
}