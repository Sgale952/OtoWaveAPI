package github.otowave.api;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import spark.Request;

import java.io.IOException;

import static spark.Spark.before;
import static spark.Spark.staticFiles;

public class UploadHelper {

    //TODO: Change temp dir in Linux
    public static void multipartConfig() {
        staticFiles.externalLocation(System.getProperty("java.io.tmpdir"));

        before((req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        });
    }

    public static Part getStaticFilePart(Request req, String staticFileType) throws IOException, ServletException {
        Part filePart;
        filePart = req.raw().getPart(staticFileType);
        return filePart;
    }

    public static String getFileExtension(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static int convertToInt(String str) {
        if(str == null) {
            throw new NullPointerException("Value is null");
        }
        return Integer.parseInt(str);
    }
}
