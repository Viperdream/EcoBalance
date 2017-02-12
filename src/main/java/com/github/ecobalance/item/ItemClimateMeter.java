package com.github.ecobalance.item;

import java.util.ArrayList;
import java.util.List;

import com.github.ecobalance.EcoBalance;
import com.github.ecobalance.Pollution;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ItemClimateMeter extends Item{

	
	public ItemClimateMeter() {
		this.setUnlocalizedName("climateMeter");
		this.setCreativeTab(EcoBalance.ecoTab);
		this.setTextureName(EcoBalance.MODID + ":climateMeter");
		this.setMaxStackSize(1);
		
		GameRegistry.registerItem(this, "Climate Meter");
	}

	/*public static final void initClimateMeter(){
		climateMeter = new Item();
		
		climateMeter.setUnlocalizedName("climateMeter").
			setCreativeTab(EcoBalance.ecoTab).
			setTextureName(EcoBalance.MODID + ":climateMeter")
			.setMaxStackSize(1);

		GameRegistry.registerItem(climateMeter, "Climate Meter");
	}*/
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World w, EntityPlayer p){
		if(!w.isRemote){
			EcoBalance.logger.info("Right clicking climate meter!");
			System.out.println("Clicked climate meter");
			
			Chunk c = w.getChunkFromChunkCoords(p.chunkCoordX, p.chunkCoordZ);
			Double pollution = Pollution.getPollution(c);
			
			p.addChatComponentMessage(new ChatComponentText(pollution.toString()));
		}
		return itemStack;
		
	}
}
