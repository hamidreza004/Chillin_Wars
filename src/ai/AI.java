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

        wantedMaterial.put(MaterialType.Powder, 5);
        wantedMaterial.put(MaterialType.Iron, 5);
        wantedMaterial.put(MaterialType.Carbon, 5);
        wantedMaterial.put(MaterialType.Gold, 0);
        wantedMaterial.put(MaterialType.Shell, 0);

        wantedAmmo.put(AmmoType.RifleBullet, 1);
        wantedAmmo.put(AmmoType.HMGBullet, 2);
        wantedAmmo.put(AmmoType.GoldenTankShell, 0);
        wantedAmmo.put(AmmoType.MortarShell, 0);
        wantedAmmo.put(AmmoType.TankShell, 0);

    }


    Agent wagent, fagent;
    Base base;
    HashMap<MaterialType, Integer> wantedMaterial = new HashMap<>();
    HashMap<AmmoType, Integer> wantedAmmo = new HashMap<>();
    int turn = 0;
    Integer lastMachineIndex;

    public void addToWantedMaterial(HashMap<MaterialType, Integer> material) {
        for (MaterialType materialType : MaterialType.values())
            wantedMaterial.put(materialType, wantedMaterial.get(materialType) + material.get(materialType));
    }

    public void addToWantedAmmo(HashMap<AmmoType, Integer> ammo) {
        for (AmmoType ammoType : AmmoType.values())
            wantedAmmo.put(ammoType, wantedAmmo.get(ammoType) + ammo.get(ammoType));
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

    public HashMap<MaterialType, Integer> fagentPickMaterialList() {
        HashMap<MaterialType, Integer> selected = new HashMap<>();
        int empty = fagent.getCMaterialsBagCapacity();
        for (MaterialType materialType : MaterialType.values()) {
            int a = min(base.getBacklineDelivery().getMaterials().get(materialType), empty);
            selected.put(materialType, a);
            empty = empty - a;
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
        materialSample.put(MaterialType.Powder, 0);
        materialSample.put(MaterialType.Iron, 0);
        materialSample.put(MaterialType.Carbon, 0);
        materialSample.put(MaterialType.Gold, 0);
        materialSample.put(MaterialType.Shell, 0);
        if (turn == 40) {
            materialSample.put(MaterialType.Powder, 5);
            materialSample.put(MaterialType.Iron, 5);
            materialSample.put(MaterialType.Carbon, 5);
        }
        if (turn == 80) {
            materialSample.put(MaterialType.Powder, 5);
            materialSample.put(MaterialType.Iron, 5);
            materialSample.put(MaterialType.Carbon, 5);
        }
        addToWantedMaterial(materialSample);
        runWareHouseAgent();

        HashMap<AmmoType, Integer> ammoSample = new HashMap<>();
        ammoSample.put(AmmoType.RifleBullet, 0);
        ammoSample.put(AmmoType.HMGBullet, 0);
        ammoSample.put(AmmoType.GoldenTankShell, 0);
        ammoSample.put(AmmoType.MortarShell, 0);
        ammoSample.put(AmmoType.TankShell, 0);
        addToWantedAmmo(ammoSample);
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
    HashMap<AmmoType, Integer> preparingAmmo = new HashMap<>();

    void runFactoryAgent() {
        if (base.getCArea().get(fagent.getPosition()) == ECell.BacklineDelivery) {
            forwardFagent = true;
            if (getSumBagAmmo(fagent.getAmmosBag()) > 0)
                factoryAgentPutAmmo();
            else if (getSumBagMaterial(base.getBacklineDelivery().getMaterials()) > 0)
                factoryAgentPickMaterial(fagentPickMaterialList());
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
        } else if (base.getCArea().get(fagent.getPosition()) == ECell.Machine) {
            if (fagent.getPosition().getIndex() == lastMachineIndex)
                forwardFagent = false;
            Machine machine = base.getFactory().getMachines().get(fagent.getPosition());
            var machineStatus = machine.getStatus();
            if (machineStatus == MachineStatus.AmmoReady) {
                preparingAmmo.put(machine.getCurrentAmmo(), preparingAmmo.get(machine.getCurrentAmmo()) + 1);
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
