package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.sensor.RangeSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.I2cAddr;
import org.firstinspires.ftc.teamcode.servo.LauncherServo;

public final class Hardware {
    //DC motors
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;

    public DcMotor grabMotor = null;
    public DcMotor fireLeftMotor = null;
    public DcMotor fireRightMotor = null;
    public DcMotor liftingMotor = null;

    //Servos
    public LauncherServo servoFire = null;
    public Servo servoGrabBall = null;
    public Servo servoBeacon = null;

    //sensors
    public ModernRoboticsI2cGyro gyro = null;
    public RangeSensor usdSensorFrontRight = null;
    public RangeSensor usdSensorFrontLeft = null;
    public RangeSensor usdSensorRightLeft = null;
    public ColorSensor colorSensorLine = null;
    public ColorSensor colorSensorBeacon = null;
    public OpticalDistanceSensor lightSensorLeft = null;
    public OpticalDistanceSensor lightSensorRight = null;


    //other things
    private HardwareMap hwMap = null;

    private DcMotor initMotorWithoutEncoder(String name) {
        DcMotor motor = hwMap.dcMotor.get(name);

        motor.setPower(0);

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return motor;
    }

    private DcMotor initMotorWithEncoder(String name) {
        DcMotor motor = hwMap.dcMotor.get(name);

        motor.setPower(0);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        return motor;
    }

    public void initBackMotors() {
        leftBackMotor = initMotorWithoutEncoder("leftBackMotor");
        rightBackMotor = initMotorWithoutEncoder("rightBackMotor");
    }

    public void initFrontMotors() {
        leftFrontMotor = initMotorWithoutEncoder("leftFrontMotor");
        rightFrontMotor = initMotorWithoutEncoder("rightFrontMotor");
    }

    public void initDriveMotors() {
        initBackMotors();
        initFrontMotors();
    }

    public void initBallMotors() {
        grabMotor = initMotorWithoutEncoder("grabMotor");
        grabMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        liftingMotor = initMotorWithoutEncoder("liftingMotor");
    }

    public void initFireMotors() {
        fireLeftMotor = initMotorWithoutEncoder("fireLeftMotor");
        fireRightMotor = initMotorWithoutEncoder("fireRightMotor");
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

    public void initServoBeacon() {
        servoBeacon = initServo("servoBeacon");
        servoBeacon.setPosition(0);

    }

    public void initServos() {
        initFireServo();
        initServoBeacon();

        servoGrabBall = initServo("servoGrabBall");
        servoGrabBall.setPosition(0);
        servoGrabBall.setDirection(Servo.Direction.FORWARD);
    }

    public void initGyro() {
        gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("gyro");
        gyro.setHeadingMode(ModernRoboticsI2cGyro.HeadingMode.HEADING_CARTESIAN);
    }

    public void initRangeSensors(){
        usdSensorFrontRight = new RangeSensor(hwMap, "usdSensorFrontRight", 0x15);
        usdSensorFrontLeft = new RangeSensor(hwMap, "usdSensorFrontLeft", 0x1d);
        usdSensorRightLeft = new RangeSensor(hwMap, "usdSensorRightLeft", 0x25);
    }

    public void initLightSensors(){
        lightSensorLeft = hwMap.opticalDistanceSensor.get("lightSensorLeft");
        lightSensorRight = hwMap.opticalDistanceSensor.get("lightSensorLeft");
    }

    public void initColorSensors(){
        colorSensorLine = hwMap.colorSensor.get("colorSensorLine");
        colorSensorLine.setI2cAddress(I2cAddr.create7bit(0x26));
        colorSensorBeacon = hwMap.colorSensor.get("colorSensorBeacon");
        colorSensorBeacon.setI2cAddress(I2cAddr.create7bit(0x1e));
        colorSensorBeacon.enableLed(false);
    }

    public void initSensors() {
        initGyro();
        initRangeSensors();
        initLightSensors();
        initColorSensors();
    }

    public void closeSensors() {
        gyro.close();

        usdSensorFrontLeft.close();
        usdSensorFrontRight.close();
        usdSensorRightLeft.close();

        lightSensorRight.close();
        lightSensorLeft.close();

        colorSensorLine.close();
        colorSensorBeacon.close();
    }

    public void closeServos() {
        servoBeacon.close();
        servoFire.close();
        servoGrabBall.close();
    }

    public void stopAllDevices() {
        closeSensors();
        closeServos();
    }

    //CONSTANTE
    public static final double MOTOR_LIMIT = 0.78;
    //public static final double MUIE_ROBOTI = 1;
    public static final double GRAB_POWER = 0.78;

    /// The power of the flywheels.
    public double fire_power = 0.25;

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public void tractiuneRobot (double left, double right, Traction tr){
        if (tr == Traction.Both) {
            tractiuneIntegrala(left, right);
        } else if (tr == Traction.Back) {
            tractiuneFata(0, 0);
            tractiuneSpate(left, right);
        } else if (tr == Traction.Front) {
            tractiuneFata(left, right);
            tractiuneSpate(0, 0);
        }
    }

    public void tractiuneSpate (double left, double right){
        left = clamp(left, -1, 1);
        right = clamp(right, -1, 1);

        leftBackMotor.setPower(left * MOTOR_LIMIT);
        rightBackMotor.setPower(-right * MOTOR_LIMIT);
    }

    public void tractiuneFata (double left, double right){
        left = clamp(left, -1, 1);
        right = clamp(right, -1, 1);

        leftFrontMotor.setPower(left * MOTOR_LIMIT);
        rightFrontMotor.setPower(-right * MOTOR_LIMIT);
    }

    public void tractiuneIntegrala (double i, double j){
        tractiuneSpate(i, j);
        tractiuneFata(i, j);
    }

    public void grabBallsv2 (double power){
        grabMotor.setPower(power * MOTOR_LIMIT);
    }

    public void grabBalls (boolean af){
        if (af == true){
            grabMotor.setPower(GRAB_POWER);
        }
        else{
            grabMotor.setPower(0);
        }
    }

    public void reverseBalls (boolean af){
        if (af == true){
            grabMotor.setPower(-GRAB_POWER);
        }
        else {
            grabMotor.setPower(0);
        }
    }

    public void prepareFire (boolean af){
        if (af == true)
            servoFire.setFiring(true);
        else
            servoFire.setFiring(false);

    }

    public void fireBalls (boolean af){
        if (af == true){
            fireLeftMotor.setPower( fire_power);
            fireRightMotor.setPower(- fire_power);
        }
        else{
            fireLeftMotor.setPower(0);
            fireRightMotor.setPower(0);}
    }

    public void changeSide (boolean dir){
        if (dir == true){
            servoBeacon.setPosition(1.0);
        }
        if (dir == false){
            servoBeacon.setPosition(0);
        }
    }


    public void liftBall (double power) {
        liftingMotor.setPower(power);
    }


    public void grabBall (boolean af1){
        if (af1 == true)
            servoGrabBall.setPosition(0.2);
        else
            servoGrabBall.setPosition(0);
    }


    public void init(HardwareMap map) {
        hwMap = map;
    }
}
