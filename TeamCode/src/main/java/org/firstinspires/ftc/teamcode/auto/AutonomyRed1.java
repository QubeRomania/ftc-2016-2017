package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Autonomy Red 1", group = "Autonomy")
public final class AutonomyRed1 extends AutonomousOpMode {

    //VARIABILE
    //pid
    public double motorCorrection = 0;

    private void goTowardsFirstBeacon2(){

        double direction = 55;
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
                modifier = 0.2;

            telemetry.addData("Gyro angle", "%f deg", angle);
            telemetry.addData("Gyro error", "%f deg", error);
            update();
        }
        while (robot.lightSensorLeft.getLightDetected() <= 0.2 && opModeIsActive());

        setStatus("Found line.");
        update();

        robot.tractiuneIntegrala(-0.2, -0.2);
        waitForMs(400);

        robot.tractiuneIntegrala(0, 0);
        rotateTo(70);

        robot.tractiuneIntegrala(0.2, 0.2);

        while(robot.colorSensorLine.alpha() < 12 && opModeIsActive()){
            setStatus("Going towards beacon");
            telemetry.addData("Alpha ", robot.colorSensorLine.alpha());
            update();
        }

        robot.tractiuneIntegrala(0, 0);

        waitForMs(200);
    }

    @Override
    public void play() {
        initialize();

        calibrateGyro();

        waitForStart();


        goTowardsCenterVortex();

        // Throw the particles.
        fireAllParticles();

        goTowardsFirstBeacon2();


        // Approach beacon.
        goToBeacon(BeaconColor.RED, 17);

        pressBeacon(BeaconColor.RED);

        rotateTo(0);

        // Go to second beacon.
        goToSecondBeacon(0);

        rotateTo(85);
        goToBeacon(BeaconColor.RED, 15);
        pressBeacon(BeaconColor.RED);
        rotateTo(45);

        runtime.reset();

        robot.tractiuneIntegrala(-BASE_SPEED * LEFT_PROP, -BASE_SPEED * RIGHT_PROP);

        while (runtime.milliseconds() < 2000 && opModeIsActive());
    }
}
