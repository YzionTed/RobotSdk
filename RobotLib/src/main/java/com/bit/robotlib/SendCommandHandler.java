package com.bit.robotlib;

import android.content.Context;
import android.serialport.SerialPort;

import java.io.IOException;

/**
 * Created by zhangjiajie on 18/2/2.
 */

public class SendCommandHandler extends BaseHandler {

    private static final String TAG = SendCommandHandler.class.getSimpleName();
    private byte[] mCmdProtocol;

    protected SendCommandHandler(Context context, SerialPort serialPort) {
        super(context, serialPort);
        setRateMs(50);
    }

    @Override
    protected void handleWork() {
        try {
            if (mOutputStream != null && mCmdProtocol != null) {
//                LogUtil.d(TAG, "CmdProtocol:" + HexUtil.bytesToStr(mCmdProtocol));
                mOutputStream.write(mCmdProtocol);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setCmd(byte[] cmdProtocol) {
        mCmdProtocol = cmdProtocol;
    }
}
