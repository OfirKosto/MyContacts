package com.ofir.mycontacts.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.User;
import com.ofir.mycontacts.view.viewmodels.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel m_ViewModel;

    private EditText m_UsernameEditText;
    private EditText m_PasswordEditText;
    private Button m_LoginBtn;
    private Button m_SignupBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        m_ViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        initUi(rootView);
        initObservers();

        return rootView;
    }



    private void initUi(View rootView) {
        m_UsernameEditText = rootView.findViewById(R.id.fragment_login_username_et);
        m_PasswordEditText = rootView.findViewById(R.id.fragment_login_password_et);
        m_LoginBtn = rootView.findViewById(R.id.fragment_login_login_btn);
        m_SignupBtn = rootView.findViewById(R.id.fragment_login_signup_btn);

        m_LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_ViewModel.loginUser(m_UsernameEditText.getText().toString(), m_PasswordEditText.getText().toString());
            }
        });

        m_SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
    }

    private void initObservers() {

        m_ViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                //TODO bundle user and navigate to contacts fragment with the bundle(action, bundle)
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_contactsFragment);
            }
        });

        m_ViewModel.getMessageToUser().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(getView(),  s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}