package com.yarg.usbi2c;

import com.fazecast.jSerialComm.SerialPort;
import com.yarg.serial.I2C;

/**
 * Implementation of: USB-I2C serial device module for communicating with I2C devices over a USB connection.
 *
 * Product Page: http://www.robot-electronics.co.uk/products/communication-adapters/interface-modules/usb-to-i2c-interface-module.html
 * Technical Documenatation: http://www.robot-electronics.co.uk/htm/usb_i2c_tech.htm
 *
 * @author Ben Yarger <benjamin.yarger@gmail.com>
 *
 */
public class USB2I2C implements I2C {

	private SerialPort usbi2c;
	private int readTimeout = 500;
	private int writeTimeout = 500;

	public USB2I2C() {
		usbi2c= SerialPort.getCommPort("cu.usbserial-A60129CO");
	}

	@Override
	public String serializeDevice() {
		String output = String.format("USB-I2C serial device%n-----------%nport name: %s%nport description: %s%nbaud rate: %d%ncts: %b%ndsr: %b%nflow control settings: %d%nsystem port name: %s",
				usbi2c.getDescriptivePortName(),
				usbi2c.getPortDescription(),
				usbi2c.getBaudRate(),
				usbi2c.getCTS(),
				usbi2c.getDSR(),
				usbi2c.getFlowControlSettings(),
				usbi2c.getSystemPortName());
		return output;
	}

	@Override
	public void setReadTimeout(int timeout) {
		readTimeout = timeout;
		usbi2c.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, readTimeout, writeTimeout);
	}

	@Override
	public void setWriteTimeout(int timeout) {
		writeTimeout = timeout;
		usbi2c.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, readTimeout, writeTimeout);
	}

	@Override
	public byte[] sendBytes(byte[] bytes, int bytesToReceive) {

		return sendBytes(bytes, bytes.length, bytesToReceive);
	}

	@Override
	public byte[] sendBytes(byte[] bytes, int numBytesToSend, int numBytesToReceive) {

		int resultOfWrite = usbi2c.writeBytes(bytes, numBytesToSend);
		if (resultOfWrite == -1 || resultOfWrite != numBytesToSend) {
			throw new IllegalStateException(String.format("Error occurred writing data to USB-I2C device. Expected to send %d bytes, but sent %d.", numBytesToSend, resultOfWrite));
		}
		byte[] readBytes = new byte[numBytesToReceive];
		int resultOfRead = usbi2c.readBytes(readBytes, readBytes.length);
		if (resultOfRead == -1 || resultOfRead != numBytesToReceive) {
			throw new IllegalStateException(String.format("Error occurred reading data from USB-I2C device. Expected to read %d bytes, but read %d.", numBytesToReceive, resultOfRead));
		}
		return readBytes;
	}

	@Override
	public void openConnection() {

		if (!usbi2c.openPort()) {
			throw new IllegalStateException("Unable to open port for USB-I2C device. Please check that the device is attached and attempt to serialize the device.");
		}
		usbi2c.setComPortParameters(19200, 8, SerialPort.TWO_STOP_BITS, SerialPort.NO_PARITY);
		usbi2c.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, readTimeout, writeTimeout);
	}

	@Override
	public void closeConnection() {

		if (!usbi2c.closePort())
		{
			throw new IllegalStateException("Unable to close port for USB-I2C device. Please check that the device is attached and was previously open.");
		}
	}

	/**
	 * Serialize all attached serial devices without instantiating any specific device.
	 */
	public static void serializeAllAttachedDevices() {

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
	}



}
