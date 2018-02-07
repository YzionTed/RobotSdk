package com.bit.imrobotlib;

import android.content.Context;
import android.util.Log;

import com.bit.robotlib.RobotClient;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

/**
 * Created by zhangjiajie on 18/2/7.
 */

public class CMDReceiver {

    private static final String TAG = CMDReceiver.class.getSimpleName();

    private Context mContext;

    protected CMDReceiver(Context context) {
        mContext = context;
    }

    public boolean handleCMD(List<IMMessage> messages) {

        boolean isHandle = false;

        for (int i = 0; i < messages.size(); i++) {
            String cmdJson = messages.get(i).getContent();
            Log.d(TAG, "messages:" + messages.get(i).getContent());

            try {
                Command command = new Gson().fromJson(cmdJson, Command.class);

                if ("move".equalsIgnoreCase(command.getCommand())) {
                    moveAction(command);
                    isHandle = true;
                    break;
                } else if ("chargePower".equalsIgnoreCase(command.getCommand())) {
                    chargePowerAction(command);
                    isHandle = true;
                    break;
                } else if ("projector".equalsIgnoreCase(command.getCommand())) {
                    projectorAction(command);
                    isHandle = true;
                    break;
                } else if ("autoWalk".equalsIgnoreCase(command.getCommand())) {
                    autoWalkAction(command);
                    isHandle = true;
                    break;
                } else if ("savePower".equalsIgnoreCase(command.getCommand())) {
                    savePowerAction(command);
                    isHandle = true;
                    break;
                }

            } catch (Exception e) {

            }

        }
        return isHandle;
    }

    private void savePowerAction(Command command) {
        if ("on".equalsIgnoreCase(command.getOperation())) {
            RobotClient.powerSaveMode();
        } else if ("off".equalsIgnoreCase(command.getOperation())) {
            RobotClient.stop();
        }
    }

    private void autoWalkAction(Command command) {
        if ("on".equalsIgnoreCase(command.getOperation())) {
            RobotClient.randomWalk();
        } else if ("off".equalsIgnoreCase(command.getOperation())) {
            RobotClient.stop();
        }
    }

    private void projectorAction(Command command) {
        if ("on".equalsIgnoreCase(command.getOperation())) {
            RobotClient.openProjector();
        } else if ("off".equalsIgnoreCase(command.getOperation())) {
            RobotClient.closeProjector();
        }
    }

    private void chargePowerAction(Command command) {
        if ("on".equalsIgnoreCase(command.getOperation())) {
            RobotClient.gotoChargePower();
        } else if ("off".equalsIgnoreCase(command.getOperation())) {
            RobotClient.stop();
        }
    }

    private void moveAction(Command command) {

        if ("go".equalsIgnoreCase(command.getOperation())) {
            RobotClient.forward();
        } else if ("back".equalsIgnoreCase(command.getOperation())) {
            RobotClient.backward();
        } else if ("left".equalsIgnoreCase(command.getOperation())) {
            RobotClient.turnLeft();
        } else if ("right".equalsIgnoreCase(command.getOperation())) {
            RobotClient.turnRight();
        } else if ("stop".equalsIgnoreCase(command.getOperation())) {
            RobotClient.stop();
        }
    }

}
