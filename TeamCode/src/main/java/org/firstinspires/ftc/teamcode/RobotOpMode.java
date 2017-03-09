package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public abstract class RobotOpMode extends OpMode {

    //CONSTANTE
    public static final double MOTOR_LIMIT = 0.78;
    //public static final double MUIE_ROBOTI = 1;
    public static final double TIME_AFTER_PRESS_BUTTON = 0.3;
    public static final double FIRE_POWER = 0.25;
    public static final double GRAB_POWER = 0.9;

    public static double multiplier = MOTOR_LIMIT;

    protected Hardware robot = new Hardware();

    protected ElapsedTime runtime = new ElapsedTime();

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /*public void setBoost(boolean boost) {
        multiplier = boost ? MUIE_ROBOTI : MOTOR_LIMIT;
    }*/

    protected void tractiuneRobot (double i, double j, Traction tr){
        if (tr == Traction.Both){
            tractiuneFata(i, j);
            tractiuneSpate(i, j);
        } else if (tr == Traction.Back){
            tractiuneFata(0, 0);
            tractiuneSpate(i, j);
        } else if (tr == Traction.Front){
            tractiuneFata(i, j);
            tractiuneSpate(0, 0);
        }
    }

    protected void tractiuneSpate (double left, double right){
        left = clamp(left, -1, 1);
        right = clamp(right, -1, 1);

        robot.leftBackMotor.setPower(left * multiplier);
        robot.rightBackMotor.setPower(-right * multiplier);
    }

    protected void tractiuneFata (double left, double right){
        left = clamp(left, -1, 1);
        right = clamp(right, -1, 1);
        
        robot.leftFrontMotor.setPower(left * multiplier);
        robot.rightFrontMotor.setPower(-right * multiplier);
    }

    protected void tractiuneIntegrala (double i, double j){
        tractiuneSpate(i, j);
        tractiuneFata(i, j);
    }


    protected void grabBalls (boolean af){
        if (af == true){
            robot.grabMotor.setPower(GRAB_POWER);
        }
        else{
            robot.grabMotor.setPower(0);
        }
    }

    protected void reverseBalls (boolean af){
        if (af == true){
            robot.grabMotor.setPower(-GRAB_POWER);
        }
        else{
            robot.grabMotor.setPower(0);
        }
    }

    protected void prepareFire (boolean af){
        if (af == true)
            robot.servoFire.setFiring(true);
        else
            robot.servoFire.setFiring(false);

    }

    protected void fireBalls (boolean af){
        if (af == true){
            robot.fireLeftMotor.setPower(FIRE_POWER);
            robot.fireRightMotor.setPower(-FIRE_POWER);
        }
        else{
            robot.fireLeftMotor.setPower(0);
            robot.fireRightMotor.setPower(0);}
    }


    protected void liftBall (double power) {
        robot.liftingMotor.setPower(power * MOTOR_LIMIT);
    }


    protected void grabBall (boolean af1, boolean af2){
        //if (af2 == true){
            //codul pentru prinderea mingii
        //}
        //else
            //codul pentru asteptarea initializarii bratelor
    }

    private Map<GamepadButton, Double> lastTime = new Hashtable<>();
    private Map<GamepadButton, Boolean> buttonLock = new Hashtable<>();

    /// Implements a toggle button control on a gamepad. Pressing a toggle-able button has a delay.
    protected boolean checkButtonToggle(int gamepad, GamepadButton button) {
        boolean buttonValue = false;

        Gamepad gp = ((gamepad == 1) ? gamepad1 : gamepad2);

        switch (button) {
            case a:
                buttonValue = gp.a;
                break;
            case b:
                buttonValue = gp.b;
                break;
            case x:
                buttonValue = gp.x;
                break;
            case y:
                buttonValue = gp.y;
                break;
            case dpad_down:
                buttonValue = gp.dpad_down;
                break;
            case dpad_left:
                buttonValue = gp.dpad_left;
                break;
            case dpad_up:
                buttonValue = gp.dpad_up;
                break;
            case right_bumper:
                buttonValue = gp.right_bumper;
                break;
            default:
                buttonValue = false;
                break;
        }

        // First time button press.
        if (buttonLock.containsKey(button) == false) {
            buttonLock.put(button, false);
            lastTime.put(button, 0.0);

            return buttonValue;
        }

        if (buttonValue == false) {
            // Disable the button's lock.
            buttonLock.put(button, false);
            return false;
        }

        if (buttonLock.get(button) == true)
            return false;

        buttonLock.put(button, true);

        if (runtime.time() - lastTime.get(button) > TIME_AFTER_PRESS_BUTTON) {
            lastTime.put(button, runtime.time());
            return true;
        }

        return false;
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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

        runtime.reset();
    }

    @Override
    public void stop(){
        setStatus("robot stopped. I hope you won");
    }
}
