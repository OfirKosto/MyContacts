package com.ofir.mycontacts.model.util;

import com.ofir.mycontacts.model.JsonGetGenderResponse;
import com.ofir.mycontacts.model.interfaces.IGenderApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class that use retrofit for get requests from api.
 */

public class GenderApiUtil {

    private static GenderApiUtil m_Instance = null;

    private final String BASE_URL = "https://api.genderize.io/";
    private IGenderApi m_CallService;

    private GenderApiUtil()
    {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        m_CallService = mRetrofit.create(IGenderApi.class);
    }

    public static GenderApiUtil getInstance()
    {
        if(m_Instance == null)
        {
            synchronized (GenderApiUtil.class)
            {
                if(m_Instance == null)
                {
                    m_Instance = new GenderApiUtil();
                }
            }
        }

        return m_Instance;
    }

    public void getGenderByName( String i_Name, Callback<JsonGetGenderResponse> iCallback)
    {
        Call<JsonGetGenderResponse> articlesCall = m_CallService.getGenderByName(i_Name);
        articlesCall.enqueue(iCallback);
    }

}
