package com.github.ecobalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.world.World;

public class Pollution {
	public static HashMap<String, Integer> gBalanceMap = new HashMap<String, Integer>(); //stores the global ecobalance, the string is: worldid,dimensionid 
	public static HashMap<String, Integer> pollutersMap = new HashMap <String, Integer>(); //stores the active polluters (furnaces etc). String contains coordinates (world, x, y, z), integer contains the block id
	public static List<Integer> aPollutersIds = new ArrayList<Integer>(); //active polluters id list, like furnaces. Used to check whether the block pollutes or not
	
	/**
	 * here you'll have to add all the id's of the blocks that pollute the environment
	 * perhaps for mod items, you should do this separately?
	 * Maybe add a config file to add ID's yourself?
	 */
	public static void init(){ 
		aPollutersIds.add(61); //furnace
		aPollutersIds.add(62); //burning furnace
		aPollutersIds.add(343); //furnace on minecart
	}
	/**
	 * checks the pollution in the world when ran, uses the pollutersmap to iterate
	 * I will use the loadedtileentitylist to check what is being used.
	 * Filter out the unwanted stuff, then add pollution per tick.
	 * TODO: find out how to add pollution per tick with a loop
	 */
	public static void checkPollution(){ 
	
	}
	
	public static void addCoordsToMap(World w, int x, int y, int z, Integer id){ //TODO: Check whether the block is already in the list
		String coords = w + "," + x + "," + y + "," + z;
		pollutersMap.put(coords, id);
	}
}
