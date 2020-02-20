package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Factory extends KSObject
{
	protected Map<Position, Machine> machines;
	protected Map<AmmoType, Map<MaterialType, Integer>> cMixtureFormulas;
	protected Map<AmmoType, Integer> cConstructionDurations;
	protected Map<AmmoType, Integer> cAmmoBoxSizes;
	
	// getters
	
	public Map<Position, Machine> getMachines()
	{
		return this.machines;
	}
	
	public Map<AmmoType, Map<MaterialType, Integer>> getCMixtureFormulas()
	{
		return this.cMixtureFormulas;
	}
	
	public Map<AmmoType, Integer> getCConstructionDurations()
	{
		return this.cConstructionDurations;
	}
	
	public Map<AmmoType, Integer> getCAmmoBoxSizes()
	{
		return this.cAmmoBoxSizes;
	}
	
	
	// setters
	
	public void setMachines(Map<Position, Machine> machines)
	{
		this.machines = machines;
	}
	
	public void setCMixtureFormulas(Map<AmmoType, Map<MaterialType, Integer>> cMixtureFormulas)
	{
		this.cMixtureFormulas = cMixtureFormulas;
	}
	
	public void setCConstructionDurations(Map<AmmoType, Integer> cConstructionDurations)
	{
		this.cConstructionDurations = cConstructionDurations;
	}
	
	public void setCAmmoBoxSizes(Map<AmmoType, Integer> cAmmoBoxSizes)
	{
		this.cAmmoBoxSizes = cAmmoBoxSizes;
	}
	
	
	public Factory()
	{
	}
	
	public static final String nameStatic = "Factory";
	
	@Override
	public String name() { return "Factory"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize machines
		s.add((byte) ((machines == null) ? 0 : 1));
		if (machines != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(machines.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (Map.Entry<Position, Machine> tmp1 : machines.entrySet())
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
		
		// serialize cMixtureFormulas
		s.add((byte) ((cMixtureFormulas == null) ? 0 : 1));
		if (cMixtureFormulas != null)
		{
			List<Byte> tmp2 = new ArrayList<>();
			tmp2.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cMixtureFormulas.size()).array()));
			while (tmp2.size() > 0 && tmp2.get(tmp2.size() - 1) == 0)
				tmp2.remove(tmp2.size() - 1);
			s.add((byte) tmp2.size());
			s.addAll(tmp2);
			
			for (Map.Entry<AmmoType, Map<MaterialType, Integer>> tmp3 : cMixtureFormulas.entrySet())
			{
				s.add((byte) ((tmp3.getKey() == null) ? 0 : 1));
				if (tmp3.getKey() != null)
				{
					s.add((byte) (tmp3.getKey().getValue()));
				}
				
				s.add((byte) ((tmp3.getValue() == null) ? 0 : 1));
				if (tmp3.getValue() != null)
				{
					List<Byte> tmp4 = new ArrayList<>();
					tmp4.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp3.getValue().size()).array()));
					while (tmp4.size() > 0 && tmp4.get(tmp4.size() - 1) == 0)
						tmp4.remove(tmp4.size() - 1);
					s.add((byte) tmp4.size());
					s.addAll(tmp4);
					
					for (Map.Entry<MaterialType, Integer> tmp5 : tmp3.getValue().entrySet())
					{
						s.add((byte) ((tmp5.getKey() == null) ? 0 : 1));
						if (tmp5.getKey() != null)
						{
							s.add((byte) (tmp5.getKey().getValue()));
						}
						
						s.add((byte) ((tmp5.getValue() == null) ? 0 : 1));
						if (tmp5.getValue() != null)
						{
							s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp5.getValue()).array()));
						}
					}
				}
			}
		}
		
		// serialize cConstructionDurations
		s.add((byte) ((cConstructionDurations == null) ? 0 : 1));
		if (cConstructionDurations != null)
		{
			List<Byte> tmp6 = new ArrayList<>();
			tmp6.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cConstructionDurations.size()).array()));
			while (tmp6.size() > 0 && tmp6.get(tmp6.size() - 1) == 0)
				tmp6.remove(tmp6.size() - 1);
			s.add((byte) tmp6.size());
			s.addAll(tmp6);
			
			for (Map.Entry<AmmoType, Integer> tmp7 : cConstructionDurations.entrySet())
			{
				s.add((byte) ((tmp7.getKey() == null) ? 0 : 1));
				if (tmp7.getKey() != null)
				{
					s.add((byte) (tmp7.getKey().getValue()));
				}
				
				s.add((byte) ((tmp7.getValue() == null) ? 0 : 1));
				if (tmp7.getValue() != null)
				{
					s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp7.getValue()).array()));
				}
			}
		}
		
		// serialize cAmmoBoxSizes
		s.add((byte) ((cAmmoBoxSizes == null) ? 0 : 1));
		if (cAmmoBoxSizes != null)
		{
			List<Byte> tmp8 = new ArrayList<>();
			tmp8.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cAmmoBoxSizes.size()).array()));
			while (tmp8.size() > 0 && tmp8.get(tmp8.size() - 1) == 0)
				tmp8.remove(tmp8.size() - 1);
			s.add((byte) tmp8.size());
			s.addAll(tmp8);
			
			for (Map.Entry<AmmoType, Integer> tmp9 : cAmmoBoxSizes.entrySet())
			{
				s.add((byte) ((tmp9.getKey() == null) ? 0 : 1));
				if (tmp9.getKey() != null)
				{
					s.add((byte) (tmp9.getKey().getValue()));
				}
				
				s.add((byte) ((tmp9.getValue() == null) ? 0 : 1));
				if (tmp9.getValue() != null)
				{
					s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp9.getValue()).array()));
				}
			}
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize machines
		byte tmp10;
		tmp10 = s[offset];
		offset += Byte.BYTES;
		if (tmp10 == 1)
		{
			byte tmp11;
			tmp11 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp12 = Arrays.copyOfRange(s, offset, offset + tmp11);
			offset += tmp11;
			int tmp13;
			tmp13 = ByteBuffer.wrap(Arrays.copyOfRange(tmp12, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			machines = new HashMap<>();
			for (int tmp14 = 0; tmp14 < tmp13; tmp14++)
			{
				Position tmp15;
				byte tmp17;
				tmp17 = s[offset];
				offset += Byte.BYTES;
				if (tmp17 == 1)
				{
					tmp15 = new Position();
					offset = tmp15.deserialize(s, offset);
				}
				else
					tmp15 = null;
				
				Machine tmp16;
				byte tmp18;
				tmp18 = s[offset];
				offset += Byte.BYTES;
				if (tmp18 == 1)
				{
					tmp16 = new Machine();
					offset = tmp16.deserialize(s, offset);
				}
				else
					tmp16 = null;
				
				machines.put(tmp15, tmp16);
			}
		}
		else
			machines = null;
		
		// deserialize cMixtureFormulas
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
			
			cMixtureFormulas = new HashMap<>();
			for (int tmp23 = 0; tmp23 < tmp22; tmp23++)
			{
				AmmoType tmp24;
				byte tmp26;
				tmp26 = s[offset];
				offset += Byte.BYTES;
				if (tmp26 == 1)
				{
					byte tmp27;
					tmp27 = s[offset];
					offset += Byte.BYTES;
					tmp24 = AmmoType.of(tmp27);
				}
				else
					tmp24 = null;
				
				Map<MaterialType, Integer> tmp25;
				byte tmp28;
				tmp28 = s[offset];
				offset += Byte.BYTES;
				if (tmp28 == 1)
				{
					byte tmp29;
					tmp29 = s[offset];
					offset += Byte.BYTES;
					byte[] tmp30 = Arrays.copyOfRange(s, offset, offset + tmp29);
					offset += tmp29;
					int tmp31;
					tmp31 = ByteBuffer.wrap(Arrays.copyOfRange(tmp30, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					
					tmp25 = new HashMap<>();
					for (int tmp32 = 0; tmp32 < tmp31; tmp32++)
					{
						MaterialType tmp33;
						byte tmp35;
						tmp35 = s[offset];
						offset += Byte.BYTES;
						if (tmp35 == 1)
						{
							byte tmp36;
							tmp36 = s[offset];
							offset += Byte.BYTES;
							tmp33 = MaterialType.of(tmp36);
						}
						else
							tmp33 = null;
						
						Integer tmp34;
						byte tmp37;
						tmp37 = s[offset];
						offset += Byte.BYTES;
						if (tmp37 == 1)
						{
							tmp34 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
							offset += Integer.BYTES;
						}
						else
							tmp34 = null;
						
						tmp25.put(tmp33, tmp34);
					}
				}
				else
					tmp25 = null;
				
				cMixtureFormulas.put(tmp24, tmp25);
			}
		}
		else
			cMixtureFormulas = null;
		
		// deserialize cConstructionDurations
		byte tmp38;
		tmp38 = s[offset];
		offset += Byte.BYTES;
		if (tmp38 == 1)
		{
			byte tmp39;
			tmp39 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp40 = Arrays.copyOfRange(s, offset, offset + tmp39);
			offset += tmp39;
			int tmp41;
			tmp41 = ByteBuffer.wrap(Arrays.copyOfRange(tmp40, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			cConstructionDurations = new HashMap<>();
			for (int tmp42 = 0; tmp42 < tmp41; tmp42++)
			{
				AmmoType tmp43;
				byte tmp45;
				tmp45 = s[offset];
				offset += Byte.BYTES;
				if (tmp45 == 1)
				{
					byte tmp46;
					tmp46 = s[offset];
					offset += Byte.BYTES;
					tmp43 = AmmoType.of(tmp46);
				}
				else
					tmp43 = null;
				
				Integer tmp44;
				byte tmp47;
				tmp47 = s[offset];
				offset += Byte.BYTES;
				if (tmp47 == 1)
				{
					tmp44 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp44 = null;
				
				cConstructionDurations.put(tmp43, tmp44);
			}
		}
		else
			cConstructionDurations = null;
		
		// deserialize cAmmoBoxSizes
		byte tmp48;
		tmp48 = s[offset];
		offset += Byte.BYTES;
		if (tmp48 == 1)
		{
			byte tmp49;
			tmp49 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp50 = Arrays.copyOfRange(s, offset, offset + tmp49);
			offset += tmp49;
			int tmp51;
			tmp51 = ByteBuffer.wrap(Arrays.copyOfRange(tmp50, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			cAmmoBoxSizes = new HashMap<>();
			for (int tmp52 = 0; tmp52 < tmp51; tmp52++)
			{
				AmmoType tmp53;
				byte tmp55;
				tmp55 = s[offset];
				offset += Byte.BYTES;
				if (tmp55 == 1)
				{
					byte tmp56;
					tmp56 = s[offset];
					offset += Byte.BYTES;
					tmp53 = AmmoType.of(tmp56);
				}
				else
					tmp53 = null;
				
				Integer tmp54;
				byte tmp57;
				tmp57 = s[offset];
				offset += Byte.BYTES;
				if (tmp57 == 1)
				{
					tmp54 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp54 = null;
				
				cAmmoBoxSizes.put(tmp53, tmp54);
			}
		}
		else
			cAmmoBoxSizes = null;
		
		return offset;
	}
}
