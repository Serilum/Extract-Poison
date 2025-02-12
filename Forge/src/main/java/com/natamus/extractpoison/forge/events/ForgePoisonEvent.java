package com.natamus.extractpoison.forge.events;

import com.natamus.extractpoison.events.PoisonEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgePoisonEvent {
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
		if (PoisonEvent.onWaterClick(e.getEntity(), e.getLevel(), e.getHand()).equals(InteractionResult.FAIL)) {
			e.setCanceled(true);
		}
	}
}
