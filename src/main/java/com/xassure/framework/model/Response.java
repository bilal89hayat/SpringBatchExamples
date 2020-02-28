package com.xassure.framework.model;

import java.io.Serializable;

public class Response implements Serializable {

    private String message;

    public Response(String str) {
        super();
        this.message = str;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
