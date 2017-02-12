package com.github.ecobalance.handlers;

import com.github.ecobalance.ChunkEcoValues;
import com.github.ecobalance.EcoBalance;
import com.github.ecobalance.Pollution;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;

public class ChunkEventHandler {
	
	private String id = EcoBalance.MODID;
	private String passiveFactor = id + "_passiveFactor";
	private String originalPassive = id + "_originalPassive";
	private String activePollution = id + "_activePollution";
	private String activeEco = id + "_activeEco";
	
	//public static List<String> benefactorList = new ArrayList<String>(); //was for an old plan, to search each chunk for leaves and add them to the data. Substituted by the postdecoratebiome event
	/**used to load the chunks into the polluted chunks list
	 * use this to check for entities on the chunk? So I can add them to the active polluters list
	 * Maybe not, I think it's easier to just use the global list
	 * Should maybe add an onChunkUnload event, to remove unloaded chunks from the pollutedChunks map*/
	/*@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load e){
		Chunk c = e.getChunk();
		String coords = c.xPosition + "," + c.zPosition;
		ChunkEcoValues cv;
		
		Pollution.loadChunkPollution(c);
	}*/
	/**This is to remove the polluted chunk from the list once the chunk unloads, so it doesn't use up too much memory*/
	@SubscribeEvent
	public void onChunkUnload(ChunkEvent.Unload e){
		Chunk c = e.getChunk();
		
		Pollution.pollutedChunks.remove(c);
	}
	/**This saves the ChunkEcoValues object to the NBTTagCompound to its corresponding chunk
	 */
	@SubscribeEvent
	public void onChunkDataSave(ChunkDataEvent.Save e){
		NBTTagCompound nbt = e.getData();
		Chunk c = e.getChunk();
		ChunkEcoValues cv;
		
		if(Pollution.pollutedChunks.containsKey(c)){
			cv = Pollution.pollutedChunks.get(c);
		}else{
			cv = new ChunkEcoValues();
			
			cv.initDefault(c);
		}
		
		nbt.setDouble(passiveFactor, cv.getPassiveFactor());
		nbt.setDouble(originalPassive, cv.getActivePollution());
		nbt.setDouble(activePollution, cv.getActivePollution());
		nbt.setDouble(activeEco, cv.getActiveEco());
	}
	/**This loads the ChunkEcoValues from the NBTTagCompound from the chunk
	 * Idea came from this thread: http://www.minecraftforge.net/forum/topic/49108-trying-to-save-values-to-chunks/#comment-249018
	 */
	@SubscribeEvent
	public void onChunkDataLoad(ChunkDataEvent.Load e){ //TODO calc ecobalance and neighbour index or just add it?
		if(!Pollution.pollutedChunks.containsKey(e.getChunk())){
			Chunk c = e.getChunk();
			NBTTagCompound nbt = e.getData();
			ChunkEcoValues cv = new ChunkEcoValues();
			
			cv.setChunk(c);
			cv.setPassiveFactor(nbt.getDouble(passiveFactor));
			cv.setOriginalPassive(nbt.getDouble(originalPassive));
			cv.setActivePollution(nbt.getDouble(activePollution));
			cv.setActiveEco(nbt.getDouble(activeEco));
			
			Pollution.pollutedChunks.put(c, cv);
		}else{
			Chunk c = e.getChunk();
			ChunkEcoValues cv = new ChunkEcoValues();
			
			cv.initDefault(c);
			
			Pollution.pollutedChunks.put(c, cv);
		}
	}
}


