package com.github.ecobalance;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class PollutionData extends WorldSavedData {
	
	private static String chunk; //string written in 'x,z'
	private int pollution = 0;
	
	private static final String IDENTIFIER = EcoBalance.MODID + "_POLLUTION_" + chunk;
	
	public PollutionData(){
		super(IDENTIFIER);
	}
	public PollutionData(String identifier){
		super(identifier);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		chunk = nbt.getString(chunk);
		this.pollution = nbt.getInteger("pollution");
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		nbt.setString(chunk, PollutionData.chunk);
		nbt.setInteger("pollution", this.pollution);
	}
	
	public void changePollution(int newPollution){
		markDirty();
		this.pollution = newPollution;
	}
	
	public int getPollution(){
		return this.pollution;
	}
	
	public static PollutionData get(World w, String chunk){
		PollutionData.chunk = chunk;
		
		PollutionData data = (PollutionData)w.loadItemData(PollutionData.class, IDENTIFIER);
		if(data == null){
			data = new PollutionData();
			w.setItemData(IDENTIFIER, data);
		}
		return data;
	}

}
