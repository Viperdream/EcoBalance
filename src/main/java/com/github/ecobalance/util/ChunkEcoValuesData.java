package com.github.ecobalance.util;

import com.github.ecobalance.EcoBalance;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class ChunkEcoValuesData extends WorldSavedData{
	
	private static final String IDENTIFIER = EcoBalance.MODID;
	
	private static String coords;
	private String jsonCv;
	
	public ChunkEcoValuesData(){
		super(IDENTIFIER);
	}
	public ChunkEcoValuesData(String id){
		super(id);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		jsonCv = nbt.getString(coords);
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString(coords, jsonCv);
	}
	
	public String getJson(){
		return jsonCv;
	}
	
	public void setJson(String json){
		this.jsonCv = json;
		markDirty();
	}
	
	public static ChunkEcoValuesData get (World w, String chunkCoords){
		coords = chunkCoords;
		
		ChunkEcoValuesData data = (ChunkEcoValuesData)w.loadItemData(ChunkEcoValuesData.class, IDENTIFIER);
		if(data == null){
			data = new ChunkEcoValuesData();
			w.setItemData(IDENTIFIER, data);
		}
		return data;
	}

}
