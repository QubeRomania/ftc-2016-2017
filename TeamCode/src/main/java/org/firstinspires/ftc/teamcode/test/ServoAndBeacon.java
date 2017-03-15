package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp(name = "Servo and beacon", group = "Tests")
public class ServoAndBeacon extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();

    com.qualcomm.robotcore.hardware.ColorSensor colorSensor;    // Hardware Device Object
     protected Hardware robot = new Hardware();

    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;

    public static final double THINKING_TIME = 2.0;

    public double time = 0;
    public String color = null;


  @Override
  public void runOpMode() {
      colorSensor = hardwareMap.colorSensor.get("colorSensorBeacon");

      leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
      leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
      rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
      rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");

      rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
      rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

      Servo servoBeacon = hardwareMap.servo.get("servoBeacon");

      colorSensor.enableLed(false);

      waitForStart();

      runtime.reset();

          time = runtime.time();

          while (runtime.time() - time <= THINKING_TIME && color == null) {
              telemetry.addData("argb", colorSensor.argb());
              telemetry.addData("red", colorSensor.red());
              telemetry.addData("blue", colorSensor.blue());
              telemetry.update();

              if (colorSensor.red() - colorSensor.blue() >= 2) {
                  telemetry.addData("rosu", colorSensor.red());
                  telemetry.update();
                  color = "rosu";
                  servoBeacon.setPosition(1);
              }
              if (colorSensor.blue() - colorSensor.red() >= 2) {
                  telemetry.addData("albastru", colorSensor);
                  telemetry.update();
                  color = "albastru";
                  servoBeacon.setPosition(0);

              }
          }
      sleep(1000);
        telemetry.addData("Status", "Culoarea este ", color);
        telemetry.update();
      leftFrontMotor.setPower(0.2);
          leftBackMotor.setPower(0.2);
          rightBackMotor.setPower(0.2);
          rightFrontMotor.setPower(0.2);

          sleep(500);

          leftFrontMotor.setPower(-0.2);
          leftBackMotor.setPower(-0.2);
          rightBackMotor.setPower(-0.2);
          rightFrontMotor.setPower(-0.2);

          sleep (500);

          leftFrontMotor.setPower(0);
          leftBackMotor.setPower(0);
          rightBackMotor.setPower(0);
          rightFrontMotor.setPower(0);


  }
}
