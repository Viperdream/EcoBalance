package com.github.ecobalance.util;

import com.github.ecobalance.ChunkEcoValues;
import com.github.ecobalance.Pollution;
import net.minecraft.world.chunk.Chunk;

public class CalcEcoValues {

	/**checks all the surrounding chunks for it's ecobalance (if it exists) and then calculates the neighbour index**/
	public static double calcNeighborIndex(Chunk c, String coords){
		String newCoords;
		int originalX = c.xPosition;
		int originalZ = c.zPosition;
		int newZ, newX;
		int x = -1;
		int z = -1;
		double totalBalance = 0;
		double neighborIndex;
		//so using a try catch in a loop gives an infinite loop
		
		for (z= -1; z<2; z++){ //z coordinate	
			for(x = -1; x<2; x++){ //x coordinate
				newX = originalX + x;
				newZ = originalZ + z;
				System.out.println("X: " + newX + ",Z: " + newZ);
				
				newCoords = newX + "," + newZ;
				if(newCoords != coords){
					ChunkEcoValues cv = new ChunkEcoValues();
					Chunk newC = c.worldObj.getChunkFromBlockCoords(newX, newZ);
					String nCoords = newX + "," + newZ;
					
					if(!Pollution.pollutedChunks.containsKey(nCoords)){
						System.out.println("Null returned when calculating a neighbor's index, using default values");
						cv.initDefault(newC);
					}else{
						cv.initDefault(newC);
					}
					
					if(cv.getEcoBalance() != 0){
						totalBalance = totalBalance + (cv.getEcoBalance()/100); //has to be divided by 100, because it has to stay between 0.1 and 2
					}else{
						totalBalance++;
					}
				}
			}
			
		}
		
		System.out.println("I got out of the loop!");
		neighborIndex = totalBalance/8; //divided by amount of neighbouring chunks to get the average
		
		if(neighborIndex > 0.1){ //can't be lower than 0.1. Shouldn't happen normally though, just to make sure
			neighborIndex = 0.1;
		}
		return neighborIndex;
	}
}
