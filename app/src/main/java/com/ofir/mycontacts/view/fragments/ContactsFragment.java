package com.ofir.mycontacts.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.Contact;
import com.ofir.mycontacts.view.adapters.ContactAdapter;
import com.ofir.mycontacts.view.fragments.dialogs.YesNoDialogFragment;
import com.ofir.mycontacts.view.viewmodels.ContactsViewModel;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    private ContactsViewModel m_ViewModel;

    private ImageButton m_LogoutBtn;
    private FloatingActionButton m_AddContactBtn;
    private RecyclerView m_ContactsRecyclerView;

    private ArrayList<Contact> m_ContactsArrayList;
    private ContactAdapter m_ContactAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        m_ViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        m_ContactsArrayList = new ArrayList<>();

        initUi(rootView);
        initObservers();

        return rootView;
    }

    private void initUi(View rootView) {
        m_LogoutBtn = rootView.findViewById(R.id.fragment_contacts_logout_ib);
        m_LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YesNoDialogFragment yesNoDialogFragment = new YesNoDialogFragment(getResources()
                        .getString(R.string.yes_no_logout), new YesNoDialogFragment.IYesNoDialogFragmentListener() {
                    @Override
                    public void userResponse(boolean i_IsUserAccepted) {
                        if (i_IsUserAccepted) {
                            logOut();
                        }
                    }
                });
                yesNoDialogFragment.show(getActivity().getSupportFragmentManager(), YesNoDialogFragment.getDialogTag());
            }
        });

        m_AddContactBtn = rootView.findViewById(R.id.fragment_contacts_add_contact_fab);
        m_AddContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ContactsFragment.this)
                        .navigate(R.id.action_contactsFragment_to_saveContactFragment);
            }
        });

        m_ContactsRecyclerView = rootView.findViewById(R.id.fragment_contacts_recyclerview);
        m_ContactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        m_ContactsRecyclerView.setHasFixedSize(true);
        setContactAdapter();
        m_ContactsRecyclerView.setAdapter(m_ContactAdapter);
    }

    private void initObservers() {

        m_ViewModel.getContactsArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Contact>>() {
            @Override
            public void onChanged(ArrayList<Contact> contacts) {
                m_ContactsArrayList.clear();
                m_ContactsArrayList = contacts;
                m_ContactAdapter.setArticles(m_ContactsArrayList);
                m_ContactAdapter.notifyDataSetChanged();
            }
        });

        m_ViewModel.getMessageToUser().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(getView(),  s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setContactAdapter()
    {
        m_ContactAdapter = new ContactAdapter(m_ContactsArrayList, getNameTextViewListener(),
                getEditImageButtonListener(), getDeleteImageButtonListener());
    }

    private ContactAdapter.NameTextViewListener getNameTextViewListener()
    {
        return new ContactAdapter.NameTextViewListener() {
            @Override
            public void onTextViewClicked(Contact contact) {
                Bundle bundle = new Bundle();
                bundle.putString("name", contact.getFirstName() + " " + contact.getLastName());
                bundle.putString("phone", contact.getPhoneNumber());
                bundle.putString("email", contact.getEmail());
                bundle.putString("gender", contact.getGender());

                NavHostFragment.findNavController(ContactsFragment.this)
                        .navigate(R.id.action_contactsFragment_to_contactInfoFragment, bundle);
            }
        };
    }

    private ContactAdapter.EditImageButtonListener getEditImageButtonListener()
    {
        return new ContactAdapter.EditImageButtonListener() {
            @Override
            public void onButtonClicked(Contact contact, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("first_name", contact.getFirstName());
                bundle.putString("last_name", contact.getLastName());
                bundle.putString("phone", contact.getPhoneNumber());
                bundle.putString("email", contact.getEmail());
                bundle.putString("gender", contact.getGender());
                bundle.putInt("position", position);

                NavHostFragment.findNavController(ContactsFragment.this)
                        .navigate(R.id.action_contactsFragment_to_saveContactFragment, bundle);
            }
        };
    }

    private ContactAdapter.DeleteImageButtonListener getDeleteImageButtonListener()
    {
        return new ContactAdapter.DeleteImageButtonListener() {
            @Override
            public void onButtonClicked(Contact contact, int position) {
                YesNoDialogFragment yesNoDialogFragment = new YesNoDialogFragment(getResources()
                        .getString(R.string.yes_no_delete_contact), new YesNoDialogFragment.IYesNoDialogFragmentListener() {
                    @Override
                    public void userResponse(boolean i_IsUserAccepted) {
                        if (i_IsUserAccepted) {
                            m_ViewModel.deleteContact(contact, position);
                        }
                    }
                });
                yesNoDialogFragment.show(getActivity().getSupportFragmentManager(), YesNoDialogFragment.getDialogTag());
            }
        };
    }

    private void refreshRecyclerView()
    {
        m_ViewModel.getUserContacts();
    }

    private void logOut()
    {
        m_ViewModel.logOut();
//        NavHostFragment.findNavController(this).popBackStack();
        NavHostFragment.findNavController(this).navigate(R.id.action_contactsFragment_to_loginFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }
}