package com.example.aleperf.jokedisplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {
    private String EXTRA_JOKE = "display extra joke";
    private String joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        Intent callingIntent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        joke = callingIntent.getStringExtra(EXTRA_JOKE);
        TextView jokeTextView = findViewById(R.id.jokeTextView);
        if (!TextUtils.isEmpty(joke)) {
            jokeTextView.setText(joke);
        } else {
            jokeTextView.setText(getString(R.string.default_no_joke));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
