package com.example.android.jdbooklisting;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button search;
    private EditText bookName;
    private String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainAsync mainAsync = new MainAsync();
                mainAsync.execute();
            }
        });
    }


    class MainAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            bookName = (EditText)findViewById(R.id.bookName);
            query = bookName.getText().toString();
            Intent intent = new Intent(MainActivity.this, BookList.class);
            intent.putExtra("query",query);
            startActivity(intent);
            return null;
        }
    }
}



