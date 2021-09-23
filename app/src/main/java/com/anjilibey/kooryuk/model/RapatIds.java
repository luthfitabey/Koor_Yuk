package com.anjilibey.kooryuk.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RapatIds {
    @SerializedName("values")
    public List<RapatId> rapatIds;

    public List<RapatId> getRapat() {
        return rapatIds;
    }

    public void setRapat(List<RapatId> rapats) {
        this.rapatIds = rapatIds;
    }
    public RapatIds(List<RapatId> rapatIds ){
        this.rapatIds = rapatIds;
    }
}
