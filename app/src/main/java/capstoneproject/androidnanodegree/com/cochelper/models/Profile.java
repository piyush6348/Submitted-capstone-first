package capstoneproject.androidnanodegree.com.cochelper.models;

import com.google.gson.annotations.SerializedName;


public class Profile {
    private String name;
    private int warStars;

    @SerializedName("league")
    private Badge badge;

    private int donations;
    private int donationsReceived;

    public String getName() {
        return name;
    }

    public int getWarStars() {
        return warStars;
    }

    public Badge getBadge() {
        return badge;
    }

    public int getDonations() {
        return donations;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }
}
