package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public abstract class RobotOpMode extends OpMode {
    static final double TIME_AFTER_PRESS_BUTTON_TOGGLE = 0.3;
    static final double TIME_AFTER_PRESS_BUTTON_HOLD = 0;

    protected Hardware robot = new Hardware();

    protected ElapsedTime runtime = new ElapsedTime();


    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void setStatus (String msg){
        telemetry.addData("Status", msg);
    }

    protected void update() {
        telemetry.update();
    }

    private boolean getButtonValue(int gamepad, GamepadButton button) {
        com.qualcomm.robotcore.hardware.Gamepad gp = ((gamepad == 1) ? gamepad1 : gamepad2);

        switch (button) {
            case a:
                return gp.a;
            case b:
                return gp.b;
            case x:
                return gp.x;
            case y:
                return gp.y;
            case dpad_down:
                return gp.dpad_down;
            case dpad_left:
                return gp.dpad_left;
            case dpad_up:
                return gp.dpad_up;
            case right_bumper:
                return gp.right_bumper;
            case left_bumper:
                return gp.left_bumper;
            case startButton:
                return gp.start;
            default:
                return false;
        }
    }

    private Map<GamepadButton, Double> lastTime = new Hashtable<>();
    private Map<GamepadButton, Boolean> lastState = new Hashtable<>();

    /// Gets the current value for a button on the gamepad.
    protected boolean checkButtonHoldDelay(int gamepad, GamepadButton button) {
        boolean buttonState = getButtonValue(gamepad, button);

        if (lastState.containsKey(button) == false) {
            lastState.put(button, buttonState);
            lastTime.put(button, runtime.time());

            return lastState.get(button);
        }

        boolean lastButtonState = lastState.get(button);

        double delay = TIME_AFTER_PRESS_BUTTON_HOLD;

        if (lastButtonState == false)
        {
            delay = 0;
        }

        if (lastButtonState != buttonState) {
            if (runtime.time() - lastTime.get(button) <= delay) {
                return lastButtonState;
            } else {
                lastState.put(button, buttonState);
                return buttonState;
            }
        } else {
            lastTime.put(button, runtime.time());
            return buttonState;
        }
    }

    protected boolean checkButtonHold(int gamepad, GamepadButton button) {
        return getButtonValue(gamepad, button);
    }

    private Map<GamepadButton, Boolean> buttonLock = new Hashtable<>();

    /// Implements a toggle button control on a gamepad. Pressing a toggle-able button has a delay.
    protected boolean checkButtonToggle(int gamepad, GamepadButton button) {
        boolean buttonValue = getButtonValue(gamepad, button);

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

        if (runtime.time() - lastTime.get(button) > TIME_AFTER_PRESS_BUTTON_TOGGLE) {
            lastTime.put(button, runtime.time());
            return true;
        }

        return false;
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
