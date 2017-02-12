package com.github.ecobalance;

import net.minecraft.world.chunk.Chunk;

public class ChunkEcoValues {
	 /**The ecobalance is calculated as followed:
	 * passiveFactor - ((activePollution - activeEco) * neighborIndex) = ecobalance
	 * 
	 * The passiveFactor has a standard value of 100. This can be increased by chunk features such as leaves and a favorable biome, or planting trees. 
	 * I will add some special items that can increase this
	 * It can be decreased by man made consequences, such as TNT, nukes or chopping down trees
	 * ****TABLE****
	 * Tree = 5
	 * TNT = 2
	 * 
	 * The activePollution factor contains all the factors that actively adds to pollution. Such as working machines. Default 0
	 * 
	 * The activeEco factor contains all the factors that actively try to mend the chunk's climate, such as blocks that improve the climate (to be made in this mod)
	 * The activeEco value is normally 0. This will increase if there are no active polluting factors. But it cannot become higher than the originalPassive value. This will be a slow proces.
	 * 
	 * The neighborindex is the average balance of all the chunks around the chunk. This can be beneficial or deteriorate the chunk's balance. Default 1. Can't be higher than 2. Minimum 0.1
	 * 
	 * The originalPassive is the ecobalance that the chunk had when it was first loaded. This makes sure that the climate can mend itself
	 * if the active polluting factors are not contributing at the moment
	 * 
	 * Chunk is obviously the chunk that contains these values. The coords string is used as an identifier for the chunk
	 * TODO: fix neighbour index, makes an infinite loop. Gotta make 2 objects, one where you calculate it and one where you don't
	 **/
	public Chunk chunk;
	public String coords;
	
	public Double passiveFactor; 
	public Double originalPassive;
	public Double activePollution;
	public Double activeEco;
	
	public Double neighborIndex;
	public Double ecoBalance;
	
	//ChunkEcoValuesData data;
	
	/*public int pollutionVal; //value that disadvantages the chunk, max 100
	public int greenVal = 0; //value that benefit the chunk, max 100
	public int originVal = 0; //original greenVal max 100
	public int ecoPercent = 50; //percentage of the ecobalance, 0 is shit tier, 100 is eden, 50 is default
	public int neighborBonus = 50; //bonus gained from good or bad treatment of neighboring chunks. Default = 50. Max 100, min. 
	PollutionData data; //the data stored in NBT*/
	
	public ChunkEcoValues(){}
	
	public ChunkEcoValues(Chunk chunk, 
			Double passiveFactor, Double originalPassive, 
			Double activePollution, Double activeEco){
		
		this.chunk = chunk;
		this.coords = this.chunk.xPosition + "," + this.chunk.zPosition;
		
		this.passiveFactor = passiveFactor;
		this.originalPassive = originalPassive;
		this.activePollution = activePollution;
		this.activeEco = activeEco;
		
		this.calcEcoBalance();
		//this.neighborIndex = CalcEcoValues.calcNeighborIndex(this.chunk, this.coords);
		
	}
	
	public Chunk getChunk(){
		return this.chunk;
	}
	public String getCoords(){
		return this.coords;
	}
	public Double getPassiveFactor(){
		return this.passiveFactor;
	}
	public Double getOriginalPassive(){
		return this.originalPassive;
	}
	public Double getActivePollution(){
		return this.activePollution;
	}
	public Double getActiveEco(){
		return this.activeEco;
	}
	public Double getNeighborIndex(){
		return this.neighborIndex;
	}
	public Double getEcoBalance(){
		this.calcEcoBalance();
		return this.ecoBalance;
	}
	
	public void setChunk(Chunk c){
		this.chunk = c;
	}
	public void setCoords(String coords){
		this.coords = coords;
	}
	public void setPassiveFactor(Double pf){
		this.passiveFactor = pf;
	}
	public void setOriginalPassive(Double op){
		this.originalPassive = op;
	}
	public void setActivePollution(Double ap){
		this.activePollution = ap;
	}
	public void setActiveEco(Double ae){
		this.activeEco = ae;
	}
	public void setNeighborIndex(Double ni){
		this.neighborIndex = ni;
	}
	public void setEcoBalance(Double eb){
		this.ecoBalance = eb;
	}
	
	public void addActivePollution(Double pollution, Chunk c){
		this.activePollution = this.activePollution + pollution;
		
		if(this.chunk == null){
			this.chunk = c;
		}
	}
	
	public void calcEcoBalance(){
		if(this.activePollution == 0 && this.passiveFactor < this.originalPassive && this.activeEco == 0){
			this.activeEco++;
		}else if(this.activePollution != 0 && this.activeEco == 1){
			this.activeEco--;
		}
		this.ecoBalance = this.passiveFactor - ((this.activePollution - this.activeEco) * this.neighborIndex);
		
		if (this.ecoBalance <= 0){ //can't be lower than 0.1
			this.ecoBalance = 0.1;
		}else if(this.ecoBalance > 200){ //can't be larger than 200
			this.ecoBalance = 200.0;
		}
	}
	
	public void initDefault(Chunk c){
		this.chunk = c;
		this.passiveFactor = 100.0;
		this.originalPassive = 100.0;
		this.activeEco = 0.0;
		this.activePollution = 0.0;
	}
}
