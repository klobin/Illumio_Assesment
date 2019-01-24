package com.illumio.domain;

public class Rule {

	private DirectionType type;
	private ProtocolType protocolType;
	private int port;
	private String ipAddress;

	public Rule(DirectionType type, ProtocolType protocolType, int port, String ipAddress) {
		super();
		this.type = type;
		this.protocolType = protocolType;
		this.port = port;
		this.ipAddress = ipAddress;
	}

	public DirectionType getType() {
		return type;
	}

	public ProtocolType getProtocolType() {
		return protocolType;
	}

	public int getPort() {
		return port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	@Override
	public String toString() {
		return "Rule [type=" + type + ", protocolType=" + protocolType + ", port=" + port + ", ipAddress=" + ipAddress
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + port;
		result = prime * result + ((protocolType == null) ? 0 : protocolType.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (port != other.port)
			return false;
		if (protocolType != other.protocolType)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
