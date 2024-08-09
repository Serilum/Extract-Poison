package com.natamus.extractpoison.neoforge.events;

import com.natamus.extractpoison.events.PoisonEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgePoisonEvent {
	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract e) {
		Level world = e.getLevel();
		if (world.isClientSide) {
			return;
		}

		if (PoisonEvent.onEntityInteract(e.getEntity(), e.getLevel(), e.getHand(), e.getTarget(), null).equals(InteractionResult.SUCCESS)) {
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void onWaterClick(PlayerInteractEvent.RightClickItem e) {
		if (PoisonEvent.onWaterClick(e.getEntity(), e.getLevel(), e.getHand()).getResult().equals(InteractionResult.FAIL)) {
			e.setCanceled(true);
		}
	}
}
