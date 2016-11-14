package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="movebott: Teleop weirdtank", group="movebot")
//@Disabled
public class weirdteleop extends OpMode{

    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
    // could also use HardwarePushbotMatrix class.


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "you are running a weird code and if you are bored than do something else also if you want to know how to controll this its left and right bumper and triggrt it might trigger you but its a cool idea");    //
        updateTelemetry(telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        boolean left;
        boolean right;
        double backleft;
        double backright;
        double leftPower =.7;
        double rightPower =.7;
        double backLPower = -.7;
        double backRPower = -.7;

        left = gamepad1.left_bumper;
        right = gamepad1.right_bumper;
        backleft = gamepad1.left_trigger;
        backright = gamepad1.right_trigger;
        robot.leftMotor.setPower(0.7);
        robot.rightMotor.setPower(0.7);
        robot.leftMotor.setPower(backLPower);
        robot.leftMotor.setPower(backRPower);
    }


        // Send telemetry message to signify robot running;

    /*
     * Code to run ONCE after the driver hits STOP
     */
    public void stop() {

    }
}