package com.udacity.gradle.builditbigger;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.database.Observable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.aleperf.jokedisplay.JokeDisplayActivity;


public class MainActivity extends AppCompatActivity implements MainActivityFragment.JokeLauncher {
    Button jokeButton;
    MutableLiveData<String> joke = new MutableLiveData<>();
    private String EXTRA_JOKE = "display extra joke";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jokeButton = findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });
        subscribe();
    }

    private void subscribe() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String retrievedJoke) {
                Intent intent = new Intent(MainActivity.this, JokeDisplayActivity.class);
                intent.putExtra(EXTRA_JOKE, retrievedJoke);
                startActivity(intent);
            }
        };
        joke.observe(this, observer);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void tellJoke() {

        new JokeRetrieverAsyncTask().execute(joke);
    }

}
