package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Autonomy Red 1", group = "Autonomy")
public final class AutonomyRed1 extends AutonomousOpMode {

    //CONSTANTE


    //VARIABILE
    //pid
    public double motorCorrection = 0;
    public double multiplier = 9;

    //sensors
    public double sideDistance = 0;

    //altele
    public String parteLinie = null;

    private void goTowardsFirstBeacon() {

        double direction = 50;
        double angle = robot.gyro.getIntegratedZValue(), error = direction - angle;
        double lastError = error;
        rotateTo(50);

        double lightLeft;

        runtime.reset();


        do {
            lightLeft = robot.lightSensorLeft.getLightDetected();

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;

            //PID
             motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;
            robot.tractiuneIntegrala((BASE_SPEED * LEFT_PROP) - motorCorrection, (BASE_SPEED * RIGHT_PROP) + motorCorrection);

            telemetry.addData("Gyro angle", "%f deg", angle);
            telemetry.addData("Gyro error", "%f deg", error);

            telemetry.addData("Left light sensor", "Light detected %.2f", lightLeft);
            update();
        }
        while (lightLeft <= 0.2 && runtime.seconds() < 4 && opModeIsActive());

        if (runtime.seconds() >= 4)
        {
            setStatus("Error: line not found.");
            update();

        }
        robot.tractiuneIntegrala(0.4, 0.4);

        waitForMs(200);

        robot.tractiuneIntegrala(0, 0);
        waitForMs(300);

        rotateTo(0);

        // Go back to white line.
        robot.tractiuneIntegrala(-0.2, -0.2);

        while (robot.colorSensorLine.alpha() < 20 && opModeIsActive()) {
            setStatus("Going back towards beacon.");
            update();
        }

        // Go a bit forward.
        robot.tractiuneIntegrala(0.3, 0.3);

        waitForMs(300);

        robot.tractiuneIntegrala(0, 0);

        waitForMs(100);
        rotateTo(90);
    }

    private void goToBeacon() {
        while (robot.usdSensorFrontRight.getDistance(DistanceUnit.CM) >= 20 && opModeIsActive()) {
            robot.tractiuneIntegrala(0.2, 0.2);
        }

        robot.tractiuneIntegrala(0, 0);
        waitForMs(1000);
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

        pressBeacon(BeaconColor.RED);

        robot.tractiuneIntegrala(0, 0);

        rotateTo(0);

        robot.tractiuneIntegrala(1, 1);
        waitForMs(500);
        double direction = 0;
        double angle = robot.gyro.getIntegratedZValue();
        double error = direction - angle;
        double lastError = error;
        double powerleft = BASE_SPEED * LEFT_PROP;
        double powerright = BASE_SPEED * RIGHT_PROP;
        boolean scazut = false;

        while (opModeIsActive() && robot.colorSensorLine.alpha() < 20) {
             angle = robot.gyro.getIntegratedZValue();
             lastError = error;
             error = direction - angle;
            //PID
            motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;
            robot.tractiuneIntegrala(powerleft - motorCorrection, powerright + motorCorrection);
            if(scazut == false && robot.lightSensorLeft.getLightDetected() > 0.2){
                scazut = true;
                powerleft /= 2;
                powerright /= 2;
            }
        }

        robot.tractiuneIntegrala(-0.15, -0.15);
        waitForMs(300);

        rotateTo(90);
        goToBeacon();
        pressBeacon(BeaconColor.RED);
    }
}
