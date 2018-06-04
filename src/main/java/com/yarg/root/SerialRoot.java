package com.yarg.root;

import java.math.BigInteger;

import com.fazecast.jSerialComm.SerialPort;

public class SerialRoot {

	// PWM doc: https://cdn-shop.adafruit.com/datasheets/PCA9685.pdf
	// USB to i2c module: http://www.robot-electronics.co.uk/products/communication-adapters/interface-modules/usb-to-i2c-interface-module.html

	public static void main(String[] args) {

		SerialPort[] serialPorts = SerialPort.getCommPorts();
		for (SerialPort serialPort : serialPorts) {

			String output = String.format("port name: %s%nport description: %s%nbaud rate: %d%ncts: %b%ndsr: %b%nflow control settings: %d%nsystem port name: %s",
					serialPort.getDescriptivePortName(),
					serialPort.getPortDescription(),
					serialPort.getBaudRate(),
					serialPort.getCTS(),
					serialPort.getDSR(),
					serialPort.getFlowControlSettings(),
					serialPort.getSystemPortName());
			System.out.println(output);
			System.out.println("---");
		}

		SerialPort serial2i2c = SerialPort.getCommPort("cu.usbserial-A60129CO");
		String output = String.format("port name: %s%nport description: %s%nbaud rate: %d%ncts: %b%ndsr: %b%nflow control settings: %d%nsystem port name: %s",
				serial2i2c.getDescriptivePortName(),
				serial2i2c.getPortDescription(),
				serial2i2c.getBaudRate(),
				serial2i2c.getCTS(),
				serial2i2c.getDSR(),
				serial2i2c.getFlowControlSettings(),
				serial2i2c.getSystemPortName());
		System.out.println(output);


		if (true) {

			// The COM port should be set up for 19200 baud, 8 data bits, no parity and two stop bits
			boolean portOpened = serial2i2c.openPort();
			serial2i2c.setComPortParameters(19200, 8, SerialPort.TWO_STOP_BITS, SerialPort.NO_PARITY);

			System.out.println("write timeout: " + serial2i2c.getWriteTimeout());
			System.out.println("read timeout: " + serial2i2c.getReadTimeout());

			serial2i2c.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 500, 500);

			System.out.println("write timeout: " + serial2i2c.getWriteTimeout());
			System.out.println("read timeout: " + serial2i2c.getReadTimeout());

			byte[] testCommand = new byte[4];
			testCommand[0] = 0x5A;
			testCommand[1] = 0x01;
			testCommand[2] = 0x00;
			testCommand[3] = 0x00;


			int resultOfWrite = serial2i2c.writeBytes(testCommand, testCommand.length);
			byte[] readBytes = new byte[1];
			serial2i2c.readBytes(readBytes, readBytes.length);

			System.out.println("RESULT: " + readBytes[0]);

			serial2i2c.closePort();
		}

		// Logging

		System.out.println("\n\n============");
		System.out.println("" + 0x10);

		System.out.println("\n\n============");
		int hexValue = 15;
		System.out.println("HEX STRING (15) " + Integer.toHexString(hexValue));
		System.out.println("BINARY STRING (15) " + Integer.toBinaryString(hexValue));

		Byte decoded = Byte.decode("0xf");
		System.out.println(decoded.byteValue());

		System.out.println("" + 0xFF);

		short a = 0xFF;
		System.out.println("a = " + a);
		System.out.println("BINARY STRING (0xFF) " + Integer.toBinaryString(0xFF));
		System.out.println("BINARY STRING (a) " + Integer.toBinaryString(a));

		byte test = (byte) 0xFF;
		System.out.println("BINARY STRING (test) " + test);
		String s1 = String.format("%8s", Integer.toBinaryString(test & 0xFF));//.replace(' ', '0');
		System.out.println(s1);
	}

	public String hexToBin(String s) {
		return new BigInteger(s, 16).toString(2);
	}
}
