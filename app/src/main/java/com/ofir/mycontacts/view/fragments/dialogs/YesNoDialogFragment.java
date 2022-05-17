package com.ofir.mycontacts.view.fragments.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ofir.mycontacts.R;


public class YesNoDialogFragment extends DialogFragment
{
    private static final String DIALOG_TAG = "Yes No Dialog Fragment";
    private String mDialogMessage;
    private IYesNoDialogFragmentListener mCallback;

    public YesNoDialogFragment(String iDialogMessage, IYesNoDialogFragmentListener iCallback)
    {
        mDialogMessage = iDialogMessage;
        mCallback = iCallback;
    }

    public static String getDialogTag(){
        return DIALOG_TAG;
    }

    public interface IYesNoDialogFragmentListener
    {
        public void userResponse(boolean iIsUserAccepted);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_yes_no, container, false);

        TextView displayMsgTv = rootView.findViewById(R.id.dialog_fragment_yes_no_user_question_tv);
        displayMsgTv.setText(mDialogMessage);

        TextView yesBtn = rootView.findViewById(R.id.dialog_fragment_yes_no_yes_tv);
        yesBtn.setOnClickListener(v -> {
            mCallback.userResponse(true);
            getDialog().dismiss();
        });

        TextView noBtn = rootView.findViewById(R.id.dialog_fragment_yes_no_no_tv);
        noBtn.setOnClickListener(v -> {
            mCallback.userResponse(false);
            getDialog().dismiss();
        });

        return rootView;
    }
}
