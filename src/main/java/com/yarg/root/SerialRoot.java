package com.yarg.root;

import com.fazecast.jSerialComm.SerialPort;

public class SerialRoot {

	public static void main(String[] args) {

		SerialPort[] serialPorts = SerialPort.getCommPorts();
		for (SerialPort serialPort : serialPorts) {
			System.out.println(serialPort.getPortDescription());
		}
	}

}
