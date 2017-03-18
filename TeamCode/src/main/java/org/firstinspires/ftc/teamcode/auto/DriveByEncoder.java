package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotOpMode;

@Autonomous(name="Encoder Drive Test", group="Tests")
public final class DriveByEncoder extends RobotOpMode {

    private static final double ROTATIONS_PER_MOTOR_REV = 0;

    private static final double DRIVE_GEAR_REDUCTION = 1;

    private static final double WHEEL_DIAMETER_CM = 10;

    private static final double WHEEL_CIRCUMFERENCE_CM = WHEEL_DIAMETER_CM * Math.PI;

    private static final double ROTATIONS_PER_CM = (ROTATIONS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
            / WHEEL_CIRCUMFERENCE_CM;
    private double distance = 50;

    @Override
    public void start() {
        robot.initDriveMotors();
        robot.initFireMotors();
        robot.leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBackMotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        robot.rightBackMotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    private void driveByEncoder(double distance) {

        robot.leftBackMotor.setTargetPosition((int)distance);
        robot.rightBackMotor.setTargetPosition((int)distance);

        robot.leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(robot.leftBackMotor.isBusy() && robot.rightBackMotor.isBusy()){
            robot.tractiuneIntegrala(0.7, 0.7);
            telemetry.addData("Status", "ne deplasam");
            telemetry.addData("Pozitie: ", "%d", robot.leftBackMotor.getCurrentPosition());
            telemetry.update();
        }

        robot.tractiuneIntegrala(0, 0);

        telemetry.addData("Status", "Am ajuns");
        telemetry.update();
    }

    @Override
    public void loop() {
        driveByEncoder(distance);

        requestOpModeStop();
    }
}
