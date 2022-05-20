package com.ofir.mycontacts.view.viewmodels;

import androidx.lifecycle.ViewModel;

import com.ofir.mycontacts.ApplicationContext;
import com.ofir.mycontacts.R;
import com.ofir.mycontacts.model.interfaces.IUserCreateListener;
import com.ofir.mycontacts.model.repositories.UserRepository;

public class SignupViewModel extends ViewModel {
        //TODO check flip phone details deleted

    public SignupViewModel(){}

    public void signupNewUser(String i_Username, String i_Password,
                              String i_ConfirmPassword, IUserCreateListener i_UserCreateListener)
    {
        boolean detailsNotEmpty = !i_Username.isEmpty() && !i_Password.isEmpty() && i_ConfirmPassword.isEmpty();

        if(detailsNotEmpty)
        {
            if(!i_Username.contains(" ") && !i_Password.contains(" "))
            {
                if(!(i_Username.length() < 6) && !(i_Password.length() < 6))
                {
                    UserRepository.getInstance().signupNewUser(i_Username, i_Password, i_UserCreateListener);
                }
                else
                {
                    i_UserCreateListener.onFailure(ApplicationContext.getContext()
                            .getResources().getString(R.string.username_and_password_should_be_at_least_6_charachters));
                }
            }
            else
            {
                i_UserCreateListener.onFailure(ApplicationContext.getContext()
                        .getResources().getString(R.string.shouldnt_contain_spaces));
            }
        }
        else
        {
            i_UserCreateListener.onFailure(ApplicationContext.getContext()
                    .getResources().getString(R.string.please_fill_all_the_details));
        }
    }

}