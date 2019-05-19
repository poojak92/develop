package com.scarlett.pogo.Upload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("step")
    @Expose
    private Integer step;
    @SerializedName("error")
    @Expose
    private String error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}