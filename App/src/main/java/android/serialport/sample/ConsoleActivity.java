/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package android.serialport.sample;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bit.robotlib.HexUtil;

import java.io.IOException;

public class ConsoleActivity extends SerialPortActivity {

    private static final String TAG = ConsoleActivity.class.getSimpleName();
    EditText mReception;
    Button quit;
    Button forword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.console);

//        Robot.getInstance().onCreate(mSerialPort);
        //		setTitle("Loopback test");
        mReception = (EditText) findViewById(R.id.EditTextReception);
        quit = (Button) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        forword = (Button) findViewById(R.id.forword);
        forword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Robot.getInstance().forward();
//                try {
//                    mOutputStream.write(RobotProtocol.FORWARD_PROTOCOL());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e(TAG, e.getMessage());
//                }
            }
        });

        EditText Emission = (EditText) findViewById(R.id.EditTextEmission);
        Emission.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                int i;
                CharSequence t = v.getText();
                char[] text = new char[t.length()];
                for (i = 0; i < t.length(); i++) {
                    text[i] = t.charAt(i);
                }
                try {
                    mOutputStream.write(new String(text).getBytes());
                    mOutputStream.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Robot.getInstance().onDestroy();
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (mReception != null) {
                    mReception.append(HexUtil.bytesToStr(buffer).substring(0, size));
                }
            }
        });
    }
}
