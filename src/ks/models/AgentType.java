package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public enum AgentType
{
	Warehouse((byte) 0),
	Factory((byte) 1),
	;

	private final byte value;
	AgentType(byte value) { this.value = value; }
	public byte getValue() { return value; }
	
	private static Map<Byte, AgentType> reverseLookup;
	
	public static AgentType of(byte value)
	{
		if (reverseLookup == null)
		{
			reverseLookup = new HashMap<>();
			for (AgentType c : AgentType.values())
				reverseLookup.put(c.getValue(), c);
		}
		return reverseLookup.get(value);
	}
}
