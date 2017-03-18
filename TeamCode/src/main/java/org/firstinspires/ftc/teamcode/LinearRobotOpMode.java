package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class LinearRobotOpMode extends LinearOpMode {
    protected Hardware robot = new Hardware();

    protected ElapsedTime runtime = new ElapsedTime();

    protected void setStatus (String msg){
        telemetry.addData("Status", msg);
    }

    protected void update() {
        telemetry.update();
    }

    @Override
    public final void runOpMode()
    {
        robot.init(hardwareMap);
        play();
    }

    public void waitForMs(long ms) {
        ElapsedTime timer = new ElapsedTime();
        double startTime = timer.milliseconds();

        while (timer.milliseconds() - startTime < ms && opModeIsActive()) {
            idle();
        }
    }


    /// This function is to be overridden in real op-modes.
    protected abstract void play();
}
