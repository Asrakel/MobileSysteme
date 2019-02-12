package com.android.zera.mobilesysteme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void downLoad(View view){
        String url = "http://app2.hs-harz.de/app/filesV2/content/m/FoodContent.xml";
        final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
        downloadTask.execute(url);
        //https://www.hs-harz.de/dokumente/extern/FB_AI/Modulhandbuecher/Modulhandbuch_Informatik_v10.pdf"
    }

    public void mensa(View view){
        String url = "http://app2.hs-harz.de/app/filesV2/content/m/FoodContent.xml";
        final MensaParser mp = new MensaParser(MainActivity.this);
        mp.execute(url);
    }
}
