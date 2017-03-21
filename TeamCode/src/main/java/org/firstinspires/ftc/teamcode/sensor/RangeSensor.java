package org.firstinspires.ftc.teamcode.sensor;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cCompassSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RangeSensor
{
    I2cDevice device;
    I2cDeviceSynch synch;

    public RangeSensor(HardwareMap hwMap, String name, int address) {
        device = hwMap.i2cDevice.get(name);



        synch = new I2cDeviceSynchImpl(device, I2cAddr.create7bit(address), false);

        synch.resetDeviceConfigurationForOpMode();

        I2cDeviceSynch.ReadWindow window = new I2cDeviceSynch.ReadWindow(ModernRoboticsI2cRangeSensor.Register.FIRST.bVal,
                ModernRoboticsI2cRangeSensor.Register.LAST.bVal - ModernRoboticsI2cRangeSensor.Register.FIRST.bVal + 1,
                I2cDeviceSynch.ReadMode.REPEAT);

        synch.setReadWindow(window);

        synch.engage();

    }

    public int rawUltrasonic() {
        byte[] cache = synch.read(4, 2);
        return cache[0] & 0xFF;
    }

    public int rawOptical() {
        byte[] cache = synch.read(4, 2);
        return cache[1] & 0xFF;
    }

    private double cmFromOptical(int opticalReading) {
        double pParam = -1.02001;
        double qParam = 0.00311326;
        double rParam = -8.39366;
        int sParam = 10;

        if (opticalReading < sParam)
            return 0;
        else
            return pParam * Math.log(qParam * (rParam + opticalReading));
    }

    public double cmOptical() {
        return cmFromOptical(rawOptical());
    }

    public double cmUltrasonic() {
        return rawUltrasonic();
    }

    public double getDistance(DistanceUnit unit)
    {
        double cmOptical = cmOptical();
        double cm = (cmOptical > 0) ? cmOptical : cmUltrasonic();
        return unit.fromUnit(DistanceUnit.CM, cm);
    }

    public void close() {
        device.close();
        synch.close();
    }
}