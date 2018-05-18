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
                callForAJoke();
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
                AdRequest adRequest = new AdRequest.Builder()
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


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String retrievedJoke) {
                if (canCount && !TextUtils.isEmpty(retrievedJoke)) {
                    decrementIdling();
                    canCount = false;
                }
                jokeToLoad = retrievedJoke;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    launchJokeIntent();
                }

            }
        };
        joke.observe(this, observer);
    }

    private void launchJokeIntent(){
        Intent intent = new Intent(getActivity(), JokeDisplayActivity.class);
        intent.putExtra(EXTRA_JOKE, jokeToLoad);
        startActivity(intent);
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
