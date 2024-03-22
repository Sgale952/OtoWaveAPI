package github.otowave.api;

import jakarta.servlet.MultipartConfigElement;

import static spark.Spark.before;
import static spark.Spark.staticFiles;

public class CommonUtils {

    //TODO: Change temp dir in Linux
    public static void multipartConfig() {
        staticFiles.externalLocation(System.getProperty("java.io.tmpdir"));

        before((req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        });
    }

    public static int convertToInt(String str) {
        return str == null? -1 : Integer.parseInt(str);
    }
}
