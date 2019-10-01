package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Set;

import static java.lang.Math.abs;

@Disabled
@TeleOp(name="SoldiersTele2", group="Linear Opmode")

public class SoldiersTele2 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor LeftFront = null;
    private DcMotor RightFront = null;
    private DcMotor LeftBack = null;
    private DcMotor RightBack = null;
    private DcMotor Lift = null;
    private DcMotor Hook = null;
    private DcMotor Extend = null;
    private DcMotor Bucket = null;
    private Servo Sweeper = null;
    private Servo Sweeper2 = null;


    BNO055IMU imu;

    Orientation angles;




    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables.
        LeftFront  = hardwareMap.get(DcMotor.class, "LeftFront");
        RightFront = hardwareMap.get(DcMotor.class, "RightFront");
        LeftBack = hardwareMap.get(DcMotor.class, "LeftBack");
        RightBack = hardwareMap.get(DcMotor.class, "RightBack");
        Lift = hardwareMap.get(DcMotor.class, "Lift");
        Hook = hardwareMap.get(DcMotor.class, "Hook");
        Extend = hardwareMap.get(DcMotor.class,"Extend");
        Bucket = hardwareMap.get(DcMotor.class, "Bucket");
        Sweeper = hardwareMap.get(Servo.class, "Sweeper");
        Sweeper2 = hardwareMap.get(Servo.class, "Sweeper2");


        double LFpower = 0;
        double RFpower = 0;
        double LBpower = 0;
        double RBpower = 0;
        double VirtualLeftStickY;
        double VirtualLeftStickX;
        double LeftStickY = 0;
        double LeftStickX = 0;
        double ThedaR = 0;
        double ThedaF = 0;
        double Yaw = 0;
        double Magnitude = 0;

        runtime.reset();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");



        BNO055IMU.Parameters imuparameters = new BNO055IMU.Parameters();
        imuparameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuparameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imuparameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        imuparameters.loggingEnabled = true;
        imuparameters.loggingTag = "IMU";
        imuparameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        sleep(100);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {






            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            LeftStickX = gamepad1.left_stick_x;
            LeftStickY = gamepad1.left_stick_y;

            Yaw = (-angles.firstAngle) * Math.PI / 180;

            ThedaF = Math.atan2(LeftStickY, LeftStickX);
            ThedaR = ThedaF - Yaw;

            Magnitude = Math.sqrt(LeftStickX * LeftStickX + LeftStickY * LeftStickY);

            VirtualLeftStickX = Math.cos(ThedaR) * Magnitude;
            VirtualLeftStickY = Math.sin(ThedaR) * Magnitude;


            telemetry.addData("LeftStickX ", VirtualLeftStickX);
            telemetry.addData("LeftStickY ", VirtualLeftStickY);
            telemetry.addData("Heading ", angles.firstAngle);
            telemetry.addData("Roll ", angles.secondAngle);
            telemetry.addData("Pitch ", angles.thirdAngle);
            telemetry.addData("Theta F ", ThedaF * 180 / Math.PI);
            telemetry.addData("Theta R ", ThedaR * 180 / Math.PI);
            telemetry.addData("VirtualLeftStickX ", VirtualLeftStickX);
            telemetry.addData("VirtualLeftStickY ", VirtualLeftStickY);


            LFpower = 0;
            LFpower = LFpower + VirtualLeftStickY;
            LFpower = LFpower - VirtualLeftStickX;
            LFpower = LFpower - gamepad1.right_stick_x;

            RFpower = 0;
            RFpower = RFpower - VirtualLeftStickY;
            RFpower = RFpower - VirtualLeftStickX;
            RFpower = RFpower - gamepad1.right_stick_x;

            LBpower = 0;
            LBpower = LBpower + VirtualLeftStickY;
            LBpower = LBpower + VirtualLeftStickX;
            LBpower = LBpower - gamepad1.right_stick_x;

            RBpower = 0;
            RBpower = RBpower - VirtualLeftStickY;
            RBpower = RBpower + VirtualLeftStickX;
            RBpower = RBpower - gamepad1.right_stick_x;

            LFpower = Range.clip(LFpower, -1, 1);
            LeftFront.setPower(LFpower);

            RFpower = Range.clip(RFpower, -1, 1);
            RightFront.setPower(RFpower);

            LBpower = Range.clip(LBpower, -1, 1);
            LeftBack.setPower(LBpower);

            RBpower = Range.clip(RBpower, -1, 1);
            RightBack.setPower(RBpower);



            if (gamepad1.x){
                imu.initialize(imuparameters);
            }





            //Extend arm
            Extend.setPower(gamepad2.right_stick_y*-.75);

            //Activate sweeper
            if (gamepad2.left_bumper){
                Sweeper.setPosition(.2);
                Sweeper2.setPosition(.8);
            }
            else if (gamepad2.right_bumper){
                Sweeper.setPosition(0.8);
                Sweeper2.setPosition(0.2);
            }
            else if (gamepad2.x){
                Sweeper.setPosition(.5);
                Sweeper2.setPosition(.5);
            }

            //Move Lift
            Lift.setPower(gamepad2.left_stick_y * -0.75);

            //Lift Hook
            if (gamepad2.dpad_up) {
                Hook.setPower(1);
            }
            else if (gamepad2.dpad_down){
                Hook.setPower(-1);
            }
            else {
                Hook.setPower(0);
            }

            //Move Bucket
            if (gamepad2.y){
                Bucket.setPower(0.25);
            }
            else if (gamepad2.a){
                Bucket.setPower(-0.25);
            }
            else {
                Bucket.setPower(0);
            }







        }
    }
}


