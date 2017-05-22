package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class LanePushbot
{
    /* Public OpMode members. */
    public DcMotor  leftMotor   = null;
    public DcMotor  rightMotor  = null;
    public DcMotor  sweeperMotor    = null;
    public DcMotor  catapultMotor= null;
    public Servo    pushRight   = null;
    public Servo    ballLifter  = null;
    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public LanePushbot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor   = hwMap.dcMotor.get("left_drive");
        rightMotor  = hwMap.dcMotor.get("right_drive");
        sweeperMotor = hwMap.dcMotor.get("sweeper");
        catapultMotor = hwMap.dcMotor.get("catapult");

        //armMotor    = hwMap.dcMotor.get("left_arm");
      //  pushRight   = hwMap.servo.get("pusher");
      //  pushRight   = hwMap.servo.get("lifter");
        //rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        //leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        //armMotor.setPower(0);



        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        //leftClaw = hwMap.servo.get("left_hand");
       //rightClaw = hwMap.servo.get("right_hand");
        //leftClaw.setPosition(MID_SERVO);
        //rightClaw.setPosition(MID_SERVO);
        pushRight   = hwMap.servo.get("pusher");
        ballLifter = hwMap.servo.get("lifter");

        pushRight.scaleRange(0.05, 0.5);
        ballLifter.scaleRange(0.05, 2.0 / 3);
        pushRight.setPosition(1);
        ballLifter.setPosition(0);


    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }

    /**
     * This file illustrates the concept of driving a path based on time.
     * It uses the common Pushbot hardware class to define the drive on the robot.
     * The code is structured as a LinearOpMode
     *
     * The code assumes that you do NOT have encoders on the wheels,
     *   otherwise you would use: PushbotAutoDriveByEncoder;
     *
     *   The desired path in this example is:
     *   - Drive forward for 3 seconds
     *   - Spin right for 1.3 seconds
     *   - Drive Backwards for 1 Second
     *   - Stop and close the claw.
     *
     *  The code is written in a simple form with no optimizations.
     *  However, there are several ways that this type of sequence could be streamlined,
     *
     * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
     * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
     */

    @Autonomous(name="Pushbot: Auto Drive By Time", group="Pushbot")

    public static class LaneProgramming extends LinearOpMode {

        /* Declare OpMode members. */
        LanePushbot robot   = new LanePushbot();   // Use a Pushbot's hardware
        private ElapsedTime     runtime = new ElapsedTime();


        static final double     FORWARD_SPEED = 0.6;
        static final double     TURN_SPEED    = 0.5;

        @Override
        public void runOpMode() throws InterruptedException {

            /*
             * Initialize the drive system variables.
             * The init() method of the hardware class does all the work here
             */
            robot.init(hardwareMap);

            // Send telemetry message to signify robot waiting;
            telemetry.addData("Status", "Ready to run");    //
            telemetry.update();

            // Wait for the game to start (driver presses PLAY)
            waitForStart();

            // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

            // Step 1:  Drive forward for 3 seconds
            robot.leftMotor.setPower(FORWARD_SPEED);
            robot.rightMotor.setPower(FORWARD_SPEED);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 3.0)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
                idle();
            }

            // Step 2:  Spin right for 1.3 seconds
            robot.leftMotor.setPower(TURN_SPEED);
            robot.rightMotor.setPower(-TURN_SPEED);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 1.3)) {
                telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
                idle();
            }

            // Step 3:  Drive Backwards for 1 Second
            robot.leftMotor.setPower(FORWARD_SPEED);
            robot.rightMotor.setPower(FORWARD_SPEED);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 1.0)) {
                telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
                idle();
            }

            // Step 4:  Stop and close the claw.
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
            //robot.leftClaw.setPosition(1.0);
            //robot.rightClaw.setPosition(0.0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
            idle();
        }
    }
}

