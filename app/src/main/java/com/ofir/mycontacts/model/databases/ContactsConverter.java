package com.ofir.mycontacts.model.databases;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ofir.mycontacts.model.Contact;

import java.lang.reflect.Type;
import java.util.ArrayList;
@ProvidedTypeConverter
public class ContactsConverter {

    @TypeConverter
    public String contactsListToString(ArrayList<Contact> contactsArraylist)
    {
        Gson gson = new Gson();
        String json = gson.toJson(contactsArraylist);
        return json;
    }

    public ArrayList<Contact> stringToContactsList(String value)
    {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

}
