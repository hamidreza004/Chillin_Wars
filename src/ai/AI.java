package ai;

import java.util.*;

import team.koala.chillin.client.RealtimeAI;

import ks.KSObject;
import ks.models.*;
import ks.commands.*;


public class AI extends RealtimeAI<World, KSObject> {

	private Random random = new Random();
	private int stage = 0;

	public AI(World world) {
		super(world);
	}

	@Override
	public void initialize() {
		System.out.println("initialize");
	}

	@Override
	public void decide() {
		System.out.println("decide");

		var base = this.world.getBases().get(this.mySide);
		var wagent = base.getAgents().get(AgentType.Warehouse);
		var fagent = base.getAgents().get(AgentType.Factory);

		if (stage == 0)
		{
			warehouseAgentMove(true);

			if (base.getCArea().get(wagent.getPosition()) == ECell.Material)
			{
				var materialType = base.getWarehouse().getMaterials().get(wagent.getPosition()).getType();
				if (wagent.getMaterialsBag().get(materialType) == 0)
					warehouseAgentPickMaterial();
			}
			else if (base.getCArea().get(wagent.getPosition()) == ECell.BacklineDelivery)
			{
				warehouseAgentPutMaterial();
				stage++;
			}
		}
		else if (stage == 1)
		{
			if (base.getCArea().get(fagent.getPosition()) == ECell.BacklineDelivery)
			{
				Map<MaterialType, Integer> requiredMaterials = base.getFactory().getCMixtureFormulas().get(AmmoType.RifleBullet);
				factoryAgentPickMaterial(requiredMaterials);
				stage++;
			}
			else
				factoryAgentMove(false);
		}
		else if (stage == 2)
		{
			if (base.getCArea().get(fagent.getPosition()) == ECell.Machine &&
				base.getFactory().getMachines().get(fagent.getPosition()).getStatus() == MachineStatus.Idle)
			{
				factoryAgentPutMaterial(AmmoType.RifleBullet);
				stage++;
			}
			else
				factoryAgentMove(true);
		}
		else if (stage == 3)
		{
			if (base.getFactory().getMachines().get(fagent.getPosition()).getStatus() == MachineStatus.AmmoReady)
			{
				factoryAgentPickAmmo();
				stage++;
			}
		}
		else if (stage == 4)
		{
			if (base.getCArea().get(fagent.getPosition()) == ECell.BacklineDelivery)
			{
				factoryAgentPutAmmo();
				stage++;
			}
			else
				factoryAgentMove(false);
		}
		else if (stage == 5)
		{
			Map<AmmoType, Integer> ammos = Map.of(
				AmmoType.RifleBullet, 1
			);
			warehouseAgentPickAmmo(ammos);
			stage++;
		}
		else if (stage == 6)
		{
			if (base.getCArea().get(wagent.getPosition()) == ECell.FrontlineDelivery)
			{
				warehouseAgentPutAmmo();
				stage = 0;
			}
			else
				warehouseAgentMove(false);
		}
	}
	
	
	// Warehouse Agent Commands

	public void warehouseAgentMove(boolean forward)
	{
		boolean fw = forward;
		this.sendCommand(new Move() {{ agentType = CommandAgentType.Warehouse; forward = fw; }});
	}

	public void warehouseAgentPickMaterial()
	{
		var m = new HashMap<CommandMaterialType, Integer>();
		this.sendCommand(new PickMaterial() {{ agentType = CommandAgentType.Warehouse; materials = m; }});
	}

	public void warehouseAgentPutMaterial()
	{
		this.sendCommand(new PutMaterial() {{ agentType = CommandAgentType.Warehouse; desiredAmmo = CommandAmmoType.RifleBullet; }});
	}

	public void warehouseAgentPickAmmo(Map<AmmoType, Integer> ammos)
	{
		var convertedAmmos = new HashMap<CommandAmmoType, Integer>();
		for (var entry: ammos.entrySet())
			convertedAmmos.put(CommandAmmoType.of(entry.getKey().getValue()), entry.getValue());
		this.sendCommand(new PickAmmo() {{ agentType = CommandAgentType.Warehouse; ammos = convertedAmmos; }});
	}

	public void warehouseAgentPutAmmo()
	{
		this.sendCommand(new PutAmmo() {{ agentType = CommandAgentType.Warehouse; }});
	}
	
	// Factory Agent Commands

	public void factoryAgentMove(boolean forward)
	{
		boolean fw = forward;
		this.sendCommand(new Move() {{ agentType = CommandAgentType.Factory; forward = fw; }});
	}

	public void factoryAgentPickMaterial(Map<MaterialType, Integer> materials)
	{
		var convertedMaterials = new HashMap<CommandMaterialType, Integer>();
		for (var entry: materials.entrySet())
			convertedMaterials.put(CommandMaterialType.of(entry.getKey().getValue()), entry.getValue());
		this.sendCommand(new PickMaterial() {{ agentType = CommandAgentType.Factory; materials = convertedMaterials; }});
	}

	public void factoryAgentPutMaterial(AmmoType desiredAmmo)
	{
		var da = CommandAmmoType.of(desiredAmmo.getValue());
		this.sendCommand(new PutMaterial() {{ agentType = CommandAgentType.Factory; desiredAmmo = da; }});
	}

	public void factoryAgentPickAmmo()
	{
		var a = new HashMap<CommandAmmoType, Integer>();
		this.sendCommand(new PickAmmo() {{ agentType = CommandAgentType.Factory; ammos = a; }});
	}

	public void factoryAgentPutAmmo()
	{
		this.sendCommand(new PutAmmo() {{ agentType = CommandAgentType.Factory; }});
	}
}
