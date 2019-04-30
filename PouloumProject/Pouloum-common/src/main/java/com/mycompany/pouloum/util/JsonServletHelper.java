package com.mycompany.pouloum.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mycompany.pouloum.util.exception.ServiceException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author WASO Team
 */
public class JsonServletHelper {

    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String CONTENTTYPE_JSON = "application/json";

    public static final SimpleDateFormat JSON_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat JSON_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static void printJsonOutput(HttpServletResponse response, JsonObject container) throws IOException {

        response.setContentType(CONTENTTYPE_JSON);
        response.setCharacterEncoding(ENCODING_UTF8);

        Gson gson = new GsonBuilder().create();
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        jsonWriter.setIndent("  ");
        jsonWriter.setSerializeNulls(true);
        gson.toJson(container, jsonWriter);
        jsonWriter.close();

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        PrintWriter out = response.getWriter();
//        out.println(gson.toJson(container));
//        out.close();
    }

    public static ServiceException ServiceObjectMetierExecutionException(String bloc, String som, String error) {
        return new ServiceException("Error in SOM " + bloc + "::" + som + ": " + error);
    }

    public static ServiceException ServiceObjectMetierExecutionException(String bloc, String som, String error, Exception ex) {
        return new ServiceException("Exception in SOM " + bloc + "::" + som + ": " + error, ex);
    }

    public static ServiceException ServiceObjectMetierExecutionException(String bloc, String som, Exception ex) {
        return new ServiceException("Exception in SOM " + bloc + "::" + som, ex);
    }

    public static ServiceException ServiceMetierExecutionException(String sma, String error) {
        return new ServiceException("Error in SMA " + sma + ": " + error);
    }

    public static ServiceException ServiceMetierExecutionException(String sma, String error, Exception ex) {
        return new ServiceException("Exception in SMA " + sma + ": " + error, ex);
    }

    public static ServiceException ServiceMetierExecutionException(String sma, Exception ex) {
        return new ServiceException("Exception in SMA " + sma, ex);
    }

    public static ServiceException ActionExecutionException(String action, String error) {
        return new ServiceException("Error in AJAX Action " + action + ": " + error);
    }
    
    public static ServiceException ActionExecutionException(String action, Exception ex) {
        return new ServiceException("Exception in AJAX Action " + action, ex);
    }
    
    public static ServiceException ActionExecutionException(String action, String error, Exception ex) {
        return new ServiceException("Exception in AJAX Action " + action + ": " + error, ex);
    }

    
    public static ServiceException ServiceObjectMetierCallException(String url, String bloc, String som) {
        return new ServiceException("Error with call to SOM " + bloc + "::" + som + " [" + url + "]");
    }

    public static ServiceException ServiceMetierCallException(String url, String sma) {
        return new ServiceException("Error with call to SMA " + sma + " [" + url + "]");
    }

}
