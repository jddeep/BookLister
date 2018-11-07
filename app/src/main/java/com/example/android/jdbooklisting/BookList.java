package com.example.android.jdbooklisting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class BookList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private EditText bookName;
    private String search;


    String url = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookListAdapter Badapter;
    private List<Book> books;
    private static final int LoaderId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        String newString;
        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("query");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("query");

        }
        url = url + newString + "&maxResults=15";
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
Log.e("BookList"," 'isconnected' is"+isConnected);
        if (isConnected) {
            getSupportLoaderManager().initLoader(LoaderId, null, this);
        } else {
            Toast.makeText(getApplicationContext(), "No Books found", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildUI(List<Book> bookList) {
        ListView listView = (ListView) findViewById(R.id.list);
        Badapter = new BookListAdapter(this, bookList);
        listView.setAdapter(Badapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = Badapter.getItem(position);
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(book.getInfoLink()));
                startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        return new BookListLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
//        Badapter.clear();
        if (data != null && !data.isEmpty()) {
            buildUI(data);
        } else {
            Toast.makeText(getApplicationContext(), "No Books found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        Badapter.clear();
    }


}



