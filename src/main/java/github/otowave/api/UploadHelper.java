package github.otowave.api;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import spark.Request;

import java.io.IOException;

import static spark.Spark.before;
import static spark.Spark.staticFiles;

public class UploadHelper {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public static void multipartConfig() {
        staticFiles.externalLocation(TEMP_DIR);

        before((req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        });
    }

    public static Part getStaticFilePart(Request req, String staticFileType) throws IOException, ServletException {
        return req.raw().getPart(staticFileType);
    }

    public static String getFileExtension(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static int convertToInt(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }
}
