package net.arcueid.betterRedstoneMusicClient.mixin.feature.limitedConfiguringTools;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BedBlock;
// #if MC > 11502
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
// #endif

@Mixin(MultiPlayerGameMode.class)
public class MixinClientPlayerInteractionManager {
  @Inject(method = "useItemOn", at = @At(value = "HEAD"), cancellable = true)
  private void
  preventIntentionalGameDesign(LocalPlayer player,
                               // #if MC <= 11802
                               //$$ ClientLevel world,
                               // #endif
                               InteractionHand hand, BlockHitResult hitResult,
                               CallbackInfoReturnable<InteractionResult> cir) {
    // #if MC > 11802
    ClientLevel world = (ClientLevel)player.getLevel();
    // #endif
    if (!Configs.limitedConfiguringTools) {
      return;
    }
    String blockId =
        Registry.BLOCK.getKey(world.getBlockState(pos).getBlock()).toString();
    String blockName =
        world.getBlockState(pos).getBlock().getName().getString();
    if (Configs.limitConfiguringToolsList.stream().anyMatch(
            s -> blockId.contains(s) || blockName.contain(s))) {
      cir.setReturnValue(InteractionResult.SUCCESS);
    }
  }
}
