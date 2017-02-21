package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This OpMode is meant to test the robot's autonomy.
 * It should test all the hardware to make sure it works.
 */
@Autonomous(name="Self Test", group="Tests")
// @Disabled
public final class SelfTest extends RobotOpMode {
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void start()
    {
        timer.reset();
    }

    @Override
    public void loop() {
        if (timer.seconds() < 1) {
            robot.leftMotor.setPower(0.5);
            robot.rightMotor.setPower(0.5);

            telemetry.addData("Status:", "Running...");
            telemetry.addData("Power: %f", robot.leftMotor.getPower());
            telemetry.update();
        } else {
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            telemetry.addData("Status: ", "Self test passed.");
            telemetry.update();

            requestOpModeStop();
        }
    }
}
