package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class PickAmmo extends BaseCommand
{
	protected Map<CommandAmmoType, Integer> ammos;
	
	// getters
	
	public Map<CommandAmmoType, Integer> getAmmos()
	{
		return this.ammos;
	}
	
	
	// setters
	
	public void setAmmos(Map<CommandAmmoType, Integer> ammos)
	{
		this.ammos = ammos;
	}
	
	
	public PickAmmo()
	{
	}
	
	public static final String nameStatic = "PickAmmo";
	
	@Override
	public String name() { return "PickAmmo"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize parents
		s.addAll(b2B(super.serialize()));
		
		// serialize ammos
		s.add((byte) ((ammos == null) ? 0 : 1));
		if (ammos != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(ammos.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (Map.Entry<CommandAmmoType, Integer> tmp1 : ammos.entrySet())
			{
				s.add((byte) ((tmp1.getKey() == null) ? 0 : 1));
				if (tmp1.getKey() != null)
				{
					s.add((byte) (tmp1.getKey().getValue()));
				}
				
				s.add((byte) ((tmp1.getValue() == null) ? 0 : 1));
				if (tmp1.getValue() != null)
				{
					s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp1.getValue()).array()));
				}
			}
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize parents
		offset = super.deserialize(s, offset);
		
		// deserialize ammos
		byte tmp2;
		tmp2 = s[offset];
		offset += Byte.BYTES;
		if (tmp2 == 1)
		{
			byte tmp3;
			tmp3 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp4 = Arrays.copyOfRange(s, offset, offset + tmp3);
			offset += tmp3;
			int tmp5;
			tmp5 = ByteBuffer.wrap(Arrays.copyOfRange(tmp4, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			ammos = new HashMap<>();
			for (int tmp6 = 0; tmp6 < tmp5; tmp6++)
			{
				CommandAmmoType tmp7;
				byte tmp9;
				tmp9 = s[offset];
				offset += Byte.BYTES;
				if (tmp9 == 1)
				{
					byte tmp10;
					tmp10 = s[offset];
					offset += Byte.BYTES;
					tmp7 = CommandAmmoType.of(tmp10);
				}
				else
					tmp7 = null;
				
				Integer tmp8;
				byte tmp11;
				tmp11 = s[offset];
				offset += Byte.BYTES;
				if (tmp11 == 1)
				{
					tmp8 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp8 = null;
				
				ammos.put(tmp7, tmp8);
			}
		}
		else
			ammos = null;
		
		return offset;
	}
}
