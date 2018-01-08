package writer;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Writer_Details_Activity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TabHost tabHost=getTabHost();
        tabHost.addTab(tabHost.newTabSpec("main").setIndicator("Tweets").setContent(new Intent(this,Writer_Tweets_Activity.class)));
        tabHost.addTab(tabHost.newTabSpec("main").setIndicator("Profile").setContent(new Intent(this,Writer_Profile_Activity.class)));
        tabHost.setCurrentTab(0);

    }
}
