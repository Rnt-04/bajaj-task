package com.rnt.bajaj.bajaj_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rnt.bajaj.bajaj_task.SolutionRequest;
import com.rnt.bajaj.bajaj_task.UserDetails;
import com.rnt.bajaj.bajaj_task.WebhookResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class App implements CommandLineRunner {

    private static final String NAME = "Ritindranath Tagore";
    private static final String REG_NO = "22BCG10049"; // Use your registration number
    private static final String EMAIL = "ritindranathtagore2022@vitbhopal.ac.in";

    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        UserDetails userDetails = new UserDetails(NAME, REG_NO, EMAIL);
        WebhookResponse webhookResponse = restTemplate.postForObject(GENERATE_WEBHOOK_URL, userDetails, WebhookResponse.class);

        if (webhookResponse == null || webhookResponse.getWebhook() == null || webhookResponse.getAccessToken() == null) {
            System.err.println("Failed to generate webhook. Response was null.");
            return;
        }

        String webhookUrl = webhookResponse.getWebhook();
        String accessToken = webhookResponse.getAccessToken();
        System.out.println("Webhook URL received: " + webhookUrl);
        System.out.println("Access Token received.");

        String finalQuery;
        finalQuery = "SELECT p.AMOUNT AS SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, d.DEPARTMENT_NAME FROM PAYMENTS p JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID WHERE DAY(p.PAYMENT_TIME) <> 1 ORDER BY p.AMOUNT DESC LIMIT 1;";

        System.out.println("Final SQL Query: " + finalQuery);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        SolutionRequest solution = new SolutionRequest(finalQuery);
        HttpEntity<SolutionRequest> requestEntity = new HttpEntity<>(solution, headers);

        try {
            restTemplate.exchange(webhookUrl, HttpMethod.POST, requestEntity, String.class);
            System.out.println("Solution submitted successfully!");
        } catch (Exception e) {
            System.err.println("Failed to submit solution: " + e.getMessage());
        }

        System.out.println("Hiring Task Finished");
    }
}