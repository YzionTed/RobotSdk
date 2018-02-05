package android.serialport.sample;

import android.content.Intent;
import android.os.Bundle;
import android.serialport.sample.model.Token;
import android.serialport.sample.net.Api;
import android.serialport.sample.net.RequestUtils;
import android.serialport.sample.net.ResponseCallBack;
import android.serialport.sample.net.ServiceException;
import android.serialport.sample.util.CheckSumBuilder;
import android.serialport.sample.util.LogUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bit.robotlib.RobotClient;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import org.xutils.http.RequestParams;

import java.util.Date;

/**
 * Created by zhangjiajie on 18/2/1.
 */

public class ControlActivity extends FragmentActivity {

    private static final String TAG = ControlActivity.class.getSimpleName();
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
    Button createIM_ID;
    Button refreshToken;
    Button loginIM;
    EditText accid;
    private Button mobile_control;

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
        accid = (EditText) findViewById(R.id.accid);
        accid.setText("robot-123456");

        degree = (EditText) findViewById(R.id.degree);
        okDegreeBtn = (Button) findViewById(R.id.okDegreeBtn);
        createIM_ID = (Button) findViewById(R.id.createIM_ID);
        refreshToken = (Button) findViewById(R.id.refreshToken);
        loginIM = (Button) findViewById(R.id.loginIM);
        mobile_control = (Button) findViewById(R.id.mobile_control);

        loginIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(AuthService.class).login(new LoginInfo("robot-123456", imToken))
                        .setCallback(new RequestCallback<LoginInfo>() {
                            @Override
                            public void onSuccess(LoginInfo o) {
                                showToast("login onSuccess");
                            }

                            @Override
                            public void onFailed(int i) {
                                showToast("login onFailed");
                            }

                            @Override
                            public void onException(Throwable throwable) {
                                showToast(throwable.getMessage());
                            }
                        });
            }
        });

        mobile_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ControlActivity.this, ControlActivity2.class));
            }
        });

        createIM_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createYunIMID();
            }
        });

        refreshToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshToken();
            }
        });

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

//        createYunIMID();
//        createYunIMID2();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    String imToken;

    /**
     * {"code":200,"info":{"token":"e6af0d215e9b24226cbb0561d82ff3f9","accid":"mobile-123456","name":""}}
     */
    private void refreshToken() {
        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("AppKey", "c7d64ed61462dfac25c0089ab171eaa4");
        requestParams.addHeader("Nonce", "123456");
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        requestParams.addHeader("CurTime", curTime);
        requestParams.addHeader("CheckSum", CheckSumBuilder.getCheckSum("744182fbc16c", "123456", curTime));
        requestParams.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

//        requestParams.addBodyParameter("accid", "robot-123456");
        String id = accid.getText().toString();
        requestParams.addBodyParameter("accid", id);

        String url = "https://api.netease.im/nimserver" + Api.REFRESH_TOKEN;
        RequestUtils.sendPostRequest(url, requestParams, new ResponseCallBack<Token>() {

            @Override
            public void onSuccess(Token data) {
                super.onSuccess(data);

                imToken = data.getToken();
                showToast("onSuccess");
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                LogUtil.d(TAG, e.getMsg());
                showToast(e.getMsg());
            }
        });
    }

//    private void sendIMMessage() {
//        // 创建文本消息
//        IMMessage message = MessageBuilder.createTextMessage(
//                sessionId, // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
//                sessionType, // 聊天类型，单聊或群组
//                content // 文本内容
//        );
//// 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
//        NIMClient.getService(MsgService.class).sendMessage(message);
//    }

    private void createYunIMID2() {
        String url = "http://39.106.248.12/robot/app/getIMToken";
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("accid", "robot-123456");
        RequestUtils.sendPostRequest(url, requestParams, new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                LogUtil.d(TAG, "data:" + data);

                Toast.makeText(ControlActivity.this, data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                LogUtil.d(TAG, e.getMsg());
            }
        });
    }

    private void createYunIMID() {
        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("AppKey", "c7d64ed61462dfac25c0089ab171eaa4");
        requestParams.addHeader("Nonce", "123456");
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        requestParams.addHeader("CurTime", curTime);
        requestParams.addHeader("CheckSum", CheckSumBuilder.getCheckSum("744182fbc16c", "123456", curTime));
        requestParams.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

//        requestParams.addBodyParameter("accid", "robot-123456");
        requestParams.addBodyParameter("accid", "mobile-123456");

        String url = "https://api.netease.im/nimserver" + Api.CREATE_ID;
        RequestUtils.sendPostRequest(url, requestParams, new ResponseCallBack<Void>() {

            @Override
            public void onSuccess(Void data) {
                super.onSuccess(data);
                Toast.makeText(ControlActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                LogUtil.d(TAG, e.getMsg());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RobotClient.onDestroy();
    }
}
