package com.school.chalkandroll.model;



public class Response {

    private String ResponseCode;

    private String ResponseMessage;

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        ResponseMessage = responseMessage;
    }

    public Response(String responseCode, String responseMessage) {
        ResponseCode = responseCode;
        ResponseMessage = responseMessage;
    }

    public Response() {
    }


}