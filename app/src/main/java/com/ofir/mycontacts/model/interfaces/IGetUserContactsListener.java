package com.ofir.mycontacts.model.interfaces;

import com.ofir.mycontacts.model.Contact;
import java.util.ArrayList;

public interface IGetUserContactsListener {

    void onSuccess(ArrayList<Contact> contactsArrayList);
    void onFailure(String s);
}
