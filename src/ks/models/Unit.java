package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Unit extends KSObject
{
	protected UnitType type;
	protected Integer health;
	protected Integer cIndividualHealth;
	protected Integer cIndividualDamage;
	protected Map<UnitType, Float> cDamageDistribution;
	protected Integer ammoCount;
	protected Integer reloadRemTime;
	protected Integer cReloadDuration;
	
	// getters
	
	public UnitType getType()
	{
		return this.type;
	}
	
	public Integer getHealth()
	{
		return this.health;
	}
	
	public Integer getCIndividualHealth()
	{
		return this.cIndividualHealth;
	}
	
	public Integer getCIndividualDamage()
	{
		return this.cIndividualDamage;
	}
	
	public Map<UnitType, Float> getCDamageDistribution()
	{
		return this.cDamageDistribution;
	}
	
	public Integer getAmmoCount()
	{
		return this.ammoCount;
	}
	
	public Integer getReloadRemTime()
	{
		return this.reloadRemTime;
	}
	
	public Integer getCReloadDuration()
	{
		return this.cReloadDuration;
	}
	
	
	// setters
	
	public void setType(UnitType type)
	{
		this.type = type;
	}
	
	public void setHealth(Integer health)
	{
		this.health = health;
	}
	
	public void setCIndividualHealth(Integer cIndividualHealth)
	{
		this.cIndividualHealth = cIndividualHealth;
	}
	
	public void setCIndividualDamage(Integer cIndividualDamage)
	{
		this.cIndividualDamage = cIndividualDamage;
	}
	
	public void setCDamageDistribution(Map<UnitType, Float> cDamageDistribution)
	{
		this.cDamageDistribution = cDamageDistribution;
	}
	
	public void setAmmoCount(Integer ammoCount)
	{
		this.ammoCount = ammoCount;
	}
	
	public void setReloadRemTime(Integer reloadRemTime)
	{
		this.reloadRemTime = reloadRemTime;
	}
	
	public void setCReloadDuration(Integer cReloadDuration)
	{
		this.cReloadDuration = cReloadDuration;
	}
	
	
	public Unit()
	{
	}
	
	public static final String nameStatic = "Unit";
	
	@Override
	public String name() { return "Unit"; }
	
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
		
		// serialize health
		s.add((byte) ((health == null) ? 0 : 1));
		if (health != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(health).array()));
		}
		
		// serialize cIndividualHealth
		s.add((byte) ((cIndividualHealth == null) ? 0 : 1));
		if (cIndividualHealth != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cIndividualHealth).array()));
		}
		
		// serialize cIndividualDamage
		s.add((byte) ((cIndividualDamage == null) ? 0 : 1));
		if (cIndividualDamage != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cIndividualDamage).array()));
		}
		
		// serialize cDamageDistribution
		s.add((byte) ((cDamageDistribution == null) ? 0 : 1));
		if (cDamageDistribution != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cDamageDistribution.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (Map.Entry<UnitType, Float> tmp1 : cDamageDistribution.entrySet())
			{
				s.add((byte) ((tmp1.getKey() == null) ? 0 : 1));
				if (tmp1.getKey() != null)
				{
					s.add((byte) (tmp1.getKey().getValue()));
				}
				
				s.add((byte) ((tmp1.getValue() == null) ? 0 : 1));
				if (tmp1.getValue() != null)
				{
					s.addAll(b2B(ByteBuffer.allocate(Float.BYTES).order(ByteOrder.LITTLE_ENDIAN).putFloat(tmp1.getValue()).array()));
				}
			}
		}
		
		// serialize ammoCount
		s.add((byte) ((ammoCount == null) ? 0 : 1));
		if (ammoCount != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(ammoCount).array()));
		}
		
		// serialize reloadRemTime
		s.add((byte) ((reloadRemTime == null) ? 0 : 1));
		if (reloadRemTime != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(reloadRemTime).array()));
		}
		
		// serialize cReloadDuration
		s.add((byte) ((cReloadDuration == null) ? 0 : 1));
		if (cReloadDuration != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cReloadDuration).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize type
		byte tmp2;
		tmp2 = s[offset];
		offset += Byte.BYTES;
		if (tmp2 == 1)
		{
			byte tmp3;
			tmp3 = s[offset];
			offset += Byte.BYTES;
			type = UnitType.of(tmp3);
		}
		else
			type = null;
		
		// deserialize health
		byte tmp4;
		tmp4 = s[offset];
		offset += Byte.BYTES;
		if (tmp4 == 1)
		{
			health = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			health = null;
		
		// deserialize cIndividualHealth
		byte tmp5;
		tmp5 = s[offset];
		offset += Byte.BYTES;
		if (tmp5 == 1)
		{
			cIndividualHealth = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cIndividualHealth = null;
		
		// deserialize cIndividualDamage
		byte tmp6;
		tmp6 = s[offset];
		offset += Byte.BYTES;
		if (tmp6 == 1)
		{
			cIndividualDamage = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cIndividualDamage = null;
		
		// deserialize cDamageDistribution
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
			
			cDamageDistribution = new HashMap<>();
			for (int tmp11 = 0; tmp11 < tmp10; tmp11++)
			{
				UnitType tmp12;
				byte tmp14;
				tmp14 = s[offset];
				offset += Byte.BYTES;
				if (tmp14 == 1)
				{
					byte tmp15;
					tmp15 = s[offset];
					offset += Byte.BYTES;
					tmp12 = UnitType.of(tmp15);
				}
				else
					tmp12 = null;
				
				Float tmp13;
				byte tmp16;
				tmp16 = s[offset];
				offset += Byte.BYTES;
				if (tmp16 == 1)
				{
					tmp13 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Float.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
					offset += Float.BYTES;
				}
				else
					tmp13 = null;
				
				cDamageDistribution.put(tmp12, tmp13);
			}
		}
		else
			cDamageDistribution = null;
		
		// deserialize ammoCount
		byte tmp17;
		tmp17 = s[offset];
		offset += Byte.BYTES;
		if (tmp17 == 1)
		{
			ammoCount = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			ammoCount = null;
		
		// deserialize reloadRemTime
		byte tmp18;
		tmp18 = s[offset];
		offset += Byte.BYTES;
		if (tmp18 == 1)
		{
			reloadRemTime = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			reloadRemTime = null;
		
		// deserialize cReloadDuration
		byte tmp19;
		tmp19 = s[offset];
		offset += Byte.BYTES;
		if (tmp19 == 1)
		{
			cReloadDuration = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cReloadDuration = null;
		
		return offset;
	}
}
