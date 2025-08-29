package com.rnt.bajaj.bajaj_task;

public class WebhookResponse {
    private String webhookResponse;
    private String accessToken;

    public WebhookResponse(String webhookResponse, String accessToken) {
        this.webhookResponse = webhookResponse;
        this.accessToken = accessToken;
    }

    public String getWebhookResponse() {
        return webhookResponse;
    }
    public void setWebhookResponse(String webhookResponse) {
        this.webhookResponse = webhookResponse;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
