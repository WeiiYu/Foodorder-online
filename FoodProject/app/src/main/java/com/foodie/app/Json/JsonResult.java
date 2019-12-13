package com.foodie.app.Json;

public class JsonResult<T> extends Result {
    private static final long serialVersionUID = 7880907731807860636L;

    private T data = null;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JsonResult() {
        super();
    }
}
