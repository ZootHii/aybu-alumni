package com.aybu9.aybualumni.core.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionDetails {
    private boolean success = false;
    private String message;
    private String type;
    private String detailedType;

    public ExceptionDetails(String message, String type, String detailedType) {
        this.message = message;
        this.type = type;
        this.detailedType = detailedType;
    }
}