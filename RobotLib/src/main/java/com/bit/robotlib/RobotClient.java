package com.bit.robotlib;

import android.content.Context;
import android.serialport.SerialPort;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Created by zhangjiajie on 18/1/30.
 */

public class RobotClient implements ReadCommandHandler.OnCommandReceivedListener {

    private static final String TAG = RobotClient.class.getSimpleName();
    private static RobotClient sRobotClient;

    private String mDev = "/dev/ttyS3";
    private int mRate = 115200;

    private Context mContext;

    private SerialPort mSerialPort;
    private ReadCommandHandler mReadCommandHandler;
    private SendCommandHandler mSendCommandHandler;

    private CommunicationHandler mCommunicationHandler;

    private RobotClient(Context context) {
        mContext = context;
    }

    public static void init(Context context) {
        if (sRobotClient == null) {
            sRobotClient = new RobotClient(context);
        }
    }

    public static RobotClient getInstance() {
        if (sRobotClient == null) {
            throw new NullPointerException("RobotClient not init");
        }
        return sRobotClient;
    }

    /**
     *
     */
    public void onCreate() {
        try {
            mSerialPort = new SerialPort(mDev, mRate, 0);

            //下位机通讯线程
            mCommunicationHandler = new CommunicationHandler(mContext, mSerialPort);
            mCommunicationHandler.startHandle();


            //接收下位机数据线程
            mReadCommandHandler = new ReadCommandHandler(mContext, mSerialPort);
            mReadCommandHandler.addOnCommandReceivedListener(this);
            mReadCommandHandler.startHandle();

            //命令发送线程
            mSendCommandHandler = new SendCommandHandler(mContext, mSerialPort);
//            mSendCommandHandler.startHandle();


        } catch (SecurityException e) {
            Log.d(TAG, "You do not have read/write permission to the serial port.");
        } catch (InvalidParameterException e) {
            Log.d(TAG, "You do not have read/write permission to the serial port.");
        } catch (IOException e) {
            Log.d(TAG, "You do not have read/write permission to the serial port.");
        }


    }

    /**
     *
     */
    public void onDestroy() {
        if (mReadCommandHandler != null) {
            mReadCommandHandler.stopHandle();
            mReadCommandHandler.removeOnCommandReceivedListener(this);
            mReadCommandHandler.removeOnCommandReceivedListener(mRoateListener);
        }
        if (mCommunicationHandler != null) {
            mCommunicationHandler.stopHandle();
        }
        if (mSendCommandHandler != null) {
            mSendCommandHandler.stopHandle();
        }
        closeSerialPort();
    }

    private void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    /**
     * 向前
     */
    public void forward() {
        mCommunicationHandler.stopHandle();
        mSendCommandHandler.setCmd(RobotProtocol.FORWARD_PROTOCOL());
        mSendCommandHandler.startHandle();
    }

    /**
     * 向后
     */
    public void backward() {
        mCommunicationHandler.stopHandle();
        mSendCommandHandler.setCmd(RobotProtocol.BACKWARD_PROTOCOL());
        mSendCommandHandler.startHandle();
    }

    /**
     * 左转
     */
    public void turnLeft() {
        mCommunicationHandler.stopHandle();
        mSendCommandHandler.setCmd(RobotProtocol.TURN_LEFT_PROTOCOL());
        mSendCommandHandler.startHandle();
    }

    /**
     * 右转
     */
    public void turnRight() {
        mCommunicationHandler.stopHandle();
        mSendCommandHandler.setCmd(RobotProtocol.TURN_RIGHT_PROTOCOL());
        mSendCommandHandler.startHandle();
    }

    /**
     * 停止运动
     */
    public void stop() {
        mSendCommandHandler.stopHandle();
        mSendCommandHandler.setCmd(null);
        mCommunicationHandler.startHandle();
    }

    /**
     * 投影打开
     */
    public void openProjector() {
        mSendCommandHandler.setCmd(RobotProtocol.OPEN_PROJECTOR_PROTOCOL());
        mSendCommandHandler.startHandleOnce();
    }

    /**
     * 投影关闭
     */
    public void closeProjector() {
        mSendCommandHandler.setCmd(RobotProtocol.CLOSE_PROJECTOR_PROTOCOL());
        mSendCommandHandler.startHandleOnce();
    }

    /**
     * 充电
     */
    public void gotoChargePower() {
        mCommunicationHandler.stopHandle();
        mSendCommandHandler.setCmd(RobotProtocol.GOTO_CHARGE_POWER_PROTOCOL());
        mSendCommandHandler.startHandle();
    }

    /**
     * 旋转
     */
    public void rotate(int degree) {
        mCommunicationHandler.stopHandle();
        mSendCommandHandler.setCmd(RobotProtocol.ROTATE_PROTOCOL(degree));
        mSendCommandHandler.startHandle();
        mReadCommandHandler.addOnCommandReceivedListener(mRoateListener);
    }

    /**
     * 游荡
     */
    public void randomWalk() {
        mCommunicationHandler.stopHandle();
        mSendCommandHandler.setCmd(RobotProtocol.RANDOM_WALK_PROTOCOL());
        mSendCommandHandler.startHandle();
    }

    /**
     * 省电模式
     */
    public void powerSaveMode() {
        mCommunicationHandler.stopHandle();
        mSendCommandHandler.setCmd(RobotProtocol.POWER_SAVING_MODE_PROTOCOL());
        mSendCommandHandler.startHandle();
    }

    /**
     * 监听旋转状态
     */
    ReadCommandHandler.OnCommandReceivedListener mRoateListener = new ReadCommandHandler.OnCommandReceivedListener() {
        @Override
        public void onCmdReceived(byte[] buffer) {
            String receivedData = HexUtil.bytesToStr(buffer);
            String roateStatus = receivedData.substring(24, 26);
            Log.d(TAG, "roateStatus:" + roateStatus);
            if (roateStatus.equalsIgnoreCase("a1")) {
                stop();
            }
        }
    };

    @Override
    public void onCmdReceived(byte[] buffer) {
        String receivedData = HexUtil.bytesToStr(buffer);
        Log.d(TAG, "receivedData:" + receivedData);
    }

}
