package com.aybu9.aybualumni.core.result;

import lombok.Data;

@Data
public class Result {

    private final boolean success;
    private String message;
    private String type = "result";
    private String detailedType = "Result";

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this(success);
        this.message = message;
    }

    public Result(boolean success, String message, String detailedType) {
        this(success, message);
        this.detailedType = detailedType;
    }
}
