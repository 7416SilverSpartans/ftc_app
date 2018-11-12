package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Driver Op", group="Linear Opmode")
public class DriverOP_7416 extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left_drive = null;
    private DcMotor right_drive = null;
    private DcMotor lift = null;
    private Servo dropper = null;
    private DcMotor sweeper = null;
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        left_drive = hardwareMap.get(DcMotor.class, "left_drive");
        right_drive = hardwareMap.get(DcMotor.class, "right_drive");
        lift = hardwareMap.get(DcMotor.class, "lift");
        dropper = hardwareMap.get(Servo.class, "dropper");
        sweeper = hardwareMap.get(DcMotor.class, "sweeper");

        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        right_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        left_drive.setDirection(DcMotorSimple.Direction.FORWARD);
        sweeper.setDirection(DcMotorSimple.Direction.FORWARD);
        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.left_stick_y != 0) {
                left_drive.setPower(Range.clip(gamepad1.left_stick_y, -1.0, 1.0));
            } else {
                left_drive.setPower(0);
            }
            if (gamepad1.right_stick_y != 0) {
                right_drive.setPower(Range.clip(gamepad1.right_stick_y, -1.0, 1.0));
            } else {
                right_drive.setPower(0);
            }
            if (gamepad2.right_stick_y != 0) {
                lift.setPower(Range.clip(gamepad2.right_stick_y, -1.0, 1.0));
            }
            //will need to fix once we see what is wrong. works but numbers for position will need to change.
            if (gamepad2.a) {
                dropper.setPosition(1.0);
                dropper.setPosition(0.0);
            }
            if (gamepad2.b) {
                sweeper.setPower(1.0);
            } else if (gamepad2.b == false){
                sweeper.setPower(0);
            }
            if (gamepad2.x) {
                sweeper.setPower(-1.0);
            } else if (gamepad2.x == false) {
                sweeper.setPower(0);
            }
        }
    }
}
