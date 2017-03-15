package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GamepadButton;
import org.firstinspires.ftc.teamcode.RobotOpMode;

@TeleOp(name = "Gamepad Test", group = "Tests")
public final class GamepadTest extends RobotOpMode {
    @Override
    public void loop() {
        telemetry.addData("Buton A", Boolean.toString(checkButtonHold(1, GamepadButton.a)));
        telemetry.update();
    }
}
