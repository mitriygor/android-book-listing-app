package com.booklisting.mitriyg.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private static final DataParser dataParser = new DataParser();
    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        String title = currentBook.getTitle();
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(title);

        ArrayList<String> authors = currentBook.getAuthors();
        if(authors != null && !authors.isEmpty()) {
            String authorsString = dataParser.formatStr(authors.toString());
            TextView authorsTextView = (TextView) listItemView.findViewById(R.id.authors);
            authorsTextView.setText(authorsString);
        }

        return listItemView;
    }
}
