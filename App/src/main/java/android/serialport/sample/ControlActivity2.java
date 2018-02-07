package android.serialport.sample;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bit.imrobotlib.IMRobotClient;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.xutils.http.RequestParams;

import java.util.Date;

/**
 * Created by zhangjiajie on 18/2/1.
 */

public class ControlActivity2 extends FragmentActivity {

    private static final String TAG = ControlActivity2.class.getSimpleName();
    Button mBtnGo;
    Button btnBack;
    Button btnRight;
    Button btnLeft;
    Button btnQuit;
    Button createIM_ID;
    Button refreshToken;
    Button loginIM;
    Button stop;
    EditText accid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity2);

        mBtnGo = (Button) findViewById(R.id.btnGo);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnRight = (Button) findViewById(R.id.btnRight);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        loginIM = (Button) findViewById(R.id.loginIM);
        stop = (Button) findViewById(R.id.stop);

        createIM_ID = (Button) findViewById(R.id.createIM_ID);
        refreshToken = (Button) findViewById(R.id.refreshToken);
        accid = (EditText) findViewById(R.id.accid);
        accid.setText("mobile-123456");

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendIMMessage("stop");
                IMRobotClient.stop();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendIMMessage("Go");
                IMRobotClient.forward();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendIMMessage("Back");
                IMRobotClient.backward();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendIMMessage("Right");
                IMRobotClient.turnRight();
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendIMMessage("Left");
                IMRobotClient.turnLeft();
            }
        });

        refreshToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshToken();
            }
        });

        loginIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(AuthService.class).login(new LoginInfo("mobile-123456", imToken))
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
                showToast("onFailure");
            }
        });
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void sendIMMessage(String content) {
        // 创建文本消息
        IMMessage message = MessageBuilder.createTextMessage(
                "robot-123456", // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                content // 文本内容
        );
// 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        NIMClient.getService(MsgService.class).sendMessage(message, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
