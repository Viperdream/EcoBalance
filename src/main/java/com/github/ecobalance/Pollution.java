package com.github.ecobalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.chunk.Chunk;

public class Pollution {
	public static List<Integer> aPollutersIds = new ArrayList<Integer>(); //active polluters id list, like furnaces. Used to check whether the block pollutes or not
	public static HashMap<Chunk, ChunkEcoValues> pollutedChunks = new HashMap<Chunk, ChunkEcoValues>();  //coords from chunk, chunkecovalues
	
	/**
	 * here you'll have to add all the id's of the blocks that pollute the environment
	 * perhaps for mod items, you should do this separately?
	 * Maybe add a config file to add ID's yourself?
	 * TODO: Gotta use unlocalized names, id's change 
	 */
	public static void init(){ 
		aPollutersIds.add(61); //furnace
		aPollutersIds.add(62); //burning furnace
		aPollutersIds.add(343); //furnace on minecart
	}
	/**
	 * checks the active pollution in the world when ran, uses the tileEntityList to iterate
	 * I will use the loadedtileentitylist to check what is being used.
	 * Filter out the unwanted stuff, then add pollution per tick.
	 * TODO: find out how to add pollution per tick with a loop
	 */
	public static void checkPollution(){ 
		for (int i = 0; i < MinecraftServer.getServer().worldServers.length; i++){
			List<?> teList = new ArrayList<Object>();		
			teList = MinecraftServer.getServer().worldServers[i].loadedTileEntityList;
			Iterator<?> it = teList.iterator();
			int counter = 0; //debugging purposes
			
			//System.out.print("[EcoBalance]: Checking pollution...");

			while(it.hasNext()){ //this works, unlike the previous one (for loop)
				TileEntity te = (TileEntity)it.next();
				Block b = te.getBlockType();
				Integer id = Block.getIdFromBlock(b);
				
				if(aPollutersIds.contains(id)){
					if((TileEntityFurnace)te != null){
						TileEntityFurnace tf = (TileEntityFurnace)te;
						
						if(tf.isBurning()){
							addPollution(te, 5); //TODO: gotta change this so it's not hardcoded
							counter++;
						}
					}				
				}
			}
			if(counter != 0){
				//System.out.println("[EcoBalance]: " + counter + " polluters!");
			}
		}
	}
	
	public static void addPollution(TileEntity te, double amount){
		Chunk c = te.getWorldObj().getChunkFromBlockCoords(te.xCoord, te.zCoord);
		ChunkEcoValues cv = new ChunkEcoValues();
		
		if(pollutedChunks.containsKey(c)){
			cv = pollutedChunks.get(c);
			
			cv.addActivePollution(amount, c);
			
			pollutedChunks.put(c, cv);
		}else{ 
			cv.initDefault(c);
			
			cv.addActivePollution(amount, c);
		
			pollutedChunks.put(c, cv);
		}

		//EcoBalance.logger.info("added " + amount + " pollution");
	}
	
	public static double getPollution(Chunk c){ //TODO: get everything from cv here and send it to the player, will help with debugging
		double pollution;
		ChunkEcoValues cv = new ChunkEcoValues();
		
		if(pollutedChunks.containsKey(c)){
			cv = pollutedChunks.get(c);
			
			pollution = cv.getActivePollution();
		}else{
			cv.initDefault(c);
			pollution = cv.getActivePollution();
			
			pollutedChunks.put(c, cv);
		}

		return pollution;
	}
}
