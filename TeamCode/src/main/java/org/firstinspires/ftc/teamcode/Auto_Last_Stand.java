package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@Autonomous(name="OUR AUTONOMOUS", group="Autonomous Opmode")
public class Auto_Last_Stand extends LinearOpMode {
    private DcMotor left_drive = null;
    private DcMotor right_drive = null;
    private DcMotor lift = null;
    @Override
    public void runOpMode() {
        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        right_drive = hardwareMap.get(DcMotor.class, "right_drive");
        lift = hardwareMap.get(DcMotor.class, "lift");
        right_drive.setDirection(DcMotorSimple.Direction.FORWARD);
        left_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("S tatus", "Initialized");
        waitForStart();
        while(opModeIsActive()) {
            left_drive.setPower(1.0);
            right_drive.setPower(1.0);
            sleep(1000);
            left_drive.setPower(0);
            right_drive.setPower(0);
            lift.setPower(0.7);
            sleep(700);
            lift.setPower(0);
        }
    }
}
