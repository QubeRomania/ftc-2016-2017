package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Autonomy Blue 1", group = "Autonomy")
public final class AutonomyBlue1 extends AutonomousOpMode {

    //VARIABILE
    //pid
    public double motorCorrection = 0;
    public double multiplier = 9;

    //sensors
    public double sideDistance = 0;

    //altele
    public String parteLinie = null;

    private void goTowardsFirstBeacon2(){

        double direction = -48;
        double angle = robot.gyro.getIntegratedZValue(), error = direction - angle;
        double lastError = error;
        boolean vazut = false;
        rotateTo(direction);
        runtime.reset();

        double modifier = 1;

        do {
            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;

            //PID
            motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 100;
            robot.tractiuneIntegrala(((BASE_SPEED * LEFT_PROP) - motorCorrection) * modifier, ((BASE_SPEED * RIGHT_PROP) + motorCorrection) * modifier );

            if (runtime.milliseconds() >= 1400)
                modifier = 0.4;

            telemetry.addData("Gyro angle", "%f deg", angle);
            telemetry.addData("Gyro error", "%f deg", error);
            telemetry.addData("Light detected", "%.3f", robot.lightSensorRight.getLightDetected());
            update();
        }
        while (robot.lightSensorRight.getLightDetected() <= 0.2 && opModeIsActive());

        setStatus("Found line.");
        update();

        robot.tractiuneIntegrala(-0.2, -0.2);
        waitForMs(200);

        rotateTo(-80);

        robot.tractiuneIntegrala(0.2, 0.2);

        while(robot.colorSensorLine.alpha() < 20 && opModeIsActive()){
            setStatus("Mergem catre linie");
        }

        robot.tractiuneIntegrala(0, 0);

        waitForMs(200);
    }

    @Override
    public void play() {
        // Initialize hardware
        robot.initServoBeacon();
        robot.initAllMotors();
        robot.initSensors();

        calibrateGyro();

        waitForStart();


        goTowardsCenterVortex();

        // Throw the particles.
        fireAllParticles();

        goTowardsFirstBeacon2();


        // Approach beacon.
        goToBeacon(BeaconColor.BLUE);

        pressBeacon(BeaconColor.BLUE);

        robot.tractiuneIntegrala(0, 0);

        rotateTo(0);

        runtime.reset();
        robot.tractiuneIntegrala(1, 1);
        while (runtime.milliseconds() <= 200 && opModeIsActive()) {
            setStatus("Leaving white line of the first beacon.");
            update();
        }

        double direction = 0;
        double angle = robot.gyro.getIntegratedZValue();
        double error = direction - angle;
        double lastError = error;
        double powerLeft = BASE_SPEED * LEFT_PROP;
        double powerRight = BASE_SPEED * RIGHT_PROP;
        boolean scazut = false;
        runtime.reset();
        while (opModeIsActive() && robot.colorSensorLine.alpha() < 20) {
            angle = robot.gyro.getIntegratedZValue();
            lastError = error;
            error = direction - angle;
            //PID
            motorCorrection = ((error * P + (error + lastError) * I + (error - lastError) * D) * scale) / 50;
            if(runtime.milliseconds() < 1200) robot.tractiuneIntegrala(powerLeft - motorCorrection, powerRight + motorCorrection);
            else robot.tractiuneIntegrala((powerLeft - motorCorrection) / 4, (powerRight + motorCorrection) / 4);
            setStatus("Going to second beacon's line.");
            update();
        }

        robot.tractiuneIntegrala(-0.2, -0.2);
        waitForMs(300);

        robot.tractiuneIntegrala(0, 0);
        waitForMs(200);

        setStatus("We found the line");
        update();

        rotateTo(-85);
        goToBeacon(BeaconColor.BLUE);
        pressBeacon(BeaconColor.BLUE);
        rotateTo(-45);

        runtime.reset();

        robot.tractiuneIntegrala(-BASE_SPEED * LEFT_PROP, -BASE_SPEED * RIGHT_PROP);

        while (runtime.milliseconds() < 2000 && opModeIsActive());
    }
}
