package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name="OUR AUTONOMOUS", group="Autonomous Opmode")
public class Auto_Last_Stand extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left_front_drive = null;
    private DcMotor right_front_drive = null;
    private DcMotor left_back_drive = null;
    private DcMotor right_back_drive = null;
    private DcMotor lift = null;
    private DcMotor latch = null;
    private DcMotor arm = null;
    @Override
    public void runOpMode() {
        left_front_drive = hardwareMap.get(DcMotor.class, "left_front_drive");
        right_front_drive = hardwareMap.get(DcMotor.class, "right_front_drive");
        left_back_drive = hardwareMap.get(DcMotor.class, "left_back_drive");
        right_back_drive = hardwareMap.get(DcMotor.class, "right_back_drive");
        arm = hardwareMap.get(DcMotor.class, "aarm");
        lift = hardwareMap.get(DcMotor.class, "lift");
        latch = hardwareMap.get(DcMotor.class, "latch");
        right_front_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        right_back_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        left_front_drive.setDirection(DcMotorSimple.Direction.FORWARD);
        left_back_drive.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        latch.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        waitForStart();
        while(opModeIsActive()) {
            latch.setPower(1.0);
            sleep(3000);
            latch.setPower(0);
            left_front_drive.setPower(1.0);
            left_back_drive.setPower(1.0);
            right_front_drive.setPower(1.0);
            right_back_drive.setPower(1.0);
            sleep(1300);
            left_front_drive.setPower(0);
            left_back_drive.setPower(0);
            right_front_drive.setPower(0);
            right_back_drive.setPower(0);
            sleep(700);
            arm.setPower(-1.0);
            sleep(700);
            left_front_drive.setPower(1.0);
            left_back_drive.setPower(1.0);
            right_front_drive.setPower(-1.0);
            right_back_drive.setPower(-1.0);
            sleep(600);
            left_front_drive.setPower(1.0);
            left_back_drive.setPower(1.0);
            right_front_drive.setPower(1.0);
            right_back_drive.setPower(1.0);
            sleep(1000);
            break;
        }
    }
}
