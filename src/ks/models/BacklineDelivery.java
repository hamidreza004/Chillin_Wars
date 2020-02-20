package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class BacklineDelivery extends KSObject
{
	protected Map<MaterialType, Integer> materials;
	protected Map<AmmoType, Integer> ammos;
	
	// getters
	
	public Map<MaterialType, Integer> getMaterials()
	{
		return this.materials;
	}
	
	public Map<AmmoType, Integer> getAmmos()
	{
		return this.ammos;
	}
	
	
	// setters
	
	public void setMaterials(Map<MaterialType, Integer> materials)
	{
		this.materials = materials;
	}
	
	public void setAmmos(Map<AmmoType, Integer> ammos)
	{
		this.ammos = ammos;
	}
	
	
	public BacklineDelivery()
	{
	}
	
	public static final String nameStatic = "BacklineDelivery";
	
	@Override
	public String name() { return "BacklineDelivery"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize materials
		s.add((byte) ((materials == null) ? 0 : 1));
		if (materials != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(materials.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (Map.Entry<MaterialType, Integer> tmp1 : materials.entrySet())
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
		
		// serialize ammos
		s.add((byte) ((ammos == null) ? 0 : 1));
		if (ammos != null)
		{
			List<Byte> tmp2 = new ArrayList<>();
			tmp2.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(ammos.size()).array()));
			while (tmp2.size() > 0 && tmp2.get(tmp2.size() - 1) == 0)
				tmp2.remove(tmp2.size() - 1);
			s.add((byte) tmp2.size());
			s.addAll(tmp2);
			
			for (Map.Entry<AmmoType, Integer> tmp3 : ammos.entrySet())
			{
				s.add((byte) ((tmp3.getKey() == null) ? 0 : 1));
				if (tmp3.getKey() != null)
				{
					s.add((byte) (tmp3.getKey().getValue()));
				}
				
				s.add((byte) ((tmp3.getValue() == null) ? 0 : 1));
				if (tmp3.getValue() != null)
				{
					s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp3.getValue()).array()));
				}
			}
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize materials
		byte tmp4;
		tmp4 = s[offset];
		offset += Byte.BYTES;
		if (tmp4 == 1)
		{
			byte tmp5;
			tmp5 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp6 = Arrays.copyOfRange(s, offset, offset + tmp5);
			offset += tmp5;
			int tmp7;
			tmp7 = ByteBuffer.wrap(Arrays.copyOfRange(tmp6, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			materials = new HashMap<>();
			for (int tmp8 = 0; tmp8 < tmp7; tmp8++)
			{
				MaterialType tmp9;
				byte tmp11;
				tmp11 = s[offset];
				offset += Byte.BYTES;
				if (tmp11 == 1)
				{
					byte tmp12;
					tmp12 = s[offset];
					offset += Byte.BYTES;
					tmp9 = MaterialType.of(tmp12);
				}
				else
					tmp9 = null;
				
				Integer tmp10;
				byte tmp13;
				tmp13 = s[offset];
				offset += Byte.BYTES;
				if (tmp13 == 1)
				{
					tmp10 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp10 = null;
				
				materials.put(tmp9, tmp10);
			}
		}
		else
			materials = null;
		
		// deserialize ammos
		byte tmp14;
		tmp14 = s[offset];
		offset += Byte.BYTES;
		if (tmp14 == 1)
		{
			byte tmp15;
			tmp15 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp16 = Arrays.copyOfRange(s, offset, offset + tmp15);
			offset += tmp15;
			int tmp17;
			tmp17 = ByteBuffer.wrap(Arrays.copyOfRange(tmp16, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			ammos = new HashMap<>();
			for (int tmp18 = 0; tmp18 < tmp17; tmp18++)
			{
				AmmoType tmp19;
				byte tmp21;
				tmp21 = s[offset];
				offset += Byte.BYTES;
				if (tmp21 == 1)
				{
					byte tmp22;
					tmp22 = s[offset];
					offset += Byte.BYTES;
					tmp19 = AmmoType.of(tmp22);
				}
				else
					tmp19 = null;
				
				Integer tmp20;
				byte tmp23;
				tmp23 = s[offset];
				offset += Byte.BYTES;
				if (tmp23 == 1)
				{
					tmp20 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp20 = null;
				
				ammos.put(tmp19, tmp20);
			}
		}
		else
			ammos = null;
		
		return offset;
	}
}
