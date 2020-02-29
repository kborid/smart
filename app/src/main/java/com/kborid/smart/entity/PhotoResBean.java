package com.kborid.smart.entity;

import java.util.List;

public class PhotoResBean {

    private boolean error;
    private List<PhotoGirl> results;

    @Override
    public String toString() {
        return "PhotoResBean{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<PhotoGirl> getResults() {
        return results;
    }

    public void setResults(List<PhotoGirl> results) {
        this.results = results;
    }
}
