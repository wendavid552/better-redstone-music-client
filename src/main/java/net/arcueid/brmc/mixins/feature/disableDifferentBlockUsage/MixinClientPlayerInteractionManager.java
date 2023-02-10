package net.arcueid.brmc.mixins.feature.disableDifferentBlockUsage;

import net.arcueid.brmc.config.Configs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MixinClientPlayerInteractionManager {
    @Inject(method = "useItemOn", at = @At(value = "HEAD"), cancellable = true)
    private void
    preventIntentionalGameDesign(LocalPlayer player,
                                 //#if MC <= 11802
                                 //$$ ClientLevel world,
                                 //#endif
                                 InteractionHand hand, BlockHitResult hitResult,
                                 CallbackInfoReturnable<InteractionResult> cir) {
        //#if MC > 11802
        ClientLevel world = (ClientLevel) player.getLevel();
        //#endif
        if (!Configs.disableDifferentBlockUsage) {
            return;
        }
        Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
        String blockId = Registry.BLOCK.getKey(block).toString();
        String blockName = block.getName().getString();
        if (Configs.differentBlockUsageBlackList.stream().anyMatch(
                s -> blockId.contains(s) || blockName.contains(s))
                && player.getItemInHand(hand).is(block.asItem())) {
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
