package com.app.architecture.model.base;

public class BaseResponse<T> {

    private Errors er;

    private T rs;

    private int sc;

    public int getStatus() {
        return sc;
    }

    public void setStatus(int sc) {
        this.sc = sc;
    }

    public T getResult() {
        return rs;
    }

    public void setResult(T rs) {
        this.rs = rs;
    }


    public Errors getError() {
        return er;
    }

    public void setError(Errors er) {
        this.er = er;
    }
}
