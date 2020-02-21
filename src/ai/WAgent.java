package ai;

import ks.commands.*;
import ks.models.*;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public class WAgent {

    boolean forwardWagent = true;
    HashMap<MaterialType, Integer> wantedMaterial = new HashMap<>();
    Agent gameAgent;
    Base base;
    AI ai;

    WAgent(Base base, AI ai){
        updateResources(base, ai);
        AgentHelper.assignMaterialMap(wantedMaterial, 5, 5, 5, 0, 0);
    }

    public void updateResources(Base base, AI ai)
    {
        this.gameAgent = base.getAgents().get(AgentType.Warehouse);
        this.base = base;
        this.ai = ai;
    }

    public HashMap<AmmoType, Integer> wagentPickAmmoList() {
        HashMap<AmmoType, Integer> selected = new HashMap<>();
        int empty = gameAgent.getCAmmosBagCapacity();
        for (AmmoType ammoType : AmmoType.values()) {
            int a = min(base.getBacklineDelivery().getAmmos().get(ammoType), empty);
            selected.put(ammoType, a);
            empty = empty - a;
        }

        return selected;
    }

    public void runWareHouseAgent() {
        if (base.getCArea().get(gameAgent.getPosition()) == ECell.BacklineDelivery) {
            if (AgentHelper.getSumBagMaterial(gameAgent.getMaterialsBag()) > 0)
                warehouseAgentPutMaterial();
            else if (AgentHelper.getSumBagAmmo(base.getBacklineDelivery().getAmmos()) > 0) {
                warehouseAgentPickAmmo(wagentPickAmmoList());
            } else {
                forwardWagent = false;
                warehouseAgentMove(false);
            }
        } else if (base.getCArea().get(gameAgent.getPosition()) == ECell.FrontlineDelivery) {
            forwardWagent = true;
            if (AgentHelper.getSumBagAmmo(gameAgent.getAmmosBag()) > 0)
                warehouseAgentPutAmmo();
            else
                warehouseAgentMove(forwardWagent);
        } else if (base.getCArea().get(gameAgent.getPosition()) == ECell.Material) {
            Material material = base.getWarehouse().getMaterials().get(gameAgent.getPosition());
            var materialType = material.getType();
            if (wantedMaterial.get(materialType) > 0 && material.getCount() > 0 && forwardWagent && AgentHelper.getSumBagMaterial(gameAgent.getMaterialsBag()) < gameAgent.getCMaterialsBagCapacity()) {
                warehouseAgentPickMaterial();
                wantedMaterial.put(materialType, wantedMaterial.get(materialType) - 1);
            } else
                warehouseAgentMove(forwardWagent);
        } else
            warehouseAgentMove(forwardWagent);
    }


    public void warehouseAgentMove(boolean forward) {
        boolean fw = forward;
        ai.sendCommand(new Move() {{
            agentType = CommandAgentType.Warehouse;
            forward = fw;
        }});
    }

    public void warehouseAgentPickMaterial() {
        var m = new HashMap<CommandMaterialType, Integer>();
        ai.sendCommand(new PickMaterial() {{
            agentType = CommandAgentType.Warehouse;
            materials = m;
        }});
    }

    public void warehouseAgentPutMaterial() {
        ai.sendCommand(new PutMaterial() {{
            agentType = CommandAgentType.Warehouse;
            desiredAmmo = CommandAmmoType.RifleBullet;
        }});
    }

    public void warehouseAgentPickAmmo(Map<AmmoType, Integer> ammos) {
        var convertedAmmos = new HashMap<CommandAmmoType, Integer>();
        for (var entry : ammos.entrySet())
            convertedAmmos.put(CommandAmmoType.of(entry.getKey().getValue()), entry.getValue());
        ai.sendCommand(new PickAmmo() {{
            agentType = CommandAgentType.Warehouse;
            ammos = convertedAmmos;
        }});
    }

    public void warehouseAgentPutAmmo() {
        ai.sendCommand(new PutAmmo() {{
            agentType = CommandAgentType.Warehouse;
        }});
    }
}
