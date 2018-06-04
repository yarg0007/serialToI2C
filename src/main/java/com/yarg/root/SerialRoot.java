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

		device.openConnection();

		byte[] testCommand = new byte[4];
		testCommand[0] = 0x5A;
		testCommand[1] = 0x01;
		testCommand[2] = 0x00;
		testCommand[3] = 0x00;

		byte[] readBytes = device.sendBytes(testCommand, 1);

		System.out.println("RESULT: " + readBytes[0]);

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
