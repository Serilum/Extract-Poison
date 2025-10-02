package com.natamus.extractpoison.forge.events;

import com.natamus.extractpoison.events.PoisonEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgePoisonEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgePoisonEvent.class);
	}

	@SubscribeEvent
	public static boolean onEntityInteract(PlayerInteractEvent.EntityInteract e) {
		Level world = e.getLevel();
		if (world.isClientSide()) {
			return false;
		}

		if (PoisonEvent.onEntityInteract(e.getEntity(), e.getLevel(), e.getHand(), e.getTarget(), null).equals(InteractionResult.SUCCESS)) {
			return true;
		}
		return false;
	}
	
	@SubscribeEvent
	public static boolean onWaterClick(PlayerInteractEvent.RightClickItem e) {
		if (PoisonEvent.onWaterClick(e.getEntity(), e.getLevel(), e.getHand()).equals(InteractionResult.FAIL)) {
			return true;
		}
		return false;
	}
}
