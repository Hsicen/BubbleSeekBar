package com.xw.samlpe.bubbleseekbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.main_tab_btn_1).setOnClickListener(this);
        findViewById(R.id.main_tab_btn_2).setOnClickListener(this);
        findViewById(R.id.main_tab_btn_3).setOnClickListener(this);
        findViewById(R.id.main_tab_btn_4).setOnClickListener(this);
        findViewById(R.id.main_tab_btn_5).setOnClickListener(this);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, DemoFragment1.newInstance(), "demo1");
            ft.commit();
            mTag = "demo1";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tab_btn_1:
                new MusicFadeDialog()
                    .show(getSupportFragmentManager());
                break;
            case R.id.main_tab_btn_2:
                switchContent("demo2");
                break;
            case R.id.main_tab_btn_3:
                switchContent("demo3");
                break;
            case R.id.main_tab_btn_4:
                switchContent("demo4");
                break;
            case R.id.main_tab_btn_5:
                switchContent("demo5");
                break;
        }
    }

    public void switchContent(String toTag) {
        if (mTag.equals(toTag))
            return;

        FragmentManager fm = getSupportFragmentManager();
        Fragment from = fm.findFragmentByTag(mTag);
        Fragment to = fm.findFragmentByTag(toTag);

        FragmentTransaction ft = fm.beginTransaction();
        if (to == null) {
            if ("demo1".equals(toTag)) {
                to = DemoFragment1.newInstance();
            } else if ("demo2".equals(toTag)) {
                to = DemoFragment2.newInstance();
            } else if ("demo3".equals(toTag)) {
                to = DemoFragment3.newInstance();
            } else if ("demo4".equals(toTag)) {
                to = DemoFragment4.newInstance();
            } else {
                to = DemoFragment5.newInstance();
            }
        }
        if (!to.isAdded()) {
            ft.hide(from).add(R.id.container, to, toTag);
        } else {
            ft.hide(from).show(to);
        }
        ft.commit();

        mTag = toTag;
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
}
