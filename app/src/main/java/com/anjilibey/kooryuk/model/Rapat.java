package com.anjilibey.kooryuk.model;

import com.google.gson.annotations.SerializedName;

public class Rapat {
    @SerializedName("id")
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopik() {
        return topik;
    }

    public void setTopik(String topik) {
        this.topik = topik;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(String waktu_mulai) {
        waktu_mulai = waktu_mulai;
    }

    public String getWaktu_selesai() {
        return waktu_selesai;
    }

    public void setWaktu_selesai(String waktu_selesai) {
        this.waktu_selesai = waktu_selesai;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getPoin1() {
        return poin1;
    }

    public void setPoin1(String poin1) {
        this.poin1 = poin1;
    }

    public String getPoin2() {
        return poin2;
    }

    public void setPoin2(String poin2) {
        this.poin2 = poin2;
    }

    public String getPoin3() {
        return poin3;
    }

    public void setPoin3(String poin3) {
        this.poin3 = poin3;
    }

    public String getPoin4() {
        return poin4;
    }

    public void setPoin4(String poin4) {
        this.poin4 = poin4;
    }

    public String getPoin5() {
        return poin5;
    }

    public void setPoin5(String poin5) {
        this.poin5 = poin5;
    }

    public String getLampiran() {
        return lampiran;
    }

    public void setLampiran(String lampiran) {
        this.lampiran = lampiran;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @SerializedName("topik")
    public String topik;
    @SerializedName("tanggal")
    public String tanggal;
    @SerializedName("waktu_mulai")
    public String waktu_mulai;
    @SerializedName("waktu_selesai")
    public String waktu_selesai;
    @SerializedName("tempat")
    public String tempat;
    @SerializedName("poin1")
    public String poin1;
    @SerializedName("poin2")
    public String poin2;
    @SerializedName("poin3")
    public String poin3;
    @SerializedName("poin4")
    public String poin4;
    @SerializedName("poin5")
    public String poin5;
    @SerializedName("lampiran")
    public String lampiran;
    @SerializedName("id_pegawai")
    public String id_pegawai;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;


}
