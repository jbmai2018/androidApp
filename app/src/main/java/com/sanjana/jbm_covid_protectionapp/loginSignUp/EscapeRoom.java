package com.sanjana.jbm_covid_protectionapp.loginSignUp;

public class EscapeRoom {

    private String name;
    private String id;
    private String phone;
    private String photoUrl;

    public EscapeRoom() {

    }

    public EscapeRoom(String name, String address, String url) {
        this.name = name;
        this.id = address;
        this.phone = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return id;
    }

    public void setAddress(String address) {
        this.id = address;
    }

    public String getUrl() {
        return phone;
    }

    public void setUrl(String url) {
        this.phone = url;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
