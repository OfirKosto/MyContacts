package com.ofir.mycontacts.view.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.ofir.mycontacts.model.User;
import com.ofir.mycontacts.model.repositories.UserRepository;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<User> m_CurrentUser;
    private MutableLiveData<String>  m_MessageToUser;

    public LoginViewModel() {
        m_CurrentUser = new MutableLiveData<>();
        m_MessageToUser = new MutableLiveData<>();
        initObservers();
    }

    public  MutableLiveData<User> getCurrentUser(){ return  m_CurrentUser;}

    public  MutableLiveData<String> getMessageToUser(){ return  m_MessageToUser;}

    public void loginUser(String i_Username, String i_Password)
    {
        UserRepository.getInstance().loginUser(i_Username, i_Password);
    }

    private void initObservers()
    {
        UserRepository.getInstance().getCurrentUser().observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                m_CurrentUser.postValue(user);
            }
        });

        UserRepository.getInstance().getMessageToUser().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                m_MessageToUser.postValue(s);
            }
        });
    }

}