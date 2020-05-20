package com.example.popularmovies.uianddata;

import android.view.View;

/**
 * SetVisibility is responsible to change any views visibility to INVISIBLE or VISIBLE
 */

public class SetVisibility {

    public SetVisibility() {
    }

    public void setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }
}
