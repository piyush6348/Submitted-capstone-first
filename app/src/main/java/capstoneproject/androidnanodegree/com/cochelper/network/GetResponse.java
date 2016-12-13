package capstoneproject.androidnanodegree.com.cochelper.network;

import java.io.IOException;

import capstoneproject.androidnanodegree.com.cochelper.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class GetResponse {
    OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url).header("Authorization","Bearer " + Constants.AUTH_TOKEN)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
