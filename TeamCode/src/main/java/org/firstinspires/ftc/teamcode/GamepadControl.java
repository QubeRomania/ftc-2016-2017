package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Control with Gamepad", group="Iterative Opmode")
//@Disabled
public class GamepadControl extends RobotOpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    //VARIABILE
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //variabele control miscare robot
    double power = 0, lastpower = 0;
    double directie;
    char tractiune = 'i';

    //variabele care tin minte timpul la care a fost apasat un buton pentru ca sa nu ia in calcul ourmatoarea apasare decat dupa 3 zecimi de secunda
    double timerGamepad1Dpad = 0, timerGamepad1a = 0, timerGamepad1b = 0, timerGamepad1y = 0, timerGamepad1x = 0, timerGamepad2Dpad = 0, timerGamepad2a = 0, timerGamepad2b = 0, timerGamepad2y = 0, timerGamepad2x = 0;

    //variabelele care tin minte stare butoanelor
    boolean afGamepad1y = false, afGamepad1a = false, afGamepad1b = false, afGamepad1x = false, afGamepad2y = false, afGamepad2a = false, afGamepad2b = false, afGamepad2x = false;

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        //CONTROL TRACTIUNE
        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //Accesare tractiune

        // varianta1
        //power = gamepad1.right_trigger - gamepad1.left_trigger;
        //directie = gamepad1.left_stick_x;
        //if (power >= 0)
            //tractiuneRobot (power + directie, power - directie, tractiune);
        //if (power < 0)
            //tractiuneRobot (power - directie, power + directie, tractiune);

        //varianta2
        tractiuneRobot (- gamepad1.left_stick_y, -gamepad1.right_stick_y, tractiune);


        //schimb tractiune
        if (gamepad1.dpad_up && runtime.time() - timerGamepad1Dpad >= TIME_AFTER_PRESS_BUTTON) {
            tractiune = 'f';
            timerGamepad1Dpad = runtime.time();
        }

        if (gamepad1.dpad_down && runtime.time() - timerGamepad1Dpad >= TIME_AFTER_PRESS_BUTTON) {
            tractiune = 's';
            timerGamepad1Dpad = runtime.time();
        }

        if (gamepad1.dpad_left && runtime.time() - timerGamepad1Dpad >= TIME_AFTER_PRESS_BUTTON) {
            tractiune = 'i';
            timerGamepad1Dpad = runtime.time();
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




        //ARUNCARE MINGI LA VORTEX
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //Aspirat mingi
        if(gamepad1.b && runtime.time() - timerGamepad1b >= TIME_AFTER_PRESS_BUTTON){
            afGamepad1b = !afGamepad1b;
            grabBalls (afGamepad1b);

            timerGamepad1b = runtime.time();
        }

        //Aruncare mingi
        if(gamepad1.a && runtime.time() - timerGamepad1a >= TIME_AFTER_PRESS_BUTTON){
            afGamepad1a = !afGamepad1a;
            fireBalls (afGamepad1a);

            timerGamepad1a = runtime.time();
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //PUNERE MINGE IN VORTEX
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //Ridicare minge
        liftBall(gamepad2.right_trigger);

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }


}
