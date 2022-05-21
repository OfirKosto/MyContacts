package com.ofir.mycontacts.model.repositories;

import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.Contact;
import com.ofir.mycontacts.model.User;
import com.ofir.mycontacts.model.databases.UserDatabase;
import com.ofir.mycontacts.model.interfaces.IActionListener;

import java.util.ArrayList;

public class UserRepository {

    private static UserRepository m_Instance;
    private User m_CurrentUser;

    public UserRepository() {
        m_CurrentUser = null;
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

    public void signupNewUser(String i_Username, String i_Password, IActionListener<String> i_UserCreateListener)
    {

        User user = null;
        user = UserDatabase.getInstance().userDao().getUserByUsername(i_Username);

        if(user != null)
        {
            i_UserCreateListener.onFailure(ApplicationContext.getContext()
                    .getResources().getString(R.string.username_taken));
        }
        else
        {
            User newUser = new User(i_Username, i_Password);
            UserDatabase.getInstance().userDao().insertUser(newUser);
            user = UserDatabase.getInstance().userDao().getUserByUsername(i_Username);

            if(user == null)
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

    public void loginUser(String i_Username, String i_Password, IActionListener<User> i_UserLoginListener)
    {
        User user = UserDatabase.getInstance().userDao().getUserByUsername(i_Username);

        if(user != null)
        {
            if(i_Password.equals(user.getM_Password()))
            {
                m_CurrentUser = user;
                i_UserLoginListener.onSuccess(m_CurrentUser);

            }
            else
            {
                i_UserLoginListener.onFailure(ApplicationContext.getContext()
                        .getResources().getString(R.string.incorrect_username_or_password));
            }
        }
        else
        {
            i_UserLoginListener.onFailure(ApplicationContext.getContext()
                    .getResources().getString(R.string.incorrect_username_or_password));
        }

    }

    public void logoutUser()
    {
        m_CurrentUser = null;
    }

    public void getUserContacts(IActionListener<ArrayList<Contact>> i_GetUserContactsListener)
    {
        if(m_CurrentUser == null)
        {
            i_GetUserContactsListener.onFailure(ApplicationContext.getContext()
                    .getResources().getString(R.string.somting_wrong_happend));
        }
        else
        {
            refreshCurrentUser();
            i_GetUserContactsListener.onSuccess(m_CurrentUser.getM_Contacts());
        }
    }

    public void deleteContactFromCurrentUser(
            Contact i_Contact, int i_ContactIndex, IActionListener<ArrayList<Contact>> i_DeleteContactListener) {

        if(m_CurrentUser.getContact(i_ContactIndex).isEqual(i_Contact))
        {
            m_CurrentUser.deleteContact(i_ContactIndex);
            UserDatabase.getInstance().userDao().insertUser(m_CurrentUser);

            User user = UserDatabase.getInstance().userDao().getUserByUsername(m_CurrentUser.getM_Username());
            if(user.getNumberOfContacts() == m_CurrentUser.getNumberOfContacts())
            {
                m_CurrentUser = user;
                i_DeleteContactListener.onSuccess(m_CurrentUser.getM_Contacts());
            }
            else
            {
                i_DeleteContactListener.onFailure(ApplicationContext.getContext()
                        .getResources().getString(R.string.failed_to_delete_contact));
            }
        }
        else
        {
            i_DeleteContactListener.onFailure(ApplicationContext.getContext()
                    .getResources().getString(R.string.somting_wrong_happend));
        }

    }

    private void refreshCurrentUser() {
        m_CurrentUser = UserDatabase.getInstance().userDao().getUserByUsername(m_CurrentUser.getM_Username());
    }



    public void addContactToCurrentUser(Contact i_Contact)
    {
//        m_CurrentUser.addContact(i_Contact);
//        UserDatabase.getInstance().userDao().insertUser(m_CurrentUser);
    }

//    private void updateCurrentUserInDatabase()
//    {
//        m_CurrentUser = UserDatabase.getInstance().userDao().getUserByUsername(m_CurrentUser.getUsername());
//    }

}
