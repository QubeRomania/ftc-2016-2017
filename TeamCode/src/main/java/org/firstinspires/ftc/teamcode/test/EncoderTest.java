package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.LinearRobotOpMode;

@TeleOp(name = "Encoder Test", group = "Tests")
public class EncoderTest extends LinearRobotOpMode {

    private static final int REVS_PER_ROTATION = 1220;

    private static final int LAUNCH_SPEED = (int)(REVS_PER_ROTATION * 0.85);

    @Override
    public void play() {
        robot.initAllMotors();
        robot.initServos();

        robot.fireRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        robot.fireLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.fireRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        robot.fireLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.fireRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        robot.fireLeftMotor.setMaxSpeed(LAUNCH_SPEED);
        robot.fireRightMotor.setMaxSpeed(LAUNCH_SPEED);

        robot.fireLeftMotor.setPower(0.78);
        robot.fireRightMotor.setPower(0.78);

        while (opModeIsActive()) {

            if (gamepad1.right_trigger > 0.5) {
                robot.servoFire.setFiring(true);
            }
            else {
                robot.servoFire.setFiring(false);
            }

            if (gamepad1.left_trigger > 0.5) {
                robot.grabBallsv2(gamepad1.left_trigger);
            } else {
                robot.grabBallsv2(0);
            }

            telemetry.addData("Speed", "%d revs", robot.fireLeftMotor.getMaxSpeed());
            update();
        }

    }
}
