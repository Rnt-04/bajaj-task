package com.rnt.bajaj.bajaj_task;

public class SolutionRequest {
    private static String finalQuery;

    SolutionRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }
    public String getFinalQuery() {
        return finalQuery;
    }
}
