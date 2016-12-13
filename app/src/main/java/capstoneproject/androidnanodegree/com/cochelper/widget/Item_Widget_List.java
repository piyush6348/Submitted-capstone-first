package capstoneproject.androidnanodegree.com.cochelper.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import capstoneproject.androidnanodegree.com.cochelper.R;
import capstoneproject.androidnanodegree.com.cochelper.database.DatabseColumns;
import capstoneproject.androidnanodegree.com.cochelper.database.QuoteProvider;


public class Item_Widget_List extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new Other_Widget_List(this.getApplicationContext(), intent);
    }
}

class Other_Widget_List implements RemoteViewsService.RemoteViewsFactory {
    Cursor c;
    private Context context = null;
    private int appWidgetId;
    RemoteViews row;

    public Other_Widget_List(Context context, Intent intent) {

        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Log.e("Item_Widget_Oncreate", "is running");
    }

    @Override
    public void onDataSetChanged() {
        Log.e("Item_Widget_onDataSEt", "is running");
        initData();
    }

    private void initData() {
        if (c != null)
            c.close();

        c = context.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDestroy() {
        if (c != null)
            c.close();
    }

    @Override
    public int getCount() {
        return c.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.e("Item_Widget_getview", "is running");
        String url = "";
        String title = "";
        //String change = "";

        if (c.moveToPosition(position)) {
            url = c.getString(c.getColumnIndex(DatabseColumns.URL));

            title = c.getString(c.getColumnIndex(DatabseColumns.TITLE));

        }
        Log.e("getViewAt: ", "hi " + title);
        row = new RemoteViews(context.getPackageName(), R.layout.list_item_quote);
        //Picasso.with(context).load(url).into(row,R.id.thumbnail,appWidgetId[position]);
        row.setTextViewText(R.id.heading, title);
        //Picasso.with(context).load(url).into(row,R.id.thumbnail,new int[]{appWidgetId});
        /*
        try {
            Bitmap b=Picasso.with(context).load(url).get();
            row.setImageViewBitmap(R.id.thumbnail,b);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

