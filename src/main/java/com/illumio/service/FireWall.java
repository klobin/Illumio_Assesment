package com.illumio.service;

import java.io.IOException;
import java.net.UnknownHostException;

import com.illumio.domain.DirectionType;
import com.illumio.domain.ProtocolType;
import com.illumio.domain.Rule;

/**
 * The Class FireWallTester.
 */
public class FireWall {

	/** The fire wall facade. */
	private FireWallFacade fireWallFacade;

	/**
	 * Instantiates a new fire wall tester.
	 *
	 * @param location the location
	 * @throws UnknownHostException the unknown host exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public FireWall(String location) throws UnknownHostException, IOException {
		fireWallFacade = new FireWallFacadeImpl(location);
	}
	
	/**
	 * Accept packet.
	 *
	 * @param direction the direction
	 * @param protocolType the protocol type
	 * @param port the port
	 * @param ip the ip
	 * @return true, if successful
	 */
	public boolean accept_packet(String direction, String protocolType, int port, String ip) {
		return fireWallFacade.checkIfValidPacket(new Rule(DirectionType.valueOf(direction), ProtocolType.valueOf(protocolType), port, ip));
	}
	
}
