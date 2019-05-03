
package com.scarlett.pogo.Register;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "success",
    "step",
    "result"
})
public class RegisterResponse {

    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("step")
    private Integer step;
    @JsonProperty("result")
    private RegisterResult result;

    @JsonProperty("error")
    private String error;

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonProperty("step")
    public Integer getStep() {
        return step;
    }

    @JsonProperty("step")
    public void setStep(Integer step) {
        this.step = step;
    }

    @JsonProperty("result")
    public RegisterResult getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(RegisterResult result) {
        this.result = result;
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(String error) {
        this.error = error;
    }

}
