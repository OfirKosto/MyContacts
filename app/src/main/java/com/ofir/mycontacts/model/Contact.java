package com.ofir.mycontacts.model;

import androidx.annotation.Nullable;

import com.ofir.mycontacts.model.enums.eGenders;

public class Contact {

    private String m_FirstName;
    private String m_LastName;
    private String m_PhoneNumber;
    private String m_Email;
    private String m_Gender;

    public Contact(String i_FirstName, String i_LastName,String i_PhoneNumber, String i_Email) {
        m_FirstName = i_FirstName;
        m_LastName = i_LastName;
        m_PhoneNumber = i_PhoneNumber;
        m_Email = i_Email;
        m_Gender = "";
    }

    public Contact(String i_FirstName, String i_LastName,String i_PhoneNumber, String i_Email, String i_Gender) {
        m_FirstName = i_FirstName;
        m_LastName = i_LastName;
        m_PhoneNumber = i_PhoneNumber;
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

    public String getPhoneNumber() {
        return m_PhoneNumber;
    }

    public void setPhoneNumber(String i_PhoneNumber) {
        m_PhoneNumber = i_PhoneNumber;
    }

    public String getEmail() {
        return m_Email;
    }

    public void setEmail(String i_Email) {
        m_Email = i_Email;
    }

    public String getGender() {
        return m_Gender;
    }

    public void setGender(String i_Gender) {
        m_Gender = i_Gender;
    }

    public boolean isEqual(Contact i_Contact)
    {
        return (i_Contact.getFirstName().equals(m_FirstName) && i_Contact.getLastName().equals(m_LastName) &&
                i_Contact.getPhoneNumber().equals(m_PhoneNumber) && i_Contact.getEmail().equals(m_Email)
                && i_Contact.getGender().equals(m_Gender));
    }
}
