package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aleperf.jokedisplay.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    Button jokeButton;
    MutableLiveData<String> joke = new MutableLiveData<>();
    private String EXTRA_JOKE = "display extra joke";
    private boolean canCount = true;
    private InterstitialAd mInterstitialAd;
    private String jokeToLoad;
    AdView mAdView;

    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        callForAJoke();
        jokeButton = root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    launchJokeIntent();
                }
            }
        });

        mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                mInterstitialAd.loadAd(adRequest);
                launchJokeIntent();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribe();

    }

    private void subscribe() {
        if (canCount) {
            incrementIdling();
        }

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String retrievedJoke) {
                if (!TextUtils.isEmpty(retrievedJoke)) {
                    if (canCount) {
                        decrementIdling();
                        canCount = false;
                    }
                    jokeToLoad = retrievedJoke;
                }
            }
        };
        joke.observe(this, observer);
    }

    private void launchJokeIntent() {
        Intent intent = new Intent(getActivity(), JokeDisplayActivity.class);
        intent.putExtra(EXTRA_JOKE, jokeToLoad);
        startActivity(intent);
    }


    public void callForAJoke() {

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

    @Override
    public void onResume() {
        super.onResume();
        callForAJoke();
    }

}
