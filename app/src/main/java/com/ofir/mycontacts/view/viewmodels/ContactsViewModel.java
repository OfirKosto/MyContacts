package com.ofir.mycontacts.view.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ofir.mycontacts.model.Contact;
import com.ofir.mycontacts.model.User;
import com.ofir.mycontacts.model.interfaces.IGetUserContactsListener;
import com.ofir.mycontacts.model.repositories.UserRepository;

import java.util.ArrayList;

public class ContactsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Contact>> m_ContactsArrayList;
    private MutableLiveData<String>  m_MessageToUser;

    public ContactsViewModel() {
        m_ContactsArrayList = new MutableLiveData<>();
        m_MessageToUser = new MutableLiveData<>();
    }

    public  MutableLiveData<ArrayList<Contact>> getContactsArrayList(){ return  m_ContactsArrayList;}

    public  MutableLiveData<String> getMessageToUser(){ return  m_MessageToUser;}

    public void logOut()
    {
        UserRepository.getInstance().logoutUser();
    }

    public void getUserContacts()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository.getInstance().getUserContacts(new IGetUserContactsListener() {
                    @Override
                    public void onSuccess(ArrayList<Contact> contactsArrayList) {
                        getContactsArrayList().postValue(contactsArrayList);
                    }

                    @Override
                    public void onFailure(String s) {
                        m_MessageToUser.postValue(s);
                    }
                });
            }
        }).start();
    }
}