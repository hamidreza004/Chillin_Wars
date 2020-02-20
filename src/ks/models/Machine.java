package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Machine extends KSObject
{
	protected Position position;
	protected MachineStatus status;
	protected AmmoType currentAmmo;
	protected Integer constructionRemTime;
	
	// getters
	
	public Position getPosition()
	{
		return this.position;
	}
	
	public MachineStatus getStatus()
	{
		return this.status;
	}
	
	public AmmoType getCurrentAmmo()
	{
		return this.currentAmmo;
	}
	
	public Integer getConstructionRemTime()
	{
		return this.constructionRemTime;
	}
	
	
	// setters
	
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	public void setStatus(MachineStatus status)
	{
		this.status = status;
	}
	
	public void setCurrentAmmo(AmmoType currentAmmo)
	{
		this.currentAmmo = currentAmmo;
	}
	
	public void setConstructionRemTime(Integer constructionRemTime)
	{
		this.constructionRemTime = constructionRemTime;
	}
	
	
	public Machine()
	{
	}
	
	public static final String nameStatic = "Machine";
	
	@Override
	public String name() { return "Machine"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize position
		s.add((byte) ((position == null) ? 0 : 1));
		if (position != null)
		{
			s.addAll(b2B(position.serialize()));
		}
		
		// serialize status
		s.add((byte) ((status == null) ? 0 : 1));
		if (status != null)
		{
			s.add((byte) (status.getValue()));
		}
		
		// serialize currentAmmo
		s.add((byte) ((currentAmmo == null) ? 0 : 1));
		if (currentAmmo != null)
		{
			s.add((byte) (currentAmmo.getValue()));
		}
		
		// serialize constructionRemTime
		s.add((byte) ((constructionRemTime == null) ? 0 : 1));
		if (constructionRemTime != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(constructionRemTime).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize position
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			position = new Position();
			offset = position.deserialize(s, offset);
		}
		else
			position = null;
		
		// deserialize status
		byte tmp1;
		tmp1 = s[offset];
		offset += Byte.BYTES;
		if (tmp1 == 1)
		{
			byte tmp2;
			tmp2 = s[offset];
			offset += Byte.BYTES;
			status = MachineStatus.of(tmp2);
		}
		else
			status = null;
		
		// deserialize currentAmmo
		byte tmp3;
		tmp3 = s[offset];
		offset += Byte.BYTES;
		if (tmp3 == 1)
		{
			byte tmp4;
			tmp4 = s[offset];
			offset += Byte.BYTES;
			currentAmmo = AmmoType.of(tmp4);
		}
		else
			currentAmmo = null;
		
		// deserialize constructionRemTime
		byte tmp5;
		tmp5 = s[offset];
		offset += Byte.BYTES;
		if (tmp5 == 1)
		{
			constructionRemTime = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			constructionRemTime = null;
		
		return offset;
	}
}
