package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.GamepadButton;
import org.firstinspires.ftc.teamcode.RobotOpMode;
import org.firstinspires.ftc.teamcode.Traction;

@TeleOp(name="Control with Gamepad", group="Iterative Opmode")
public class GamepadControl extends RobotOpMode
{
    //VARIABILE
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //variabele control miscare robot
    double power = 0;
    double directie;
    Traction tractiune = Traction.Both;

    //variabelele care tin minte stare butoanelor
    boolean afGamepad1y = false, afGamepad1a = false, afGamepad1b = false, afGamepad1x = false, afGamepad2y = false, afGamepad2a = false, afGamepad2b = false, afGamepad2x = false;

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    @Override
    public void start() {
        robot.initAllMotors();
        robot.initServos();
    }

    @Override
    public void loop() {
        setStatus("Running: " + runtime.toString());

        //CONTROL TRACTIUNE
        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //Accesare tractiune

        // varianta1
        power = gamepad1.right_trigger - gamepad1.left_trigger;
        directie = gamepad1.left_stick_x;

        //setBoost(gamepad1.right_bumper);

        if (power >= 0)
            tractiuneRobot (power + directie, power - directie, tractiune);
        else
            tractiuneRobot (power - directie, power + directie, tractiune);

        //varianta2
        //tractiuneRobot (- gamepad1.left_stick_y, -gamepad1.right_stick_y, tractiune);


        //schimb tractiune
        if (checkButtonToggle(1, GamepadButton.dpad_up)) {
            tractiune = Traction.Front;
        }

        if (checkButtonToggle(1, GamepadButton.dpad_down)) {
            tractiune = Traction.Back;
        }

        if (checkButtonToggle(1, GamepadButton.dpad_left)) {
            tractiune = Traction.Both;
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




        //ARUNCARE MINGI LA VORTEX
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //Aspirat mingi
        if(checkButtonToggle(1, GamepadButton.b)){
            afGamepad1b = !afGamepad1b;
            grabBalls (afGamepad1b);
        }

        //Reverse mingi
        if(checkButtonToggle(1, GamepadButton.y)){
            afGamepad1y = !afGamepad1y;
            reverseBalls(afGamepad1y);
        }

        //Pregatire aruncare mingi
        if (checkButtonToggle(1, GamepadButton.x)){
            afGamepad1x = !afGamepad1x;
            prepareFire(afGamepad1x);
        }

        //Aruncare mingi
        if(checkButtonToggle(1, GamepadButton.a)){
            afGamepad1a = !afGamepad1a;
            fireBalls (afGamepad1a);
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //PUNERE MINGE IN VORTEX
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //Ridicare minge
        liftBall(gamepad2.right_trigger);

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }


}
