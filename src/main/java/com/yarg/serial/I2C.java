package com.yarg.serial;

/**
 * Extend the functionality of I2CDevice. This interface is intended to be used in conjunction with
 * I2CDevice to provide initialization and shutdown helpers around the I2CDevice instances being used.
 * @author ben
 *
 */
public interface I2C {

	/**
	 * Get the current serial device configuration.
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
	 * Open the serial device connection.
	 */
	public void openConnection();

	/**
	 * Close the serial device connection.
	 */
	public void closeConnection();
}
