package com.github.ecobalance.item;

import com.github.ecobalance.EcoBalance;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class EcoTab extends CreativeTabs{
	public EcoTab(int id, String name){
		super(id, name);
	}

	@Override
	public Item getTabIconItem() {
		return Items.apple;
	}
	
	public String getTranslatedTabLabel(){
		return EcoBalance.MODNAME;
	}
}
