/*
 * Copyright 2016 AlexZHOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alex.iswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alex.alexswitch.ISwitch;

public class DemoActivity extends AppCompatActivity {

    private ISwitch iSwitch;
    String TAG = "DemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iSwitch = (ISwitch) findViewById(R.id.id_apple_is);

        iSwitch.setOnISwitchOnClickListener(new ISwitch.ISwitchOnClickListeners() {
            @Override
            public void open() {
                Log.i(TAG, "open: ");
                Toast.makeText(getApplicationContext(), "Open", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void close() {
                Log.i(TAG, "close: ");
                Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
