package com.illumio.service;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.illumio.domain.DirectionType;
import com.illumio.domain.ProtocolType;
import com.illumio.domain.Rule;

/**
 * 
 * @author Thomas Francis
 * 
 * Implementation of the facade, where loading and rule loop can be done
 * 
 */
public class FireWallFacadeImpl implements FireWallFacade {

	/** The Constant DASH. */
	private static final String DASH = "-";
	
	/** The Constant PERIOD. */
	private static final String PERIOD = ",";
	
	/** The base rules for look up when packet is called*/
	private Set<Rule> rules;

	/**
	 * Will create instance of FireWallFacadeImpl with loading all rules from file.
	 *
	 * @param location the location
	 * @throws UnknownHostException the unknown host exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public FireWallFacadeImpl(String location) throws UnknownHostException, IOException {
		rules = new HashSet<>();
		loadRules(Files.readAllLines(Paths.get(location)));
	}

	/**
	 * Load rules.
	 *
	 * @param listOfRuleString the list of rule string
	 * @throws UnknownHostException the unknown host exception
	 */
	private void loadRules(List<String> listOfRuleString) throws UnknownHostException {
		for (String s : listOfRuleString) {
			this.rules.addAll(parseIntoArray(s));
		}
	}

	/**
	 * Parses the into array.
	 *
	 * @param s the s
	 * @return the list
	 * @throws UnknownHostException the unknown host exception
	 */
	private List<Rule> parseIntoArray(String s) throws UnknownHostException {
		return processPacket(s.split(PERIOD));
	}

	/**
	 * Process packet.
	 *
	 * @param ruleInfo the rule info
	 * @return the list
	 * @throws UnknownHostException the unknown host exception
	 */
	private List<Rule> processPacket(String[] ruleInfo) throws UnknownHostException {
		List<Rule> result = new ArrayList<>();
		if (ruleInfo[2].contains(DASH)) { //check is PORT contains "-"
			String[] ports = ruleInfo[2].split(DASH);
			int port1 = Integer.parseInt(ports[0]);
			int port2 = Integer.parseInt(ports[1]);
			Stream<Integer> portRange = IntStream.rangeClosed(port1, port2).boxed();
			if (ruleInfo[3].contains(DASH)) { //check is IP address contains "-"
				for (int port : portRange.collect(toList())) {
					processIpAddress(ruleInfo, result, port);
				}
			} else {
				// we only process for range of the port
				return portRange.map(port -> mapToRuleObject(ruleInfo[0], ruleInfo[1], port, ruleInfo[3]))
								.collect(toList());
			}
		} else if (ruleInfo[3].contains(DASH)) { //check if the IP address contains "-" irrespective of PORT state
			processIpAddress(ruleInfo, result, Integer.parseInt(ruleInfo[2]));
		} else {
			result.add(mapToRuleObject(ruleInfo[0], ruleInfo[1], Integer.parseInt(ruleInfo[2]), ruleInfo[3]));
		}
		return result;
	}

	/**
	 * Process IP address.
	 *
	 * @param ruleInfo the rule info
	 * @param result the result
	 * @param port the port
	 * @throws UnknownHostException the unknown host exception
	 */
	private void processIpAddress(String[] ruleInfo, List<Rule> result, int port) throws UnknownHostException {
		String[] ipz = ruleInfo[3].split(DASH);
		long startIp = convertIpToLong(ipz[0]);
		long endIp = convertIpToLong(ipz[1]);
		for (long ip : LongStream.rangeClosed(startIp, endIp).boxed().collect(toList())) {
			result.add(mapToRuleObject(ruleInfo[0], ruleInfo[1], port, getIpAddressFromLong(ip)));
		}
	}

	/**
	 * Ip to long.
	 *
	 * credits : https://gist.github.com/madan712/6651967
	 * @param ipAddress the ip address
	 * @return the long
	 * @throws UnknownHostException the unknown host exception
	 */
	private long convertIpToLong(String ipAddress) throws UnknownHostException {
		long result = 0;
		for (byte octet : InetAddress.getByName(ipAddress).getAddress()) {
			result <<= 8;
			result |= octet & 0xff;
		}
		return result;
	}

	/**
	 * Gets the string format of the IP in Long
	 *
	 * credits : https://gist.github.com/madan712/6651967
	 * @param ip the Long
	 * @return the IP as String
	 */
	private final String getIpAddressFromLong(final long ip) {
		return String.format("%d.%d.%d.%d", 
				(ip >>> 24) & 0xff,
				(ip >>> 16) & 0xff, 
				(ip >>> 8) & 0xff,
				(ip) & 0xff);
	}

	/**
	 * Map to rule object.
	 *
	 * @param direction the direction
	 * @param protocolType the protocol type
	 * @param port the port
	 * @param ip the String ip
	 * @return the rule
	 */
	private Rule mapToRuleObject(String direction, String protocolType, int port, String ip) {
		return new Rule(DirectionType.valueOf(direction), ProtocolType.valueOf(protocolType), port, ip);
	}

	/**
	 * Check if valid packet
	 * @param Rule rule
	 * @return boolean
	 */
	@Override
	public boolean checkIfValidPacket(Rule rule) {
		return this.rules.contains(rule);
	}

}
