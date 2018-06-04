package com.yarg.serial;

public interface I2C {

	/**
	 * Output the current serial device configuration.
	 * @return String describing the serial device configuration.
	 */
	public String serializeDevice();

	/**
	 * Set the timeout for reading data from the I2C device.
	 * @param timeout Timeout in milliseconds.
	 */
	public void setReadTimeout(int timeout);

	/**
	 * Set the timeout for writing data to the I2C device.
	 * @param timeout Timeout in milliseconds.
	 */
	public void setWriteTimeout(int timeout);

	/**
	 * Send the byte array to the I2C device.
	 *
	 * @param bytes
	 *            Byte array to be sent.
	 * @param numBytesToReceive
	 *            The number of bytes expected to receive. This must match or
	 *            exceed the number of bytes that will be returned. If this
	 *            exceeds the number of bytes to be returned then you will have
	 *            to wait for the response timeout to occur before receiving
	 *            data back.
	 * @return Bytes returned from I2C device.
	 */
	public byte[] sendBytes(byte[] bytes, int numBytesToReceive);

	/**
	 * Send the byte array to the I2C device.
	 *
	 * @param bytes
	 *            Byte array to be sent.
	 * @param numBytesToSend
	 *            The number of bytes to send.
	 * @param bytesToReceive
	 *            The number of bytes expected to receive. This must match or
	 *            exceed the number of bytes that will be returned. If this
	 *            exceeds the number of bytes to be returned then you will have
	 *            to wait for the response timeout to occur before receiving
	 *            data back.
	 * @return Bytes returned from I2C device.
	 */
	public byte[] sendBytes(byte[] bytes, int numBytesToSend, int bytesToReceive);

	/**
	 * Open the serial device connection.
	 */
	public void openConnection();

	/**
	 * Close the serial device connection.
	 */
	public void closeConnection();
}
