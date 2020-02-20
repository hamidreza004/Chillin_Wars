package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public enum CommandAgentType
{
	Warehouse((byte) 0),
	Factory((byte) 1),
	;

	private final byte value;
	CommandAgentType(byte value) { this.value = value; }
	public byte getValue() { return value; }
	
	private static Map<Byte, CommandAgentType> reverseLookup;
	
	public static CommandAgentType of(byte value)
	{
		if (reverseLookup == null)
		{
			reverseLookup = new HashMap<>();
			for (CommandAgentType c : CommandAgentType.values())
				reverseLookup.put(c.getValue(), c);
		}
		return reverseLookup.get(value);
	}
}
