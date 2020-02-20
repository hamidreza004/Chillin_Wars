package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public enum CommandMaterialType
{
	Powder((byte) 0),
	Iron((byte) 1),
	Carbon((byte) 2),
	Gold((byte) 3),
	Shell((byte) 4),
	;

	private final byte value;
	CommandMaterialType(byte value) { this.value = value; }
	public byte getValue() { return value; }
	
	private static Map<Byte, CommandMaterialType> reverseLookup;
	
	public static CommandMaterialType of(byte value)
	{
		if (reverseLookup == null)
		{
			reverseLookup = new HashMap<>();
			for (CommandMaterialType c : CommandMaterialType.values())
				reverseLookup.put(c.getValue(), c);
		}
		return reverseLookup.get(value);
	}
}
