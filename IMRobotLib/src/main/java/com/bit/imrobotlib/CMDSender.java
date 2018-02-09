package com.bit.imrobotlib;

import android.content.Context;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by zhangjiajie on 18/2/7.
 */

public class CMDSender {

    private Context mContext;
    private String mAccid;
    private Gson mGson = new Gson();

    public CMDSender(Context context) {
        mContext = context;
    }

    public void setTargetAccid(String accid) {
        mAccid = accid;
    }

    private Command createMoveCommand() {
        Command command = new Command();
        command.setCommand("move");
        return command;
    }

    public void forward(long ms) {
        Command command = createMoveCommand();
        command.setOperation("go");
        command.setMillisecond(ms);
        sendIMMessage(mGson.toJson(command));
    }

    public void forward() {
        Command command = createMoveCommand();
        command.setOperation("go");
        sendIMMessage(mGson.toJson(command));
    }

    public void backward() {
        Command command = createMoveCommand();
        command.setOperation("back");
        sendIMMessage(mGson.toJson(command));
    }

    public void turnLeft() {
        Command command = createMoveCommand();
        command.setOperation("left");
        sendIMMessage(mGson.toJson(command));
    }

    public void turnRight() {
        Command command = createMoveCommand();
        command.setOperation("right");
        sendIMMessage(mGson.toJson(command));
    }

    public void stop() {
        Command command = new Command();
        command.setCommand("stop");
        sendIMMessage(mGson.toJson(command));
    }

    public void openProjector() {
        Command command = new Command();
        command.setCommand("projector");
        command.setOperation("on");
        sendIMMessage(mGson.toJson(command));
    }

    public void closeProjector() {
        Command command = new Command();
        command.setCommand("projector");
        command.setOperation("off");
        sendIMMessage(mGson.toJson(command));
    }

    public void gotoChargePower() {
        Command command = new Command();
        command.setCommand("projector");
        command.setOperation("off");
        sendIMMessage(mGson.toJson(command));
    }

    public void rotate(int degree) {
    }

    public void randomWalk() {
        Command command = new Command();
        command.setCommand("autoWalk");
        sendIMMessage(mGson.toJson(command));
    }

    public void powerSaveMode() {
        Command command = new Command();
        command.setCommand("savePower");
        sendIMMessage(mGson.toJson(command));
    }

    private void sendIMMessage(String content) {
        // 创建文本消息
        IMMessage message = MessageBuilder.createTextMessage(
                mAccid, // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                content // 文本内容
        );
// 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        NIMClient.getService(MsgService.class).sendMessage(message, false);
    }
}
