package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public final class Hardware {
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;

    private HardwareMap hwMap = null;
    private ElapsedTime timer = new ElapsedTime();

    /**
     * This function initializes the robot's hardware.
     * It looks for the various sensors and motors, and initializes them.
     *
     * @param map A map of the robot's hardware.
     */
    public void init(HardwareMap map) {
        hwMap = map;

        leftMotor = hwMap.dcMotor.get("leftMotor");
        rightMotor = hwMap.dcMotor.get("rightMotor");

        // TODO: is it really necessary to set this to 0?
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void waitForTick(long periodInMs) {
        long remaining = periodInMs - (long) timer.milliseconds();

        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        timer.reset();
    }
}
