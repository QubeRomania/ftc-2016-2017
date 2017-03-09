package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GamepadButton;
import org.firstinspires.ftc.teamcode.RobotOpMode;


@TeleOp(name="Test Servomotors", group="Tests")
public class ServoTest extends RobotOpMode
{
    @Override
    public void start() {
        robot.initFireServo();
    }

    @Override
    public void loop() {
        if (checkButtonToggle(1, GamepadButton.a)) {
            robot.servoFire.toggle();
        }
    }
}
