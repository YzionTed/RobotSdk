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

    private IMRobotClient(Context context) {
        mContext = context;
        mCMDSender = new CMDSender(context);
        mCMDReceiver = new CMDReceiver(context);
    }

    /**
     * @param context
     */
    public static void init(Context context) {
        if (sImRobotClient == null) {
            sImRobotClient = new IMRobotClient(context);
        }
    }

    public static void setTargetAccid(String accid) {
        sImRobotClient.mCMDSender.setTargetAccid(accid);
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
