package com.booklisting.mitriyg.booklisting;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final String URL = "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView bookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        loadingIndicator = findViewById(R.id.loading_indicator);

        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.hello);

        bookListView.setEmptyView(mEmptyStateTextView);

        final Random generator = new Random();
        final EditText editText = (EditText) findViewById(R.id.edit_message);
        Button searchButton = (Button) findViewById(R.id.search_button);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString().trim();
                if (str != null && !str.isEmpty()) {
                    try {
                        String url = URLEncoder.encode(str, "UTF-8");
                        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        loadingIndicator.setVisibility(View.VISIBLE);
                        mEmptyStateTextView.setText("");
                        if (networkInfo != null && networkInfo.isConnected()) {
                            LoaderManager loaderManager = getLoaderManager();
                            int i = generator.nextInt(Integer.MAX_VALUE) + 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("s", url);
                            loaderManager.initLoader(i, bundle, MainActivity.this);
                        } else {
                            loadingIndicator.setVisibility(View.GONE);
                            mEmptyStateTextView.setText(R.string.no_internet_connection);
                        }
                    } catch (UnsupportedEncodingException e) {
                        mEmptyStateTextView.setText(R.string.error);
                        e.printStackTrace();
                    }
                }
            }
        });

    }



    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, URL + bundle.getString("s"));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_books);
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
