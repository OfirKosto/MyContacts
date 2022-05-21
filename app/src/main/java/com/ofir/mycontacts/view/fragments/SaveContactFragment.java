package com.ofir.mycontacts.view.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.Contact;
import com.ofir.mycontacts.model.enums.eGenders;
import com.ofir.mycontacts.view.viewmodels.SaveContactViewModel;

public class SaveContactFragment extends Fragment {

    private SaveContactViewModel m_ViewModel;

    private boolean m_isEditMode;
    private int m_ContactToEditPosition;

    private TextView m_TitleTv;
    private EditText m_FirstNameEd;
    private EditText m_LastNameEd;
    private EditText m_PhoneEd;
    private EditText m_EmailEd;
    private Button m_CancelBtn;
    private Button m_SaveBtn;
    //TODO THE SPINNER
    private RelativeLayout m_GenderRl;
    private Spinner m_GenderSpinner;
    ArrayAdapter<CharSequence> m_GenderSpinnerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.save_contact_fragment, container, false);
        m_ViewModel = new ViewModelProvider(this).get(SaveContactViewModel.class);

        initUi(rootView);
        initObservers();
        return rootView;
    }

    private void initUi(View rootView) {
        m_TitleTv = rootView.findViewById(R.id.fragment_save_contact_name_tv);
        m_FirstNameEd = rootView.findViewById(R.id.fragment_save_contact_first_name_et);
        m_LastNameEd = rootView.findViewById(R.id.fragment_save_contact_last_name_et);
        m_PhoneEd = rootView.findViewById(R.id.fragment_save_contact_phone_et);
        m_EmailEd = rootView.findViewById(R.id.fragment_save_contact_email_et);
        m_CancelBtn = rootView.findViewById(R.id.fragment_save_contact_cancel_btn);
        m_SaveBtn = rootView.findViewById(R.id.fragment_save_contact_save_btn);
        m_GenderRl = rootView.findViewById(R.id.fragment_save_contact_gender_relative_layout);
        m_GenderSpinner = rootView.findViewById(R.id.fragment_save_contact_gender_spinner);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            m_isEditMode = true;
            m_TitleTv.setText(R.string.edit_contact);
            m_FirstNameEd.setText(bundle.getString("first_name"));
            m_LastNameEd.setText(bundle.getString("last_name"));
            m_PhoneEd.setText(bundle.getString("phone"));
            m_EmailEd.setText(bundle.getString("email"));
            m_ContactToEditPosition = bundle.getInt("position");

            m_GenderRl.setVisibility(View.VISIBLE);

            m_GenderSpinnerAdapter = ArrayAdapter.createFromResource(ApplicationContext.getContext(),
                    R.array.genders_array, android.R.layout.simple_spinner_item);
            //TODO CHECK SPINNER POSITION
            int spinnerPosition = eGenders.valueOf(bundle.getString("gender")).ordinal();
            m_GenderSpinnerAdapter.setDropDownViewResource(spinnerPosition);
            m_GenderSpinner.setAdapter(m_GenderSpinnerAdapter);
        }
        else
        {
            m_isEditMode = false;
            m_GenderRl.setVisibility(View.GONE);
        }

        m_CancelBtn.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());

        setSaveButtonClick();
    }

    private void setSaveButtonClick() {
        if (m_isEditMode)
        {
            m_SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    m_ViewModel.editContact(m_ContactToEditPosition, new Contact(m_FirstNameEd.getText().toString(),
                            m_LastNameEd.getText().toString(),
                            m_PhoneEd.getText().toString(),
                            m_EmailEd.getText().toString(),
                            eGenders.values()[m_GenderSpinner.getSelectedItemPosition()].m_Gender));
                }
            });
        }
        else
        {
            m_SaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    m_ViewModel.addContact(new Contact(m_FirstNameEd.getText().toString(),
                            m_LastNameEd.getText().toString(),
                            m_PhoneEd.getText().toString(),
                            m_EmailEd.getText().toString()));
                }
            });
        }
    }


    private void initObservers() {

        m_ViewModel.getMessageToUser().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
            }
        });

        m_ViewModel.getFinishedSaveContactFlag().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    m_CancelBtn.callOnClick();
                }
            }
        });
    }



}