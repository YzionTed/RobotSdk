package android.serialport.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bit.robotlib.RobotClient;

/**
 * Created by zhangjiajie on 18/2/1.
 */

public class ControlActivity extends FragmentActivity {

    Button mBtnGo;
    Button btnBack;
    Button btnRight;
    Button btnLeft;
    Button btnQuit;
    Button openProjector;
    Button closeProjector;
    Button chargePowor;
    Button stop;
    Button walk;

    EditText degree;
    Button okDegreeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity);

        RobotClient.onCreate();
        RobotClient.setLog(true);

        mBtnGo = (Button) findViewById(R.id.btnGo);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnRight = (Button) findViewById(R.id.btnRight);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        openProjector = (Button) findViewById(R.id.openProjector);
        closeProjector = (Button) findViewById(R.id.closeProjector);
        chargePowor = (Button) findViewById(R.id.chargePowor);
        stop = (Button) findViewById(R.id.stop);
        walk = (Button) findViewById(R.id.walk);

        degree = (EditText) findViewById(R.id.degree);
        okDegreeBtn = (Button) findViewById(R.id.okDegreeBtn);

        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RobotClient.randomWalk();
            }
        });

        okDegreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int d = Integer.parseInt(degree.getText().toString());
                    RobotClient.rotate(d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        openProjector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RobotClient.openProjector();
            }
        });

        closeProjector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RobotClient.closeProjector();
            }
        });

        chargePowor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RobotClient.gotoChargePower();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RobotClient.stop();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBtnGo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    RobotClient.forward();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    RobotClient.stop();
                }
                return true;
            }
        });

        btnBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    RobotClient.backward();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    RobotClient.stop();
                }
                return true;
            }
        });

        btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    RobotClient.turnLeft();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    RobotClient.stop();
                }
                return true;
            }
        });

        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    RobotClient.turnRight();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    RobotClient.stop();
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RobotClient.onDestroy();
    }
}
