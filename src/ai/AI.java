package ai;

import java.util.*;

import team.koala.chillin.client.RealtimeAI;
import ks.KSObject;
import ks.models.*;


public class AI extends RealtimeAI<World, KSObject> {

    public AI(World world) {
        super(world);
    }


    Base base;
    FAgent fAgent;
    WAgent wAgent;
    int turn = 0;

    @Override
    public void initialize() {
        System.out.println("initialize");
        base = this.world.getBases().get(this.mySide);
        fAgent = new FAgent(base, this);
        wAgent = new WAgent(base, this);
    }

    @Override
    public void decide() {
        System.out.println("decide");
        turn++;
        base = this.world.getBases().get(this.mySide);
        wAgent.updateResources(base, this);
        fAgent.updateResources(base, this);

        HashMap<MaterialType, Integer> materialSample = new HashMap<>();
        AgentHelper.assignMaterialMap(materialSample, 0, 0, 0, 0, 0);
        if (turn == 40 || turn == 80)
            AgentHelper.assignMaterialMap(materialSample, 5, 5, 5, 0, 0);
        AgentHelper.addToMaterialMap(wAgent.wantedMaterial, materialSample);
        wAgent.runWareHouseAgent();

        HashMap<AmmoType, Integer> ammoSample = new HashMap<>();
        if (turn == 70)
        {
            AgentHelper.assignAmmoMap(ammoSample, 2, 0, 1, 0, 0);
            AgentHelper.addToAmmoMap(fAgent.wantedAmmo, ammoSample);
            AgentHelper.addToAmmoMap(fAgent.fagentWantedAmmo, ammoSample);
        }
        AgentHelper.assignAmmoMap(ammoSample, 0, 0, 0, 0, 0);
        AgentHelper.addToAmmoMap(fAgent.wantedAmmo, ammoSample);
        AgentHelper.addToAmmoMap(fAgent.fagentWantedAmmo, ammoSample);
        fAgent.runFactoryAgent();
    }
}