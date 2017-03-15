package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotOpMode;

@Autonomous(name="Autonomous Period", group="DriveByEncoder")
@Disabled
public final class DriveByEncoder extends RobotOpMode {

    private static final double ROTATIONS_PER_MOTOR_REV = 0;

    private static final double DRIVE_GEAR_REDUCTION = 1;

    private static final double WHEEL_DIAMETER_CM = 10;

    private static final double WHEEL_CIRCUMFERENCE_CM = WHEEL_DIAMETER_CM * Math.PI;

    private static final double ROTATIONS_PER_CM = (ROTATIONS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
            / WHEEL_CIRCUMFERENCE_CM;

    @Override
    public void start() {
        robot.initDriveMotors();
        robot.initFireMotors();

        robot.setDriveMotorsRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.setDriveMotorsRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void driveByEncoder(double speed, double distance, double timeout) {
        robot.setDriveMotorsRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        int newPos = robot.leftBackMotor.getCurrentPosition() + (int) (ROTATIONS_PER_CM * distance);

        robot.setDriveMotorTargetPosition(newPos);

        robot.tractiuneIntegrala(speed, speed);

        double timeoutBegin = runtime.milliseconds();

        while ((runtime.milliseconds() - timeoutBegin < timeout) && robot.leftBackMotor.isBusy()) {
            sleep(10);
        }

        robot.tractiuneIntegrala(0, 0);

        robot.setDriveMotorsRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        driveByEncoder(1, 2, 5000);

        requestOpModeStop();
    }
}
