package ai;

import ks.models.AmmoType;
import ks.models.MaterialType;

import java.util.Map;

public class AgentHelper {

    public static void printBag(Map<Object, Integer> bag){
        for(var obj: bag.keySet())
            System.out.println(obj + ":" + bag.get(obj));
        System.out.flush();
    }

    public static void addToMaterialMap(Map<MaterialType, Integer> main, Map<MaterialType, Integer> toAdd) {
        for (MaterialType materialType : MaterialType.values())
            if (toAdd.containsKey(materialType))
                main.put(materialType, main.get(materialType) + toAdd.get(materialType));
    }

    public static void addToAmmoMap(Map<AmmoType, Integer> main, Map<AmmoType, Integer> toAdd) {
        for (AmmoType ammoType : AmmoType.values())
            main.put(ammoType, main.get(ammoType) + toAdd.get(ammoType));
    }

    public static void assignMaterialMap(Map<MaterialType, Integer> material, Integer powder, Integer iron, Integer carbon, Integer gold, Integer shell) {
        material.put(MaterialType.Powder, powder);
        material.put(MaterialType.Iron, iron);
        material.put(MaterialType.Carbon, carbon);
        material.put(MaterialType.Gold, gold);
        material.put(MaterialType.Shell, shell);
    }

    public static void assignAmmoMap(Map<AmmoType, Integer> ammo, Integer rifleBullet, Integer tankShell, Integer hmgBullet, Integer mortarShell, Integer goldenTankShell) {
        ammo.put(AmmoType.RifleBullet, rifleBullet);
        ammo.put(AmmoType.TankShell, tankShell);
        ammo.put(AmmoType.HMGBullet, hmgBullet);
        ammo.put(AmmoType.MortarShell, mortarShell);
        ammo.put(AmmoType.GoldenTankShell, goldenTankShell);
    }

    public static int getSumBagMaterial(Map<MaterialType, Integer> bag) {
        int sum = 0;
        for (MaterialType materialType : MaterialType.values())
            if (bag.containsKey(materialType))
                sum += bag.get(materialType);
        return sum;
    }

    public static int getSumBagAmmo(Map<AmmoType, Integer> bag) {
        int sum = 0;
        for (AmmoType ammoType : AmmoType.values())
            sum += bag.get(ammoType);
        return sum;
    }

    public static boolean isSubBag(Map<MaterialType, Integer> sub, Map<MaterialType, Integer> mother) {
        for (MaterialType materialType : MaterialType.values()) {
            if (!sub.containsKey(materialType))
                continue;
            if (sub.get(materialType) > mother.get(materialType))
                return false;
        }
        return true;
    }
}
