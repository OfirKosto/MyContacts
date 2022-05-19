package com.ofir.mycontacts.model;

import com.google.gson.annotations.SerializedName;
import com.ofir.mycontacts.model.enums.eGenders;

import java.util.ArrayList;

public class JsonGetGenderResponse {

    @SerializedName("gender")
    private String m_Gender;

    public eGenders getGender() {
        return eGenders.valueOf(m_Gender);
    }
}
