package com.mycompany.pouloum.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author WASO Team
 */
public class JsonHttpClient {

    public static class Parameter extends BasicNameValuePair {
        
        public Parameter(String name, String value) {
            super(name, value);
        }
    }
    
    protected CloseableHttpClient httpclient;

    public JsonHttpClient() {
        httpclient = HttpClients.createDefault();
    }

    public void close() throws IOException {
        httpclient.close();
    }

    public JsonObject post(String url, NameValuePair... parameters) throws IOException {

        JsonElement responseElement = null;
        
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(parameters), JsonServletHelper.ENCODING_UTF8));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        
        try {

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(entity.getContent(), JsonServletHelper.ENCODING_UTF8));
                try {

                    JsonParser parser = new JsonParser();
                    responseElement = parser.parse(jsonReader);

                } finally {
                    jsonReader.close();
                }
            }

        } finally {
            response.close();
        }

        JsonObject responseContainer = null;
        try {
            if (responseElement != null) {
                responseContainer = responseElement.getAsJsonObject();
            }
        } catch (IllegalStateException ex) {
            throw new IOException("Wrong HTTP Response Format - not a JSON Object (bad request?)", ex);
        }

        return responseContainer;
    }

    public static boolean checkJsonObject(JsonElement callResult) {

        return (callResult != null && callResult.isJsonObject());
    }

}
