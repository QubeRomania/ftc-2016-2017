package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotOpMode;


@Autonomous(name="Test Sensors", group="Tests")
public class SensorsTest extends RobotOpMode
{
    @Override
    public void start() {
        robot.initSensors();
    }

    @Override
    public void loop() {
        setStatus("Testing sensors.");


    }
}
