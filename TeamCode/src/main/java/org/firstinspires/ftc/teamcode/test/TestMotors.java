package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.LinearRobotOpMode;

@Autonomous(name = "TestRobot", group = "Tests")

public class TestMotors extends LinearRobotOpMode {

    public double i = 0;

    @Override
    public void play(){
        robot.initDriveMotors();

        waitForStart();

        while (opModeIsActive()){

            for (i = 0; i <= 1; i += 0.001){
                robot.tractiuneIntegrala(i, i);
                sleep(10);
            }

            for (i = 1; i >= -1; i -= 0.001){
                robot.tractiuneIntegrala(i, i);
                sleep(10);
            }

            for (i = -1; i <= 0; i += 0.001){
                robot.tractiuneIntegrala(i, i);
                sleep(10);
            }

        }
    }
}
