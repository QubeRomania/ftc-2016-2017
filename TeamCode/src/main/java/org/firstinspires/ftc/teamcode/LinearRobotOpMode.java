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

    private final double P = 30,
            I = 10,
            D = 60,
            scale = 0.05;

    private final double MAX_ERROR = 3.1415;

    public void rotateTo(double direction) {
        double angle = robot.gyro.getIntegratedZValue();
        double error  = direction - angle;
        double lastError = error;
        boolean arrived = false;
        double timer = 50;
        runtime.reset();
        while (opModeIsActive() && ((runtime.time() - timer < 1.5) || arrived == false)){
            //PID
            double  motorCorrection = (((P * error) + (I * (error + lastError)) + D * (error - lastError)) * scale) / 100;

            robot.tractiuneIntegrala(- motorCorrection, motorCorrection);

            lastError = error;
            angle = robot.gyro.getIntegratedZValue();
            error = direction - angle;

            telemetry.addData("Angle", "%f", angle);
            telemetry.addData("Error", "%f", error);
            update();

            if(Math.abs(error) <= MAX_ERROR && arrived == false){
                arrived = true;
                timer = runtime.time();
            }
        }

        setStatus("Rotated fully to " + Double.toString(direction));
        update();
    }

    /// This function is to be overridden in real op-modes.
    protected abstract void play();
}
