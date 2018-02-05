package com.bit.robotlib;

/**
 * Created by zhangjiajie on 18/1/30.
 */

public class RobotProtocol {

    /**
     * 保持通讯
     */
    private static final String NORMAL_PROTOCOL = "ffaa0800337f7f";

    /**
     * 向前
     */
    private static final String FORWARD_PROTOCOL = "ffaa0800339f7f";

    /**
     * 后退
     */
    private static final String BACKWARD_PROTOCOL = "ffaa0800335f7f";

    /**
     * 左转
     */
    private static final String TURN_LEFT_PROTOCOL = "ffaa0800337f5f";

    /**
     * 右转
     */
    private static final String TURN_RIGHT_PROTOCOL = "ffaa0800337f9f";

    /**
     * 打开投影
     */
    private static final String OPEN_PROJECTOR_PROTOCOL = "ffaa0800140001";

    /**
     * 关闭投影
     */
    private static final String CLOSE_PROJECTOR_PROTOCOL = "ffaa0800140000";

    /**
     * 自动充电
     */
    private static final String AUTO_CHARGE_POWER_PROTOCOL = "ffaa0800117f7f";

    /**
     * 角度旋转
     */
    private static final String ROTATE_PROTOCOL = "ffaa080012";

    /**
     * 游荡
     */
    private static final String RANDOM_WALK_PROTOCOL = "ffaa0800387f7f";

    /**
     * Power saving mode
     */
    private static final String POWER_SAVING_MODE_PROTOCOL = "ffaa0800387f7f";

    /**
     * reset error
     */
    private static final String RESET_ERROR_PROTOCOL = "ffaa0800327f7f";

    /**
     *
     */
    private static final String RESET_DEGREE_PROTOCOL = "ffaa0800137f7f";

    private static final String TAG = RobotProtocol.class.getSimpleName();

    private RobotProtocol() {

    }

    protected static byte[] FORWARD_PROTOCOL() {
        String cmd = FORWARD_PROTOCOL + HexUtil.getCheckSUMByte(FORWARD_PROTOCOL);
        return HexUtil.strTobyte(cmd);
    }

    protected static byte[] BACKWARD_PROTOCOL() {
        return HexUtil.strTobyte(BACKWARD_PROTOCOL + HexUtil.getCheckSUMByte(BACKWARD_PROTOCOL));
    }

    protected static byte[] TURN_LEFT_PROTOCOL() {
        return HexUtil.strTobyte(TURN_LEFT_PROTOCOL + HexUtil.getCheckSUMByte(TURN_LEFT_PROTOCOL));
    }

    protected static byte[] TURN_RIGHT_PROTOCOL() {
        return HexUtil.strTobyte(TURN_RIGHT_PROTOCOL + HexUtil.getCheckSUMByte(TURN_RIGHT_PROTOCOL));
    }

    protected static byte[] OPEN_PROJECTOR_PROTOCOL() {
        return HexUtil.strTobyte(OPEN_PROJECTOR_PROTOCOL + HexUtil.getCheckSUMByte(OPEN_PROJECTOR_PROTOCOL));
    }

    protected static byte[] CLOSE_PROJECTOR_PROTOCOL() {
        return HexUtil.strTobyte(CLOSE_PROJECTOR_PROTOCOL + HexUtil.getCheckSUMByte(CLOSE_PROJECTOR_PROTOCOL));
    }

    protected static byte[] GOTO_CHARGE_POWER_PROTOCOL() {
        return HexUtil.strTobyte(AUTO_CHARGE_POWER_PROTOCOL + HexUtil.getCheckSUMByte(AUTO_CHARGE_POWER_PROTOCOL));
    }

    protected static byte[] NORMAL_PROTOCOL() {
        return HexUtil.strTobyte(NORMAL_PROTOCOL + HexUtil.getCheckSUMByte(NORMAL_PROTOCOL));
    }

    protected static byte[] RANDOM_WALK_PROTOCOL() {
        return HexUtil.strTobyte(RANDOM_WALK_PROTOCOL + HexUtil.getCheckSUMByte(RANDOM_WALK_PROTOCOL));
    }

    protected static byte[] POWER_SAVING_MODE_PROTOCOL() {
        return HexUtil.strTobyte(POWER_SAVING_MODE_PROTOCOL + HexUtil.getCheckSUMByte(POWER_SAVING_MODE_PROTOCOL));
    }

    protected static byte[] RESET_ERROR_PROTOCOL() {
        return HexUtil.strTobyte(RESET_ERROR_PROTOCOL + HexUtil.getCheckSUMByte(RESET_ERROR_PROTOCOL));
    }

    protected static byte[] RESET_DEGREE_PROTOCOL() {
        return HexUtil.strTobyte(RESET_DEGREE_PROTOCOL + HexUtil.getCheckSUMByte(RESET_DEGREE_PROTOCOL));
    }

    //d[0,360]
    protected static byte[] ROTATE_PROTOCOL(int d) {
        String sd;
        if (d > 255) {
            sd = "0" + Integer.toHexString(d);
        } else {
            sd = "00" + Integer.toHexString(d);
        }
        String cmd = ROTATE_PROTOCOL + sd;
        return HexUtil.strTobyte(cmd + HexUtil.getCheckSUMByte(cmd));
    }
}
