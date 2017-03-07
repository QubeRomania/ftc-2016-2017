package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Test Sensors", group="Tests")
//@Disabled
public class SensorsTest extends RobotOpMode
{

    protected Hardware robot = new Hardware();

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.update();
    }


}
