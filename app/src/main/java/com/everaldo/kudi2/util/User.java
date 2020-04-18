package com.everaldo.kudi2.util;

public class User {

    private String phone;
    private String email;
    private String password;
    private String name;
    private String address;
    private String idCardType;
    private String idCard;
    private String idCardValidity;

    public User(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public User(String phone, String email, String password) {
        this(phone, email);
        this.password = password;
    }
    public User(String phone, String email, String password, String name) {
        this(phone, email);
        this.password = password;
        this.name = name;
    }
    public User(String phone, String email, String password, String name, String address, String idCardType, String idCard, String idCardValidity) {
        this(phone, email);
        this.password = password;
        this.name = name;
        this.address = address;
        this.idCardType = idCardType;
        this.idCard = idCard;
        this.idCardValidity = idCardValidity;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getIdCardValidity() {
        return idCardValidity;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setIdCardValidity(String idCardValidity) {
        this.idCardValidity = idCardValidity;
    }


    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
