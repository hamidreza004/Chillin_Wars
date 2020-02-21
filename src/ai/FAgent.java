package ai;

import ks.commands.*;
import ks.models.*;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class FAgent {

    boolean forwardFagent = false;
    HashMap<AmmoType, Integer> wantedAmmo = new HashMap<>();
    HashMap<AmmoType, Integer> fagentWantedAmmo = new HashMap<>();
    Integer lastMachineIndex;
    Agent gameAgent;
    Base base;
    AI ai;

    FAgent(Base base, AI ai) {
        updateResources(base, ai);
        this.lastMachineIndex = gameAgent.getPosition().getIndex();
        for (Machine machine : base.getFactory().getMachines().values())
            if (machine != null) {
                if (abs(machine.getPosition().getIndex().intValue() - gameAgent.getPosition().getIndex().intValue()) > abs(lastMachineIndex.intValue() - gameAgent.getPosition().getIndex().intValue()))
                    lastMachineIndex = machine.getPosition().getIndex();
            }
        AgentHelper.assignAmmoMap(wantedAmmo, 1, 0, 2, 0, 0);
        AgentHelper.assignAmmoMap(fagentWantedAmmo, 0, 0, 0, 0, 0);
        AgentHelper.addToAmmoMap(fagentWantedAmmo, wantedAmmo);
    }

    public void updateResources(Base base, AI ai)
    {
        this.gameAgent = base.getAgents().get(AgentType.Factory);
        this.base = base;
        this.ai = ai;
    }


    public HashMap<MaterialType, Integer> fagentPickMaterialList() {
        HashMap<MaterialType, Integer> selected = new HashMap<>();
        AgentHelper.assignMaterialMap(selected, 0, 0, 0, 0, 0);
        int empty = gameAgent.getCMaterialsBagCapacity();
        for (AmmoType ammoType : AmmoType.values()) {
            Map<MaterialType, Integer> requireMaterial = base.getFactory().getCMixtureFormulas().get(ammoType);
            while (fagentWantedAmmo.get(ammoType) > 0)
                if (AgentHelper.isSubBag(requireMaterial, base.getBacklineDelivery().getMaterials()) && AgentHelper.getSumBagMaterial(requireMaterial) <= empty) {
                    AgentHelper.addToMaterialMap(selected, requireMaterial);
                    fagentWantedAmmo.put(ammoType, fagentWantedAmmo.get(ammoType) - 1);
                    empty -= AgentHelper.getSumBagMaterial(requireMaterial);
                }

        }
        return selected;
    }

    void runFactoryAgent() {
        if (base.getCArea().get(gameAgent.getPosition()) == ECell.BacklineDelivery) {
            forwardFagent = true;
            if (AgentHelper.getSumBagAmmo(gameAgent.getAmmosBag()) > 0)
                factoryAgentPutAmmo();
            else {
                var pickMaterialList = fagentPickMaterialList();
                if (AgentHelper.getSumBagMaterial(pickMaterialList) > 0)
                    factoryAgentPickMaterial(pickMaterialList);
                else {
                    boolean canBuild = false;
                    for (AmmoType ammoType : AmmoType.values()) {
                        Map<MaterialType, Integer> requireMaterial = base.getFactory().getCMixtureFormulas().get(ammoType);
                        if (AgentHelper.isSubBag(requireMaterial, gameAgent.getMaterialsBag())) {
                            canBuild = true;
                            break;
                        }
                    }
                    for (Machine machine : base.getFactory().getMachines().values()) {
                        if (machine == null)
                            continue;
                        if (machine.getStatus() == MachineStatus.AmmoReady || (machine.getStatus() == MachineStatus.Idle && canBuild) || (machine.getStatus() == MachineStatus.Working && abs(machine.getPosition().getIndex().intValue() - gameAgent.getPosition().getIndex().intValue()) >= machine.getConstructionRemTime())) {
                            factoryAgentMove(forwardFagent);
                            break;
                        }
                    }
                }
            }
        } else if (base.getCArea().get(gameAgent.getPosition()) == ECell.Machine) {
            if (gameAgent.getPosition().getIndex() == lastMachineIndex)
                forwardFagent = false;
            Machine machine = base.getFactory().getMachines().get(gameAgent.getPosition());
            var machineStatus = machine.getStatus();
            if (machineStatus == MachineStatus.AmmoReady) {
                factoryAgentPickAmmo();
            } else if (machineStatus == MachineStatus.Idle) {
                boolean isBuild = false;
                for (AmmoType ammoType : AmmoType.values()) {
                    Map<MaterialType, Integer> requireMaterial = base.getFactory().getCMixtureFormulas().get(ammoType);
                    if (wantedAmmo.get(ammoType) > 0 && AgentHelper.isSubBag(requireMaterial, gameAgent.getMaterialsBag())) {
                        factoryAgentPutMaterial(ammoType);
                        wantedAmmo.put(ammoType, wantedAmmo.get(ammoType) - 1);
                        isBuild = true;
                        break;
                    }
                }
                if (!isBuild)
                    factoryAgentMove(forwardFagent);
            } else if (machineStatus == MachineStatus.Working) {
                if (machine.getConstructionRemTime() > 1)
                    factoryAgentMove(forwardFagent);
            }
        } else if (base.getCArea().get(gameAgent.getPosition()) == ECell.Empty) {
            factoryAgentMove(forwardFagent);
        }
    }

    public void factoryAgentMove(boolean forward) {
        boolean fw = forward;
        ai.sendCommand(new Move() {{
            agentType = CommandAgentType.Factory;
            forward = fw;
        }});
    }

    public void factoryAgentPickMaterial(Map<MaterialType, Integer> materials) {
        var convertedMaterials = new HashMap<CommandMaterialType, Integer>();
        for (var entry : materials.entrySet())
            convertedMaterials.put(CommandMaterialType.of(entry.getKey().getValue()), entry.getValue());
        ai.sendCommand(new PickMaterial() {{
            agentType = CommandAgentType.Factory;
            materials = convertedMaterials;
        }});
    }

    public void factoryAgentPutMaterial(AmmoType desiredAmmo) {
        var da = CommandAmmoType.of(desiredAmmo.getValue());
        ai.sendCommand(new PutMaterial() {{
            agentType = CommandAgentType.Factory;
            desiredAmmo = da;
        }});
    }

    public void factoryAgentPickAmmo() {
        var a = new HashMap<CommandAmmoType, Integer>();
        ai.sendCommand(new PickAmmo() {{
            agentType = CommandAgentType.Factory;
            ammos = a;
        }});
    }

    public void factoryAgentPutAmmo() {
        ai.sendCommand(new PutAmmo() {{
            agentType = CommandAgentType.Factory;
        }});
    }
}
