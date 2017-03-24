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
        robot.tractiuneIntegrala(0.3, 0.3);
        waitForMs(200);

        rotateTo(40);

        fireAllParticles();
    }
}
