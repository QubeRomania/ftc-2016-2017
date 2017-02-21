package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="GamepadControl", group="Iterative Opmode")
//@Disabled
public class GamepadControl extends RobotOpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    double power = 0, time1, time2;
    double directie;
    boolean af1 = false, af2 = false;

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        power = gamepad1.right_trigger - gamepad1.left_trigger;
        directie = gamepad1.left_stick_x;
        if (power >= 0)
            motoareControl1 (power + directie, power - directie);
        if (power < 0)
            motoareControl1 (power - directie, power + directie);

        /*if (gamepad1.y && af1 == false) {
            grabMotor.setPower(1);
            af1 = false;
            time1 = runtime.time();
        }
        if (gamepad1.a && af2 == false) {
            leftFireMotor.setPower(1);
            af2 = true;
            time2 = runtime.time();
        }
        if (gamepad1.y && af1 == true){
            grabMotor.setPower(0);
            af1 = false;
        }
        if (gamepad1.a && af2 == true){
            leftFireMotor.setPower(0);
            af2 = false;
        }*/
    }


}
