package com.github.ecobalance;

import java.util.logging.Logger;

import com.github.ecobalance.item.EcoTab;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = EcoBalance.MODID, version = EcoBalance.VERSION)
public class EcoBalance 
{
	public static final String MODID = "ecobalance";
	public static final String VERSION = "1710-1.0.0.0";
	public static final String MODNAME = "EcoBalance";
	
	public static Logger logger = Logger.getLogger(MODNAME);
	
	public static CreativeTabs ecoTab = new EcoTab(CreativeTabs.getNextID(), EcoBalance.MODNAME);
	
	@SidedProxy(clientSide="com.github.ecobalance.ClientProxy", serverSide="com.github.ecobalance.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e){	
		proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		proxy.postInit(e);
	}
}
