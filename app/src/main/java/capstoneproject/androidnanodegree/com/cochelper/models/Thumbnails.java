package capstoneproject.androidnanodegree.com.cochelper.models;

import com.google.gson.annotations.SerializedName;



public class Thumbnails {
    @SerializedName("high")
    private Default def;

    public Default getDef() {
        return def;
    }

    public void setDef(Default def) {
        this.def = def;
    }
}
