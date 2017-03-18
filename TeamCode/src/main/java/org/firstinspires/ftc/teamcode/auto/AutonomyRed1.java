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
import org.firstinspires.ftc.teamcode.test.BeaconTest;

@Autonomous(name = "AutonomyRed1", group = "Autonomy")
public final class AutonomyRed1 extends LinearRobotOpMode {


    //CONSTANTE

    //viteza
    // TODO: set this to a bigger speed
    static final double BASE_SPEED = 0.4;
    static final double LEFT_SPEED = 0.55 / 2;
    static final double RIGHT_SPEED = 0.75 / 2;

    /// In centimeters.
    static final double DISTANCE_FROM_CENTER_VORTEX = 75;

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

    private void rotate(int targetHeading){
        this.direction = targetHeading;

        int currentHeading, error, absError;

        do {
            currentHeading = robot.gyro.getIntegratedZValue();
            error = currentHeading - targetHeading;

            absError = Math.abs(error);

            setStatus("Rotating to " + Integer.toString(targetHeading) + " degrees.");
            telemetry.addData("Error", "%d deg", (int)error);
            update();

            // Between 0.0 and 1.0
            double relError = absError / 180.0;

            // Between 1.1 and 2.1
            double powerModifier = 1.1 + relError;

            double power = BASE_SPEED * powerModifier;

            // Too much to the left.
            if (error > 0) {
                robot.tractiuneIntegrala(power, -power);
            }
            // Too much to the right.
            else {
                robot.tractiuneIntegrala(-power, power);
            }
        }
        while (absError > 5 && opModeIsActive());

        robot.tractiuneIntegrala(0, 0);
    }
    private void addDistanceData() {
        telemetry.addData("Left front distance", "%f cm", leftFrontDistance);
        telemetry.addData("Right front distance", "%f cm", rightFrontDistance);
        telemetry.update();
    }

    private void calibrateGyro() {
        robot.gyro.calibrate();

        while (robot.gyro.isCalibrating()){
            setStatus("Calibrating...");
            update();
            idle();
        }

        setStatus("Done calibrating gyro.");
        update();
    }

    private void fireAllParticles() {
        setStatus("Throwing balls...");
        update();

        robot.fireBalls(true);

        robot.prepareFire(true);

        waitForMs(1500);

        for (int ball = 1; ball <= 2; ++ball)
        {
            robot.grabBalls(true);
            waitForMs(250);

            robot.grabBalls(false);
            waitForMs(1000);
        }

        robot.grabBalls(false);
        robot.prepareFire(false);
        robot.fireBalls(false);

        setStatus("All balls fired");
        update();
    }

    @Override
    public void play() {

        //START LOOP
        //-------------------------------------------------------------------------------------------------------------------------

        // Initializare hardware
        robot.initServos();
        robot.initAllMotors();
        robot.initSensors();

        calibrateGyro();

        waitForStart();

        //-------------------------------------------------------------------------------------------------------------------------

        //ACTUAL CODE
        //-------------------------------------------------------------------------------------------------------------------------

        // Go towards center vortex.
        runtime.reset();
        time = runtime.time();

        direction = 0;
        angle = robot.gyro.getIntegratedZValue();
        error = direction - angle;
        lastError = error;

        addDistanceData();

        //medie de 3 termeni
        do {
            leftFrontDistance = robot.usdSensorFrontLeft.getDistance(DistanceUnit.CM);
            rightFrontDistance = robot.usdSensorFrontRight.getDistance(DistanceUnit.CM);

            //PID
            //motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            robot.tractiuneIntegrala(LEFT_SPEED - error / 50, RIGHT_SPEED + error / 50);
            addDistanceData();

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;
        }
        while (leftFrontDistance >= DISTANCE_FROM_CENTER_VORTEX || rightFrontDistance >= DISTANCE_FROM_CENTER_VORTEX);

        robot.tractiuneIntegrala(0, 0);

        waitForMs(1000);

        // Throw the particles.
        fireAllParticles();

        direction = 48;
        angle = robot.gyro.getIntegratedZValue();
        error = direction - angle;
        lastError = error;

        lightLeft = robot.lightSensorLeft.getLightDetected();
        lightRight = robot.lightSensorRight.getLightDetected();

        time = runtime.seconds();
        while (lightLeft <= 0.2 && lightRight <= 0.2 && runtime.seconds() - time < 3){
            //PID
           // motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            robot.tractiuneIntegrala(BASE_SPEED - error / 50, BASE_SPEED + error / 50);

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
        waitForMs(400);

        robot.tractiuneIntegrala(0.5, 0.5);
        waitForMs(700);

        robot.tractiuneIntegrala(0, 0);
        waitForMs(500);

        rotate(20);

        robot.tractiuneIntegrala(-0.2, -0.2);

        while(robot.colorSensorLine.alpha() < 20) {
            setStatus("Going back towards beacon.");
            update();
        }

        robot.tractiuneIntegrala(0, 0);

        robot.tractiuneIntegrala(0.3, 0.3);

        waitForMs(200);

        robot.tractiuneIntegrala(0, 0);

        waitForMs(300);


        rotate(70);

        waitForMs(1000);

        // Approach beacon.

        robot.tractiuneIntegrala(0.2, 0.2);

        while (robot.usdSensorFrontRight.getDistance(DistanceUnit.CM) >= 12 && opModeIsActive()) {
            setStatus("Going towards beacon.");
            update();
        }

        robot.tractiuneIntegrala(0, 0);

        waitForMs(1000);

        BeaconTest.pressBeacon(robot, telemetry);

        robot.tractiuneIntegrala(0, 0);

        //-------------------------------------------------------------------------------------------------------------------------


        /*multiplier = 2.3;

        while (error + lastError >= 5){
            //PID
            motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;

            robot.tractiuneIntegrala(motorCorrection * multiplier, - motorCorrection * multiplier);

            telemetry.addData("Gyro", angle);
            telemetry.update();

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;

        }
*/

    }
}