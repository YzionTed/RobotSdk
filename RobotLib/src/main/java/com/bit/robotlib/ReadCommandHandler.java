package com.bit.robotlib;

import android.content.Context;
import android.serialport.SerialPort;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 接收下位机发送的数据
 * Created by zhangjiajie on 18/2/2.
 */

public class ReadCommandHandler extends BaseHandler {

    private LinkedList<OnCommandReceivedListener> mListenerLinkedList = new LinkedList<>();

    public interface OnCommandReceivedListener {
        void onCmdReceived(byte[] buffer);
    }

    public ReadCommandHandler(Context context, SerialPort serialPort) {
        super(context, serialPort);
        setRateMs(0);
    }

    @Override
    protected void handleWork() {
        int size;
        try {
            byte[] buffer = new byte[16];
            if (mInputStream == null) return;
            size = mInputStream.read(buffer);
            if (size > 0) {
                notifyListener(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeOnCommandReceivedListener(OnCommandReceivedListener listener) {
        mListenerLinkedList.remove(listener);
    }

    public void addOnCommandReceivedListener(OnCommandReceivedListener listener) {
        mListenerLinkedList.add(listener);
    }

    private void clearLisntener() {
        mListenerLinkedList.clear();
    }

    private void notifyListener(byte[] buffer) {
        Iterator<OnCommandReceivedListener> listenerIterator = mListenerLinkedList.iterator();
        while (listenerIterator.hasNext()) {
            OnCommandReceivedListener onCommandReceivedListener = listenerIterator.next();
            onCommandReceivedListener.onCmdReceived(buffer);
        }
    }

}
