package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LinearRobotOpMode;

@TeleOp(name = "PidTest", group = "Tests")
//@Disabled


public class PidTest extends LinearRobotOpMode {

    //PID procentual
    static final double P = 30;
    static final double I = 10;
    static final double D = 60;

    static final double scale = 0.05;

    //VARIABILE
    //pid
    public double motorCorrection = 0;
    public double multiplier = 0;

    static final double BASE_SPEED = 0.6;
    static final double LEFT_RATIO = 0.95;
    static final double RIGHT_RATIO = 1;

    private void calibrateGyro() {
        setStatus("Started calibrating gyro.");
        update();

        robot.gyro.calibrate();

        while (robot.gyro.isCalibrating() && opModeIsActive()) {
            setStatus("Calibrating...");
            update();

            idle();
        }

        if (isStarted()) {
            setStatus("Error: gyro NOT CALIBRATED!");

            requestOpModeStop();
            return;
        }

        setStatus("Done calibrating gyro.");
        update();
    }

    public void play(){
        robot.initGyro();
        calibrateGyro();
        robot.initAllMotors();
        waitForStart();

        double direction;
        double angle;
        double error;
        double lastError;

        while (opModeIsActive()){
            if (gamepad1.dpad_up) {
                direction = 0;
                angle = robot.gyro.getIntegratedZValue();
                error = direction - angle;
                lastError = error;

                while (gamepad1.dpad_up && opModeIsActive()) {
                    //PID
                    motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

                    robot.tractiuneIntegrala(BASE_SPEED * LEFT_RATIO - motorCorrection, BASE_SPEED * RIGHT_RATIO + motorCorrection);

                    lastError = error;
                    angle = robot.gyro.getIntegratedZValue();
                    error = direction - angle;
                    telemetry.addData("Angle", "%f", angle);
                    telemetry.addData("Error", "%f", error);
                    update();
                }
            }

            if (gamepad2.dpad_down){
                direction = -180;
                angle = robot.gyro.getIntegratedZValue();
                error = direction - angle;
                lastError = error;

                while (gamepad1.dpad_down && opModeIsActive()) {
                    //PID
                    motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

                    robot.tractiuneIntegrala(BASE_SPEED * LEFT_RATIO - motorCorrection, BASE_SPEED * RIGHT_RATIO + motorCorrection);

                    lastError = error;
                    angle = robot.gyro.getIntegratedZValue();
                    error = direction - angle;
                    telemetry.addData("Angle", "%f", angle);
                    telemetry.addData("Error", "%f", error);
                    update();
                }
            }

            if (gamepad1.dpad_right){
                direction = -90;
                angle = robot.gyro.getIntegratedZValue();
                error = direction - angle;
                lastError = error;

                while (gamepad1.dpad_right && opModeIsActive()) {
                    //PID
                    motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

                    robot.tractiuneIntegrala(BASE_SPEED * LEFT_RATIO - motorCorrection, BASE_SPEED * RIGHT_RATIO + motorCorrection);

                    lastError = error;
                    angle = robot.gyro.getIntegratedZValue();
                    error = direction - angle;
                    telemetry.addData("Angle", "%f", angle);
                    telemetry.addData("Error", "%f", error);
                    update();
                }
            }

            if (gamepad1.dpad_left && opModeIsActive()){
                direction = 90;
                angle = robot.gyro.getIntegratedZValue();
                error = direction - angle;
                lastError = error;

                while (gamepad1.dpad_left && opModeIsActive()) {
                    //PID
                    motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

                    robot.tractiuneIntegrala(BASE_SPEED * LEFT_RATIO - motorCorrection, BASE_SPEED * RIGHT_RATIO + motorCorrection);

                    lastError = error;
                    angle = robot.gyro.getIntegratedZValue();
                    error = direction - angle;
                    telemetry.addData("Angle", "%f", angle);
                    telemetry.addData("Error", "%f", error);
                    update();
                }
            }

            if (gamepad1.y && opModeIsActive()){
                direction = 0;
                angle = robot.gyro.getIntegratedZValue();
                error  = direction - angle;
                lastError = error;

                while (gamepad1.y && opModeIsActive()) {
                    //PID
                    motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

                    robot.tractiuneIntegrala(- motorCorrection, motorCorrection);

                    lastError = error;
                    angle = robot.gyro.getIntegratedZValue();
                    error = direction - angle;
                    telemetry.addData("Angle", "%f", angle);
                    telemetry.addData("Error", "%f", error);
                    update();
                }
            }

            if (gamepad1.a && opModeIsActive()){
                direction = -180;
                angle = robot.gyro.getIntegratedZValue();
                error  = direction - angle;
                lastError = error;

                while (gamepad1.a && opModeIsActive()) {
                    //PID
                    motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

                    robot.tractiuneIntegrala(- motorCorrection, motorCorrection);

                    lastError = error;
                    angle = robot.gyro.getIntegratedZValue();
                    error = direction - angle;
                    telemetry.addData("Angle", "%f", angle);
                    telemetry.addData("Error", "%f", error);
                    update();
                }
            }

            if (gamepad1.b && opModeIsActive()){
                direction = -90;
                angle = robot.gyro.getIntegratedZValue();
                error  = direction - angle;
                lastError = error;

                while (gamepad1.b && opModeIsActive()) {
                    //PID
                    motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

                    robot.tractiuneIntegrala(- motorCorrection, motorCorrection);

                    lastError = error;
                    angle = robot.gyro.getIntegratedZValue();
                    error = direction - angle;
                    telemetry.addData("Angle", "%f", angle);
                    telemetry.addData("Error", "%f", error);
                    update();
                }
            }

            if (gamepad1.x && opModeIsActive()){
                direction = 90;
                angle = robot.gyro.getIntegratedZValue();
                error  = direction - angle;
                lastError = error;

                while (gamepad1.x && opModeIsActive()) {
                    //PID
                    motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

                    robot.tractiuneIntegrala(- motorCorrection, motorCorrection);

                    lastError = error;
                    angle = robot.gyro.getIntegratedZValue();
                    error = direction - angle;
                    telemetry.addData("Angle", "%f", angle);
                    telemetry.addData("Error", "%f", error);
                    update();
                }
            }
        }

    }
}
