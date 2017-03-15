package org.firstinspires.ftc.teamcode.servo;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public final class LauncherServo {
    static final double OPEN_POSITION = 1;
    static final double CLOSED_POSITION = 0;

    Servo servo;
    boolean isFiring = false;

    public LauncherServo(HardwareMap hwMap) {
        servo = hwMap.servo.get("servoFire");
        servo.setDirection(Servo.Direction.FORWARD);

        updateServo();
    }

    public void toggle() {
        isFiring = !isFiring;
        updateServo();
    }

    public void setFiring(boolean firing) {
        isFiring = firing;
        updateServo();
    }

    public void close() {
        servo.close();
    }

    private void updateServo() {
        servo.setPosition(isFiring ? OPEN_POSITION : CLOSED_POSITION);
    }
}
