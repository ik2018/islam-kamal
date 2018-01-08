package writer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islamkamal.as7allklam.R;

import java.util.ArrayList;

public class Writer_Tweets_Activity extends AppCompatActivity {
ListView listView;
    ArrayList<WriterAdapter>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer__tweets_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        list=new ArrayList<>();
        list.add(0, new WriterAdapter("Discover the perfect tool for software development with Microsoft Visual Studio", "islam kamal"));
        list.add(1,new WriterAdapter("Explore all of the Visual Studio software available, and shop with confidence. At Microsoft, you'll get free shipping and free returns on all of our products every day. ","ahmed anwar"));
        list.add(2, new WriterAdapter("powerful tool for teams collaborating on the development of apps for PCs, mobile, and the cloud. It doesn't matter whether your team members are working on different technologies or languages – Visual Studio Enterprise 2015 is a cohesive solution for defining, ", "khaled salah"));

        listView=(ListView)findViewById(R.id.tweetslistView3);
        WriterTweetsAdapter adapter=new WriterTweetsAdapter(list);
        listView.setAdapter(adapter);


    }
    private class WriterTweetsAdapter extends BaseAdapter {

        Button like;
        Button post;
        Button share;

        ArrayList<WriterAdapter>tweets_list;
        public WriterTweetsAdapter(ArrayList<WriterAdapter> list){
            tweets_list=new ArrayList<>();
            tweets_list=list;
        }
        @Override
        public int getCount() {
            return tweets_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View tweetView=inflater.inflate(R.layout.writer_tweet_item, null);
            TextView tweetText=(TextView)tweetView.findViewById(R.id.tweettextView12);
            tweetText.setText(tweets_list.get(position).getTweet());
            like=(Button)tweetView.findViewById(R.id.likebutton);
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //write code for like this tweet
                    Toast.makeText(getBaseContext(), "like button", Toast.LENGTH_SHORT).show();
                }
            });
            post=(Button)tweetView.findViewById(R.id.postbutton2);
            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //write code for post tis tweet in home page
                    Toast.makeText(getBaseContext(),"post button",Toast.LENGTH_SHORT).show();
                }
            });
            share=(Button)tweetView.findViewById(R.id.sharebutton3);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //write code for sharing this tweet in facebook or tweet
                    Toast.makeText(getBaseContext(),"share button  "+tweets_list.get(position).getWriter_name(),Toast.LENGTH_SHORT).show();
                }
            });

            return tweetView;
        }


    }

}
