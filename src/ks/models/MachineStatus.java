package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public enum MachineStatus
{
	Idle((byte) 0),
	Working((byte) 1),
	AmmoReady((byte) 2),
	;

	private final byte value;
	MachineStatus(byte value) { this.value = value; }
	public byte getValue() { return value; }
	
	private static Map<Byte, MachineStatus> reverseLookup;
	
	public static MachineStatus of(byte value)
	{
		if (reverseLookup == null)
		{
			reverseLookup = new HashMap<>();
			for (MachineStatus c : MachineStatus.values())
				reverseLookup.put(c.getValue(), c);
		}
		return reverseLookup.get(value);
	}
}
