package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class PutAmmo extends BaseCommand
{
	
	// getters
	
	
	// setters
	
	
	public PutAmmo()
	{
	}
	
	public static final String nameStatic = "PutAmmo";
	
	@Override
	public String name() { return "PutAmmo"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize parents
		s.addAll(b2B(super.serialize()));
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize parents
		offset = super.deserialize(s, offset);
		
		return offset;
	}
}
