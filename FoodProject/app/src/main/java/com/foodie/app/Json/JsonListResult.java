package com.foodie.app.Json;

import java.util.List;

public class JsonListResult<T> extends Result {
    private static final long serialVersionUID = 7880907731807860636L;

    private List<T> data;


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public JsonListResult() {
        super();
    }
}
