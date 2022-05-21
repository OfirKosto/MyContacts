package com.ofir.mycontacts.view.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.Contact;
import com.ofir.mycontacts.model.interfaces.IActionListener;
import com.ofir.mycontacts.model.repositories.UserRepository;

import java.util.ArrayList;

public class SaveContactViewModel extends ViewModel {

    private MutableLiveData<String> m_MessageToUser;
    private MutableLiveData<Boolean> m_FinishedSaveContactFlag;

    public SaveContactViewModel() {
        m_MessageToUser = new MutableLiveData<>();
        m_FinishedSaveContactFlag = new MutableLiveData<>();
    }

    public  MutableLiveData<String> getMessageToUser(){ return  m_MessageToUser;}

    public  MutableLiveData<Boolean> getFinishedSaveContactFlag(){ return  m_FinishedSaveContactFlag;}

    public void editContact(int i_PositionOfOldContact, Contact i_UpdatedContact)
    {
        if(!i_UpdatedContact.getFirstName().isEmpty())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserRepository.getInstance().updateContact(i_PositionOfOldContact, i_UpdatedContact, new IActionListener<Boolean>() {
                        @Override
                        public void onSuccess(Boolean data) {
                            m_FinishedSaveContactFlag.postValue(data);
                        }

                        @Override
                        public void onFailure(String s) {
                            m_MessageToUser.postValue(s);
                        }
                    });
                }
            }).start();
        }
        else
        {
            m_MessageToUser.postValue(ApplicationContext.getContext()
                    .getResources().getString(R.string.please_enter_contact_first_name));
        }
    }

    public void addContact(Contact i_NewContact)
    {
        if(!i_NewContact.getFirstName().isEmpty())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserRepository.getInstance().addNewContact(i_NewContact, new IActionListener<Boolean>() {
                        @Override
                        public void onSuccess(Boolean data) {
                            m_FinishedSaveContactFlag.postValue(data);
                        }

                        @Override
                        public void onFailure(String s) {
                            m_MessageToUser.postValue(s);
                        }
                    });
                }
            }).start();
        }
        else
        {
            m_MessageToUser.postValue(ApplicationContext.getContext()
                    .getResources().getString(R.string.please_enter_contact_first_name));
        }
    }
}