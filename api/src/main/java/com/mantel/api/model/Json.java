package com.mantel.api.model;

public class Json {
    private Object data;
    private Object info;

    public Json(Object data, Object info) {
        this.data = data;
        this.info = info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
