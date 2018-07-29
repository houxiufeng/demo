package com.example.demo.domain;

/**
 * Created by fitz on 2018/7/28.
 */
public class UserInfo extends User{
    private String countryName;
    private String code;
    private String address;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
