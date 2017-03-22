package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Autonomy Blue 1", group = "Autonomy")
public final class AutonomyBlue1 extends AutonomousOpMode {

    //VARIABILE
    //pid
    public double motorCorrection = 0;

    private void goTowardsFirstBeacon2(){

        double direction = -53;
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
        while (robot.lightSensorRight.getLightDetected() <= 0.12 && opModeIsActive());

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
        initialize();

        calibrateGyro();

        waitForStart();


        goTowardsCenterVortex();

        // Throw the particles.
        fireAllParticles();

        goTowardsFirstBeacon2();


        // Approach beacon.
        goToBeacon(BeaconColor.BLUE, 17);

        pressBeacon(BeaconColor.BLUE);

        rotateTo(0);

        goToSecondBeacon();

        rotateTo(-85);
        goToBeacon(BeaconColor.BLUE, 15);
        pressBeacon(BeaconColor.BLUE);
        rotateTo(-45);

        runtime.reset();

        robot.tractiuneIntegrala(-BASE_SPEED * LEFT_PROP, -BASE_SPEED * RIGHT_PROP);

        while (runtime.milliseconds() < 2000 && opModeIsActive());
    }
}
