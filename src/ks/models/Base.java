package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Base extends KSObject
{
	protected Map<Position, ECell> cArea;
	protected Map<AgentType, Agent> agents;
	protected List<FrontlineDelivery> frontlineDeliveries;
	protected Warehouse warehouse;
	protected BacklineDelivery backlineDelivery;
	protected Factory factory;
	protected Map<UnitType, Unit> units;
	
	// getters
	
	public Map<Position, ECell> getCArea()
	{
		return this.cArea;
	}
	
	public Map<AgentType, Agent> getAgents()
	{
		return this.agents;
	}
	
	public List<FrontlineDelivery> getFrontlineDeliveries()
	{
		return this.frontlineDeliveries;
	}
	
	public Warehouse getWarehouse()
	{
		return this.warehouse;
	}
	
	public BacklineDelivery getBacklineDelivery()
	{
		return this.backlineDelivery;
	}
	
	public Factory getFactory()
	{
		return this.factory;
	}
	
	public Map<UnitType, Unit> getUnits()
	{
		return this.units;
	}
	
	
	// setters
	
	public void setCArea(Map<Position, ECell> cArea)
	{
		this.cArea = cArea;
	}
	
	public void setAgents(Map<AgentType, Agent> agents)
	{
		this.agents = agents;
	}
	
	public void setFrontlineDeliveries(List<FrontlineDelivery> frontlineDeliveries)
	{
		this.frontlineDeliveries = frontlineDeliveries;
	}
	
	public void setWarehouse(Warehouse warehouse)
	{
		this.warehouse = warehouse;
	}
	
	public void setBacklineDelivery(BacklineDelivery backlineDelivery)
	{
		this.backlineDelivery = backlineDelivery;
	}
	
	public void setFactory(Factory factory)
	{
		this.factory = factory;
	}
	
	public void setUnits(Map<UnitType, Unit> units)
	{
		this.units = units;
	}
	
	
	public Base()
	{
	}
	
	public static final String nameStatic = "Base";
	
	@Override
	public String name() { return "Base"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize cArea
		s.add((byte) ((cArea == null) ? 0 : 1));
		if (cArea != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(cArea.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (Map.Entry<Position, ECell> tmp1 : cArea.entrySet())
			{
				s.add((byte) ((tmp1.getKey() == null) ? 0 : 1));
				if (tmp1.getKey() != null)
				{
					s.addAll(b2B(tmp1.getKey().serialize()));
				}
				
				s.add((byte) ((tmp1.getValue() == null) ? 0 : 1));
				if (tmp1.getValue() != null)
				{
					s.add((byte) (tmp1.getValue().getValue()));
				}
			}
		}
		
		// serialize agents
		s.add((byte) ((agents == null) ? 0 : 1));
		if (agents != null)
		{
			List<Byte> tmp2 = new ArrayList<>();
			tmp2.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(agents.size()).array()));
			while (tmp2.size() > 0 && tmp2.get(tmp2.size() - 1) == 0)
				tmp2.remove(tmp2.size() - 1);
			s.add((byte) tmp2.size());
			s.addAll(tmp2);
			
			for (Map.Entry<AgentType, Agent> tmp3 : agents.entrySet())
			{
				s.add((byte) ((tmp3.getKey() == null) ? 0 : 1));
				if (tmp3.getKey() != null)
				{
					s.add((byte) (tmp3.getKey().getValue()));
				}
				
				s.add((byte) ((tmp3.getValue() == null) ? 0 : 1));
				if (tmp3.getValue() != null)
				{
					s.addAll(b2B(tmp3.getValue().serialize()));
				}
			}
		}
		
		// serialize frontlineDeliveries
		s.add((byte) ((frontlineDeliveries == null) ? 0 : 1));
		if (frontlineDeliveries != null)
		{
			List<Byte> tmp4 = new ArrayList<>();
			tmp4.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(frontlineDeliveries.size()).array()));
			while (tmp4.size() > 0 && tmp4.get(tmp4.size() - 1) == 0)
				tmp4.remove(tmp4.size() - 1);
			s.add((byte) tmp4.size());
			s.addAll(tmp4);
			
			for (FrontlineDelivery tmp5 : frontlineDeliveries)
			{
				s.add((byte) ((tmp5 == null) ? 0 : 1));
				if (tmp5 != null)
				{
					s.addAll(b2B(tmp5.serialize()));
				}
			}
		}
		
		// serialize warehouse
		s.add((byte) ((warehouse == null) ? 0 : 1));
		if (warehouse != null)
		{
			s.addAll(b2B(warehouse.serialize()));
		}
		
		// serialize backlineDelivery
		s.add((byte) ((backlineDelivery == null) ? 0 : 1));
		if (backlineDelivery != null)
		{
			s.addAll(b2B(backlineDelivery.serialize()));
		}
		
		// serialize factory
		s.add((byte) ((factory == null) ? 0 : 1));
		if (factory != null)
		{
			s.addAll(b2B(factory.serialize()));
		}
		
		// serialize units
		s.add((byte) ((units == null) ? 0 : 1));
		if (units != null)
		{
			List<Byte> tmp6 = new ArrayList<>();
			tmp6.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(units.size()).array()));
			while (tmp6.size() > 0 && tmp6.get(tmp6.size() - 1) == 0)
				tmp6.remove(tmp6.size() - 1);
			s.add((byte) tmp6.size());
			s.addAll(tmp6);
			
			for (Map.Entry<UnitType, Unit> tmp7 : units.entrySet())
			{
				s.add((byte) ((tmp7.getKey() == null) ? 0 : 1));
				if (tmp7.getKey() != null)
				{
					s.add((byte) (tmp7.getKey().getValue()));
				}
				
				s.add((byte) ((tmp7.getValue() == null) ? 0 : 1));
				if (tmp7.getValue() != null)
				{
					s.addAll(b2B(tmp7.getValue().serialize()));
				}
			}
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize cArea
		byte tmp8;
		tmp8 = s[offset];
		offset += Byte.BYTES;
		if (tmp8 == 1)
		{
			byte tmp9;
			tmp9 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp10 = Arrays.copyOfRange(s, offset, offset + tmp9);
			offset += tmp9;
			int tmp11;
			tmp11 = ByteBuffer.wrap(Arrays.copyOfRange(tmp10, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			cArea = new HashMap<>();
			for (int tmp12 = 0; tmp12 < tmp11; tmp12++)
			{
				Position tmp13;
				byte tmp15;
				tmp15 = s[offset];
				offset += Byte.BYTES;
				if (tmp15 == 1)
				{
					tmp13 = new Position();
					offset = tmp13.deserialize(s, offset);
				}
				else
					tmp13 = null;
				
				ECell tmp14;
				byte tmp16;
				tmp16 = s[offset];
				offset += Byte.BYTES;
				if (tmp16 == 1)
				{
					byte tmp17;
					tmp17 = s[offset];
					offset += Byte.BYTES;
					tmp14 = ECell.of(tmp17);
				}
				else
					tmp14 = null;
				
				cArea.put(tmp13, tmp14);
			}
		}
		else
			cArea = null;
		
		// deserialize agents
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
			
			agents = new HashMap<>();
			for (int tmp22 = 0; tmp22 < tmp21; tmp22++)
			{
				AgentType tmp23;
				byte tmp25;
				tmp25 = s[offset];
				offset += Byte.BYTES;
				if (tmp25 == 1)
				{
					byte tmp26;
					tmp26 = s[offset];
					offset += Byte.BYTES;
					tmp23 = AgentType.of(tmp26);
				}
				else
					tmp23 = null;
				
				Agent tmp24;
				byte tmp27;
				tmp27 = s[offset];
				offset += Byte.BYTES;
				if (tmp27 == 1)
				{
					tmp24 = new Agent();
					offset = tmp24.deserialize(s, offset);
				}
				else
					tmp24 = null;
				
				agents.put(tmp23, tmp24);
			}
		}
		else
			agents = null;
		
		// deserialize frontlineDeliveries
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
			
			frontlineDeliveries = new ArrayList<>();
			for (int tmp32 = 0; tmp32 < tmp31; tmp32++)
			{
				FrontlineDelivery tmp33;
				byte tmp34;
				tmp34 = s[offset];
				offset += Byte.BYTES;
				if (tmp34 == 1)
				{
					tmp33 = new FrontlineDelivery();
					offset = tmp33.deserialize(s, offset);
				}
				else
					tmp33 = null;
				frontlineDeliveries.add(tmp33);
			}
		}
		else
			frontlineDeliveries = null;
		
		// deserialize warehouse
		byte tmp35;
		tmp35 = s[offset];
		offset += Byte.BYTES;
		if (tmp35 == 1)
		{
			warehouse = new Warehouse();
			offset = warehouse.deserialize(s, offset);
		}
		else
			warehouse = null;
		
		// deserialize backlineDelivery
		byte tmp36;
		tmp36 = s[offset];
		offset += Byte.BYTES;
		if (tmp36 == 1)
		{
			backlineDelivery = new BacklineDelivery();
			offset = backlineDelivery.deserialize(s, offset);
		}
		else
			backlineDelivery = null;
		
		// deserialize factory
		byte tmp37;
		tmp37 = s[offset];
		offset += Byte.BYTES;
		if (tmp37 == 1)
		{
			factory = new Factory();
			offset = factory.deserialize(s, offset);
		}
		else
			factory = null;
		
		// deserialize units
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
			
			units = new HashMap<>();
			for (int tmp42 = 0; tmp42 < tmp41; tmp42++)
			{
				UnitType tmp43;
				byte tmp45;
				tmp45 = s[offset];
				offset += Byte.BYTES;
				if (tmp45 == 1)
				{
					byte tmp46;
					tmp46 = s[offset];
					offset += Byte.BYTES;
					tmp43 = UnitType.of(tmp46);
				}
				else
					tmp43 = null;
				
				Unit tmp44;
				byte tmp47;
				tmp47 = s[offset];
				offset += Byte.BYTES;
				if (tmp47 == 1)
				{
					tmp44 = new Unit();
					offset = tmp44.deserialize(s, offset);
				}
				else
					tmp44 = null;
				
				units.put(tmp43, tmp44);
			}
		}
		else
			units = null;
		
		return offset;
	}
}
