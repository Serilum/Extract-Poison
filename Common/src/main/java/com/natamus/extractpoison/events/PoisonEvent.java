package com.natamus.extractpoison.events;

import com.natamus.collective.functions.EntityFunctions;
import com.natamus.collective.functions.ItemFunctions;
import com.natamus.extractpoison.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PoisonEvent {
	private static final Map<UUID, LocalTime> lastuse = new HashMap<UUID, LocalTime>();
	
	public static InteractionResult onEntityInteract(Player player, Level world, InteractionHand hand, Entity target, EntityHitResult hitResult) {
		if (world.isClientSide) {
			return InteractionResult.PASS;
		}

		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.getItem().equals(Items.GLASS_BOTTLE)) {
			String entityname = EntityFunctions.getEntityString(target).toLowerCase();
			if (entityname.contains("cavespider") || target instanceof CaveSpider || target instanceof Bee) {
				LocalTime now = LocalTime.now();
				UUID targetuuid = target.getUUID();
				if (lastuse.containsKey(targetuuid)) {
					LocalTime lastnow = lastuse.get(targetuuid); 
					
					int msbetween = (int)Duration.between(lastnow, now).toMillis();
					if (msbetween < ConfigHandler.extractDelayMs) {
						return InteractionResult.PASS;
					}
				}

				ItemStack poison = PotionContents.createItemStack(Items.POTION, Potions.POISON);
				
				ItemFunctions.shrinkGiveOrDropItemStack(player, hand, itemstack, poison);
				lastuse.put(targetuuid, now);
				
				return InteractionResult.SUCCESS;
			}
		}
		
		return InteractionResult.PASS;
	}

	public static InteractionResult onWaterClick(Player player, Level world, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (world.isClientSide) {
			return InteractionResult.PASS;
		}

		if (itemstack.getItem().equals(Items.GLASS_BOTTLE)) {
			LocalTime now = LocalTime.now();
			BlockPos pos = player.blockPosition();

			List<Entity> entitiesaround = world.getEntities(player, new AABB(pos.getX()-4, pos.getY()-4, pos.getZ()-4, pos.getX()+4, pos.getY()+4, pos.getZ()+4));
			for (Entity ea : entitiesaround) {
				if (ea instanceof Pufferfish) {
					UUID targetuuid = ea.getUUID();
					if (lastuse.containsKey(targetuuid)) {
						LocalTime lastnow = lastuse.get(targetuuid);

						int msbetween = (int)Duration.between(lastnow, now).toMillis();
						if (msbetween < ConfigHandler.extractDelayMs) {
							continue;
						}
					}

					ItemStack poison = PotionContents.createItemStack(Items.POTION, Potions.POISON);

					ItemFunctions.shrinkGiveOrDropItemStack(player, hand, itemstack, poison);
					lastuse.put(targetuuid, now);
					return InteractionResult.FAIL;
				}
			}
		}

		return InteractionResult.PASS;
	}
}
