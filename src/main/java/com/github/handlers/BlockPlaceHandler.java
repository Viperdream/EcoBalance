package com.github.handlers;

import com.github.ecobalance.Pollution;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class BlockPlaceHandler {
	@SubscribeEvent
	public void onBlockAdded(BlockEvent.PlaceEvent e){
		//Integer blockMeta = e.blockMetadata;  //returns 0
		Block block = e.placedBlock;
		Integer id = Block.getIdFromBlock(block); //returns the id and an array? '61[15:56:56]': that's the time you wanker
		
		//System.out.print("[EcoBalance]: block id: " + id);
		/** This event is used to check whether the block placed is a polluter
		 * If they are, they will then be added to the database of polluters so we can track them
		**/
		if(Pollution.aPollutersIds.contains(id)){ //checks if it is a polluter
			int x = e.x;
			int y = e.y;
			int z = e.z;
			World w = e.world; //just world gives net.minecraft.world.WorldServer@40460366
			
			Pollution.addCoordsToMap(w, x, y, z, id);
			
		}
		
	}
}
