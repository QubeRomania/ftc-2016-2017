package org.firstinspires.ftc.teamcode.auto;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotOpMode;
import org.firstinspires.ftc.teamcode.Traction;

public final class FirstAutonomy extends RobotOpMode {

    @Override
    public void start() {
        robot.initAllMotors();
        robot.initServos();
        //robot.initSensors();
    }

    @Override
    public void loop() {

        // Drive forward.
        tractiuneRobot(1, 1, Traction.Both);
        sleep(1100);
        tractiuneRobot(0, 0, Traction.Both);

        // Fire motors
        prepareFire(true);
        sleep(1000);
        fireBalls(true);
        sleep(4000);
        grabBalls(true);
        sleep(300);
        grabBalls(false);
        fireBalls(false);
        prepareFire(false);
/*
        // Turn to the beacon
        tractiuneRobot(-1, 1, Traction.Both);
        sleep(200);
        tractiuneIntegrala(0, 0);
        sleep(200);

        // Go to the beacon
        tractiuneRobot(1, 1, Traction.Both);

        while (robot.colorSensorLine.alpha() < 10) {
            setStatus("mergem");
        }

        tractiuneRobot(0, 0, Traction.Both);

        // Ne aliniem ca sa apasam beacanul
        while (Math.abs(robot.usdSensorLeft.getDistance(DistanceUnit.CM) - robot.usdSensorRight.getDistance(DistanceUnit.CM)) <= 4) {
            if (robot.usdSensorLeft.getDistance(DistanceUnit.CM) <= robot.usdSensorRight.getDistance(DistanceUnit.CM))
                tractiuneRobot(-0.3, 0.3, Traction.Both);

            if (robot.usdSensorLeft.getDistance(DistanceUnit.CM) > robot.usdSensorRight.getDistance(DistanceUnit.CM)){
                tractiuneRobot(0.3, -0.3, Traction.Both);
            }
        }

        tractiuneRobot(0, 0, Traction.Both);

        // Mergem in fata putin
        while (robot.usdSensorLeft.getDistance(DistanceUnit.CM) >= 7) {
            tractiuneRobot(0.4, 0.4, Traction.Both);
        }
        tractiuneIntegrala(0, 0);

        // colorSensorBeacon este rosu daca e >= 6 si albastru daca e < 6;

        boolean color; // 0 daca e albastru si 1 daca e rosu
        // Vedem starea beaconului
        if (robot.colorSensorBeacon.argb() >= 6)
            color = true;
        else
            color = false;

        //daca e rosu
        if (color == true) {
            robot.servoBeacon.setPosition(1);
        }
        //daca e albastru
        else{
            robot.servoBeacon.setPosition(0);
        }

        tractiuneRobot(0.5, 0.5, Traction.Both);
        sleep(1000);

        tractiuneRobot(-0.5, -0.5, Traction.Both);
        sleep(500);

        tractiuneRobot(0, 0, Traction.Both);
*/
        requestOpModeStop();
    }
}
