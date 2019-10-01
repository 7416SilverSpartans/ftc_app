package org.firstinspires.ftc.robotcontroller.external.samples;




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


@TeleOp(name="SoldiersTeleOp1", group="Linear Opmode")

public class SoldiersMechTeleOp1 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    BNO055IMU imu;

    Orientation angles;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables.
        frontLeft  = hardwareMap.get(DcMotor.class, "Front left");
        frontRight = hardwareMap.get(DcMotor.class, "Front right");
        backLeft = hardwareMap.get(DcMotor.class, "Back left");
        backRight = hardwareMap.get(DcMotor.class, "Back right");

        //Lets user know the robot is finished initializing
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        runtime.reset();

        //I will figure out how all of this works later.
        //Looks like stuff is being initialized twice and that's not good but...
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
        imu.initialize(imuparameters);
        sleep(100);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;

            if(gamepad1.a){
                frontLeft.setPower(1.0);
            }
            if(gamepad1.b){
                frontRight.setPower(1.0);
            }
            if(gamepad1.x){
                backLeft.setPower(1.0);
            }
            if(gamepad1.y){
                backRight.setPower(1.0);
            }
        }
    }
}



