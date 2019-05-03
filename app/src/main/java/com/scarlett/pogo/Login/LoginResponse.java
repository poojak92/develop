
package com.scarlett.pogo.Login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "success",
    "step",
    "result"
})
public class LoginResponse {

    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("step")
    private Integer step;
    @JsonProperty("result")
    private LoginResult result;

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
    public LoginResult getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(LoginResult result) {
        this.result = result;
    }

}
