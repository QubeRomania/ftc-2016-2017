package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Sensor: Gyro", group = "Tests")
public class GyroTest extends LinearOpMode {


  @Override
  public void runOpMode() {

    ModernRoboticsI2cGyro gyro;   // Hardware Device Object
    int xVal, yVal, zVal = 0;     // Gyro rate Values
    int heading = 0;              // Gyro integrated heading
    int angleZ = 0;
    boolean lastResetState = false;
    boolean curResetState  = false;

    // get a reference to a Modern Robotics GyroSensor object.
    gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

    // start calibrating the gyro.
    telemetry.addData(">", "Gyro Calibrating. Do Not move!");
    telemetry.update();
    gyro.calibrate();

    // make sure the gyro is calibrated.
    while (!isStopRequested() && gyro.isCalibrating())  {
      sleep(50);
      idle();
    }

    telemetry.addData(">", "Gyro Calibrated.  Press Start.");
    telemetry.update();

    // wait for the start button to be pressed.
    waitForStart();

    while (opModeIsActive())  {

      // if the A and B buttons are pressed just now, reset Z heading.
      curResetState = (gamepad1.a && gamepad1.b);
      if(curResetState && !lastResetState)  {
        gyro.resetZAxisIntegrator();
      }
      lastResetState = curResetState;

      // get the x, y, and z values (rate of change of angle).
      xVal = gyro.rawX();
      yVal = gyro.rawY();
      zVal = gyro.rawZ();

      // get the heading info.
      // the Modern Robotics' gyro sensor keeps
      // track of the current heading for the Z axis only.
      heading = gyro.getHeading();
      angleZ  = gyro.getIntegratedZValue();

      telemetry.addData(">", "Press A & B to reset Heading.");
      telemetry.addData("0", "Heading %03d", heading);
      telemetry.addData("1", "Int. Ang. %03d", angleZ);
      telemetry.addData("2", "X av. %03d", xVal);
      telemetry.addData("3", "Y av. %03d", yVal);
      telemetry.addData("4", "Z av. %03d", zVal);
      telemetry.update();
    }
  }
}
