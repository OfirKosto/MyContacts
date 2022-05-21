package com.ofir.mycontacts.model;

import com.google.gson.annotations.SerializedName;

public class JsonGetGenderResponse {

    @SerializedName("gender")
    private String m_Gender;

    public String getGender() {
        return m_Gender;
    }
}
