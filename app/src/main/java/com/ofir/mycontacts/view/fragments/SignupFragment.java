package com.ofir.mycontacts.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ofir.mycontacts.R;
import com.ofir.mycontacts.view.viewmodels.SignupViewModel;

public class SignupFragment extends Fragment {

    private SignupViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }

}