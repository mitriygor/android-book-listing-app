package com.booklisting.mitriyg.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import org.json.JSONException;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = BookLoader.class.getName();
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground()   {
        if (mUrl == null) {
            return null;
        }

        List<Book> books = null;
        // Perform the network request, parse the response, and extract a list of books.

        try {
            books = QueryUtils.fetchBookData(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }
}