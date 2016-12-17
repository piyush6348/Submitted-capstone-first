package capstoneproject.androidnanodegree.com.cochelper.fragments;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import capstoneproject.androidnanodegree.com.cochelper.R;
import capstoneproject.androidnanodegree.com.cochelper.adapter.VideoListCursorAdapter;
import capstoneproject.androidnanodegree.com.cochelper.database.DatabseColumns;
import capstoneproject.androidnanodegree.com.cochelper.database.QuoteProvider;
import capstoneproject.androidnanodegree.com.cochelper.models.Video;
import capstoneproject.androidnanodegree.com.cochelper.models.VideoList;
import capstoneproject.androidnanodegree.com.cochelper.network.YoutubeAPIResponse;
import capstoneproject.androidnanodegree.com.cochelper.utils.Constants;


public class VideoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView recyclerView;
    VideoFragment cont;
    private VideoListCursorAdapter videoListCursorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        Cursor c = getContext().getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);
        cont = this;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        new ShowList().execute();


        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(), QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (videoListCursorAdapter != null)
            videoListCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        videoListCursorAdapter.swapCursor(null);
    }

    public class ShowList extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... voids) {
            YoutubeAPIResponse obj = new YoutubeAPIResponse();
            try {
                String x = obj.run(Constants.YOUTUBE_QUERY_URL);
                return x;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new GsonBuilder().create();

            VideoList videoList = gson.fromJson(s, VideoList.class);

            ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(videoList.getVidList().size());

            Cursor c = getContext().getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);
            if (videoList.getVidList().size() != 0) {
                getContext().getContentResolver().delete(QuoteProvider.Quotes.CONTENT_URI, null, null);
            }
            for (Video planet : videoList.getVidList()) {
                ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                        QuoteProvider.Quotes.CONTENT_URI);
                builder.withValue(DatabseColumns.VIDEOID, planet.getId().getVideoId());
                builder.withValue(DatabseColumns.DESCRIPTION, planet.getSnippet().getDescription());
                builder.withValue(DatabseColumns.TITLE, planet.getSnippet().getTitle());
                builder.withValue(DatabseColumns.URL, planet.getSnippet().getThumbnails().getDef().getUrl());
                batchOperations.add(builder.build());
            }

            try {
                getActivity().getContentResolver().applyBatch(QuoteProvider.AUTHORITY, batchOperations);
            } catch (RemoteException | OperationApplicationException e) {
            }
            c = getContext().getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);
            videoListCursorAdapter = new VideoListCursorAdapter(getActivity(), c);
            videoListCursorAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(videoListCursorAdapter);
            getLoaderManager().initLoader(0, null, cont);


        }
    }
}
