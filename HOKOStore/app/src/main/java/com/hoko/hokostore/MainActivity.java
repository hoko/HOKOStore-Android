package com.hoko.hokostore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hokolinks.exitpoints.ExitPoints;
import com.hokolinks.exitpoints.model.Exit;
import com.hokolinks.exitpoints.model.ExitApp;

/**
 * Created by Pedro Vieira on 22/07/15.
 * Copyright © 2015 HOKO. All rights reserved.
 */
public class MainActivity extends Activity {

    private ProductListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Product[] products = {
                Product.getProductWithId(0),
                Product.getProductWithId(1),
                Product.getProductWithId(2)
        };

        this.listAdapter = new ProductListAdapter(this, products);

        ListView productsListView = (ListView) findViewById(R.id.productsListView);
        productsListView.setAdapter(this.listAdapter);
        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);
                                                        intent.putExtra("id", listAdapter.getItem(position).getId());
                                                        startActivity(intent);
                                                    }
                                                }
        );
        ExitPoints.presentExitPoints(this, "y1DV4Y7", "title", new ExitPoints.ExitPointsListener() {
            @Override
            public void onShow(Exit exit) {

            }

            @Override
            public void onException(Exception exception) {

            }

            @Override
            public void onAppOpened(ExitApp app, boolean googlePlay) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listAdapter.notifyDataSetChanged();
            }
        });

    }
}
