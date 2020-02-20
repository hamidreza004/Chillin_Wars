package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class World extends KSObject
{
	protected Integer maxCycles;
	protected Map<String, Base> bases;
	protected Map<String, Integer> totalHealths;
	
	// getters
	
	public Integer getMaxCycles()
	{
		return this.maxCycles;
	}
	
	public Map<String, Base> getBases()
	{
		return this.bases;
	}
	
	public Map<String, Integer> getTotalHealths()
	{
		return this.totalHealths;
	}
	
	
	// setters
	
	public void setMaxCycles(Integer maxCycles)
	{
		this.maxCycles = maxCycles;
	}
	
	public void setBases(Map<String, Base> bases)
	{
		this.bases = bases;
	}
	
	public void setTotalHealths(Map<String, Integer> totalHealths)
	{
		this.totalHealths = totalHealths;
	}
	
	
	public World()
	{
	}
	
	public static final String nameStatic = "World";
	
	@Override
	public String name() { return "World"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize maxCycles
		s.add((byte) ((maxCycles == null) ? 0 : 1));
		if (maxCycles != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(maxCycles).array()));
		}
		
		// serialize bases
		s.add((byte) ((bases == null) ? 0 : 1));
		if (bases != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(bases.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (Map.Entry<String, Base> tmp1 : bases.entrySet())
			{
				s.add((byte) ((tmp1.getKey() == null) ? 0 : 1));
				if (tmp1.getKey() != null)
				{
					List<Byte> tmp2 = new ArrayList<>();
					tmp2.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp1.getKey().length()).array()));
					while (tmp2.size() > 0 && tmp2.get(tmp2.size() - 1) == 0)
						tmp2.remove(tmp2.size() - 1);
					s.add((byte) tmp2.size());
					s.addAll(tmp2);
					
					s.addAll(b2B(tmp1.getKey().getBytes(Charset.forName("ISO-8859-1"))));
				}
				
				s.add((byte) ((tmp1.getValue() == null) ? 0 : 1));
				if (tmp1.getValue() != null)
				{
					s.addAll(b2B(tmp1.getValue().serialize()));
				}
			}
		}
		
		// serialize totalHealths
		s.add((byte) ((totalHealths == null) ? 0 : 1));
		if (totalHealths != null)
		{
			List<Byte> tmp3 = new ArrayList<>();
			tmp3.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(totalHealths.size()).array()));
			while (tmp3.size() > 0 && tmp3.get(tmp3.size() - 1) == 0)
				tmp3.remove(tmp3.size() - 1);
			s.add((byte) tmp3.size());
			s.addAll(tmp3);
			
			for (Map.Entry<String, Integer> tmp4 : totalHealths.entrySet())
			{
				s.add((byte) ((tmp4.getKey() == null) ? 0 : 1));
				if (tmp4.getKey() != null)
				{
					List<Byte> tmp5 = new ArrayList<>();
					tmp5.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp4.getKey().length()).array()));
					while (tmp5.size() > 0 && tmp5.get(tmp5.size() - 1) == 0)
						tmp5.remove(tmp5.size() - 1);
					s.add((byte) tmp5.size());
					s.addAll(tmp5);
					
					s.addAll(b2B(tmp4.getKey().getBytes(Charset.forName("ISO-8859-1"))));
				}
				
				s.add((byte) ((tmp4.getValue() == null) ? 0 : 1));
				if (tmp4.getValue() != null)
				{
					s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp4.getValue()).array()));
				}
			}
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize maxCycles
		byte tmp6;
		tmp6 = s[offset];
		offset += Byte.BYTES;
		if (tmp6 == 1)
		{
			maxCycles = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			maxCycles = null;
		
		// deserialize bases
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
			
			bases = new HashMap<>();
			for (int tmp11 = 0; tmp11 < tmp10; tmp11++)
			{
				String tmp12;
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
					
					tmp12 = new String(s, offset, tmp17, Charset.forName("ISO-8859-1"));
					offset += tmp17;
				}
				else
					tmp12 = null;
				
				Base tmp13;
				byte tmp18;
				tmp18 = s[offset];
				offset += Byte.BYTES;
				if (tmp18 == 1)
				{
					tmp13 = new Base();
					offset = tmp13.deserialize(s, offset);
				}
				else
					tmp13 = null;
				
				bases.put(tmp12, tmp13);
			}
		}
		else
			bases = null;
		
		// deserialize totalHealths
		byte tmp19;
		tmp19 = s[offset];
		offset += Byte.BYTES;
		if (tmp19 == 1)
		{
			byte tmp20;
			tmp20 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp21 = Arrays.copyOfRange(s, offset, offset + tmp20);
			offset += tmp20;
			int tmp22;
			tmp22 = ByteBuffer.wrap(Arrays.copyOfRange(tmp21, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			totalHealths = new HashMap<>();
			for (int tmp23 = 0; tmp23 < tmp22; tmp23++)
			{
				String tmp24;
				byte tmp26;
				tmp26 = s[offset];
				offset += Byte.BYTES;
				if (tmp26 == 1)
				{
					byte tmp27;
					tmp27 = s[offset];
					offset += Byte.BYTES;
					byte[] tmp28 = Arrays.copyOfRange(s, offset, offset + tmp27);
					offset += tmp27;
					int tmp29;
					tmp29 = ByteBuffer.wrap(Arrays.copyOfRange(tmp28, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					
					tmp24 = new String(s, offset, tmp29, Charset.forName("ISO-8859-1"));
					offset += tmp29;
				}
				else
					tmp24 = null;
				
				Integer tmp25;
				byte tmp30;
				tmp30 = s[offset];
				offset += Byte.BYTES;
				if (tmp30 == 1)
				{
					tmp25 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp25 = null;
				
				totalHealths.put(tmp24, tmp25);
			}
		}
		else
			totalHealths = null;
		
		return offset;
	}
}
