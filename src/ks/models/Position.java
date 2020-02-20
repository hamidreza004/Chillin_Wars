package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Position extends KSObject
{
	protected Integer index;
	
	// getters
	
	public Integer getIndex()
	{
		return this.index;
	}
	
	
	// setters
	
	public void setIndex(Integer index)
	{
		this.index = index;
	}
	
	
	public Position()
	{
	}
	
	public static final String nameStatic = "Position";
	
	@Override
	public String name() { return "Position"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize index
		s.add((byte) ((index == null) ? 0 : 1));
		if (index != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(index).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize index
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			index = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			index = null;
		
		return offset;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Position)
			return this.index == ((Position)obj).index;
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return index;
	}

	@Override
	public String toString()
	{
		return String.format("<Index: {0}>", index);
	}
}
