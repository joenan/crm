package com.example.crmapi.Dto;

import java.io.Serializable;

public class TokenRequestDto implements Serializable {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expires_in;
    private String scope;


    public TokenRequestDto() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenRequestDto that = (TokenRequestDto) o;

        if (access_token != null ? !access_token.equals(that.access_token) : that.access_token != null) return false;
        if (refresh_token != null ? !refresh_token.equals(that.refresh_token) : that.refresh_token != null)
            return false;
        if (token_type != null ? !token_type.equals(that.token_type) : that.token_type != null) return false;
        if (expires_in != null ? !expires_in.equals(that.expires_in) : that.expires_in != null) return false;
        return scope != null ? scope.equals(that.scope) : that.scope == null;
    }

    @Override
    public int hashCode() {
        int result = access_token != null ? access_token.hashCode() : 0;
        result = 31 * result + (refresh_token != null ? refresh_token.hashCode() : 0);
        result = 31 * result + (token_type != null ? token_type.hashCode() : 0);
        result = 31 * result + (expires_in != null ? expires_in.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        return result;
    }
}
