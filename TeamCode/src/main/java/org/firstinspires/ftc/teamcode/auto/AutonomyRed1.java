package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.LinearRobotOpMode;
import org.firstinspires.ftc.teamcode.RobotOpMode;

@Autonomous(name = "AutonomyRed1", group = "Autonomy")
public final class AutonomyRed1 extends LinearRobotOpMode {

    //CONSTANTE

    //viteza
    // TODO: set this to a bigger speed
    static final double BASE_SPEED = 0.4;
    static final double LEFT_SPEED = 0.55 / 2;
    static final double RIGHT_SPEED = 0.75 / 2;

    /// In centimeters.
    static final double DISTANCE_FROM_CENTER_VORTEX = 65;
    static final double DISTANCE_FROM_WALL = 15;

    //PID procentual
    static final double P = 20;
    static final double I = 30;
    static final double D = 120;

    static final double scale = 0.01;

    //VARIABILE
    //pid
    public double motorCorrection = 0;
    public double multiplier = 9;

    //sensors
    public double leftFrontDistance = 0;
    public double rightFrontDistance = 0;
    public double sideDistance = 0;

    //altele
    public String parteLinie = null;

    private void rotate(int targetHeading) {
        int currentHeading, error, absError;

        do {
            currentHeading = robot.gyro.getIntegratedZValue();
            error = currentHeading - targetHeading;

            absError = Math.abs(error);

            setStatus("Rotating to " + Integer.toString(targetHeading) + " degrees.");
            telemetry.addData("Error", "%d deg", (int) error);
            update();

            // Between 0.0 and 1.0
            double relError = absError / 180.0;

            double power = BASE_SPEED;

            // TODO: fix rotations.

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

    private void goTowardsCenterVortex() {
        runtime.reset();
        time = runtime.time();

        double direction = 0;
        double angle = robot.gyro.getIntegratedZValue();
        double error = direction - angle;
        double lastError = error;

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
        while (leftFrontDistance >= DISTANCE_FROM_CENTER_VORTEX && rightFrontDistance >= DISTANCE_FROM_CENTER_VORTEX);

        robot.tractiuneIntegrala(0, 0);

        waitForMs(600);
    }

    private void goTowardsFirstBeacon() {
        rotate(46);

        //double direction = 50;
        double angle, error;
        // double lastError = error;

        double lightLeft;

        runtime.reset();

        robot.tractiuneIntegrala(BASE_SPEED, BASE_SPEED);

        do {
            lightLeft = robot.lightSensorLeft.getLightDetected();

            // lastError = error;
            //angle = robot.gyro.getIntegratedZValue();
            //error = direction - angle;

            //PID
            // motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;


            //telemetry.addData("Gyro angle", "%f deg", angle);
            //telemetry.addData("Gyro error", "%f deg", error);

            telemetry.addData("Left light sensor", "Light detected %.2f", lightLeft);
            update();
        }
        while (lightLeft <= 0.2 && runtime.seconds() < 4 && opModeIsActive());

        if (runtime.seconds() >= 4)
        {
            setStatus("Error: line not found.");
            update();

            requestOpModeStop();

            return;
        }

        robot.tractiuneIntegrala(0.4, 0.4);

        waitForMs(200);

        robot.tractiuneIntegrala(0, 0);
        waitForMs(300);

        rotate(20);

        // Go back to white line.
        robot.tractiuneIntegrala(-0.2, -0.2);

        while (robot.colorSensorLine.alpha() < 20 && opModeIsActive()) {
            setStatus("Going back towards beacon.");
            update();
        }

        // Go a bit forward.
        robot.tractiuneIntegrala(0.3, 0.3);

        waitForMs(200);

        robot.tractiuneIntegrala(0, 0);

        waitForMs(100);

        rotate(70);
    }

    private void fireAllParticles() {
        setStatus("Throwing balls...");
        update();

        robot.fireBalls(true);

        robot.prepareFire(true);

        waitForMs(500);

        robot.grabBalls(true);
        waitForMs(250);

        robot.grabBalls(false);
        waitForMs(700);

        robot.grabBalls(true);
        waitForMs(250);

        robot.grabBalls(false);
        robot.prepareFire(false);
        robot.fireBalls(false);

        setStatus("All balls fired");
        update();
    }

    private void goToBeacon() {
        while (robot.usdSensorFrontRight.getDistance(DistanceUnit.CM) >= 15 && opModeIsActive()) {
            robot.tractiuneIntegrala(0.2, 0.2);
        }

        robot.tractiuneIntegrala(0, 0);
        waitForMs(1000);
    }

    private void pressBeacon() {
        double THINKING_TIME = 2;

        runtime.reset();

        String color = null;

        while (runtime.time() <= THINKING_TIME && color == null && opModeIsActive()) {
            telemetry.addData("argb", robot.colorSensorBeacon.argb());
            telemetry.addData("red", robot.colorSensorBeacon.red());
            telemetry.addData("blue", robot.colorSensorBeacon.blue());
            telemetry.update();

            if (robot.colorSensorBeacon.red() - robot.colorSensorBeacon.blue() >= 2) {
                telemetry.addData("rosu", robot.colorSensorBeacon.red());
                color = "rosu";
                robot.servoBeacon.setPosition(1);
            }

            if (robot.colorSensorBeacon.blue() - robot.colorSensorBeacon.red() >= 2) {
                telemetry.addData("albastru", robot.colorSensorBeacon);
                color = "albastru";
                robot.servoBeacon.setPosition(0);
            }

            telemetry.update();
        }

        if (runtime.time() > THINKING_TIME)
        {
            telemetry.addData("Status", "Error: thinking time exceeded!");
            telemetry.update();

            return;
        }

        telemetry.addData("Beacon color", "The color is %s", color);
        telemetry.update();

        robot.tractiuneIntegrala(0.6, 0.6);

        waitForMs(500);

        robot.tractiuneIntegrala(-0.6, -0.6);

        waitForMs(500);

        robot.tractiuneIntegrala(0, 0);
    }


    @Override
    public void play() {
        // Initialize hardware
        robot.initServos();
        robot.initAllMotors();
        robot.initSensors();

        calibrateGyro();

        waitForStart();


        goTowardsCenterVortex();

        // Throw the particles.
        fireAllParticles();

        goTowardsFirstBeacon();


        // Approach beacon.
        goToBeacon();

        pressBeacon();

        robot.tractiuneIntegrala(0, 0);

        rotate(20);

        robot.tractiuneIntegrala(1, 1);
        waitForMs(500);

        while (opModeIsActive() && robot.colorSensorLine.alpha() < 20) {
            double distance = robot.usdSensorRightLeft.getDistance(DistanceUnit.CM);
            double error = DISTANCE_FROM_WALL - distance;
            robot.tractiuneIntegrala(LEFT_SPEED + error / 50, RIGHT_SPEED - error / 50);

            if (distance > 50) {
                setStatus("Error: distance from wall too big.");
                update();

                requestOpModeStop();
                return;
            }

            telemetry.addData("Distance from wall", "%f cm", distance);
            update();
        }

        robot.tractiuneIntegrala(-0.3, -0.3);
        waitForMs(200);

        rotate(80);
        goToBeacon();
        pressBeacon();


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
