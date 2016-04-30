# ISwitch

##Dome

<img src="https://github.com/Alex-ZHOU/ISwitch/blob/master/ShowImage/ISwitch_v1.1.2.gif?raw=true" height="480">

##Usage

xml

```
<com.alex.alexswitch.ISwitch
   		android:id="@+id/id_apple_is"
        android:layout_width="40dp"
        android:layout_height="20dp"/>

<com.alex.alexswitch.ISwitch
        android:id="@+id/id_apple2_is"
        android:layout_width="150dp"
        android:layout_height="75dp"
        android:layout_below="@id/id_apple_is"
        android:layout_marginTop="@dimen/activity_vertical_margin"
        alexswitch:animationTime="2000"
        alexswitch:buttonColor="#ffff00"
        alexswitch:closeColor="#cddc39"
        alexswitch:isOpen="true"
        alexswitch:openColor="#673ab7" />
```

java
```
iSwitch.setOnISwitchOnClickListener(new ISwitch.ISwitchOnClickListeners() {
	@Override
	public void open() {
		Log.i(TAG, "open: ");
		Toast.makeText(getApplicationContext(), "Open",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void close() {
		Log.i(TAG, "close: ");
		Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
	}});
```

##Download
```
allprojects {
  repositories {
    jcenter()
  }
}

dependencies {
  compile 'com.alex.alexswitch:alexswitch:1.1.2'
}
```




##LICENSE

Copyright 2016 AlexZHOU

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.