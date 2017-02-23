package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="testServo", group="Iterative Opmode")
//@Disabled
public class TestServo extends RobotOpMode
{

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        robot.servo1.setPosition(gamepad2.right_trigger);

    }


}
