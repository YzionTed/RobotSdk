package com.bit.robotlib;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.serialport.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zhangjiajie on 18/2/2.
 */

public abstract class BaseHandler {

    private static final int MESSAGE_LOOP = 1;
    private static final int MESSAGE_ONCE = 2;

    protected Context mContext;
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    protected InputStream mInputStream;
    protected HandlerThread mHandlerThread;
    protected LoopHandler mHandler;

    private long mDelayedMs = 100;

    public BaseHandler(Context context, SerialPort serialPort) {
        mContext = context;
        mSerialPort = serialPort;
        mOutputStream = serialPort.getOutputStream();
        mInputStream = serialPort.getInputStream();
        mHandlerThread = new HandlerThread(this.getClass().getSimpleName());
        mHandlerThread.start();

        mHandler = new LoopHandler(mHandlerThread.getLooper());
    }

    public void setRateMs(long ms) {
        mDelayedMs = ms;
    }

    public void startHandle() {
        mHandler.removeMessages(MESSAGE_LOOP);
        mHandler.sendEmptyMessage(MESSAGE_LOOP);
    }

    public void startHandleOnce() {
        mHandler.removeMessages(MESSAGE_ONCE);
        mHandler.sendEmptyMessage(MESSAGE_ONCE);
    }

    public void stopHandle() {
        mHandler.removeMessages(MESSAGE_ONCE);
        mHandler.removeMessages(MESSAGE_LOOP);
    }

    public class LoopHandler extends Handler {

        public LoopHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            handleWork();
            switch (msg.what) {
                case MESSAGE_LOOP:
                    mHandler.sendEmptyMessageDelayed(MESSAGE_LOOP, mDelayedMs);
                    break;
                case MESSAGE_ONCE:
                    break;
            }
        }
    }

    protected void destroy() {
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        if (mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void handleWork();
}
