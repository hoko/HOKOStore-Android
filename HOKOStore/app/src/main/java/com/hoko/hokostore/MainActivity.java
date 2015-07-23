package com.hoko.hokostore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.getSharedPreferences("MyPreferences", 0).edit().clear().commit();


        Product[] products = {
                Product.getProductWithId(0),
                Product.getProductWithId(1),
                Product.getProductWithId(2)
        };

        final ProductListAdapter adapter = new ProductListAdapter(this, products);

        final ListView productsListView = (ListView) findViewById(R.id.productsListView);
        productsListView.setAdapter(adapter);
        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);
                                                        intent.putExtra("id", adapter.getItem(position).getId());
                                                        startActivity(intent);
                                                    }
                                                }
        );
    }
}
