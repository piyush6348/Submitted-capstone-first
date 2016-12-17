package capstoneproject.androidnanodegree.com.cochelper.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import capstoneproject.androidnanodegree.com.cochelper.R;
import capstoneproject.androidnanodegree.com.cochelper.models.Profile;
import capstoneproject.androidnanodegree.com.cochelper.network.GetResponse;
import capstoneproject.androidnanodegree.com.cochelper.utils.Constants;
import capstoneproject.androidnanodegree.com.cochelper.utils.SuperPrefs;


public class FileFragment extends Fragment {
    private String result;
    private EditText profileTag;
    private Button getProfile;
    private ImageView leagueBadge;
    private SuperPrefs sharedPreferences;
    private TextView name, warStarts, troopsDonated, troopsRec, percTroop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.file_fragment, container, false);
        init(view);

        sharedPreferences = new SuperPrefs(getActivity());
        if (sharedPreferences.stringExists("tag"))
            new Asy().execute(sharedPreferences.getString("tag"));
        getProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileTag.getText().toString().compareToIgnoreCase("") != 0)
                    new Asy().execute(profileTag.getText().toString());
                else
                    Toast.makeText(getActivity(), getString(R.string.profile_tag), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    void init(View view) {
        leagueBadge = (ImageView) view.findViewById(R.id.league_badge);
        name = (TextView) view.findViewById(R.id.name);
        warStarts = (TextView) view.findViewById(R.id.war_stars);
        troopsDonated = (TextView) view.findViewById(R.id.troop_donated);
        troopsRec = (TextView) view.findViewById(R.id.troop_recieved);
        percTroop = (TextView) view.findViewById(R.id.troop_perc);
        profileTag = (EditText) view.findViewById(R.id.profile_tag);
        getProfile = (Button) view.findViewById(R.id.get);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class Asy extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... tag) {
            GetResponse res = new GetResponse();

            if (tag[0].charAt(0) == '#')
                tag[0] = tag[0].substring(1);
            Log.e("nimit", tag[0]);
            try {
                return res.run(Constants.BASE_URL_PROFILE + tag[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("ijujnj", s);
            String auth = chechAuth(s);
            if (auth.compareToIgnoreCase("Correct") == 0) {
                Gson gson = new GsonBuilder().create();
                Profile profile = gson.fromJson(s, Profile.class);
                sharedPreferences.setString("tag", profileTag.getText().toString());
                name.setText(profile.getName());
                if (profile.getBadge() != null)
                    Picasso.with(getActivity())
                            .load(profile.getBadge().getIconUrls().getMedium())
                            .into(leagueBadge);
                else
                    leagueBadge.setImageDrawable(getResources().getDrawable(R.drawable.unrankedleague));
                warStarts.setText(getString(R.string.war_star) + "- " + profile.getWarStars());
                troopsDonated.setText(getString(R.string.troop_donated) + " - " + profile.getDonations());
                troopsRec.setText(getString(R.string.troop_recieved) + " - " + profile.getDonationsReceived());
                float don;
                try {
                    don = (profile.getDonations() * 100) / profile.getDonationsReceived();
                } catch (Exception e) {
                    don = 0;
                }
                percTroop.setText("Donation Ratio -" + String.valueOf(don) + "%");
            } else {
                Toast.makeText(getActivity(), auth + "  Try Again! ", Toast.LENGTH_LONG).show();
            }
        }
    }

    String chechAuth(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("reason") && jsonObject.has("message"))
                return jsonObject.getString("message");
            else if (jsonObject.has("reason"))
                return jsonObject.getString("reason");
            else
                return "Correct";
        } catch (JSONException e) {
            e.printStackTrace();
            return "Correct";
        }
    }
}

