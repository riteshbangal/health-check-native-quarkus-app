package com.rcb.quarkus.health;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class HealthCheckProcessor implements Callable<Response> {

    /*
    Callable<String> callable = () -> {
        // Perform some computation
        Thread.sleep(2000);
        return "Return some result";
    };
    */

    private Logger LOGGER = Logger.getLogger(HealthCheckProcessor.class.getName());
    private String sURL;

    public HealthCheckProcessor(String pURL) {
        this.sURL = pURL;
    }

    @Override
    public Response call() throws Exception {

        URLConnection connection = null;
        try {
            // Connect to the URL using java's native library
            URL url = new URL(sURL);
            connection = url.openConnection();
            connection.connect();

            /*
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            */

            return new Response(connection, url).invoke();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
