package com.anjilibey.kooryuk.utils;

public class UtilsApi {
    //    public static final String BASE_URL_API = "http://10.33.35.204/PRESENSI/public/";
    public static final String BASE_URL_API = "https://kooryuk.000webhostapp.com/";

//    public static final String BASE_URL_API = "http://192.168.32.30:8000/";
    // Declare Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
