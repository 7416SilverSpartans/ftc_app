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
    private DcMotor left_front_drive = null;
    private DcMotor right_front_drive = null;
    private DcMotor left_back_drive = null;
    private DcMotor right_back_drive = null;
    private DcMotor arm = null;
    private Servo left_door = null;
    private Servo right_door = null;
    private DcMotor lift = null;
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        left_front_drive = hardwareMap.get(DcMotor.class, "left_front_drive");
        right_front_drive = hardwareMap.get(DcMotor.class, "right_front_drive");
        left_back_drive = hardwareMap.get(DcMotor.class, "left_back_drive");
        right_back_drive = hardwareMap.get(DcMotor.class, "right_back_drive");
        arm = hardwareMap.get(DcMotor.class, "aarm");
        left_door = hardwareMap.get(Servo.class, "left_door");
        right_door = hardwareMap.get(Servo.class, "right_door");

        lift = hardwareMap.get(DcMotor.class, "lift");

        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        right_front_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        right_back_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        left_front_drive.setDirection(DcMotorSimple.Direction.FORWARD);
        left_back_drive.setDirection(DcMotorSimple.Direction.FORWARD);
        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.left_stick_y != 0) {
                left_front_drive.setPower(Range.clip(gamepad1.left_stick_y, -1.0, 1.0));
                left_back_drive.setPower(Range.clip(gamepad1.left_stick_y, -1.0, 1.0));
            } else {
                left_front_drive.setPower(0);
                left_back_drive.setPower(0);
            }
            if (gamepad1.right_stick_y != 0) {
                right_front_drive.setPower(Range.clip(gamepad1.right_stick_y, -1.0, 1.0));
                right_back_drive.setPower(Range.clip(gamepad1.right_stick_y, -1.0, 1.0));
            } else {
                right_front_drive.setPower(0);
                right_back_drive.setPower(0);
            }
            if (gamepad2.right_stick_y >= 0.5) {
                arm.setPower(Range.clip(gamepad2.right_stick_y, -1.0, 1.0));
            } else {
                arm.setPower(0);
            }
            //will need to fix once we see what is wrong. works but numbers for position will need to change.
            if (gamepad2.a) {
                left_door.setPosition(1.0);
                right_door.setPosition(0);
            } else {
                left_door.setPosition(0);
                right_door.setPosition(1.0);
            }
            if (gamepad2.b) {
                lift.setPower(1.0);
            } else if (!gamepad2.b){
                lift.setPower(0);
            }
            if (gamepad2.x) {
                lift.setPower(-1.0);
            } else if (!gamepad2.x) {
                lift.setPower(0);
            }
        }
    }
}
