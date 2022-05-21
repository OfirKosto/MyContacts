package com.ofir.mycontacts.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.Contact;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private ArrayList<Contact> m_ContactsList;

    private NameTextViewListener m_NameTextViewListener;
    private EditImageButtonListener m_EditImageButtonListener;
    private DeleteImageButtonListener m_DeleteImageButtonListener;

    public ContactAdapter(ArrayList<Contact> i_ContactsList, NameTextViewListener i_NameTextViewListener,
                          EditImageButtonListener i_EditImageButtonListener, DeleteImageButtonListener i_DeleteImageButtonListener)
    {
        m_ContactsList = i_ContactsList;
        m_NameTextViewListener = i_NameTextViewListener;
        m_EditImageButtonListener = i_EditImageButtonListener;
        m_DeleteImageButtonListener = i_DeleteImageButtonListener;
    }

    public void setArticles(ArrayList<Contact> i_ContactsList) {
        m_ContactsList = i_ContactsList;
    }

    public interface NameTextViewListener{
        void onTextViewClicked(Contact contact);
    }

    public interface EditImageButtonListener{
        void onButtonClicked(Contact contact, int position);
    }

    public interface DeleteImageButtonListener{
        void onButtonClicked(Contact contact, int position);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Contact contact = m_ContactsList.get(position);

        String contactName = contact.getFirstName() + " " + contact.getLastName();
        holder.contactNameTextView.setText(contactName);

        holder.contactNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_NameTextViewListener.onTextViewClicked(contact);
            }
        });

        holder.editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_EditImageButtonListener.onButtonClicked(contact, position);
            }
        });

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_DeleteImageButtonListener.onButtonClicked(contact, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_ContactsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView contactNameTextView;
        private ImageButton editImageButton;
        private ImageButton deleteImageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactNameTextView = itemView.findViewById(R.id.contact_card_name_tv);
            editImageButton = itemView.findViewById(R.id.contact_card_edit_btn);
            deleteImageButton = itemView.findViewById(R.id.contact_card_delete_btn);
        }
    }
}
