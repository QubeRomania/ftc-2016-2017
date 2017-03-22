package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.AutonomousOpMode;

@Autonomous(name = "PidTestv2", group = "Tests")

public class PidTestv2 extends AutonomousOpMode {

    //PID procentual
    static final double P = 35;
    static final double I = 5;
    static final double D = 70;

    static final double scale = 0.054;

    boolean arrived = false;

    //VARIABILE
    //pid
    public double motorCorrection = 0;
    public double multiplier = 0;

    static final double BASE_SPEED = 1;
    static final double LEFT_RATIO = 1;
    static final double RIGHT_RATIO = 1;


    public double angle = 0;
    public double error = 0;
    public double lastError = 0;

    public double viteza = 0;

    public void rotateTo(double direction){
        angle = robot.gyro.getIntegratedZValue();
        error = direction - angle;
        lastError = error;

        do {
            if (error > 40 || error < -40)
            {
                //PID
                motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;
                robot.tractiuneIntegrala(-motorCorrection, motorCorrection);

                lastError = error;
                angle = robot.gyro.getIntegratedZValue();
                error = direction - angle;

                telemetry.addData("Angle", "%f", angle);
                telemetry.addData("Error", "%f", error);
                telemetry.addData("Phase", "1");
                update();
            }
            if (error <= 40 && error >= -40){
                if (error < 0){
                    while (opModeIsActive() && Math.abs(error - lastError) >= 5) {
                        robot.tractiuneIntegrala(-0.3, 0.3);
                        lastError = error;
                        angle = robot.gyro.getIntegratedZValue();
                        error = direction - angle;

                        telemetry.addData("Angle", "%f", angle);
                        telemetry.addData("Error", "%f", error);
                        telemetry.addData("Phase", "2");
                        update();
                    }

                    while (opModeIsActive() && error != lastError && error !=0){
                        robot.tractiuneIntegrala(0, 0);

                        lastError = error;
                        angle = robot.gyro.getIntegratedZValue();
                        error = direction - angle;

                        telemetry.addData("Angle", "%f", angle);
                        telemetry.addData("Error", "%f", error);
                        telemetry.addData("Phase", "3");
                        update();
                    }

                    viteza = 0;

                    while (opModeIsActive() && error != 0 && lastError != 0){
                        if (error == lastError && error != 0){
                            viteza += 0.01;
                        }
                        if (error == 0){
                            viteza = 0;
                        }

                        robot.tractiuneIntegrala(viteza, -viteza);

                        lastError = error;
                        angle = robot.gyro.getIntegratedZValue();
                        error = direction - angle;

                        telemetry.addData("Angle", "%f", angle);
                        telemetry.addData("Error", "%f", error);
                        telemetry.addData("Phase", "4");
                        update();
                    }
                }

                if (error > 0){
                    while (opModeIsActive() && Math.abs(error - lastError) >= 5) {
                        robot.tractiuneIntegrala(0.3, -0.3);
                        lastError = error;
                        angle = robot.gyro.getIntegratedZValue();
                        error = direction - angle;

                        telemetry.addData("Angle", "%f", angle);
                        telemetry.addData("Error", "%f", error);
                        telemetry.addData("Phase", 2);
                        update();
                    }

                    while (opModeIsActive() && error != lastError && error !=0){
                        robot.tractiuneIntegrala(0, 0);

                        lastError = error;
                        angle = robot.gyro.getIntegratedZValue();
                        error = direction - angle;

                        telemetry.addData("Angle", "%f", angle);
                        telemetry.addData("Error", "%f", error);
                        telemetry.addData("Phase", "3");
                        update();
                    }

                    viteza = 0;

                    while (opModeIsActive() && error != 0 && lastError != 0){
                        if (error == lastError && error != 0){
                            viteza += 0.01;
                        }
                        if (error == 0){
                            viteza = 0;
                        }

                        robot.tractiuneIntegrala(-viteza, viteza);

                        lastError = error;
                        angle = robot.gyro.getIntegratedZValue();
                        error = direction - angle;

                        telemetry.addData("Angle", "%f", angle);
                        telemetry.addData("Error", "%f", error);
                        telemetry.addData("Phase", "4");
                        update();
                    }
                }
            }

        }
        while (opModeIsActive() && error != 0 && lastError != 0);
    }

    public void play(){
        robot.initGyro();
        robot.initAllMotors();
        calibrateGyro();
        waitForStart();

        while (opModeIsActive()){
            rotateTo(90);
        }

    }
}
