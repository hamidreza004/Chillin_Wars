package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class FrontlineDelivery extends KSObject
{
	protected Map<AmmoType, Integer> ammos;
	protected Integer deliveryRemTime;
	protected Integer cDeliveryDuration;
	
	// getters
	
	public Map<AmmoType, Integer> getAmmos()
	{
		return this.ammos;
	}
	
	public Integer getDeliveryRemTime()
	{
		return this.deliveryRemTime;
	}
	
	public Integer getCDeliveryDuration()
	{
		return this.cDeliveryDuration;
	}
	
	
	// setters
	
	public void setAmmos(Map<AmmoType, Integer> ammos)
	{
		this.ammos = ammos;
	}
	
	public void setDeliveryRemTime(Integer deliveryRemTime)
	{
		this.deliveryRemTime = deliveryRemTime;
	}
	
	public void setCDeliveryDuration(Integer cDeliveryDuration)
	{
		this.cDeliveryDuration = cDeliveryDuration;
	}
	
	
	public FrontlineDelivery()
	{
	}
	
	public static final String nameStatic = "FrontlineDelivery";
	
	@Override
	public String name() { return "FrontlineDelivery"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
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
			
			for (Map.Entry<AmmoType, Integer> tmp1 : ammos.entrySet())
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
		
		// serialize deliveryRemTime
		s.add((byte) ((deliveryRemTime == null) ? 0 : 1));
		if (deliveryRemTime != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(deliveryRemTime).array()));
		}
		
		// serialize cDeliveryDuration
		s.add((byte) ((cDeliveryDuration == null) ? 0 : 1));
		if (cDeliveryDuration != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cDeliveryDuration).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
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
				AmmoType tmp7;
				byte tmp9;
				tmp9 = s[offset];
				offset += Byte.BYTES;
				if (tmp9 == 1)
				{
					byte tmp10;
					tmp10 = s[offset];
					offset += Byte.BYTES;
					tmp7 = AmmoType.of(tmp10);
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
		
		// deserialize deliveryRemTime
		byte tmp12;
		tmp12 = s[offset];
		offset += Byte.BYTES;
		if (tmp12 == 1)
		{
			deliveryRemTime = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			deliveryRemTime = null;
		
		// deserialize cDeliveryDuration
		byte tmp13;
		tmp13 = s[offset];
		offset += Byte.BYTES;
		if (tmp13 == 1)
		{
			cDeliveryDuration = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			cDeliveryDuration = null;
		
		return offset;
	}
}
