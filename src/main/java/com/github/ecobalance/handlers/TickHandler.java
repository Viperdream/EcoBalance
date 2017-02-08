package com.github.ecobalance.handlers;

import com.github.ecobalance.Pollution;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class TickHandler {
	int i = 0;
	
	@SubscribeEvent
	public void onTick(ServerTickEvent e){
		if(i !=60){
			i++;
			//System.out.print("[EcoBalance]: Tock!");
		}else{
			i = 0;
			//System.out.print("[EcoBalance]: Checking Pollution!");
			
			Pollution.checkPollution();
		}
	}
}
