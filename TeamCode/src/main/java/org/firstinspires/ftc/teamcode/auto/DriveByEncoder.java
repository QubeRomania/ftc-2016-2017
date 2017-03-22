package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.LinearRobotOpMode;
import org.firstinspires.ftc.teamcode.RobotOpMode;

@Autonomous(name="Drive by Encoder Test", group="Tests")
public final class DriveByEncoder extends AutonomousOpMode {

    // AndyMark motors.
    private static final int ROTATIONS_PER_MOTOR_REV = 1220;

    @Override
    public void play() {
        initialize();

        waitForStart();

        driveForRotations(1.8);
    }

    private void driveForRotations(double rotations) {
        robot.setDriveMotorTargetRotations(rotations);

        robot.setDriveMotorsRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.tractiuneIntegrala(1, 1);


        while(robot.leftFrontMotor.getCurrentPosition() < robot.leftFrontMotor.getTargetPosition() && opModeIsActive()) {
            setStatus("Moving to position.");
            telemetry.addData("Left target", "%d", robot.leftFrontMotor.getTargetPosition());
            telemetry.addData("Left position", "%d", robot.leftFrontMotor.getCurrentPosition());
            update();
        }

        robot.tractiuneIntegrala(0, 0);

        setStatus("Position reached.");
        update();

        robot.setDriveMotorsRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.setDriveMotorsRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void driveBySpeed(double maxSpeed) {
        robot.setDriveMotorMaxSpeed(0);

        robot.tractiuneIntegrala(1, 1);

        double pow = 0;

        while (pow <= maxSpeed && opModeIsActive())
        {
            robot.setDriveMotorMaxSpeed(pow);

            pow += maxSpeed / 100.0;

            telemetry.addData("Max speed", "%.2f", robot.rightFrontMotor.getMaxSpeed() / 1220.0);
            update();

            waitForMs(10);
        }

        robot.tractiuneIntegrala(0, 0);
        setStatus("Target speed reached.");
        update();
    }
}
