package com.ofir.mycontacts.model.repositories;

import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.Contact;
import com.ofir.mycontacts.model.JsonGetGenderResponse;
import com.ofir.mycontacts.model.User;
import com.ofir.mycontacts.model.databases.UserDatabase;
import com.ofir.mycontacts.model.interfaces.IActionListener;
import com.ofir.mycontacts.model.util.GenderApiUtil;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        User user = UserDatabase.getInstance().userDao().getUserByUsername(i_Username);

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
                    .getResources().getString(R.string.user_is_disconnected_please_reconncect));
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


    public void updateContact(int i_Position, Contact i_UpdatedContact, IActionListener<Boolean> i_UpdateContactListener)
    {
        m_CurrentUser.setContact(i_Position, i_UpdatedContact);
        UserDatabase.getInstance().userDao().insertUser(m_CurrentUser);

        User user = UserDatabase.getInstance().userDao().getUserByUsername(m_CurrentUser.getM_Username());
        if(user.getContact(i_Position).isEqual(m_CurrentUser.getContact(i_Position)))
        {
            m_CurrentUser = user;
            i_UpdateContactListener.onSuccess(true);
        }
        else
        {
            i_UpdateContactListener.onFailure(ApplicationContext
                    .getContext().getResources().getString(R.string.failed_to_update_contact));
        }
    }

    public void addNewContact(Contact i_NewContact, IActionListener<Boolean> i_AddContactListener)
    {
        getGenderForName(i_NewContact.getFirstName(), new IActionListener<String>() {
            @Override
            public void onSuccess(String data) {

                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(ApplicationContext.getContext().getResources().getStringArray(R.array.genders_array)));

                if(data == null)
                {
                    i_NewContact.setGender(arrayList.get(0));
                }
                else if(data.equals("male"))
                {
                    i_NewContact.setGender(arrayList.get(1));
                }
                else if (data.equals("female"))
                {
                    i_NewContact.setGender(arrayList.get(2));
                }
                else
                {
                    i_NewContact.setGender(arrayList.get(0));
                }

                addContact(i_NewContact, i_AddContactListener);
            }

            @Override
            public void onFailure(String s) {
                i_NewContact.setGender(s);
                addContact(i_NewContact, i_AddContactListener);
            }
        });
    }

    private void addContact(Contact i_Contact, IActionListener<Boolean> i_AddContactListener)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                m_CurrentUser.addContact(i_Contact);
                UserDatabase.getInstance().userDao().insertUser(m_CurrentUser);

                User user = UserDatabase.getInstance().userDao().getUserByUsername(m_CurrentUser.getM_Username());
                if(user.getNumberOfContacts() == m_CurrentUser.getNumberOfContacts())
                {
                    m_CurrentUser = user;
                    i_AddContactListener.onSuccess(true);
                }
                else
                {
                    i_AddContactListener.onFailure(ApplicationContext.getContext()
                            .getResources().getString(R.string.failed_to_add_contact));
                }
            }
        }).start();
    }

    private void getGenderForName(String i_Name, IActionListener<String> i_GetGenderForNameListener)
    {
        GenderApiUtil.getInstance().getGenderByName(i_Name, new Callback<JsonGetGenderResponse>() {
            @Override
            public void onResponse(Call<JsonGetGenderResponse> call, Response<JsonGetGenderResponse> response) {
                if(response.isSuccessful())
                {
                    if(response.body() != null)
                    {
                        if(response.body().getGender() != null)
                        {
                            i_GetGenderForNameListener.onSuccess(response.body().getGender());
                        }
                        else
                        {
                            i_GetGenderForNameListener.onSuccess(null);
                        }
                    }
                }
                else
                {
                    i_GetGenderForNameListener.onFailure(" ");
                }
            }

            @Override
            public void onFailure(Call<JsonGetGenderResponse> call, Throwable t) {
                i_GetGenderForNameListener.onFailure("none");
            }
        });
    }
}
