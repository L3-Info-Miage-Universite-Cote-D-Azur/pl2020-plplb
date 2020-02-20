package com.example.plplbproject;

public class Ue {

    private String ueName;
    private String ueCode;
    private Boolean checked;

    public Ue(String ueName, String ueCode) {
        this.ueName = ueName;
        this.ueCode = ueCode;
        this.checked = false;
    }

    public String getUeName() {
        return ueName;
    }

    public String getUeCode() {
        return ueCode;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
