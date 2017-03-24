package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Autonomy Blue 2", group = "Autonomy")
public class AutonomyBlue2 extends AutonomousOpMode {

    @Override
    public void play(){
        initialize();
        calibrateGyro();

        waitForStart();

        waitForMs(2000);

        goTowardsCenterVortex();

        rotateTo(-40);

        fireAllParticles();
    }
}
