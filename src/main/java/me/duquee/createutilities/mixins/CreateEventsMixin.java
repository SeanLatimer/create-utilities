package me.duquee.createutilities.mixins;

import java.util.concurrent.Executor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.foundation.events.CommonEvents;

import me.duquee.createutilities.CreateUtilities;
import me.duquee.createutilities.blocks.voidtypes.chest.VoidChestInventoriesData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.DimensionDataStorage;

@Mixin(CommonEvents.class)
public class CreateEventsMixin {

	//CommonEvents  onLoadWorld
	@Inject(method = "onLoadWorld", at = @At("HEAD"), cancellable = true)
	private static void onLoadWorld(Executor executor, LevelAccessor level, CallbackInfo ci) {
		MinecraftServer server = level.getServer();
		if (server == null || server.overworld() != level)
			return;

		DimensionDataStorage dataStorage = server.overworld().getDataStorage();

		CreateUtilities.VOID_CHEST_INVENTORIES_DATA = dataStorage
				.computeIfAbsent(VoidChestInventoriesData::load, VoidChestInventoriesData::new, "VoidChestInventories");
	}
}
