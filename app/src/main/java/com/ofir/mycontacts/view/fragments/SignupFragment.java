package com.ofir.mycontacts.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.interfaces.IUserCreateListener;
import com.ofir.mycontacts.view.viewmodels.LoginViewModel;
import com.ofir.mycontacts.view.viewmodels.SignupViewModel;

public class SignupFragment extends Fragment {

    private SignupViewModel m_ViewModel;

    private EditText m_UsernameEditText;
    private EditText m_PasswordEditText;
    private EditText m_ConfirmPasswordEditText;
    private Button m_SignupBtn;
    private Button m_BackBtn;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.signup_fragment, container, false);
        m_ViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        initUi(rootView);

        return rootView;
    }

    private void initUi(View rootView) {
        m_UsernameEditText = rootView.findViewById(R.id.fragment_signup_username_et);
        m_PasswordEditText = rootView.findViewById(R.id.fragment_signup_password_et);
        m_ConfirmPasswordEditText = rootView.findViewById(R.id.fragment_signup_confirm_password_et);
        m_SignupBtn = rootView.findViewById(R.id.fragment_signup_signup_btn);
        m_BackBtn = rootView.findViewById(R.id.fragment_signup_back_btn);

        m_SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_ViewModel.signupNewUser(m_UsernameEditText.getText().toString()
                        , m_PasswordEditText.getText().toString(), m_ConfirmPasswordEditText.getText().toString(),
                        new IUserCreateListener() {
                            @Override
                            public void onSuccess(String s) {
                                showMessageToUser(s);
                                m_BackBtn.callOnClick();
                            }

                            @Override
                            public void onFailure(String s) {
                                showMessageToUser(s);
                            }
                        });
            }
        });

        m_BackBtn.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
    }

    public void showMessageToUser(String message)
    {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

}