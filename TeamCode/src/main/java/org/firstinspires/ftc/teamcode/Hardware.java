package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
//DENUMIRI COMPONENTE HARDWARE DIN TELEFON
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    Dc Motors
        -Tractiune
            leftFrontMotor
            rightFrontMotor
            leftBackMotor
            rightBackMotor
        -Sistem aruncare mingi mici
            grabMotor
            fireLeftMotor
            fireRightMotor

    Servos
        -Test
            servo1

    Sensors
        Not yet

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
*/


public final class Hardware {
    //DC motors
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;

    public DcMotor grabMotor = null;
    public DcMotor fireLeftMotor = null;
    public DcMotor fireRightMotor = null;


    //Servos
    public Servo servo1 = null;


    //other things
    private HardwareMap hwMap = null;
    private ElapsedTime timer = new ElapsedTime();


    //sensors (not yet)


    public void init(HardwareMap map) {
        hwMap = map;

        leftFrontMotor = hwMap.dcMotor.get("leftFrontMotor");
        rightFrontMotor = hwMap.dcMotor.get("rightFrontMotor");
        leftBackMotor = hwMap.dcMotor.get("leftBackMotor");
        rightBackMotor = hwMap.dcMotor.get("rightBackMotor");

        grabMotor = hwMap.dcMotor.get("grabMotor");
        fireLeftMotor = hwMap.dcMotor.get("fireLeftMotor");
        fireRightMotor = hwMap.dcMotor.get("fireRightMotor");


        servo1 = hwMap.servo.get("servo1");

        // TODO: is it really necessary to set this to 0?
        leftFrontMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightBackMotor.setPower(0);

        grabMotor.setPower(0);
        fireLeftMotor.setPower(0);
        fireRightMotor.setPower(0);

        servo1.setPosition(0.5);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        grabMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fireLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fireRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void waitForTick(long periodInMs) {
        long remaining = periodInMs - (long) timer.milliseconds();

        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        timer.reset();
    }
}
