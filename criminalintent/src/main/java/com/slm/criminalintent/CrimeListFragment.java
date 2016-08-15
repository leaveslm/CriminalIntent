package com.slm.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Leaves on 2016/7/12.
 */
public class CrimeListFragment extends ListFragment {

    private static final  String TAG = "CrimeListFragment";
    private static final int REQUEST_CRIME = 1;

    private ArrayList<Crime> crimes;
    private boolean subtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指出fragment愿意添加item到选项菜单，接受onCreateOptionsMenu(...)方法的调用和指令
        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.crime_title);
        crimes = CrimeLab.get(getActivity()).getCrimes();

//        ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, crimes);
        CrimeAdapter adapter = new CrimeAdapter(crimes);
        setListAdapter(adapter);

        setRetainInstance(true);
        subtitleVisible = false;
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (subtitleVisible) {
                android.support.v7.app.ActionBar actionBar = ((android.support.v7.app.AppCompatActivity) getActivity()).getSupportActionBar();
                actionBar.setSubtitle(R.string.subtitle);
            }
        }
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        Crime crime = (Crime) getListAdapter().getItem(position);
        Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
//        Log.d(TAG, crime.getTitle() + " was clicked");

//        // 启动 CrimeActivity
//        Intent intent = new Intent(getActivity(), CrimeActivity.class);
        // 启动 CrimePagerActivity
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivity(intent);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            // 调用超类的构造方法来绑定Crime对象的数组列表，0：不使用预定义布局
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 检查是否是复用对象，如不是，则从定制布局中创建一个新的视图对象
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            Crime crime = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(crime.getTitle());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(crime.getDate().toString());
            CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(crime.isSolved());

            return convertView;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CRIME) {
            // Handler result
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (subtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_criem:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
                intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(intent, 0);
                return true;

            case R.id.menu_item_show_subtitle:
                android.support.v7.app.ActionBar actionBar = ((android.support.v7.app.AppCompatActivity) getActivity()).getSupportActionBar();
                if (actionBar.getSubtitle() == null) {
                    actionBar.setSubtitle(R.string.subtitle);
                    subtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    actionBar.setSubtitle(null);
                    subtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }
}
