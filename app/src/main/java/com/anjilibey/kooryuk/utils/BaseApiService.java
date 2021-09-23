package com.anjilibey.kooryuk.utils;

import com.anjilibey.kooryuk.model.RapatIds;
import com.anjilibey.kooryuk.model.TlIds;
import com.anjilibey.kooryuk.viewModel.RapatList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    //login
    @Headers({
            "Accept:application/json"
    })
    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseBody> loginRequest(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/register")
    Call<ResponseBody> registerRequest(
            @Header("Accept") String Accept,
            @Field("name") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("c_password") String c_password,
            @Field("role") String role
    );
    @FormUrlEncoded
    @POST("api/rapat")
    Call<ResponseBody> rapatRequest(
            @Header("Accept") String Accept,
            @Header("Authorization") String Authorization,
            @Field("topik") String topik,
            @Field("tanggal") String tanggal,
            @Field("waktu_mulai") String waktu_mulai,
            @Field("waktu_selesai") String waktu_selesai,
            @Field("tempat") String tempat,
            @Field("poin1") String poin1,
            @Field("poin2") String poin2,
            @Field("poin3") String poin3,
            @Field("poin4") String poin4,
            @Field("poin5") String poin5,
            @Field("lampiran") String lampiran
    );

    @GET("api/rapat")
    Call<RapatList> viewRapatRequest(
            @Header("Accept") String Accept,
            @Header("Authorization") String Authorization,
            @Header("Content-type") String Content
    );

    @GET("api/rapat/{id}")
    Call<RapatIds> viewRapatIdRequest(
            @Header("Accept") String Accept,
            @Header("Authorization") String Authorization,
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("api/tl/{id}")
    Call<ResponseBody> rapatRequest(
            @Header("Accept") String Accept,
            @Header("Authorization") String Authorization,
            @Path("id") String id,
            @Field("tl_kaban") String tl_kaban
    );

    @GET("api/tlShow/{id}")
    Call<TlIds> tlRequestShow(
            @Header("Accept") String Accept,
            @Header("Authorization") String Authorization,
            @Path("id") String id
    );

}
