package ru.svolf.moddialog;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ModDialog.prepare(this);
    }
}
