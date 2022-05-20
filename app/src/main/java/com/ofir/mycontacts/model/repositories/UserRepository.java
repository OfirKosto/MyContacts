package com.ofir.mycontacts.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.Contact;
import com.ofir.mycontacts.model.User;
import com.ofir.mycontacts.model.databases.UserDatabase;
import com.ofir.mycontacts.model.interfaces.IUserCreateListener;

import java.util.ArrayList;

public class UserRepository {

    private static UserRepository m_Instance;
    private MutableLiveData<User>  m_CurrentUser;
    private Observer<User> m_CurrentUserObserver;
    private MutableLiveData<String>  m_MessageToUser;

    public UserRepository() {
        m_CurrentUser = new MutableLiveData<>();
        m_MessageToUser = new MutableLiveData<>();
    }

    public static UserRepository getInstance()
    {
        if(m_Instance == null)
        {
            synchronized (UserDatabase.class)
            {
                if(m_Instance == null)
                {
                    m_Instance = new UserRepository();
                }
            }
        }

        return m_Instance;
    }

    public  MutableLiveData<User> getCurrentUser(){ return  m_CurrentUser;}

    public  MutableLiveData<String> getMessageToUser(){ return  m_MessageToUser;}

    //TODO add listener? post value now
    public void signupNewUser(String i_Username, String i_Password, IUserCreateListener i_UserCreateListener)
    {
        //TODO check what return if not found
        LiveData<User> userLiveData = null;
        userLiveData = UserDatabase.getInstance().userDao().getUserByUsername(i_Username);

        if(userLiveData.getValue() != null)
        {
            i_UserCreateListener.onFailure(ApplicationContext.getContext()
                    .getResources().getString(R.string.username_taken));
        }
        else
        {
            User newUser = new User(i_Username, i_Password);
            UserDatabase.getInstance().userDao().insertUser(newUser);
            userLiveData = UserDatabase.getInstance().userDao().getUserByUsername(i_Username);
            if(userLiveData.getValue() == null)
            {
                i_UserCreateListener.onFailure(ApplicationContext.getContext()
                        .getResources().getString(R.string.couldnt_create_your_account));
            }
            else
            {
                i_UserCreateListener.onSuccess(ApplicationContext.getContext()
                        .getResources().getString(R.string.account_succesfully_created));
            }
        }
    }

    //TODO add listener? post value now
    public void loginUser(String i_Username, String i_Password)
    {
        //TODO check what return if not found
        LiveData<User> userLiveData = null;
        userLiveData = UserDatabase.getInstance().userDao().getUserByUsername(i_Username);

        //TODO CHECK IF userLiveData.getValue() != null?
        if(userLiveData.getValue() != null)
        {
            if(i_Password.equals(userLiveData.getValue().getM_Password()))
            {
                m_CurrentUserObserver = new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        m_CurrentUser.postValue(user);
                    }
                };
                userLiveData.observeForever(m_CurrentUserObserver);

                m_CurrentUser.postValue(userLiveData.getValue());
            }
            else
            {
                m_MessageToUser.postValue(ApplicationContext.getContext()
                        .getResources().getString(R.string.incorrect_username_or_password));
            }
        }
        else
        {
            m_MessageToUser.postValue(ApplicationContext.getContext()
                    .getResources().getString(R.string.incorrect_username_or_password));
        }

    }

    public void logoutUser()
    {
        if(m_CurrentUser.getValue() != null)
        {
            LiveData<User> user = UserDatabase.getInstance().userDao()
                    .getUserByUsername(m_CurrentUser.getValue().getM_Username());

            user.removeObserver(m_CurrentUserObserver);
            m_CurrentUserObserver = null;
            m_CurrentUser = new MutableLiveData<>();
        }
    }

    //TODO GET USER SOMEHOW (Not surly needed)
//    public ArrayList<Contact> getUserContacts()
//    {
//        if(m_CurrentUser == null)
//        {
//            return null;
//        }
//
//        return m_CurrentUser.postValue();
//    }

    public void addContactToCurrentUser(Contact i_Contact)
    {
//        m_CurrentUser.addContact(i_Contact);
//        UserDatabase.getInstance().userDao().insertUser(m_CurrentUser);
    }

    public void deleteContactFromCurrentUser(int i_ContactIndex) {
//        m_CurrentUser.deleteContact(i_ContactIndex);
//        UserDatabase.getInstance().userDao().insertUser(m_CurrentUser);
    }

//    private void updateCurrentUserInDatabase()
//    {
//        m_CurrentUser = UserDatabase.getInstance().userDao().getUserByUsername(m_CurrentUser.getUsername());
//    }

}
