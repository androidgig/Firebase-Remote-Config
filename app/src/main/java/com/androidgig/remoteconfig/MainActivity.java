package com.androidgig.remoteconfig;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeMain = (RelativeLayout) findViewById(R.id.relativeMain);

        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setDefaults(R.xml.default_config);

        String bg_color = firebaseRemoteConfig.getString("background_color");
        relativeMain.setBackgroundColor(Color.parseColor(bg_color));

        firebaseRemoteConfig.fetch().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Once the config is successfully fetched it must be activated before newly fetched
                    // values are returned.
                    firebaseRemoteConfig.activateFetched();
                    String bg_color = firebaseRemoteConfig.getString("background_color");
                    relativeMain.setBackgroundColor(Color.parseColor(bg_color));
                } else {
                    Log.d("TAG", "Fetch failed");
                }
            }
        });
    }
}
