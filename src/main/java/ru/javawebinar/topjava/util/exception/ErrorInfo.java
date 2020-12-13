package ru.javawebinar.topjava.util.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorInfo {
    @JsonProperty
    private String url;

    @JsonProperty
    private ErrorType type;

    @JsonProperty
    private String detail;

    public ErrorInfo(){}

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public ErrorType getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }
}