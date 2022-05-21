package com.ofir.mycontacts.model.interfaces;

public interface IActionListener<T> {

    void onSuccess(T data);
    void onFailure(String s);

}
