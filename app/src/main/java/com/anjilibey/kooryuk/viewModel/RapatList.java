package com.anjilibey.kooryuk.viewModel;

import com.anjilibey.kooryuk.model.Rapat;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RapatList {
    @SerializedName("values")
    private ArrayList<Rapat> values;

    public ArrayList<Rapat> getRapatArrayList() {
        return values;
    }

    public void setRapatArrayList(ArrayList<Rapat> rapatArrayList) {
        this.values = rapatArrayList;
    }
}
