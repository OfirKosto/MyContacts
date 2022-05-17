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
import com.ofir.mycontacts.view.viewmodels.ContactInfoViewModel;

public class ContactInfoFragment extends Fragment {

    private ContactInfoViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_info_fragment, container, false);
    }

}