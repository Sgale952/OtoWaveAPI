package github.otowave.api;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import spark.Request;

import java.io.IOException;
import java.util.Objects;

import static spark.Spark.before;
import static spark.Spark.staticFiles;

public class UploadHelper {
    //private static final String TEMP_DIR = "/home/otowave/data/temp/";
    private static final String TEMP_DIR = "D:\\i\\temp";

    public static void multipartConfig() {
        staticFiles.externalLocation(TEMP_DIR);

        before((req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(TEMP_DIR));

        });
    }

    public static boolean haveAttribute(String attribute, Request req) {
        for (String attr : req.attributes()) {
            if (Objects.equals(attr, attribute)) {
                return true;
            }
        }
        return false;
    }

    public static Part getStaticFilePart(Request req, String staticFileType) throws IOException, ServletException {
        return req.raw().getPart(staticFileType);
    }

    public static String getFileExtension(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static int convertToInt(String str) {
        if(str == null) {
            return 0;
        }
        return Integer.parseInt(str);
    }
}
