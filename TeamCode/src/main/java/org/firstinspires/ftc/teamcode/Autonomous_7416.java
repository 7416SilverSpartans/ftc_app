package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

import java.util.ArrayList;
import java.util.List;


@Autonomous(name="Autonomous 7416", group="Autonomous Opmode")
public class Autonomous_7416 extends LinearOpMode {

    VuforiaLocalizer vuforia;
    public static final String TAG = "Vuforia Navigation Sample";
    OpenGLMatrix lastLocation = null;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left_drive = null;
    private DcMotor right_drive = null;
    private DcMotor lift = null;
    private Servo dropper = null;
    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AW5cuAv/////AAAAGYwNILq1OU9al3sfWEE5bcaICmP0eurTfet/MBdtqccaDPUeboEWlVB11qhifLo+6Co5JJ/QCme2qocAtIGwiqen3MPNv3knzWjYnCR9bEAvn7lUb4Mq/lSPZf6zT/Lm2jRriRM9K+/YJdzJbtlj991eAI8jg0PqjLnS0WoT2keQ6C64PjBEqzOVA9QeKciad4SeX//YwTYi/V2ffB2ukVLJqTBu/bFUnGScYTfL6w8jbkMqETQG5bV00DasZXa7NEVgIbP5Zq/fo6wApIFpVomMlLoHc6Aqm/CSVjjithl63B5cnvMMGEVtVgKRUkxwPJHVaeerebihIsfzriHPy9/isYEdUC4IHRcJ5PEnbZVI";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        VuforiaTrackables stonesAndChips = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable redTarget = stonesAndChips.get(0);
        redTarget.setName("BluePerimeter");  // Stones
        VuforiaTrackable blueTarget  = stonesAndChips.get(1);
        blueTarget.setName("RedPerimeter");  // Chips
        VuforiaTrackable frontWall = stonesAndChips.get(2);
        frontWall.setName("FrontPerimeter");  // Stones
        VuforiaTrackable backWall  = stonesAndChips.get(3);
        backWall.setName("BackPerimeter");  // Chips+
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(stonesAndChips);

        float mmPerInch        = 25.4f;
        float mmBotWidth       = 18 * mmPerInch;            // ... or whatever is right for your robot
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels

        OpenGLMatrix redTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        redTarget.setLocation(redTargetLocationOnField);
        RobotLog.ii(TAG, "Red Target=%s", format(redTargetLocationOnField));

        /*
         * To place the Stones Target on the Blue Audience wall:
         * - First we rotate it 90 around the field's X axis to flip it upright
         * - Finally, we translate it along the Y axis towards the blue audience wall.
         */
        OpenGLMatrix blueTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        blueTarget.setLocation(blueTargetLocationOnField);
        RobotLog.ii(TAG, "Blue Target=%s", format(blueTargetLocationOnField));

        OpenGLMatrix frontWallLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        redTarget.setLocation(frontWallLocationOnField);
        RobotLog.ii(TAG, "Front Target=%s", format(frontWallLocationOnField));

        OpenGLMatrix backWallLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        redTarget.setLocation(backWallLocationOnField);
        RobotLog.ii(TAG, "Front Target=%s", format(backWallLocationOnField));

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(TAG, "phone=%s", format(phoneLocationOnRobot));

        ((VuforiaTrackableDefaultListener)redTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)blueTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        stonesAndChips.activate();

        while (opModeIsActive()) {
            boolean found = false;
            String name = "";
            for (VuforiaTrackable trackable : allTrackables) {
                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible() ? "Visible" : "Not Visible");    //

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                if (!((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    left_drive.setPower(0.2);
                    right_drive.setPower(-0.2);
                } else {
                    found = true;
                    name = trackable.getName();
                    break;
                }
            }
            if (lastLocation != null) {
                //  RobotLog.vv(TAG, "robot=%s", format(lastLocation));
                String coordinatesStr = format(lastLocation).replace("{EXTRINSIC XYZ 0 67 45} {-345.67 76.66 -123.25} ", "");
                String[] coordinatesArray = coordinatesStr.split("[ ]");
                String x = coordinatesArray[5].replace("{", "");
                String y = coordinatesArray[6];
                String z = coordinatesArray[7].replace("}", "");
                //for (int i = 0; i <= xyz_unparsed.length; i++) {
                   // xyz[i] = Integer.parseInt(xyz_unparsed[i]);
                //}

                telemetry.addData("x", x);
                telemetry.addData("y", y);
                telemetry.addData("z", z);

                left_drive = hardwareMap.get(DcMotor.class, "left_drive");
                right_drive = hardwareMap.get(DcMotor.class, "right_drive");
                left_drive.setDirection(DcMotorSimple.Direction.FORWARD);
                right_drive.setDirection(DcMotorSimple.Direction.REVERSE);
                if (found) {
                    if (name.equals("BluePerimeter")) {
                        left_drive.setPower(1.0);
                        right_drive.setPower(1.0);
                        sleep(500);
                        left_drive.setPower(0);
                        right_drive.setPower(0);
                    }
                }
            } else {
                telemetry.addData("Pos", "Unknown");
            }
            telemetry.update();
        }
    }
    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }
}
