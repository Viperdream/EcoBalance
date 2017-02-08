package com.github.ecobalance.handlers;

import com.github.ecobalance.Pollution;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PlayerInteractHandler {
	@SubscribeEvent 
	public void onPlayerRightClick(PlayerInteractEvent e){
		Action action = e.action;
		EntityPlayer p = e.entityPlayer;
		ItemStack i;
		Integer heldId;
		
		if(p.getEquipmentInSlot(0) != null){
			i = p.getEquipmentInSlot(0);
			String iName = i.getUnlocalizedName();
			System.out.println("[EcoBalance]: name: " + iName);
			
			/**this serves for debugging, the climate meter will call the method to check the pollution in the whole world
			**/
			if(((action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK)) ){ 
				System.out.print("[EcoBalance]: item in hand: " + p.getEquipmentInSlot(0)); //returns [EcoBalance]: item in hand: 1xitem.climateMeter@0 for the climate meter
				
				if(iName.equals("item.climateMeter")){ 
					World w = p.getEntityWorld();
					Chunk c = w.getChunkFromChunkCoords(p.chunkCoordX, p.chunkCoordZ);
					String s = String.valueOf(Pollution.getPollution(c));

					System.out.println("[EcoBalance]: At chunk:" + c.xPosition + ", " + c.zPosition);
					p.addChatComponentMessage(new ChatComponentText(s)); //TODO: gotta make this fancier
					
					//LOADED TILE ENTITY LIST. COULD FIX MY PROBLEM 
					//Pollution.checkPollution();//this serves for debugging, the climatemeter will call the method to check the pollution in the whole world
				}
			}
		}else{
			/** This event is used to check whether the block clicked is a polluter
			 * If they are, they will then be added to the database of polluters so we can track them
			**/
			if(action == Action.RIGHT_CLICK_BLOCK){ 
				Block b = p.getEntityWorld().getBlock(e.x, e.y, e.z);
				Integer blockId = Block.getIdFromBlock(b);
				
				if (Pollution.aPollutersIds.contains(Block.getIdFromBlock(b))){
					System.out.println("[EcoBalance]: Yes, it's a polluter!");
					
					/*int x = e.x;
					int y = e.y;
					int z = e.z;
					World w = e.world;
					
					Pollution.addCoordsToMap(w, x, y, z, blockId);*/
				}
			}
		}	
	}
}
