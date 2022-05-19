package com.ofir.mycontacts.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    private String m_Username;

    @ColumnInfo(name = "password")
    private String m_Password;

    @ColumnInfo(name = "contacts")
    private ArrayList<Contact> m_Contacts;

    public User(String i_Username, String i_Password) {
        m_Username = i_Username;
        m_Password = i_Password;
        m_Contacts = new ArrayList<>();
    }

    public String getUsername() {
        return m_Username;
    }

    public void setUsername(String m_Username) {
        this.m_Username = m_Username;
    }

    public String getPassword() {
        return m_Password;
    }

    public void setPassword(String m_Password) {
        this.m_Password = m_Password;
    }

    public ArrayList<Contact> getContacts() {
        return new ArrayList<>(m_Contacts);
    }

    public void addContact(Contact i_Contact)
    {
        m_Contacts.add(i_Contact);
    }

    public void deleteContact(int i_ContactIndex)
    {
        m_Contacts.remove(i_ContactIndex);
    }
}
