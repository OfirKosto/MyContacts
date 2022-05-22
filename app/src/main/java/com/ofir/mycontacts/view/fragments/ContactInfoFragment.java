package com.ofir.mycontacts.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ofir.mycontacts.R;

public class ContactInfoFragment extends Fragment {

    private TextView m_NameTv;
    private TextView m_PhoneTv;
    private TextView m_EmailTv;
    private TextView m_GenderTv;
    private Button m_BackBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_info_fragment, container, false);

        initUi(rootView);

        return rootView;
    }

    private void initUi(View rootView) {
        m_NameTv = rootView.findViewById(R.id.fragment_contact_info_contact_name_tv);
        m_PhoneTv = rootView.findViewById(R.id.fragment_contact_info_phone_tv);
        m_EmailTv = rootView.findViewById(R.id.fragment_contact_info_email_tv);
        m_GenderTv = rootView.findViewById(R.id.fragment_contact_info_gender_tv);
        m_BackBtn = rootView.findViewById(R.id.fragment_contact_info_back_btn);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            String temp;
            temp = bundle.getString("name");
            m_NameTv.setText(temp);

            temp = m_PhoneTv.getText().toString() + ": " + bundle.getString("phone");
            m_PhoneTv.setText(temp);

            temp = m_EmailTv.getText().toString() + ": " + bundle.getString("email");
            m_EmailTv.setText(temp);

            temp = m_GenderTv.getText().toString() + ": " + bundle.getString("gender");
            m_GenderTv.setText(temp);
        }

        m_BackBtn.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
    }

}