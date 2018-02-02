package com.bit.robotlib;

import android.content.Context;
import android.serialport.SerialPort;

import java.io.IOException;

/**
 * 保持和下位机通讯
 * Created by zhangjiajie on 18/2/2.
 */

public class CommunicationHandler extends BaseHandler {

    protected CommunicationHandler(Context context, SerialPort serialPort) {
        super(context, serialPort);
        setRateMs(100);
    }

    @Override
    protected void handleWork() {
        try {
            if (mOutputStream != null) {
                mOutputStream.write(RobotProtocol.NORMAL_PROTOCOL());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
