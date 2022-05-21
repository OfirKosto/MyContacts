package com.ofir.mycontacts.view.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.interfaces.IActionListener;
import com.ofir.mycontacts.model.repositories.UserRepository;

public class SignupViewModel extends ViewModel {
        //TODO check flip phone details deleted

    private final int SIGNUP_FIELDS_MIN_LENGTH = 6;
    private MutableLiveData<String> m_MessageToUser;
    private MutableLiveData<Boolean> m_FinishedSignupFlag;

    public SignupViewModel()
    {
        m_MessageToUser = new MutableLiveData<>();
        m_FinishedSignupFlag = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMessageToUser(){return m_MessageToUser;}

    public MutableLiveData<Boolean> getFinishedSignupFlag(){return m_FinishedSignupFlag;}

    public void signupNewUser(String i_Username, String i_Password, String i_ConfirmPassword)
    {

        if(!i_Username.isEmpty() && !i_Password.isEmpty() && !i_ConfirmPassword.isEmpty())
        {
            if(!i_Username.contains(" ") && !i_Password.contains(" "))
            {
                if(!(i_Username.length() < SIGNUP_FIELDS_MIN_LENGTH) && !(i_Password.length() < SIGNUP_FIELDS_MIN_LENGTH))
                {
                    if(i_Password.equals(i_ConfirmPassword))
                    {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                UserRepository.getInstance().signupNewUser(i_Username, i_Password, new IActionListener<String>() {
                                    @Override
                                    public void onSuccess(String s) {
                                        m_MessageToUser.postValue(s);
                                        m_FinishedSignupFlag.postValue(true);
                                    }

                                    @Override
                                    public void onFailure(String s) {
                                        m_MessageToUser.postValue(s);
                                        m_FinishedSignupFlag.postValue(false);
                                    }
                                });
                            }
                        }).start();
                    }
                    else
                    {
                        m_MessageToUser.postValue(ApplicationContext.getContext()
                                .getResources().getString(R.string.passwords_do_not_match));
                    }
                }
                else
                {
                    m_MessageToUser.postValue(ApplicationContext.getContext()
                            .getResources().getString(R.string.username_and_password_should_be_at_least_6_charachters));
                }
            }
            else
            {
                m_MessageToUser.postValue(ApplicationContext.getContext()
                        .getResources().getString(R.string.shouldnt_contain_spaces));
            }
        }
        else
        {
            m_MessageToUser.postValue(ApplicationContext.getContext()
                    .getResources().getString(R.string.please_fill_all_the_details));
        }
    }

}