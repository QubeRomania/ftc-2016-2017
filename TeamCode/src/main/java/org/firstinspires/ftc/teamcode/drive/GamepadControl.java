package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.GamepadButton;
import org.firstinspires.ftc.teamcode.RobotOpMode;
import org.firstinspires.ftc.teamcode.Traction;

@TeleOp(name="Control with Gamepad", group="Iterative Opmode")
public class GamepadControl extends RobotOpMode
{
    //VARIABILE
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //variabele control miscare robot
    double motorPower = 0, grabPower = 0;
    double directie;
    Traction tractiune = Traction.Both;

    //variabelele care tin minte stare butoanelor
    boolean afGamepad1y = false, afGamepad1a = false, afGamepad1b = false, afGamepad1x = false, afGamepad1start = false, afgamepad1rightBumper = false, afGamepad2y = false, afGamepad2a = false, afGamepad2b = false, afGamepad2x = false, afgamepad2start = false, afGamepad2rightBumper = false;

    //variabila control mod leftTrigger, rightTrigger
    boolean changeModeGamepad2 = false;

    //variabila control mod condus
    boolean changeModeGamepad1 = false;

    //variabila sens condus
    boolean sens = false;

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    @Override
    public void start() {
        robot.initAllMotors();
        robot.initServos();
        robot.initSensors();
        robot.closeSensors();

        gamepad1.reset();
        gamepad2.reset();
    }

    @Override
    public void loop() {

        //CONTROL TRACTIUNE
        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //schimb mod de condus
        if (checkButtonToggle(1, GamepadButton.startButton)){
            afGamepad1start = !afGamepad1start;
            changeModeGamepad1 = !changeModeGamepad1;
            robot.tractiuneRobot(0, 0, Traction.Both);
            motorPower = 0;
            directie = 0;
        }

        //schimb sens de condus
        if (checkButtonToggle(1, GamepadButton.y)){
            afGamepad1y = !afGamepad1y;
            sens = !sens;
            motorPower = 0;
            directie = 0;
        }

        //Accesare tractiune

        // varianta1
        if (changeModeGamepad1 == false) {
            if (sens == false) {
                motorPower = gamepad1.right_trigger - gamepad1.left_trigger;
                directie = gamepad1.left_stick_x;

                if (motorPower >= 0)
                    robot.tractiuneRobot(motorPower + directie, motorPower - directie, tractiune);
                else
                    robot.tractiuneRobot(motorPower - directie, motorPower + directie, tractiune);
            } else {
                motorPower = gamepad1.left_trigger - gamepad1.right_trigger;
                directie = -gamepad1.left_stick_x;

                if (motorPower > 0)
                    robot.tractiuneRobot(motorPower + directie, motorPower - directie, tractiune);
                else
                    robot.tractiuneRobot(motorPower - directie, motorPower + directie, tractiune);
            }
        }
        //varianta2
        else {
            if (sens == false)
                robot.tractiuneRobot(-gamepad1.left_stick_y, -gamepad1.right_stick_y, tractiune);
            else
                robot.tractiuneRobot(gamepad1.right_stick_x, gamepad1.left_stick_y, tractiune);
        }


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


        //Apasare beacon
        if(checkButtonToggle(1, GamepadButton.a)){
            afGamepad1a = !afGamepad1a;
            robot.changeSide(afGamepad1a);
        }

        //Schimb mod control gamepad2
        if (checkButtonToggle(2, GamepadButton.startButton)){
            changeModeGamepad2 = !changeModeGamepad2;
            robot.grabBallsv2(0);
            robot.liftBall(0);
        }


        //ARUNCARE MINGI LA VORTEX
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        if (changeModeGamepad2 == false) {
            /*//Aspirat mingi
            if(checkButtonToggle(2, GamepadButton.b)){
                afGamepad2b = !afGamepad2b;
                grabBalls (afGamepad2b);
            }

            //Reverse mingi
            if(checkButtonToggle(2, GamepadButton.y)){
                afGamepad2y = !afGamepad2y;
                reverseBalls(afGamepad2y);
            }*/

            grabPower = gamepad2.right_trigger - gamepad2.left_trigger;
            //Aspirat si ridicat + reverse mingi
            robot.grabBallsv2(grabPower);

            //Pregatire aruncare mingi
            if (checkButtonToggle(2, GamepadButton.right_bumper)) {
                afGamepad2rightBumper = !afGamepad2rightBumper;
                robot.prepareFire(afGamepad2rightBumper);
            }

            //Aruncare mingi
            robot.fireBalls(checkButtonHold(2, GamepadButton.a));
        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


        //PUNERE MINGE IN VORTEX
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        if (changeModeGamepad2 == true) {

            //Ridicare minge
            robot.liftBall(gamepad2.right_trigger - gamepad2.left_trigger);

            //Prindere minge
            if (changeModeGamepad2 == true){
                robot.grabBall(gamepad2.right_stick_y);
            }


        }

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }


}
