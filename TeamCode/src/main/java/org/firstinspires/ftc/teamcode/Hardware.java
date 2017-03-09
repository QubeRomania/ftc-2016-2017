package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.servo.LauncherServo;

public final class Hardware {
    //DC motors
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;

    public DcMotor grabMotor = null;
    public DcMotor fireLeftMotor = null;
    public DcMotor fireRightMotor = null;
    public DcMotor liftingMotor = null;

    //Servos
    public LauncherServo servoFire = null;
    public Servo servoGrabBallUp = null;
    public Servo servoGrabBallDown = null;
    public Servo servoBeacon = null;

    //sensors
    public OpticalDistanceSensor odsSensor = null;
    public ModernRoboticsI2cGyro gyro = null;
    public ModernRoboticsI2cRangeSensor usdSensorRight = null;
    public ModernRoboticsI2cRangeSensor usdSensorLeft = null;
    public ColorSensor colorSensorLine = null;
    public ColorSensor colorSensorBeacon = null;

    //other things
    private HardwareMap hwMap = null;

    private DcMotor initMotor(String name) {
        DcMotor motor = hwMap.dcMotor.get(name);

        motor.setPower(0);

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return motor;
    }

    public void initBackMotors() {
        leftBackMotor = initMotor("leftBackMotor");
        rightBackMotor = initMotor("rightBackMotor");
    }

    public void initFrontMotors() {
        leftFrontMotor = initMotor("leftFrontMotor");
        rightFrontMotor = initMotor("rightFrontMotor");
    }

    public void initDriveMotors() {
        initBackMotors();
        initFrontMotors();
    }

    public void initBallMotors() {
        grabMotor = initMotor("grabMotor");
        liftingMotor = initMotor("liftingMotor");
    }

    public void initFireMotors() {
        fireLeftMotor = initMotor("fireLeftMotor");
        fireRightMotor = initMotor("fireRightMotor");
    }

    public void initAllMotors() {
        initDriveMotors();
        initBallMotors();
        initFireMotors();
    }

    public void setDriveMotorsRunMode(DcMotor.RunMode runMode) {
        leftBackMotor.setMode(runMode);
        rightBackMotor.setMode(runMode);
        leftFrontMotor.setMode(runMode);
        rightFrontMotor.setMode(runMode);
    }

    public void setDriveMotorTargetPosition(int position) {
        leftBackMotor.setTargetPosition(position);
        rightBackMotor.setTargetPosition(position);
        leftFrontMotor.setTargetPosition(position);
        rightFrontMotor.setTargetPosition(position);
    }

    private Servo initServo(String name) {
        Servo servo = hwMap.servo.get(name);

        servo.setPosition(0);

        return servo;
    }

    public void initFireServo() {
        servoFire = new LauncherServo(hwMap);
    }

    public void initServos() {
        initFireServo();

        servoBeacon = initServo("servoBeacon");
        servoBeacon.setPosition(0.5);

        servoGrabBallUp = initServo("servoGrabBallUp");

        servoGrabBallDown = initServo("servoGrabBallDown");
        servoGrabBallDown.setPosition(1);
    }

    public void initGyro() {
        gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("gyro");
    }

    public void initSensors() {
        odsSensor = hwMap.opticalDistanceSensor.get("odsSensor");
        initGyro();
        usdSensorRight = (ModernRoboticsI2cRangeSensor)hwMap.ultrasonicSensor.get("usdSensorRight");
        usdSensorLeft = (ModernRoboticsI2cRangeSensor)hwMap.ultrasonicSensor.get("usdSensorLeft");
        colorSensorLine = hwMap.colorSensor.get("colorSensorLine");
        colorSensorBeacon = hwMap.colorSensor.get("colorSensorBeacon");

        colorSensorBeacon.enableLed(false);
        colorSensorLine.enableLed(true);
    }

    public void init(HardwareMap map) {
        hwMap = map;
    }
}
