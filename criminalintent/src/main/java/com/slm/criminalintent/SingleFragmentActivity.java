package com.slm.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Leaves on 2016/8/10.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            // fm.beginTransaction() 创建并返回FragmentTransaction实例
            // 创建一个新的fragment事务，加入一个添加操作，然后提交该事物
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }
}
