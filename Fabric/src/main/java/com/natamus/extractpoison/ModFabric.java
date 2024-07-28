package com.natamus.extractpoison;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.extractpoison.events.PoisonEvent;
import com.natamus.extractpoison.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			return PoisonEvent.onEntityInteract(player, world, hand, entity, hitResult);
		});

		UseItemCallback.EVENT.register((player, world, hand) -> {
			return PoisonEvent.onWaterClick(player, world, hand);
		});
	}

	private static void setGlobalConstants() {

	}
}
