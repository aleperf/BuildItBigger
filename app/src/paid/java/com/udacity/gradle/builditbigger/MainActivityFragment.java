package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.aleperf.jokedisplay.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.IdlingManager;
import com.udacity.gradle.builditbigger.JokeLauncher;



/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private Button jokeButton;
    private MutableLiveData<String> joke = new MutableLiveData<>();
    private String EXTRA_JOKE = "display extra joke";
    private boolean canCount = true;
    private ProgressBar progressBar;


    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        jokeButton = root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                callForAJoke();
            }
        });
        progressBar = root.findViewById(R.id.progress_bar);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribe();

    }

    private void subscribe() {


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String retrievedJoke) {
                if (canCount && !TextUtils.isEmpty(retrievedJoke)) {
                    decrementIdling();
                    canCount = false;
                }

                Intent intent = new Intent(getActivity(), JokeDisplayActivity.class);
                intent.putExtra(EXTRA_JOKE, retrievedJoke);
                startActivity(intent);
                progressBar.setVisibility(View.INVISIBLE);
            }
        };
        joke.observe(this, observer);
    }


    public void callForAJoke() {
        if (canCount) {
            incrementIdling();
        }

        if (getActivity() instanceof JokeLauncher) {
            JokeLauncher jokeLauncher = (JokeLauncher) getActivity();
            jokeLauncher.tellJoke(joke);
        }
    }

    public void decrementIdling() {
        if (getActivity() instanceof IdlingManager) {
            IdlingManager manager = (IdlingManager) getActivity();
            manager.decrementIdlingCounter();
        }
    }

    public void incrementIdling() {
        if (getActivity() instanceof IdlingManager) {
            IdlingManager manager = (IdlingManager) getActivity();
            manager.incrementIdlingCounter();
        }
    }

}
