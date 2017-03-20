package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.LinearRobotOpMode;
import org.firstinspires.ftc.teamcode.RobotOpMode;

@Autonomous(name="Encoder Drive Test", group="Tests")
public final class DriveByEncoder extends LinearRobotOpMode {

    // AndyMark motors.
    private static final double ROTATIONS_PER_MOTOR_REV = 1220;

    private static final double DRIVE_GEAR_REDUCTION = 2;

    private static final double WHEEL_DIAMETER_CM = 10;

    private static final double WHEEL_CIRCUMFERENCE_CM = WHEEL_DIAMETER_CM * Math.PI;

    private static final double ROTATIONS_PER_CM = (ROTATIONS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
            / WHEEL_CIRCUMFERENCE_CM;

    @Override
    public void play() {
        robot.initDriveMotors();
        robot.initFireMotors();

        // Reset back motors.

        robot.leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();
        waitForStart();

        driveByEncoder(10);
    }

    private void driveByEncoder(double distanceInCm) {
        int targetPosition = (int)(distanceInCm * ROTATIONS_PER_CM);

        robot.leftBackMotor.setTargetPosition(targetPosition);
        robot.rightBackMotor.setTargetPosition(targetPosition);

        robot.leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.tractiuneIntegrala(0.2, 0.2);

        while(robot.leftBackMotor.isBusy() && robot.rightBackMotor.isBusy()) {
            setStatus("Moving");
            telemetry.addData("Position: ", "%d", robot.leftBackMotor.getCurrentPosition());
            update();
        }

        robot.tractiuneIntegrala(0, 0);

        setStatus("Position reached.");
        update();

        waitForMs(1000);
    }
}
