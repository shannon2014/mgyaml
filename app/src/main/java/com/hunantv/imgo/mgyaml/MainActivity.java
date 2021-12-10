package com.hunantv.imgo.mgyaml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onParseYmlClick(View view) {

        ComponentConfigEntity entity = SchemaConfigManager.readConfig(this, "schema/MGXWebRouterConfig.yml",true);
        Log.d("MainActivity","onParseYmlClick()" + entity.routerConfig.size());
    }
}