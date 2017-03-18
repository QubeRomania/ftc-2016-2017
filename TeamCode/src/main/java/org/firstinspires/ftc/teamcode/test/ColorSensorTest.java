package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.LinearRobotOpMode;

@Autonomous(name = "Color Sensor Test", group = "Tests")
public class ColorSensorTest extends LinearRobotOpMode {

  @Override
  public void play() {
      robot.initSensors();
    waitForStart();
    while (opModeIsActive()) {
      telemetry.addData("argb", robot.colorSensorLine.argb());
      telemetry.addData("red", robot.colorSensorLine.red());
      telemetry.addData("blue", robot.colorSensorLine.blue());
      telemetry.addData("alpha", robot.colorSensorLine.alpha());

      telemetry.update();
    }
  }
}
