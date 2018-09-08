package com.yarg.root;

import java.math.BigInteger;

import com.yarg.usbi2c.USB2I2C;

public class SerialRoot {

	// PWM doc: https://cdn-shop.adafruit.com/datasheets/PCA9685.pdf
	// USB to i2c module: http://www.robot-electronics.co.uk/products/communication-adapters/interface-modules/usb-to-i2c-interface-module.html

	public static void main(String[] args) {

		USB2I2C.serializeAllAttachedDevices();

		USB2I2C device = new USB2I2C();
		String output = device.serializeDevice();
		System.out.println(output);

		device.setReadTimeout(2000);
		device.setWriteTimeout(2000);
		device.openConnection();

		byte[] testCommand = new byte[4];
		testCommand[0] = 0x5A;
		testCommand[1] = 0x01;
		testCommand[2] = 0x00;
		testCommand[3] = 0x00;

		byte[] readBytes = device.sendBytes(testCommand, 1);

		System.out.println("USB-I2C Firmware version: " + readBytes[0]);


		//		System.out.println("BINARY STRING (15) " + Integer.toBinaryString(15));
		//		byte test = 0b00000110;
		//		System.out.println("BINARY STRING (test) " + test);
		//		String s1 = String.format("%8s", Integer.toBinaryString(test & 0xFF)).replace(' ', '0');
		//		System.out.println(s1);

		byte starting = (byte) 0b10000001;

		for (int i = 0; i < 128; i++) {

			byte add = (byte)(i << 1);

			byte[] servoCommand = new byte[4];
			servoCommand[0] = 0x55;
			servoCommand[1] = (byte) (starting | add);
			servoCommand[2] = 0x00;
			servoCommand[3] = 0x01;

			readBytes = device.sendBytes(servoCommand, 1);
			System.out.println("read bytes LOOP: i = " + i + " address: " + Integer.toBinaryString(servoCommand[1] & 0xFF).replace(' ', '0') + " read: " + (int)readBytes[0]);
		}

		// Try read
		byte[] servoCommand = new byte[4];
		servoCommand[0] = 0x55;
		servoCommand[1] = (byte)0b10000001;
		servoCommand[2] = 0x00;
		servoCommand[3] = 0x01;

		readBytes = device.sendBytes(servoCommand, 1);
		System.out.println("read bytes A: address: " + Integer.toBinaryString(servoCommand[1] & 0xFF).replace(' ', '0') + " read: " + (int)readBytes[0]);

		// Try write
		servoCommand = new byte[5];
		servoCommand[0] = 0x55;
		servoCommand[1] = (byte)0b10000000;
		servoCommand[2] = 0x00;
		servoCommand[3] = 0x01;
		servoCommand[4] = 0x00;

		readBytes = device.sendBytes(servoCommand, 1);
		System.out.println("post write read bytes: " + readBytes[0]);

		servoCommand = new byte[4];
		servoCommand[0] = 0x55;
		servoCommand[1] = (byte)0b10000001;
		servoCommand[2] = 0x00;
		servoCommand[3] = 0x01;

		readBytes = device.sendBytes(servoCommand, 1);
		System.out.println("read bytes B: " + (int)readBytes[0]);

		servoCommand = new byte[5];
		servoCommand[0] = 0x55;
		servoCommand[1] = (byte)0b10000000;
		servoCommand[2] = 0x00;
		servoCommand[3] = 0x01;
		servoCommand[4] = (byte) 17;

		readBytes = device.sendBytes(servoCommand, 1);
		System.out.println("post write read bytes: " + readBytes[0]);

		servoCommand = new byte[4];
		servoCommand[0] = 0x55;
		servoCommand[1] = (byte)0b10000001;
		servoCommand[2] = 0x00;
		servoCommand[3] = 0x01;

		readBytes = device.sendBytes(servoCommand, 1);
		System.out.println("read bytes C: " + (int)readBytes[0]);

		//		for (int i = 0; i < 256; i++) {
		//			servoCommand[1] = (byte)i;
		//			try {
		//
		//
		//				break;
		//			} catch (Exception e) {
		//				System.out.println("No device at address: " + i);
		//			}
		//		}

		device.closeConnection();

		// Logging

		//		System.out.println("\n\n============");
		//		System.out.println("" + 0x10);
		//
		//		System.out.println("\n\n============");
		//		int hexValue = 15;
		//		System.out.println("HEX STRING (15) " + Integer.toHexString(hexValue));
		//		System.out.println("BINARY STRING (15) " + Integer.toBinaryString(hexValue));
		//
		//		Byte decoded = Byte.decode("0xf");
		//		System.out.println(decoded.byteValue());
		//
		//		System.out.println("" + 0xFF);
		//
		//		short a = 0xFF;
		//		System.out.println("a = " + a);
		//		System.out.println("BINARY STRING (0xFF) " + Integer.toBinaryString(0xFF));
		//		System.out.println("BINARY STRING (a) " + Integer.toBinaryString(a));
		//
		//		byte test = (byte) 0xFF;
		//		System.out.println("BINARY STRING (test) " + test);
		//		String s1 = String.format("%8s", Integer.toBinaryString(test & 0xFF));//.replace(' ', '0');
		//		System.out.println(s1);
	}

	public String hexToBin(String s) {
		return new BigInteger(s, 16).toString(2);
	}
}
