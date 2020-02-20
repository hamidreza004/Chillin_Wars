package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Agent extends KSObject
{
	protected AgentType type;
	protected Position position;
	protected Map<MaterialType, Integer> materialsBag;
	protected Integer cMaterialsBagCapacity;
	protected Map<AmmoType, Integer> ammosBag;
	protected Integer cAmmosBagCapacity;
	
	// getters
	
	public AgentType getType()
	{
		return this.type;
	}
	
	public Position getPosition()
	{
		return this.position;
	}
	
	public Map<MaterialType, Integer> getMaterialsBag()
	{
		return this.materialsBag;
	}
	
	public Integer getCMaterialsBagCapacity()
	{
		return this.cMaterialsBagCapacity;
	}
	
	public Map<AmmoType, Integer> getAmmosBag()
	{
		return this.ammosBag;
	}
	
	public Integer getCAmmosBagCapacity()
	{
		return this.cAmmosBagCapacity;
	}
	
	
	// setters
	
	public void setType(AgentType type)
	{
		this.type = type;
	}
	
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	public void setMaterialsBag(Map<MaterialType, Integer> materialsBag)
	{
		this.materialsBag = materialsBag;
	}
	
	public void setCMaterialsBagCapacity(Integer cMaterialsBagCapacity)
	{
		this.cMaterialsBagCapacity = cMaterialsBagCapacity;
	}
	
	public void setAmmosBag(Map<AmmoType, Integer> ammosBag)
	{
		this.ammosBag = ammosBag;
	}
	
	public void setCAmmosBagCapacity(Integer cAmmosBagCapacity)
	{
		this.cAmmosBagCapacity = cAmmosBagCapacity;
	}
	
	
	public Agent()
	{
	}
	
	public static final String nameStatic = "Agent";
	
	@Override
	public String name() { return "Agent"; }
	
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
		
		// serialize materialsBag
		s.add((byte) ((materialsBag == null) ? 0 : 1));
		if (materialsBag != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(materialsBag.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (Map.Entry<MaterialType, Integer> tmp1 : materialsBag.entrySet())
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
		
		// serialize cMaterialsBagCapacity
		s.add((byte) ((cMaterialsBagCapacity == null) ? 0 : 1));
		if (cMaterialsBagCapacity != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cMaterialsBagCapacity).array()));
		}
		
		// serialize ammosBag
		s.add((byte) ((ammosBag == null) ? 0 : 1));
		if (ammosBag != null)
		{
			List<Byte> tmp2 = new ArrayList<>();
			tmp2.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(ammosBag.size()).array()));
			while (tmp2.size() > 0 && tmp2.get(tmp2.size() - 1) == 0)
				tmp2.remove(tmp2.size() - 1);
			s.add((byte) tmp2.size());
			s.addAll(tmp2);
			
			for (Map.Entry<AmmoType, Integer> tmp3 : ammosBag.entrySet())
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
		
		// serialize cAmmosBagCapacity
		s.add((byte) ((cAmmosBagCapacity == null) ? 0 : 1));
		if (cAmmosBagCapacity != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cAmmosBagCapacity).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize type
		byte tmp4;
		tmp4 = s[offset];
		offset += Byte.BYTES;
		if (tmp4 == 1)
		{
			byte tmp5;
			tmp5 = s[offset];
			offset += Byte.BYTES;
			type = AgentType.of(tmp5);
		}
		else
			type = null;
		
		// deserialize position
		byte tmp6;
		tmp6 = s[offset];
		offset += Byte.BYTES;
		if (tmp6 == 1)
		{
			position = new Position();
			offset = position.deserialize(s, offset);
		}
		else
			position = null;
		
		// deserialize materialsBag
		byte tmp7;
		tmp7 = s[offset];
		offset += Byte.BYTES;
		if (tmp7 == 1)
		{
			byte tmp8;
			tmp8 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp9 = Arrays.copyOfRange(s, offset, offset + tmp8);
			offset += tmp8;
			int tmp10;
			tmp10 = ByteBuffer.wrap(Arrays.copyOfRange(tmp9, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			materialsBag = new HashMap<>();
			for (int tmp11 = 0; tmp11 < tmp10; tmp11++)
			{
				MaterialType tmp12;
				byte tmp14;
				tmp14 = s[offset];
				offset += Byte.BYTES;
				if (tmp14 == 1)
				{
					byte tmp15;
					tmp15 = s[offset];
					offset += Byte.BYTES;
					tmp12 = MaterialType.of(tmp15);
				}
				else
					tmp12 = null;
				
				Integer tmp13;
				byte tmp16;
				tmp16 = s[offset];
				offset += Byte.BYTES;
				if (tmp16 == 1)
				{
					tmp13 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp13 = null;
				
				materialsBag.put(tmp12, tmp13);
			}
		}
		else
			materialsBag = null;
		
		// deserialize cMaterialsBagCapacity
		byte tmp17;
		tmp17 = s[offset];
		offset += Byte.BYTES;
		if (tmp17 == 1)
		{
			cMaterialsBagCapacity = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cMaterialsBagCapacity = null;
		
		// deserialize ammosBag
		byte tmp18;
		tmp18 = s[offset];
		offset += Byte.BYTES;
		if (tmp18 == 1)
		{
			byte tmp19;
			tmp19 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp20 = Arrays.copyOfRange(s, offset, offset + tmp19);
			offset += tmp19;
			int tmp21;
			tmp21 = ByteBuffer.wrap(Arrays.copyOfRange(tmp20, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			ammosBag = new HashMap<>();
			for (int tmp22 = 0; tmp22 < tmp21; tmp22++)
			{
				AmmoType tmp23;
				byte tmp25;
				tmp25 = s[offset];
				offset += Byte.BYTES;
				if (tmp25 == 1)
				{
					byte tmp26;
					tmp26 = s[offset];
					offset += Byte.BYTES;
					tmp23 = AmmoType.of(tmp26);
				}
				else
					tmp23 = null;
				
				Integer tmp24;
				byte tmp27;
				tmp27 = s[offset];
				offset += Byte.BYTES;
				if (tmp27 == 1)
				{
					tmp24 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp24 = null;
				
				ammosBag.put(tmp23, tmp24);
			}
		}
		else
			ammosBag = null;
		
		// deserialize cAmmosBagCapacity
		byte tmp28;
		tmp28 = s[offset];
		offset += Byte.BYTES;
		if (tmp28 == 1)
		{
			cAmmosBagCapacity = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cAmmosBagCapacity = null;
		
		return offset;
	}
}
