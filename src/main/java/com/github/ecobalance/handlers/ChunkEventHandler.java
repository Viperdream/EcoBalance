package com.github.ecobalance.handlers;

import java.util.ArrayList;
import java.util.List;

import com.github.ecobalance.ChunkEcoValues;
import com.github.ecobalance.Pollution;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.world.ChunkEvent;

public class ChunkEventHandler {
	//public static List<String> benefactorList = new ArrayList<String>(); //was for an old plan, to search each chunk for leaves and add them to the data. Substituted by the postdecoratebiome event
	/**used to load the chunks into the polluted chunks list
	 * use this to check for entities on the chunk? So I can add them to the active polluters list
	 * Maybe not, I think it's easier to just use the global list
	 * Should maybe add an onChunkUnload event, to remove unloaded chunks from the pollutedChunks map*/
	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load e){
		Chunk c = e.getChunk();
		String coords = c.xPosition + "," + c.zPosition;
		ChunkEcoValues cv;
		
		if(!Pollution.pollutedChunks.containsKey(coords)){
			//ChunkEcoValues cv = Pollution.pollutedChunks.get(coords);
			try{
				cv = new ChunkEcoValues(c);
			}catch(Exception exception){
				System.out.println("Null returned for chunk");
				cv = new ChunkEcoValues(c, 100, 100, 0, 0);
			}	
			Pollution.pollutedChunks.put(coords, cv);
		}else{
			System.out.println("Found the chunk!");
		}
	}
	@SubscribeEvent
	public void postDecorateBiomeEvent(DecorateBiomeEvent.Decorate e){
		/**fires when a tree is generated on a new chunk, used for the origin value
		 * could always add more of these, like grass maybe?
		 * You might have to put this in a different class, so it won't mix up with the previous event bus
		 */
		if(e.type == EventType.TREE){
			Chunk c = e.world.getChunkFromChunkCoords(e.chunkX, e.chunkZ);
			String coords = c.xPosition + "," + c.zPosition;
			ChunkEcoValues cv;
			
			if(Pollution.pollutedChunks.containsKey(coords)){
				System.out.println("Found the chunk!");
				cv = Pollution.pollutedChunks.get(coords);
				
				cv.setOriginalPassive(cv.getOriginalPassive() + 5);
				cv.setPassiveFactor(cv.getOriginalPassive());
			}else{
				try{
					cv = new ChunkEcoValues(c);
					
					cv.setOriginalPassive(cv.getOriginalPassive() + 5);
					cv.setPassiveFactor(cv.getOriginalPassive());
				}catch(Exception exception){
					System.out.println("Null returned for chunk");
					cv = new ChunkEcoValues(c, 105, 105, 0, 0);
				}
			}
		}
	}
}


