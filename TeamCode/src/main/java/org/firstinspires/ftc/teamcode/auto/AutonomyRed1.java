package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.LinearRobotOpMode;
import org.firstinspires.ftc.teamcode.RobotOpMode;
import org.firstinspires.ftc.teamcode.Traction;

@Autonomous(name = "AutonomyRed1", group = "Autonomy")
public final class AutonomyRed1 extends LinearRobotOpMode {


    //CONSTANTE

    //viteza
    static final double BASE_SPEED = 0.8;
    static final double LEFT_CONSTANT = 0.95;
    static final double RIGHT_CONSTANT = 1;

    //PID procentual
    static final double P = 20;
    static final double I = 30;
    static final double D = 120;

    static final double scale = 0.01;

    //VARIABILE
    //pid
    public double error = 0;
    public double lastError = 0;
    public double direction = 0;
    public double angle = 0;
    public double motorCorrection = 0;
    public double multiplier = 9;

    //sensors
    public double leftFrontDistance = 0;
    public double rightFrontDistance = 0;
    public double sideDistance = 0;

    public double lightLeft = 0;
    public double lightRight = 0;

    //altele
    public String parteLinie = null;






    @Override
    public void play() {

        //START LOOP
        //-------------------------------------------------------------------------------------------------------------------------
        robot.initServos();
        robot.initAllMotors();
        robot.initSensors();

        robot.gyro.calibrate();

        while (robot.gyro.isCalibrating()){
            setStatus("Calibrating");
            update();
            idle();
        }
        telemetry.addData("Done", "!");
        telemetry.update();

        waitForStart();
        //-------------------------------------------------------------------------------------------------------------------------

        //ACTUAL CODE
        //-------------------------------------------------------------------------------------------------------------------------
        runtime.reset();
        time = runtime.time();

        direction = 0;
        angle = robot.gyro.getIntegratedZValue();
        error = direction - angle;
        lastError = error;

        leftFrontDistance = robot.usdSensorFrontLeft.getDistance(DistanceUnit.CM);
        rightFrontDistance = robot.usdSensorFrontRight.getDistance(DistanceUnit.CM);

        while ((leftFrontDistance >= 45 || leftFrontDistance == 0) && (rightFrontDistance >= 45 ||  rightFrontDistance == 0) && runtime.time() - time >= 1){
            //PID
            motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            robot.tractiuneIntegrala((BASE_SPEED + motorCorrection) *LEFT_CONSTANT, (BASE_SPEED - motorCorrection) * RIGHT_CONSTANT);


            leftFrontDistance = robot.usdSensorFrontLeft.getDistance(DistanceUnit.CM);
            rightFrontDistance = robot.usdSensorFrontRight.getDistance(DistanceUnit.CM);

            telemetry.addData("leftFrontDistance", leftFrontDistance);
            telemetry.addData("rightFrontDistance", rightFrontDistance);
            telemetry.update();

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
        }

        robot.tractiuneIntegrala(0, 0);

        telemetry.addData("Throwing balls", "!");

        robot.fireBalls(true);
        robot.prepareFire(true);
        sleep(1500);

        robot.grabBalls(true);
        sleep(200);
        robot.grabBalls(false);
        sleep(300);
        robot.grabBalls(true);
        sleep(700);

        robot.grabBalls(false);
        robot.prepareFire(false);
        robot.fireBalls(false);

        direction = 60;
        angle = robot.gyro.getIntegratedZValue();
        error = direction - angle;
        lastError = error;

        lightLeft = robot.lightSensorLeft.getLightDetected();
        lightRight = robot.lightSensorRight.getLightDetected();

        while (lightLeft <= 0.2 && lightRight <= 0.2){
            //PID
            motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            robot.tractiuneIntegrala((BASE_SPEED + motorCorrection) *LEFT_CONSTANT, (BASE_SPEED - motorCorrection) * RIGHT_CONSTANT);

            lightLeft = robot.lightSensorLeft.getLightDetected();
            lightRight = robot.lightSensorRight.getLightDetected();

            telemetry.addData("lightLeft", lightLeft);
            telemetry.addData("lightRight", lightRight);
            telemetry.update();

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
        }

        robot.tractiuneIntegrala(0, 0);
        sleep(400);



        direction = 90;
        angle = robot.gyro.getIntegratedZValue();
        error = direction - angle;
        lastError = error;

        multiplier = 2.3;

        while (error + lastError >= 5){
            //PID
            motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            robot.tractiuneIntegrala(motorCorrection * LEFT_CONSTANT * multiplier, - motorCorrection * RIGHT_CONSTANT * multiplier);

            telemetry.addData("Gyro", angle);
            telemetry.update();

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;

        }



        //-------------------------------------------------------------------------------------------------------------------------
    }
}