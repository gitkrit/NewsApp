package com.example.kriti.newsreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by sandeep on 1/3/16.
 */
public class ArticleFragment extends Fragment {

    private View view;
    private ProgressDialog progressDialog;
    private String url;
    private boolean error=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Bundle bundle = this.getArguments();
        url = bundle.getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.article, container, false);
            new Article().execute();

        } else {
            // If we are returning from a configuration change:
            // "view" is still attached to the previous view hierarchy
            // so we need to remove it and re-attach it to the current one
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;

    }


    private class Article extends AsyncTask<Void, Void, Void>{

        String title;
        String desc;
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {


            try{
                Document document = Jsoup.connect(url).get();
                title = document.title();
                Elements description = document.select("meta[name=description]");
                desc = description.attr("content");
                Element img = document.select("meta[property=og:image]").first();
                      String srcValue = img.attr("abs:content");
                   // String srcValue = img.attr("src");
                    InputStream input = new URL(srcValue).openStream();
                    bitmap = BitmapFactory.decodeStream(input);

            }catch (Exception e){
                error =true;
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
               if(error==false) {
                   TextView textView = (TextView) view.findViewById(R.id.article_title);
                   textView.setText(title);
                   TextView descView = (TextView) view.findViewById(R.id.article_desc);
                   descView.setText(desc);
                   ImageView image = (ImageView) view.findViewById(R.id.article_image);
                   image.setImageBitmap(bitmap);
               }
            else
               {
                   getView().setVisibility(View.GONE);
                   FragmentManager fm = getActivity().getSupportFragmentManager();
                   fm.popBackStack();
                  // TextView textView = (TextView) view.findViewById(R.id.article_title);
                  // textView.setText("Open with your choice of browser");
               }
            progressDialog.dismiss();

        }
    }

}




