package ai;

import java.util.*;

import team.koala.chillin.client.RealtimeAI;

import ks.KSObject;
import ks.models.*;
import ks.commands.*;

import static java.lang.Math.abs;
import static java.lang.Math.min;


public class AI extends RealtimeAI<World, KSObject> {

    //private Random random = new Random();

    public AI(World world) {
        super(world);
    }

    @Override
    public void initialize() {
        System.out.println("initialize");

        base = this.world.getBases().get(this.mySide);
        fagent = base.getAgents().get(AgentType.Factory);
        lastMachineIndex = fagent.getPosition().getIndex();
        for (Machine machine : base.getFactory().getMachines().values())
            if (machine != null) {
                if (abs(machine.getPosition().getIndex().intValue() - fagent.getPosition().getIndex().intValue()) > abs(lastMachineIndex.intValue() - fagent.getPosition().getIndex().intValue()))
                    lastMachineIndex = machine.getPosition().getIndex();
            }

        assignMaterialMap(wantedMaterial, 5, 5, 5, 0, 0);
        assignAmmoMap(wantedAmmo, 1, 0, 2, 0, 0);
        assignAmmoMap(fagentWantedAmmo, 0, 0, 0, 0, 0);
        addToAmmoMap(fagentWantedAmmo, wantedAmmo);

    }

    public void assignMaterialMap(Map<MaterialType, Integer> material, Integer powder, Integer iron, Integer carbon, Integer gold, Integer shell) {
        material.put(MaterialType.Powder, powder);
        material.put(MaterialType.Iron, iron);
        material.put(MaterialType.Carbon, carbon);
        material.put(MaterialType.Gold, gold);
        material.put(MaterialType.Shell, shell);
    }

    public void assignAmmoMap(Map<AmmoType, Integer> ammo, Integer rifleBullet, Integer tankShell, Integer hmgBullet, Integer mortarShell, Integer goldenTankShell) {
        ammo.put(AmmoType.RifleBullet, rifleBullet);
        ammo.put(AmmoType.TankShell, tankShell);
        ammo.put(AmmoType.HMGBullet, hmgBullet);
        ammo.put(AmmoType.MortarShell, mortarShell);
        ammo.put(AmmoType.GoldenTankShell, goldenTankShell);
    }

    Agent wagent, fagent;
    Base base;
    HashMap<MaterialType, Integer> wantedMaterial = new HashMap<>();
    HashMap<AmmoType, Integer> wantedAmmo = new HashMap<>();
    int turn = 0;
    Integer lastMachineIndex;

    public void addToMaterialMap(Map<MaterialType, Integer> main, Map<MaterialType, Integer> toAdd) {
        for (MaterialType materialType : MaterialType.values())
            if (toAdd.containsKey(materialType))
                main.put(materialType, main.get(materialType) + toAdd.get(materialType));
    }

    public void addToAmmoMap(Map<AmmoType, Integer> main, Map<AmmoType, Integer> toAdd) {
        for (AmmoType ammoType : AmmoType.values())
            main.put(ammoType, main.get(ammoType) + toAdd.get(ammoType));
    }


    public HashMap<AmmoType, Integer> wagentPickAmmoList() {
        HashMap<AmmoType, Integer> selected = new HashMap<>();
        int empty = wagent.getCAmmosBagCapacity();
        for (AmmoType ammoType : AmmoType.values()) {
            int a = min(base.getBacklineDelivery().getAmmos().get(ammoType), empty);
            selected.put(ammoType, a);
            empty = empty - a;
        }

        return selected;
    }

    HashMap<AmmoType, Integer> fagentWantedAmmo = new HashMap<>();

    public HashMap<MaterialType, Integer> fagentPickMaterialList() {
        HashMap<MaterialType, Integer> selected = new HashMap<>();
        assignMaterialMap(selected, 0, 0, 0, 0, 0);
        int empty = fagent.getCMaterialsBagCapacity();
        for (AmmoType ammoType : AmmoType.values()) {
            Map<MaterialType, Integer> requireMaterial = base.getFactory().getCMixtureFormulas().get(ammoType);
            while (fagentWantedAmmo.get(ammoType) > 0)
                if (isSubBag(requireMaterial, base.getBacklineDelivery().getMaterials()) && getSumBagMaterial(requireMaterial) <= empty) {
                    addToMaterialMap(selected, requireMaterial);
                    fagentWantedAmmo.put(ammoType, fagentWantedAmmo.get(ammoType) - 1);
                    empty -= getSumBagMaterial(requireMaterial);
                }

        }
        return selected;
    }

    @Override
    public void decide() {
        System.out.println("decide");
        turn++;
        base = this.world.getBases().get(this.mySide);
        wagent = base.getAgents().get(AgentType.Warehouse);
        fagent = base.getAgents().get(AgentType.Factory);

        HashMap<MaterialType, Integer> materialSample = new HashMap<>();
        assignMaterialMap(materialSample, 0, 0, 0, 0, 0);
        if (turn == 40 || turn == 80)
            assignMaterialMap(materialSample, 5, 5, 5, 0, 0);
        addToMaterialMap(wantedMaterial, materialSample);
        runWareHouseAgent();

        HashMap<AmmoType, Integer> ammoSample = new HashMap<>();
        assignAmmoMap(ammoSample, 0, 0, 0, 0, 0);
        addToAmmoMap(wantedAmmo, ammoSample);
        addToAmmoMap(fagentWantedAmmo, ammoSample);
        runFactoryAgent();
    }

    boolean forwardWagent = true;

    public void runWareHouseAgent() {
        if (base.getCArea().get(wagent.getPosition()) == ECell.BacklineDelivery) {
            if (getSumBagMaterial(wagent.getMaterialsBag()) > 0)
                warehouseAgentPutMaterial();
            else if (getSumBagAmmo(base.getBacklineDelivery().getAmmos()) > 0) {
                warehouseAgentPickAmmo(wagentPickAmmoList());
            } else {
                forwardWagent = false;
                warehouseAgentMove(false);
            }
        } else if (base.getCArea().get(wagent.getPosition()) == ECell.FrontlineDelivery) {
            forwardWagent = true;
            if (getSumBagAmmo(wagent.getAmmosBag()) > 0)
                warehouseAgentPutAmmo();
            else
                warehouseAgentMove(forwardWagent);
        } else if (base.getCArea().get(wagent.getPosition()) == ECell.Material) {
            Material material = base.getWarehouse().getMaterials().get(wagent.getPosition());
            var materialType = material.getType();
            if (wantedMaterial.get(materialType) > 0 && material.getCount() > 0 && forwardWagent && getSumBagMaterial(wagent.getMaterialsBag()) < wagent.getCMaterialsBagCapacity()) {
                warehouseAgentPickMaterial();
                wantedMaterial.put(materialType, wantedMaterial.get(materialType) - 1);
            } else
                warehouseAgentMove(forwardWagent);
        } else
            warehouseAgentMove(forwardWagent);
    }

    boolean forwardFagent = false;

    void runFactoryAgent() {
        if (base.getCArea().get(fagent.getPosition()) == ECell.BacklineDelivery) {
            forwardFagent = true;
            if (getSumBagAmmo(fagent.getAmmosBag()) > 0)
                factoryAgentPutAmmo();
            else {
                var pickMaterialList = fagentPickMaterialList();
                if (getSumBagMaterial(pickMaterialList) > 0)
                    factoryAgentPickMaterial(pickMaterialList);
                else {
                    boolean canBuild = false;
                    for (AmmoType ammoType : AmmoType.values()) {
                        Map<MaterialType, Integer> requireMaterial = base.getFactory().getCMixtureFormulas().get(ammoType);
                        if (isSubBag(requireMaterial, fagent.getMaterialsBag())) {
                            canBuild = true;
                            break;
                        }
                    }
                    for (Machine machine : base.getFactory().getMachines().values()) {
                        if (machine == null)
                            continue;
                        if (machine.getStatus() == MachineStatus.AmmoReady || (machine.getStatus() == MachineStatus.Idle && canBuild) || (machine.getStatus() == MachineStatus.Working && abs(machine.getPosition().getIndex().intValue() - fagent.getPosition().getIndex().intValue()) >= machine.getConstructionRemTime())) {
                            factoryAgentMove(forwardFagent);
                            break;
                        }
                    }
                }
            }
        } else if (base.getCArea().get(fagent.getPosition()) == ECell.Machine) {
            if (fagent.getPosition().getIndex() == lastMachineIndex)
                forwardFagent = false;
            Machine machine = base.getFactory().getMachines().get(fagent.getPosition());
            var machineStatus = machine.getStatus();
            if (machineStatus == MachineStatus.AmmoReady) {
                factoryAgentPickAmmo();
            } else if (machineStatus == MachineStatus.Idle) {
                boolean isBuild = false;
                for (AmmoType ammoType : AmmoType.values()) {
                    Map<MaterialType, Integer> requireMaterial = base.getFactory().getCMixtureFormulas().get(ammoType);
                    if (wantedAmmo.get(ammoType) > 0 && isSubBag(requireMaterial, fagent.getMaterialsBag())) {
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
        } else if (base.getCArea().get(fagent.getPosition()) == ECell.Empty) {
            factoryAgentMove(forwardFagent);
        }
    }

    public int getSumBagMaterial(Map<MaterialType, Integer> bag) {
        int sum = 0;
        for (MaterialType materialType : MaterialType.values())
            if (bag.containsKey(materialType))
                sum += bag.get(materialType);
        return sum;
    }

    public int getSumBagAmmo(Map<AmmoType, Integer> bag) {
        int sum = 0;
        for (AmmoType ammoType : AmmoType.values())
            sum += bag.get(ammoType);
        return sum;
    }

    public boolean isSubBag(Map<MaterialType, Integer> sub, Map<MaterialType, Integer> mother) {
        for (MaterialType materialType : MaterialType.values()) {
            if (!sub.containsKey(materialType))
                continue;
            if (sub.get(materialType) > mother.get(materialType))
                return false;
        }
        return true;
    }

    // Warehouse Agent Commands

    public void warehouseAgentMove(boolean forward) {
        boolean fw = forward;
        this.sendCommand(new Move() {{
            agentType = CommandAgentType.Warehouse;
            forward = fw;
        }});
    }

    public void warehouseAgentPickMaterial() {
        var m = new HashMap<CommandMaterialType, Integer>();
        this.sendCommand(new PickMaterial() {{
            agentType = CommandAgentType.Warehouse;
            materials = m;
        }});
    }

    public void warehouseAgentPutMaterial() {
        this.sendCommand(new PutMaterial() {{
            agentType = CommandAgentType.Warehouse;
            desiredAmmo = CommandAmmoType.RifleBullet;
        }});
    }

    public void warehouseAgentPickAmmo(Map<AmmoType, Integer> ammos) {
        var convertedAmmos = new HashMap<CommandAmmoType, Integer>();
        for (var entry : ammos.entrySet())
            convertedAmmos.put(CommandAmmoType.of(entry.getKey().getValue()), entry.getValue());
        this.sendCommand(new PickAmmo() {{
            agentType = CommandAgentType.Warehouse;
            ammos = convertedAmmos;
        }});
    }

    public void warehouseAgentPutAmmo() {
        this.sendCommand(new PutAmmo() {{
            agentType = CommandAgentType.Warehouse;
        }});
    }

    // Factory Agent Commands

    public void factoryAgentMove(boolean forward) {
        boolean fw = forward;
        this.sendCommand(new Move() {{
            agentType = CommandAgentType.Factory;
            forward = fw;
        }});
    }

    public void factoryAgentPickMaterial(Map<MaterialType, Integer> materials) {
        var convertedMaterials = new HashMap<CommandMaterialType, Integer>();
        for (var entry : materials.entrySet())
            convertedMaterials.put(CommandMaterialType.of(entry.getKey().getValue()), entry.getValue());
        this.sendCommand(new PickMaterial() {{
            agentType = CommandAgentType.Factory;
            materials = convertedMaterials;
        }});
    }

    public void factoryAgentPutMaterial(AmmoType desiredAmmo) {
        var da = CommandAmmoType.of(desiredAmmo.getValue());
        this.sendCommand(new PutMaterial() {{
            agentType = CommandAgentType.Factory;
            desiredAmmo = da;
        }});
    }

    public void factoryAgentPickAmmo() {
        var a = new HashMap<CommandAmmoType, Integer>();
        this.sendCommand(new PickAmmo() {{
            agentType = CommandAgentType.Factory;
            ammos = a;
        }});
    }

    public void factoryAgentPutAmmo() {
        this.sendCommand(new PutAmmo() {{
            agentType = CommandAgentType.Factory;
        }});
    }
}
