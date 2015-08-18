package com.hoko.hokostore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Pedro Vieira on 22/07/15.
 * Copyright © 2015 HOKO. All rights reserved.
 */
public class MainActivity extends Activity {

    private ProductListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Product[] products = {
                Product.getProductWithId(0),
                Product.getProductWithId(1),
                Product.getProductWithId(2)
        };

        this.mListAdapter = new ProductListAdapter(this, products);

        ListView productsListView = (ListView) findViewById(R.id.productsListView);
        productsListView.setAdapter(this.mListAdapter);
        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);
                                                        intent.putExtra("id", mListAdapter.getItem(position).getId());
                                                        startActivity(intent);
                                                    }
                                                }
        );

    }

    @Override
    protected void onResume() {
        super.onResume();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListAdapter.notifyDataSetChanged();
            }
        });
    }
}
