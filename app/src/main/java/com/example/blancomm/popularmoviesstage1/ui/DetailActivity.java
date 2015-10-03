package com.example.blancomm.popularmoviesstage1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.blancomm.popularmoviesstage1.R;
import com.example.blancomm.popularmoviesstage1.utils.Constant;

public class DetailActivity extends AppCompatActivity {

    private DetailActivityFragment fragmentDetail;
    private String mIdMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get extras from latest when the user click on a element of list.
        Intent intent = getIntent();
        mIdMovie = intent.getStringExtra(Intent.EXTRA_TEXT);

        //Pass the extras get the gridlist with the id of the movie to the detail fragment.
        Bundle args = new Bundle();
        args.putString(Constant.TAG_ID_MOVIE, mIdMovie);
        fragmentDetail = DetailActivityFragment.newInstance(mIdMovie);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragmentDetail,
                Constant.TAG_DETAIL_FRAGMENT).addToBackStack(Constant.TAG_DETAIL_FRAGMENT).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
