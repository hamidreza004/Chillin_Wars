package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public enum AmmoType
{
	RifleBullet((byte) 0),
	TankShell((byte) 1),
	HMGBullet((byte) 2),
	MortarShell((byte) 3),
	GoldenTankShell((byte) 4),
	;

	private final byte value;
	AmmoType(byte value) { this.value = value; }
	public byte getValue() { return value; }
	
	private static Map<Byte, AmmoType> reverseLookup;
	
	public static AmmoType of(byte value)
	{
		if (reverseLookup == null)
		{
			reverseLookup = new HashMap<>();
			for (AmmoType c : AmmoType.values())
				reverseLookup.put(c.getValue(), c);
		}
		return reverseLookup.get(value);
	}
}
