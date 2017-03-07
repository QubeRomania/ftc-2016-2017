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

        fireBalls
            boolean af - daca motoarele care arunca bulele sa fie pornit sau nu si daca servoul sa lasa mingile sa treaca sau nu

        liftBall
            double power - puterea motorului care trage sfoara si ridica mingea

        grabBall
            boolean af1 - daca mingea este sau nu pe robot
            booleean af2 - daca sa initializam hardwareul de ridicarea mingii mari

        setStatus
            string msg - statusul care va fi afisat pe consola

        mesage
            string msg - mesaj afisat pe consola

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   */

public abstract class RobotOpMode extends OpMode {

    //CONSTANTE
    public static final double MOTOR_LIMIT = 0.78;
    public static final double TIME_AFTER_PRESS_BUTTON = 0.2;
    public static final double FIRE_POWER = 0.78;
    public static final double GRAB_POWER = 0.78;

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
        //robot.leftFrontMotor.setPower(i * MOTOR_LIMIT);
        //robot.rightFrontMotor.setPower(-j * MOTOR_LIMIT);
        //robot.leftBackMotor.setPower(i * MOTOR_LIMIT);
        //robot.rightBackMotor.setPower(-j * MOTOR_LIMIT);
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
        //robot.leftBackMotor.setPower(i * MOTOR_LIMIT);
        //robot.rightBackMotor.setPower(-j * MOTOR_LIMIT);
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
        //robot.leftFrontMotor.setPower(i * MOTOR_LIMIT);
        //robot.rightFrontMotor.setPower(-j * MOTOR_LIMIT);
    }





    protected void grabBalls (boolean af){
        if (af == true){
            robot.grabMotor.setPower(GRAB_POWER);
        }
        else{
            robot.grabMotor.setPower(0);
        }
    }

    protected void fireBalls (boolean af){
        if (af == true){
            //robot.fireLeftMotor.setPower(FIRE_POWER);
            //robot.fireRightMotor.setPower(-FIRE_POWER);
            //robot.servoFire.setPosition(1);
        }
        else{
            //robot.fireLeftMotor.setPower(0);
            //robot.fireRightMotor.setPower(0);
            //robot.servoFire.setPosition(0.5);
        }
    }



    protected void liftBall (double power){
        //robot.liftingMotor.setPower(power * MOTOR_LIMIT);
    }


    protected void grabBall (boolean af1, boolean af2){
        //if (af2 == true){
            //codul pentru prinderea mingii
        //}
        //else
            //codul pentru asteptarea initializarii bratelor
    }





    protected void setStatus (String msg){
        telemetry.addData("Status", msg);
        telemetry.update();
    }

    protected void mesage (String msg){
        telemetry.addData(msg, toString());
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
