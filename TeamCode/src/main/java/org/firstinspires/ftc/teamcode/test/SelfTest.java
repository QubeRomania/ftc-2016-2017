package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotOpMode;

/**
 * This OpMode is meant to test the robot's autonomy.
 * It should test all the hardware to make sure it works.
 */
@Autonomous(name="Robot Self Test", group="Tests")
// @Disabled
public final class SelfTest extends RobotOpMode {
    @Override
    public void start()
    {
        robot.initDriveMotors();
    }

    @Override
    public void loop() {
        if (runtime.seconds() < 1) {
            tractiuneIntegrala (0.5, 0.5);

            telemetry.addData("Status", "Running...");
            telemetry.addData("Power", "%f", robot.leftFrontMotor.getPower());
            telemetry.update();
        } else {
            tractiuneIntegrala (0, 0);

            telemetry.addData("Status: ", "Self test passed.");
            telemetry.update();

            requestOpModeStop();
        }
    }
}
