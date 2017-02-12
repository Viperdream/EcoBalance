package com.github.ecobalance.item;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryNamespaced;

public class EcoBalanceItem {

	Item climateMeter = new Item();
	
	public static final void registerEcoBalanceItems(){
		
		Item climateMeter = new ItemClimateMeter();
		
		
	}
	
}
