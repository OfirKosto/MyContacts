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

    @TypeConverter
    public ArrayList<Contact> stringToContactsList(String value)
    {
        Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

}
