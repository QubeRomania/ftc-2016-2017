package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.I2cAddrConfig;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.LinearRobotOpMode;
import org.firstinspires.ftc.teamcode.sensor.RangeSensor;


@Autonomous (name = "Range Sensor Test", group = "Tests")
public class RangeSensorTest extends LinearRobotOpMode {

    private void addUSDSensorData(RangeSensor sensor, String name) {
        telemetry.addData(name + " raw ultrasonic", sensor.rawUltrasonic());
        telemetry.addData(name + " raw optical", sensor.rawOptical());
        telemetry.addData(name + " cm optical", "%.2f cm", sensor.cmOptical());
        telemetry.addData(name + " cm", "%.2f cm", sensor.getDistance(DistanceUnit.CM));
    }

    @Override
    public void play() {
        // get a reference to our compass
        robot.initRangeSensors();

        // wait for the start button to be pressed
        waitForStart();

        while (opModeIsActive()) {
            addUSDSensorData(robot.usdSensorFrontLeft, "Front left sensor");
            addUSDSensorData(robot.usdSensorFrontRight, "Front right sensor");
            addUSDSensorData(robot.usdSensorRightLeft, "Front left sensor");
            telemetry.update();
        }
    }
}
