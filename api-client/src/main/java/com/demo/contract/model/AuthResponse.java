package com.demo.contract.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("partner_name")
    private String partnerName;
    @JsonProperty("partner_id")
    private String partnerId;
    @JsonProperty("expires")
    private Integer expires;
    @JsonProperty("token_type")
    private String tokenType;

}