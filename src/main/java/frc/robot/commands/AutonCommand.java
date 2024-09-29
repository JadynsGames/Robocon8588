package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Indexing;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.Wrist;

public class AutonCommand extends SequentialCommandGroup {

    public AutonCommand(
        SwerveSubsystem driveSubsystem, Intake intake, Indexing indexing, Shooter shooter, Wrist wrist
    ) {
        addRequirements(driveSubsystem);

        final double velocityX = -0.4;
        final double velocityY = 0;
        // Angular Velocity
        final double omegaRadiansPerSecond = 0;

        addCommands(
            // Make sure everything is stopped
             new ParallelCommandGroup(
                 Commands.runOnce(()->{
                 intake.stop();
                 indexing.stop();
             })
             ),
             new WaitCommand(10).andThen(
                driveSubsystem.run(() -> driveSubsystem.drive(new ChassisSpeeds(velocityX, velocityY, omegaRadiansPerSecond))).withTimeout(5)
                )             
            );

        /*addCommands(
            // Make sure everything is stopped
             new ParallelCommandGroup(
                 Commands.runOnce(()->{
                 intake.stop();
                 indexing.stop();
             })
             ),

             driveSubsystem.run(() -> driveSubsystem.drive(new ChassisSpeeds(velocityX, velocityY, omegaRadiansPerSecond))).withTimeout(4.7),

             new WaitCommand(0.5).andThen(new ParallelCommandGroup(
                new StartEndCommand(
                () -> {
                    shooter.set(-1); // starts the shooter
                },
                () -> {
                    shooter.set(0); // stops the shooter
                },
                shooter),
                new WaitCommand(1.5).andThen(
                    new StartEndCommand(
                    ()-> {
                    //shooter.set(-1); // continues shooting
                    indexing.set(1); // starts the indexing
                    },
                    () -> {
                    //shooter.set(0); // stops the shooter
                    indexing.set(0); // stops the indexing
                    },
                    indexing).withTimeout(1) // retain maximum power
                )
          );
        ));*/

    }

}