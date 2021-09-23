package com.anjilibey.kooryuk.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TlIds {
    @SerializedName("values")
    public List<TlId> TlIds;

    public List<TlId> getTl() {
        return TlIds;
    }

    public void setTl(List<TlId> tls) {
        this.TlIds = TlIds;
    }
    public TlIds(List<TlId> TlIds ){
        this.TlIds = TlIds;
    }
}
