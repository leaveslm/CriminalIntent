package com.slm.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Leaves on 2016/8/10.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
