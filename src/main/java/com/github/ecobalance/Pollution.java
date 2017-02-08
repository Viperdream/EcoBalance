package com.github.ecobalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Pollution {
	//public static HashMap<String, Integer> gBalanceMap = new HashMap<String, Integer>(); //stores the global ecobalance, the string is: worldid,dimensionid 
	//public static HashMap<String, Integer> pollutersMap = new HashMap <String, Integer>(); //stores the active polluters (furnaces etc). String contains coordinates (world, x, y, z), integer contains the block id
	public static List<Integer> aPollutersIds = new ArrayList<Integer>(); //active polluters id list, like furnaces. Used to check whether the block pollutes or not
	public static HashMap<String, ChunkEcoValues> pollutedChunks = new HashMap<String, ChunkEcoValues>();  //coords from chunk, chunkecovalues
	
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
	@SuppressWarnings("unchecked")
	public static void checkPollution(){ 
		for (int i = 0; i < MinecraftServer.getServer().worldServers.length; i++){
			List teList = new ArrayList();		
			teList = MinecraftServer.getServer().worldServers[i].loadedTileEntityList;
			Iterator it = teList.iterator();
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
				System.out.println("[EcoBalance]: " + counter + " polluters!");
			}
		}
	}
	
	public static void addPollution(TileEntity te, double amount){
		Chunk c = te.getWorldObj().getChunkFromBlockCoords(te.xCoord, te.zCoord);
		String coords = c.xPosition + "," + c.zPosition;
		ChunkEcoValues cv;
		
		if(pollutedChunks.containsKey(coords)){
			cv = pollutedChunks.get(coords);
			
			cv.addActivePollution(amount);
			
			pollutedChunks.put(coords, cv);
		}else{ //doesn't work properly
			cv = new ChunkEcoValues(te.getWorldObj()
					.getChunkFromBlockCoords(te.xCoord, te.zCoord));
			cv.addActivePollution(amount);
		}

		System.out.println("[EcoBalance]: current pollution: " + cv.getActivePollution());
	}
	
	public static double getPollution(Chunk c){ //TODO: get everything from cv here and send it to the player, will help with debugging
		String coords = c.xPosition + "," + c.zPosition;
		double pollution;
		ChunkEcoValues cv;
		
		if(pollutedChunks.containsKey(coords)){
			cv = pollutedChunks.get(coords);
			
			pollution = cv.getActivePollution();
		}else{
			cv = new ChunkEcoValues(c);
			pollution = cv.getActivePollution();
		}

		return pollution;
	}
	
	/*public static void addCoordsToMap(World w, 
			int x, int y, int z, Integer id){ //TODO: Check whether the block is already in the list
		String coords = w + "," + x + "," + y + "," + z;
		pollutersMap.put(coords, id);
	}*/
}
