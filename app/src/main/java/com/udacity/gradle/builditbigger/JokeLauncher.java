package com.udacity.gradle.builditbigger;

import android.arch.lifecycle.MutableLiveData;

public interface JokeLauncher {
    void tellJoke(MutableLiveData<String> joke);
}
