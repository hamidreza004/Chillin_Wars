package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class PutMaterial extends BaseCommand
{
	protected CommandAmmoType desiredAmmo;
	
	// getters
	
	public CommandAmmoType getDesiredAmmo()
	{
		return this.desiredAmmo;
	}
	
	
	// setters
	
	public void setDesiredAmmo(CommandAmmoType desiredAmmo)
	{
		this.desiredAmmo = desiredAmmo;
	}
	
	
	public PutMaterial()
	{
	}
	
	public static final String nameStatic = "PutMaterial";
	
	@Override
	public String name() { return "PutMaterial"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize parents
		s.addAll(b2B(super.serialize()));
		
		// serialize desiredAmmo
		s.add((byte) ((desiredAmmo == null) ? 0 : 1));
		if (desiredAmmo != null)
		{
			s.add((byte) (desiredAmmo.getValue()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize parents
		offset = super.deserialize(s, offset);
		
		// deserialize desiredAmmo
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			byte tmp1;
			tmp1 = s[offset];
			offset += Byte.BYTES;
			desiredAmmo = CommandAmmoType.of(tmp1);
		}
		else
			desiredAmmo = null;
		
		return offset;
	}
}
