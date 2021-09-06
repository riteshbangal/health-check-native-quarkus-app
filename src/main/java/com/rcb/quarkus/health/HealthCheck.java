package com.rcb.quarkus.health;

import com.rcb.quarkus.HealthCheckApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static com.rcb.quarkus.utils.Utility.HTTP_SUCCESS_STATUS_CODE;
import static com.rcb.quarkus.utils.Utility.HTTP_SUCCESS_STATUS_MESSAGE;

public class HealthCheck {

    private Logger LOGGER = Logger.getLogger(HealthCheckApp.class.getName());

    public String checkHealth() {
        try {
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
            //scheduledExecutorService.schedule(new FactorialCalculator(5), 15, TimeUnit.SECONDS);
            //scheduledExecutorService.scheduleAtFixedRate(new Processor(), 5, 10, TimeUnit.SECONDS);
            scheduledExecutorService.scheduleWithFixedDelay(new Processor(), 5, 20, TimeUnit.SECONDS);

            return "Healthy";
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        return "Unhealthy!";
    }

    private static class Processor implements Runnable {

        private Logger LOGGER = Logger.getLogger(this.getClass().getName());

        //String sURL = "http://localhost:5000/health"; //just a string
        //String sURL = "http://localhost:8080/hello"; //just a string
        //String sURL = "http://localhost:8080/hello/v1"; //just a string
        //String sURL = "https://app-gw.eintuition.net/readstories-app"; //just a string
        String sURL = "https://github.com/riteshbangal/health-check-native-quarkus-app"; //just a string


        @Override
        public void run() {
            LOGGER.info("****** Start: Processor execution ******");
            int coreCount = Runtime.getRuntime().availableProcessors(); // If your task is CPU intensive, not IO intensive.
            LOGGER.info("Number of Cores: " + coreCount);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(coreCount);

            List<Future<Response>> resultList = new ArrayList<>();

            Random random = new Random();

            for (int i = 0; i < 4; i++) {
                HealthCheckProcessor calculator = new HealthCheckProcessor(sURL);
                Future<Response> result = executor.submit(calculator);
                resultList.add(result);

                //CompletableFuture.
            }
            // LOGGER.info("Result has been prepared.");

            for (Future<Response> future : resultList) {
                try {
                    Response response = future.get(10, TimeUnit.SECONDS);
                    if (response.getResponseCode() == HTTP_SUCCESS_STATUS_CODE || HTTP_SUCCESS_STATUS_MESSAGE.equals(response.getResponseMessage())) {
                        LOGGER.info("Future result is - Healthy");
                    } else {
                        LOGGER.info("Future result is - Unhealthy");
                    }
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    future.cancel(false);
                    LOGGER.info("future.isCancelled(): " + future.isCancelled());

                    e.printStackTrace();
                }
            }

            // shut down the executor service now
            //executor.shutdown();
            executor.shutdownNow();
            LOGGER.info("executor.isShutdown(): " + executor.isShutdown());
            LOGGER.info("executor.isTerminated(): " + executor.isTerminated());

            LOGGER.info("****** End: Processor execution ******");
        }
    }
}
