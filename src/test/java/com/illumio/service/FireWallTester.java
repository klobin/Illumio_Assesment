package com.illumio.service;

import java.io.IOException;
import java.net.UnknownHostException;

public class FireWallTester {
	public static void main(String[] args) throws UnknownHostException, IOException {
		FireWall fw = new FireWall("src/main/resources/data.csv");
		System.out.println(fw.accept_packet("inbound", "tcp", 80, "192.168.1.2"));
		System.out.println(fw.accept_packet("inbound", "udp", 53, "192.168.2.1"));
		System.out.println(fw.accept_packet("outbound", "tcp", 10234, "192.168.10.11"));
		System.out.println(fw.accept_packet("inbound", "tcp", 81, "192.168.1.2"));
		System.out.println(fw.accept_packet("inbound", "udp", 24, "52.12.48.92"));
	}
}
