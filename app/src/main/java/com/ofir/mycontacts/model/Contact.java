package com.ofir.mycontacts.model;

import com.ofir.mycontacts.model.enums.eGenders;

public class Contact {

    private String m_FirstName;
    private String m_LastName;
    private String m_Email;
    private eGenders m_Gender;

    public Contact(String i_FirstName, String i_LastName, String i_Email, eGenders i_Gender) {
        m_FirstName = i_FirstName;
        m_LastName = i_LastName;
        m_Email = i_Email;
        m_Gender = i_Gender;
    }

    public String getFirstName() {
        return m_FirstName;
    }

    public void setFirstName(String i_FirstName) {
        m_FirstName = i_FirstName;
    }

    public String getLastName() {
        return m_LastName;
    }

    public void setLastName(String i_LastName) {
        m_LastName = i_LastName;
    }

    public String getEmail() {
        return m_Email;
    }

    public void setEmail(String i_Email) {
        m_Email = i_Email;
    }

    public eGenders getGender() {
        return m_Gender;
    }

    public void setGender(eGenders i_Gender) {
        m_Gender = i_Gender;
    }
}
