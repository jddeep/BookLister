package com.example.android.jdbooklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.List;

public class BookListAdapter extends ArrayAdapter<Book> {
    public BookListAdapter(Context context, List<Book> books){
        super(context,0,books);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if(listitemView == null){
            listitemView= LayoutInflater.from(getContext()).inflate(R.layout.book_list,parent,false);

        }
        Book currentbook = getItem(position);
        TextView kind = (TextView)listitemView.findViewById(R.id.kind);
        kind.setText(currentbook.getKind());
        TextView title = (TextView)listitemView.findViewById(R.id.title);
        title.setText(currentbook.getTitle());
        TextView authors = (TextView)listitemView.findViewById(R.id.authors);
        authors.setText(currentbook.getAuthor());
        ImageView thumbnail  =(ImageView)listitemView.findViewById(R.id.thumbnail);

        DownloadImageTask downloadImageTask = new DownloadImageTask(thumbnail);
        downloadImageTask.execute(currentbook.getThumbNail());

        return listitemView;
    }
    public class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

        ImageView thumbnail;

        public DownloadImageTask(ImageView bmImage) {
            thumbnail = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            thumbnail.setImageBitmap(result);


        }
    }
}
