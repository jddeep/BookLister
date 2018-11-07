package com.example.android.jdbooklisting;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;


class BookListLoader extends AsyncTaskLoader {

    private String mUrl;
    private List<Book> bookList;

    public BookListLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        try {
            bookList = QueryUtils.fetchBookData(mUrl);

        } catch (IOException e) {
            Log.e("LoaderActivity", "Error in loadinBackground");
        }
        return bookList;
    }
}
