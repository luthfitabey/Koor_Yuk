package com.anjilibey.kooryuk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefManager {
    public static final String SP_KOORYUK = "spKoorYuk";
    public static final String SP_TOKEN = "spToken";
    public static final String SP_ROLE= "spRole";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_KOORYUK, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }
    public void deleteSPString(String keySP){
        spEditor.remove(keySP).commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpToken(){
        return sp.getString(SP_TOKEN, "");
    }
    public String getSpRole(){
        return sp.getString(SP_ROLE, "");
    }


    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
