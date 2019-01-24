package com.illumio.service;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.UnknownHostException;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import com.illumio.domain.DirectionType;
import com.illumio.domain.ProtocolType;
import com.illumio.domain.Rule;

public class FireWallFacadeImplTest {

	@Test
	public void testLoadRulesWithOneRuleOutput() throws UnknownHostException, IOException {
		FireWallFacadeImpl facadeImpl = new FireWallFacadeImpl("src/main/resources/data.csv");
		assertThat(facadeImpl.checkIfValidPacket(new Rule(DirectionType.inbound, ProtocolType.tcp, 80, "192.168.1.2")),
				CoreMatchers.is(Boolean.TRUE));
	}

	@Test(expected = IOException.class)
	public void testLoadRulesForBadFilePath() throws UnknownHostException, IOException {
		new FireWallFacadeImpl("src/main/resources/data.jpg");
	}

	@Test(expected = UnknownHostException.class)
	public void testLoadRulesForInvalidIp() throws UnknownHostException, IOException {
		new FireWallFacadeImpl("src/main/resources/data-test.csv");
	}

}
