package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.LinearRobotOpMode;
import org.firstinspires.ftc.teamcode.RobotOpMode;

@Autonomous(name = "Beacon press test", group = "Tests")
public class BeaconTest extends LinearRobotOpMode {
    public static final double THINKING_TIME = 2.0;

    public static void pressBeacon(Hardware robot, Telemetry telemetry) {

        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();

        String color = null;

        while (runtime.time() <= THINKING_TIME && color == null) {
            telemetry.addData("argb", robot.colorSensorBeacon.argb());
            telemetry.addData("red", robot.colorSensorBeacon.red());
            telemetry.addData("blue", robot.colorSensorBeacon.blue());
            telemetry.update();

            if (robot.colorSensorBeacon.red() - robot.colorSensorBeacon.blue() >= 2) {
                telemetry.addData("rosu", robot.colorSensorBeacon.red());
                color = "rosu";
                robot.servoBeacon.setPosition(1);
            }

            if (robot.colorSensorBeacon.blue() - robot.colorSensorBeacon.red() >= 2) {
                telemetry.addData("albastru", robot.colorSensorBeacon);
                color = "albastru";
                robot.servoBeacon.setPosition(0);
            }

            telemetry.update();
        }

        if (runtime.time() > THINKING_TIME)
        {
            return;
        }

        RobotOpMode.sleep(1000);
        telemetry.addData("Status", "Culoarea este %s", color);
        telemetry.update();

        RobotOpMode.sleep(1000);

        robot.tractiuneIntegrala(0.6, 0.6);

        RobotOpMode.sleep(500);

        robot.tractiuneIntegrala(-0.6, -0.6);

        RobotOpMode.sleep(500);

        robot.tractiuneIntegrala(0, 0);
    }

  @Override
  public void play() {
      waitForStart();

      pressBeacon(robot, telemetry);
  }
}
