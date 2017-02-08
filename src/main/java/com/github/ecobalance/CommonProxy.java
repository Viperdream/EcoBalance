package com.github.ecobalance;

import com.github.ecobalance.handlers.BlockPlaceHandler;
import com.github.ecobalance.handlers.ChunkEventHandler;
import com.github.ecobalance.handlers.PlayerInteractHandler;
import com.github.ecobalance.handlers.TickHandler;
import com.github.ecobalance.item.ModItems;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e){
		ModItems.init();
		Pollution.init();
	}
	public void init(FMLInitializationEvent e){
		MinecraftForge.EVENT_BUS.register(new BlockPlaceHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerInteractHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new ChunkEventHandler());
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
		
		FMLCommonHandler.instance().bus().register(new TickHandler());
	}
	public void postInit(FMLPostInitializationEvent e){
		
	}
}
