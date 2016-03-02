package com.example.kriti.newsreader;

/**
 * Created by sandeep on 1/3/16.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

public class NewsMain extends AppCompatActivity {


    private String[] RSSFeeds = {"http://www.thehindu.com/?service=rss",
                                 "http://economictimes.indiatimes.com/news/rssfeeds/1715249553.cms",
                                 "http://dynamic.feedsportal.com/pf/555218/http://toi.timesofindia.indiatimes.com/rssfeedstopstories.cms",
                                  " http://www.deccanherald.com/rss-internal/top-stories.rss",
                                   "http://www.ft.com/rss/world/asiapacific/india",
            " http://indiatoday.feedsportal.com/c/33614/f/589704/index.rss?http://indiatoday.intoday.in/rss/article.jsp?sid=34",
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);

        GridView gridview = (GridView) findViewById(R.id.grid_news);
        gridview.setAdapter(new ImageAdapter(this));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext() ,NewsActivity.class);
                intent.putExtra("url", RSSFeeds[position]);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
