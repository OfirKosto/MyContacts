package com.ofir.mycontacts.model.enums;

import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.R;

public enum eGenders {
    MALE(ApplicationContext.getContext().getResources().getString(R.string.male)),
    FEMALE(ApplicationContext.getContext().getResources().getString(R.string.female)),
    OTHER(ApplicationContext.getContext().getResources().getString(R.string.other));

    public final String m_Gender;
    private eGenders(String i_Gender)
    {
        m_Gender = i_Gender;
    }
}
