package com.ofir.mycontacts.view.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ofir.mycontacts.model.User;
import com.ofir.mycontacts.model.interfaces.IActionListener;
import com.ofir.mycontacts.model.repositories.UserRepository;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<User> m_CurrentUser;
    private MutableLiveData<String>  m_MessageToUser;

    public LoginViewModel() {
        m_CurrentUser = new MutableLiveData<>();
        m_MessageToUser = new MutableLiveData<>();
    }

    public  MutableLiveData<User> getCurrentUser(){ return  m_CurrentUser;}

    public  MutableLiveData<String> getMessageToUser(){ return  m_MessageToUser;}

    public void loginUser(String i_Username, String i_Password)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository.getInstance().loginUser(i_Username, i_Password, new IActionListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        m_CurrentUser.postValue(user);
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