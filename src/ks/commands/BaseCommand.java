package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class BaseCommand extends KSObject
{
	protected CommandAgentType agentType;
	
	// getters
	
	public CommandAgentType getAgentType()
	{
		return this.agentType;
	}
	
	
	// setters
	
	public void setAgentType(CommandAgentType agentType)
	{
		this.agentType = agentType;
	}
	
	
	public BaseCommand()
	{
	}
	
	public static final String nameStatic = "BaseCommand";
	
	@Override
	public String name() { return "BaseCommand"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize agentType
		s.add((byte) ((agentType == null) ? 0 : 1));
		if (agentType != null)
		{
			s.add((byte) (agentType.getValue()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize agentType
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			byte tmp1;
			tmp1 = s[offset];
			offset += Byte.BYTES;
			agentType = CommandAgentType.of(tmp1);
		}
		else
			agentType = null;
		
		return offset;
	}
}
