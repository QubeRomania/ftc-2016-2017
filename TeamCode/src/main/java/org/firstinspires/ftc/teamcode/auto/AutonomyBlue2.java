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
        robot.tractiuneIntegrala(0.3, 0.3);
        waitForMs(200);

        rotateTo(-40);

        fireAllParticles();
    }
}
