package com.ofir.mycontacts.model.interfaces;

import com.ofir.mycontacts.model.User;

public interface IUserLoginListener {

    void onSuccess(User user);
    void onFailure(String s);
}
