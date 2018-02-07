package com.bit.imrobotlib;

import android.content.Context;

import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

/**
 * Created by zhangjiajie on 18/2/7.
 */

public class IMRobotClient {

    private static IMRobotClient sImRobotClient;

    private CMDSender mCMDSender;
    private CMDReceiver mCMDReceiver;

    private Context mContext;

    private String mRobotAccountId;

    private IMRobotClient(Context context, String robotAccountId) {
        mContext = context;
        mRobotAccountId = robotAccountId;
        mCMDSender = new CMDSender(context, robotAccountId);
        mCMDReceiver = new CMDReceiver(context);
    }

    /**
     * @param context
     * @param robotAccountId 机器人云信ID(机器人的设备码)
     */
    public static void init(Context context, String robotAccountId) {
        if (sImRobotClient == null) {
            sImRobotClient = new IMRobotClient(context, robotAccountId);
        }
    }

    public static boolean handleCommand(List<IMMessage> messages) {
        return sImRobotClient.mCMDReceiver.handleCMD(messages);
    }

    public static void forward() {
        sImRobotClient.mCMDSender.forward();
    }

    public static void backward() {
        sImRobotClient.mCMDSender.backward();
    }

    public static void turnLeft() {
        sImRobotClient.mCMDSender.turnLeft();
    }

    public static void turnRight() {
        sImRobotClient.mCMDSender.turnRight();
    }

    public static void stop() {
        sImRobotClient.mCMDSender.stop();
    }

    public static void openProjector() {
        sImRobotClient.mCMDSender.openProjector();
    }

    public static void closeProjector() {
        sImRobotClient.mCMDSender.closeProjector();
    }

    public static void gotoChargePower() {
        sImRobotClient.mCMDSender.gotoChargePower();
    }

    public static void rotate(int degree) {
        sImRobotClient.mCMDSender.rotate(degree);
    }

    public static void randomWalk() {
        sImRobotClient.mCMDSender.randomWalk();
    }

    public static void powerSaveMode() {
        sImRobotClient.mCMDSender.powerSaveMode();
    }
}
