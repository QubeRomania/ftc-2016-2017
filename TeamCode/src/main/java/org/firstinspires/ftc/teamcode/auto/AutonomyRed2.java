package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Autonomy Red 2", group = "Autonomy")
public class AutonomyRed2 extends AutonomousOpMode {

    @Override
    public void play(){
        initialize();
        calibrateGyro();

        waitForStart();

        waitForMs(2000);

        goTowardsCenterVortex();

        rotateTo(40);

        fireAllParticles();
    }
}
