package com.bit.imrobotlib;

/**
 * Created by zhangjiajie on 18/2/7.
 */

public class Command {

    /**
     * 移动
     */
    public static final int OPERATION_MOVE_GO = 1;
    public static final int OPERATION_MOVE_BACK = 2;
    public static final int OPERATION_MOVE_LEFT = 3;
    public static final int OPERATION_MOVE_RIGHT = 4;

    /**
     * 开关
     */
    public static final int OPERATION_ON = 5;
    public static final int OPERATION_OFF = 6;

    /**
     * 停止
     */
    public static final int OPERATION_STOP = 7;

    private String command;
    private String operation;
    private long millisecond;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(long millisecond) {
        this.millisecond = millisecond;
    }
}
