package com.skyzone.androidservice.Net;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.skyzone.androidservice.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImageLoadActivity extends AppCompatActivity {

    ListView mListView;
    LoadImageAdapter mAdapter;

    List<String> mStrings;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mStrings = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.load_image_list_view);
        mAdapter = new LoadImageAdapter(mStrings, mContext);
        mListView.setAdapter(mAdapter);

        //get urls
        AsycNetutil.get("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1", new AsycNetutil.CallBack() {
            @Override
            public void onResponse(String response) {
                try {
                    mStrings.clear();
                    final JSONObject object = new JSONObject(response);
                    final JSONArray jsonArray = object.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        mStrings.add(jsonArray.getJSONObject(i).getString("url"));
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onImageLoad(Bitmap bitmap) {

            }
        });
    }
}
