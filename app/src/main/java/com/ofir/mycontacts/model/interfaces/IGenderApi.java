package com.ofir.mycontacts.model.interfaces;

import com.ofir.mycontacts.model.JsonGetGenderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGenderApi {
    @GET()
    Call<JsonGetGenderResponse> getGenderByName(@Query("name") String i_Name);
}
