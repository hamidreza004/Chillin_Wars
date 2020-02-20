package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Move extends BaseCommand
{
	protected Boolean forward;
	
	// getters
	
	public Boolean getForward()
	{
		return this.forward;
	}
	
	
	// setters
	
	public void setForward(Boolean forward)
	{
		this.forward = forward;
	}
	
	
	public Move()
	{
	}
	
	public static final String nameStatic = "Move";
	
	@Override
	public String name() { return "Move"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize parents
		s.addAll(b2B(super.serialize()));
		
		// serialize forward
		s.add((byte) ((forward == null) ? 0 : 1));
		if (forward != null)
		{
			s.add((byte) ((forward) ? 1 : 0));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize parents
		offset = super.deserialize(s, offset);
		
		// deserialize forward
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			forward = (s[offset] == 0) ? false : true;
			offset += Byte.BYTES;
		}
		else
			forward = null;
		
		return offset;
	}
}
