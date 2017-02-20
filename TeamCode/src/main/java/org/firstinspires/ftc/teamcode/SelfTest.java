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
public final class SelfTest extends LinearOpMode {

    Hardware robot = new Hardware();
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Robot is ready.");
        telemetry.update();

        waitForStart();

        robot.leftMotor.setPower(0.1);
        robot.rightMotor.setPower(0.1);

        timer.reset();

        while (opModeIsActive() && timer.seconds() < 0.5) {
            telemetry.addData("Status", "Running...");
            telemetry.addData("Power", "Power: ", robot.leftMotor.getPower());
            telemetry.update();
        }

        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);

        telemetry.addData("Status", "Self test passed.");
        telemetry.update();
    }
}
