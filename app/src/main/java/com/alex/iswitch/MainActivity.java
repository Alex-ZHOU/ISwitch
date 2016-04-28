package com.alex.iswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alex.alexswitch.ISwitch;

public class MainActivity extends AppCompatActivity {

    private ISwitch iSwitch;
    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iSwitch = (ISwitch) findViewById(R.id.id_is);

        iSwitch.setOnISwitchOnClickListener(new ISwitch.ISwitchOnClickListeners() {
            @Override
            public void open() {
                Log.i(TAG, "open: ");
                Toast.makeText(getApplicationContext(),"Open",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void close() {
                Log.i(TAG, "close: ");
                Toast.makeText(getApplicationContext(),"Close",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
