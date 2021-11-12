package com.aybu9.aybualumni.core.result;

public class DataResult<T> extends Result {

    private T data;

    public DataResult(T data, boolean success, String message) {
        super(success, message, "DataResult");
        this.data = data;
    }

    public DataResult(T data, boolean success) {
        super(success, null, "DataResult");
        this.data = data;
    }
    
    
    public T getData() {
        return data;
    }
}   
