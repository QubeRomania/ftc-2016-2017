package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Codrin on 2/21/2017.
 */

public abstract class RobotOpMode extends OpMode {
    protected Hardware robot = new Hardware();

    protected void motoareControl1 (double i, double j){
        if (i > 1)
            i = 1;
        if (i < - 1)
            i = - 1;
        if (j > 1)
            j = 1;
        if (j < - 1)
            j = - 1;
        robot.leftMotor.setPower(i);
        robot.rightMotor.setPower(-j);
    }

    protected void setStatus (String msg){
        telemetry.addData("Status", msg);
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
