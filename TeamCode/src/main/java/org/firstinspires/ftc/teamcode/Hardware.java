package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;

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
        -Fire
            servoFire
        -Grab big ball
            servoGrabBallUp
            servoGrabBallDown

    Sensors
        odsSensor (optical distance sensor)
        gyroSensor (gyroscope)
        irSensor (color of the beacon sensor)
        touchSensor (button)
        usdSensor (ultra sonic distance sensor)
        colorSensor (color sensor)

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
*/


public final class Hardware {
    //DC motors
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;

    public DcMotor grabMotor = null;
    //public DcMotor fireLeftMotor = null;
    //public DcMotor fireRightMotor = null;
    //public DcMotor liftingMotor = null;


    //Servos
    //public Servo servo1 = null;
    //public Servo servoFire = null;
    //public Servo servoGrabBallUp = null;
    //public Servo servoGrabBallDown = null;





    //sensors
    //public OpticalDistanceSensor odsSensor = null;
    //public GyroSensor gyroSensor = null;
    //public IrSeekerSensor irSensor = null;
    //public TouchSensor touchSensor = null;
    //public UltrasonicSensor usdSensor = null;
    //public ColorSensor colorSensor = null;





    //other things
    private HardwareMap hwMap = null;
    private ElapsedTime timer = new ElapsedTime();





    public void init(HardwareMap map) {
        hwMap = map;

        leftFrontMotor = hwMap.dcMotor.get("leftFrontMotor");
        rightFrontMotor = hwMap.dcMotor.get("rightFrontMotor");
        leftBackMotor = hwMap.dcMotor.get("leftBackMotor");
        rightBackMotor = hwMap.dcMotor.get("rightBackMotor");

        grabMotor = hwMap.dcMotor.get("grabMotor");
        //fireLeftMotor = hwMap.dcMotor.get("fireLeftMotor");
        //fireRightMotor = hwMap.dcMotor.get("fireRightMotor");
        //liftingMotor = hwMap.dcMotor.get("liftingMotor");


        //servo1 = hwMap.servo.get("servo1");
        //servoFire = hwMap.servo.get("servoFire");
        //servoGrabBallUp = hwMap.servo.get("servoGrabBallUp");
        //servoGrabBallDown = hwMap.servo.get("servoGrabBallDown");

        //odsSensor = hwMap.opticalDistanceSensor.get("odsSensor");
        //gyroSensor = hwMap.gyroSensor.get("gyroSensor");
        //irSensor = hwMap.irSeekerSensor.get("gyroSensor");
        //touchSensor = hwMap.touchSensor.get("touchSensor");
        //usdSensor = hwMap.ultrasonicSensor.get("usdsSensor");
        //colorSensor = hwMap.colorSensor.get("colorSensor");

        // TODO: is it really necessary to set this to 0?
        leftFrontMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightBackMotor.setPower(0);

        grabMotor.setPower(0);
        //fireLeftMotor.setPower(0);
        //fireRightMotor.setPower(0);
        //liftingMotor.setPower(0);

        //servo1.setPosition(- 0.5);
        //servoFire.setPosition(0.5);
        //servoGrabBallDown.setPosition(1);
        //servoGrabBallUp.setPosition(0);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        grabMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //fireLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //fireRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //liftingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
