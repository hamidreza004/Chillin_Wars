package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Warehouse extends KSObject
{
	protected Map<Position, Material> materials;
	protected Integer materialsReloadRemTime;
	protected Integer cMaterialsReloadDuration;
	
	// getters
	
	public Map<Position, Material> getMaterials()
	{
		return this.materials;
	}
	
	public Integer getMaterialsReloadRemTime()
	{
		return this.materialsReloadRemTime;
	}
	
	public Integer getCMaterialsReloadDuration()
	{
		return this.cMaterialsReloadDuration;
	}
	
	
	// setters
	
	public void setMaterials(Map<Position, Material> materials)
	{
		this.materials = materials;
	}
	
	public void setMaterialsReloadRemTime(Integer materialsReloadRemTime)
	{
		this.materialsReloadRemTime = materialsReloadRemTime;
	}
	
	public void setCMaterialsReloadDuration(Integer cMaterialsReloadDuration)
	{
		this.cMaterialsReloadDuration = cMaterialsReloadDuration;
	}
	
	
	public Warehouse()
	{
	}
	
	public static final String nameStatic = "Warehouse";
	
	@Override
	public String name() { return "Warehouse"; }
	
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
			
			for (Map.Entry<Position, Material> tmp1 : materials.entrySet())
			{
				s.add((byte) ((tmp1.getKey() == null) ? 0 : 1));
				if (tmp1.getKey() != null)
				{
					s.addAll(b2B(tmp1.getKey().serialize()));
				}
				
				s.add((byte) ((tmp1.getValue() == null) ? 0 : 1));
				if (tmp1.getValue() != null)
				{
					s.addAll(b2B(tmp1.getValue().serialize()));
				}
			}
		}
		
		// serialize materialsReloadRemTime
		s.add((byte) ((materialsReloadRemTime == null) ? 0 : 1));
		if (materialsReloadRemTime != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(materialsReloadRemTime).array()));
		}
		
		// serialize cMaterialsReloadDuration
		s.add((byte) ((cMaterialsReloadDuration == null) ? 0 : 1));
		if (cMaterialsReloadDuration != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cMaterialsReloadDuration).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize materials
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
			
			materials = new HashMap<>();
			for (int tmp6 = 0; tmp6 < tmp5; tmp6++)
			{
				Position tmp7;
				byte tmp9;
				tmp9 = s[offset];
				offset += Byte.BYTES;
				if (tmp9 == 1)
				{
					tmp7 = new Position();
					offset = tmp7.deserialize(s, offset);
				}
				else
					tmp7 = null;
				
				Material tmp8;
				byte tmp10;
				tmp10 = s[offset];
				offset += Byte.BYTES;
				if (tmp10 == 1)
				{
					tmp8 = new Material();
					offset = tmp8.deserialize(s, offset);
				}
				else
					tmp8 = null;
				
				materials.put(tmp7, tmp8);
			}
		}
		else
			materials = null;
		
		// deserialize materialsReloadRemTime
		byte tmp11;
		tmp11 = s[offset];
		offset += Byte.BYTES;
		if (tmp11 == 1)
		{
			materialsReloadRemTime = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			materialsReloadRemTime = null;
		
		// deserialize cMaterialsReloadDuration
		byte tmp12;
		tmp12 = s[offset];
		offset += Byte.BYTES;
		if (tmp12 == 1)
		{
			cMaterialsReloadDuration = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cMaterialsReloadDuration = null;
		
		return offset;
	}
}
