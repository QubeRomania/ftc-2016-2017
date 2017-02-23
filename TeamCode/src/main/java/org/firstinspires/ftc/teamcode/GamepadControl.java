package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Control with Gamepad", group="Iterative Opmode")
//@Disabled
public class GamepadControl extends RobotOpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    //variabele control miscare robot
    double power = 0;
    double directie;
    char tractiune = 'i';

    //variabele care tin minte timpul la care a fost apasat un buton pentru ca sa nu ia in calcul ourmatoarea apasare decat dupa 3 zecimi de secunda
    double timeDpad = 0, timerGamepad1a = 0, timerGamepad1b = 0, timerGamepad1y = 0, timerGamepad1x = 0;

    //variabelele care tin minte stare butoanelor
    boolean afGamepad1y = false, afGamepad1a = false, afGamepad1b = false, afGamepad1x = false;


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
        power = gamepad1.right_trigger - gamepad1.left_trigger;
        directie = gamepad1.left_stick_x;
        if (power >= 0)
            tractiuneRobot (power + directie, power - directie, tractiune);
        if (power < 0)
            tractiuneRobot (power - directie, power + directie, tractiune);

        // varianta2
        //tractiuneRobot (gamepad1.left_stick_y, gamepad1.right_stick_y, tractiune);


        //schimb tractiune
        if (gamepad1.dpad_up && runtime.time() - timeDpad >= 0.2) {
            tractiune = 'f';
            timeDpad = runtime.time();
        }

        if (gamepad1.dpad_down && runtime.time() - timeDpad >= 0.2) {
            tractiune = 's';
            timeDpad = runtime.time();
        }

        if (gamepad1.dpad_left && runtime.time() - timeDpad >= 0.2) {
            tractiune = 'i';
            timeDpad = runtime.time();
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




        //ARUNCARE MINGI LA VORTEX
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //Aspirat mingi
        if(gamepad1.y && runtime.time() - timerGamepad1y >= 0.2){
            afGamepad1y = !afGamepad1y;
            grabBalls (afGamepad1y);

            timerGamepad1y = runtime.time();
        }

        //Aruncare mingi
        if(gamepad1.a && runtime.time() - timerGamepad1a >= 0.2){
            afGamepad1a = !afGamepad1a;
            fireBalls (afGamepad1a);

            timerGamepad1a = runtime.time();
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }


}
