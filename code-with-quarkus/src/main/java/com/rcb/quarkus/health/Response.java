package com.rcb.quarkus.health;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static com.rcb.quarkus.utils.Utility.HTTPS_PROTOCOL;
import static com.rcb.quarkus.utils.Utility.HTTP_PROTOCOL;

public class Response {
    private URLConnection connection;
    private URL url;

    private int responseCode;
    private String responseMessage;

    public Response(URLConnection connection, URL url) {
        this.connection = connection;
        this.url = url;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Response invoke() throws Exception {
        if (HTTP_PROTOCOL.equalsIgnoreCase(url.getProtocol())) {
            responseCode = ((HttpURLConnection) connection).getResponseCode();
            responseMessage = ((HttpURLConnection) connection).getResponseMessage();
        } else if (HTTPS_PROTOCOL.equalsIgnoreCase(url.getProtocol())) {
            responseCode = ((HttpsURLConnection) connection).getResponseCode();
            responseMessage = ((HttpsURLConnection) connection).getResponseMessage();
        } else {
            throw new Exception("Wrong protocol");
        }
        return this;
    }
}