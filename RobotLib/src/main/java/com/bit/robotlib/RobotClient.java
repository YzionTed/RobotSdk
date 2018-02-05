package com.bit.robotlib;

import android.content.Context;

/**
 * Created by zhangjiajie on 18/2/5.
 */

public class RobotClient {

    public interface OnActionCallback {
        void onActionCallback(int action, int code);
    }

    public static void init(Context context) {
        Robot.init(context);
    }

    public static void setLog(boolean isLog) {
        LogUtil.isDebug = isLog;
    }

    public static void onCreate() {
        Robot.getInstance().onCreate();
    }

    public static void onDestroy() {
        Robot.getInstance().onDestroy();
    }

    public static void forward() {
        Robot.getInstance().forward();
    }

    public static void backward() {
        Robot.getInstance().backward();
    }

    public static void turnLeft() {
        Robot.getInstance().turnLeft();
    }

    public static void turnRight() {
        Robot.getInstance().turnRight();
    }

    public static void stop() {
        Robot.getInstance().stop();
    }

    public static void openProjector() {
        Robot.getInstance().openProjector();
    }

    public static void closeProjector() {
        Robot.getInstance().closeProjector();
    }

    public static void gotoChargePower() {
        Robot.getInstance().gotoChargePower();
    }

    public static void rotate(int degree) {
        Robot.getInstance().rotate(degree);
    }

    public static void randomWalk() {
        Robot.getInstance().randomWalk();
    }

    public static void powerSaveMode() {
        Robot.getInstance().powerSaveMode();
    }
}
