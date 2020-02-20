package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Material extends KSObject
{
	protected MaterialType type;
	protected Position position;
	protected Integer count;
	protected Integer cCapacity;
	
	// getters
	
	public MaterialType getType()
	{
		return this.type;
	}
	
	public Position getPosition()
	{
		return this.position;
	}
	
	public Integer getCount()
	{
		return this.count;
	}
	
	public Integer getCCapacity()
	{
		return this.cCapacity;
	}
	
	
	// setters
	
	public void setType(MaterialType type)
	{
		this.type = type;
	}
	
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	public void setCount(Integer count)
	{
		this.count = count;
	}
	
	public void setCCapacity(Integer cCapacity)
	{
		this.cCapacity = cCapacity;
	}
	
	
	public Material()
	{
	}
	
	public static final String nameStatic = "Material";
	
	@Override
	public String name() { return "Material"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize type
		s.add((byte) ((type == null) ? 0 : 1));
		if (type != null)
		{
			s.add((byte) (type.getValue()));
		}
		
		// serialize position
		s.add((byte) ((position == null) ? 0 : 1));
		if (position != null)
		{
			s.addAll(b2B(position.serialize()));
		}
		
		// serialize count
		s.add((byte) ((count == null) ? 0 : 1));
		if (count != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(count).array()));
		}
		
		// serialize cCapacity
		s.add((byte) ((cCapacity == null) ? 0 : 1));
		if (cCapacity != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cCapacity).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize type
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			byte tmp1;
			tmp1 = s[offset];
			offset += Byte.BYTES;
			type = MaterialType.of(tmp1);
		}
		else
			type = null;
		
		// deserialize position
		byte tmp2;
		tmp2 = s[offset];
		offset += Byte.BYTES;
		if (tmp2 == 1)
		{
			position = new Position();
			offset = position.deserialize(s, offset);
		}
		else
			position = null;
		
		// deserialize count
		byte tmp3;
		tmp3 = s[offset];
		offset += Byte.BYTES;
		if (tmp3 == 1)
		{
			count = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			count = null;
		
		// deserialize cCapacity
		byte tmp4;
		tmp4 = s[offset];
		offset += Byte.BYTES;
		if (tmp4 == 1)
		{
			cCapacity = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cCapacity = null;
		
		return offset;
	}
}
