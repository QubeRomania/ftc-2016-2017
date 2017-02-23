package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Codrin on 2/21/2017.
 */

/*
//INVENTAR FUNCTII PLUS DESCRIERE
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    void:
        tractiuneRobot
            double i - putere motoare stanga
            double j - putere potoare dreapta
            char tr - selectarea tractiunii

        tractiuneIntegrala
            double i - putere motoare stanga
            double j - putere motare dreapta

        tractiuneSpate
            double i - putere motor spate stanga
            double j - putere motor spate dreapta

        tractiuneFata
            double i - putere motor fata stanga
            double j - putere motor fata dreapta

        grabBalls
            booolean af - daca motorul cu care strangem bilelel sa fie portnit sau nu

        setStatus
            string msg - statusul care va fi afisat pe consola

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   */

public abstract class RobotOpMode extends OpMode {

    public static final double MOTOR_LIMIT = 0.78;

    protected Hardware robot = new Hardware();

    protected void tractiuneRobot (double i, double j, char tr){
        if (i > 1)
            i = 1;
        if (i < - 1)
            i = - 1;
        if (j > 1)
            j = 1;
        if (j < - 1)
            j = - 1;

        if (tr == 'i'){
            robot.leftFrontMotor.setPower(i * MOTOR_LIMIT);
            robot.rightFrontMotor.setPower(-j * MOTOR_LIMIT);
            robot.leftBackMotor.setPower(i * MOTOR_LIMIT);
            robot.rightBackMotor.setPower(-j * MOTOR_LIMIT);
        }
        if (tr == 's'){
            robot.leftFrontMotor.setPower(0);
            robot.rightFrontMotor.setPower(0);
            robot.leftBackMotor.setPower(i * MOTOR_LIMIT);
            robot.rightBackMotor.setPower(-j * MOTOR_LIMIT);
        }
        if (tr == 'f'){
            robot.leftFrontMotor.setPower(i * MOTOR_LIMIT);
            robot.rightFrontMotor.setPower(-j * MOTOR_LIMIT);
            robot.leftBackMotor.setPower(0);
            robot.rightBackMotor.setPower(0);
        }

    }

    protected void tractiuneIntegrala (double i, double j){
        if (i > 1)
            i = 1;
        if (i < - 1)
            i = - 1;
        if (j > 1)
            j = 1;
        if (j < - 1)
            j = - 1;
        robot.leftFrontMotor.setPower(i * MOTOR_LIMIT);
        robot.rightFrontMotor.setPower(-j * MOTOR_LIMIT);
        robot.leftBackMotor.setPower(i * MOTOR_LIMIT);
        robot.rightBackMotor.setPower(-j * MOTOR_LIMIT);
    }

    protected void tractiuneSpate (double i, double j){
        if (i > 1)
            i = 1;
        if (i < - 1)
            i = - 1;
        if (j > 1)
            j = 1;
        if (j < - 1)
            j = - 1;
        robot.leftBackMotor.setPower(i * MOTOR_LIMIT);
        robot.rightBackMotor.setPower(-j * MOTOR_LIMIT);
    }

    protected void tractiuneFata (double i, double j){
        if (i > 1)
            i = 1;
        if (i < - 1)
            i = - 1;
        if (j > 1)
            j = 1;
        if (j < - 1)
            j = - 1;
        robot.leftFrontMotor.setPower(i * MOTOR_LIMIT);
        robot.rightFrontMotor.setPower(-j * MOTOR_LIMIT);
    }





    protected void grabBalls (boolean af){
        if (af == true){
            robot.grabMotor.setPower(MOTOR_LIMIT);
        }
        else{
            robot.grabMotor.setPower(0);
        }
    }

    protected void fireBalls (boolean af){
        if (af == true){
            robot.fireLeftMotor.setPower(MOTOR_LIMIT);
            robot.fireRightMotor.setPower(-MOTOR_LIMIT);
        }
        else{
            robot.fireLeftMotor.setPower(0);
            robot.fireRightMotor.setPower(0);
        }

        // mai e de adaugat servoul care va lasa mingile sa treaca
    }





    protected void setStatus (String msg){
        telemetry.addData("Status", msg);
        telemetry.update();
    }

    @Override
    public void init() {
        setStatus ("Initialized");
        robot.init(hardwareMap);
    }

    @Override
    public void stop(){
        setStatus("robot stopped. I hope you won");
    }
}
