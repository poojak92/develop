
package com.scarlett.pogo.Login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "mobile",
    "email",
    "role",
    "status",
    "cre_dt",
    "mod_dt",
    "api_key"
})
public class LoginResult {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private String role;
    @JsonProperty("status")
    private String status;
    @JsonProperty("cre_dt")
    private String creDt;
    @JsonProperty("mod_dt")
    private String modDt;
    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }

    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("cre_dt")
    public String getCreDt() {
        return creDt;
    }

    @JsonProperty("cre_dt")
    public void setCreDt(String creDt) {
        this.creDt = creDt;
    }

    @JsonProperty("mod_dt")
    public String getModDt() {
        return modDt;
    }

    @JsonProperty("mod_dt")
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @JsonProperty("api_key")
    public String getApiKey() {
        return apiKey;
    }

    @JsonProperty("api_key")
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
