package com.illumio.service;

import com.illumio.domain.Rule;

// TODO: Auto-generated Javadoc
/**
 * The Interface FireWallFacade.
 */
public interface FireWallFacade {

//	public void loadRules(List<String> rules) throws UnknownHostException;

	/**
 * Check if valid package.
 *
 * @param rule the rule
 * @return true, if successful
 */
public boolean checkIfValidPacket(Rule rule);
	
}
