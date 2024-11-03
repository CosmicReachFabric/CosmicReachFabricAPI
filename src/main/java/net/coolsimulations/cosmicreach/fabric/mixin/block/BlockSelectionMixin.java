package net.coolsimulations.cosmicreach.fabric.mixin.block;

import com.badlogic.gdx.audio.Sound;
import finalforeach.cosmicreach.world.BlockPosition;
import finalforeach.cosmicreach.world.BlockSelection;
import finalforeach.cosmicreach.world.blocks.Block;
import net.coolsimulations.cosmicreach.fabric.impl.block.BlockRegistryImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockSelection.class)
public class BlockSelectionMixin {

    @Unique
    private Block targetBlock;
    @Unique
    private BlockPosition targetPosition;

    @ModifyArg(method = "raycast", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/world/BlockSelection;placeBlock(Lfinalforeach/cosmicreach/world/World;Lfinalforeach/cosmicreach/world/blocks/BlockState;Lfinalforeach/cosmicreach/world/BlockPosition;)V", ordinal = 0), index = 2)
    public BlockPosition targetedBreakBlock(BlockPosition position) {
        targetBlock = position.getBlockState().getBlock();
        return position;
    }

    @ModifyArg(method = "raycast", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/world/BlockSelection;placeBlock(Lfinalforeach/cosmicreach/world/World;Lfinalforeach/cosmicreach/world/blocks/BlockState;Lfinalforeach/cosmicreach/world/BlockPosition;)V", ordinal = 1), index = 2)
    public BlockPosition targetedPlacePosition(BlockPosition position) {
        targetPosition = position;
        return position;
    }

    @Inject(method = "raycast", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/world/BlockSelection;placeBlock(Lfinalforeach/cosmicreach/world/World;Lfinalforeach/cosmicreach/world/blocks/BlockState;Lfinalforeach/cosmicreach/world/BlockPosition;)V", shift = At.Shift.AFTER, ordinal = 1))
    public void targetedPlaceBlock(CallbackInfo info) {
        targetBlock = targetPosition.getBlockState().getBlock();
    }

    @ModifyArg(method = "raycast", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/audio/SoundManager;playSound(Lcom/badlogic/gdx/audio/Sound;)J", ordinal = 0), index = 0)
    public Sound onBreakSound(Sound sound) {
        if (BlockRegistryImpl.getBreakSound(targetBlock) != null)
            return BlockRegistryImpl.getBreakSound(targetBlock);
        return sound;
    }

    @ModifyArg(method = "raycast", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/audio/SoundManager;playSound(Lcom/badlogic/gdx/audio/Sound;)J", ordinal = 1), index = 0)
    public Sound onPlaceSound(Sound sound) {
        if (BlockRegistryImpl.getPlaceSound(targetBlock) != null)
            return BlockRegistryImpl.getPlaceSound(targetBlock);
        return sound;
    }
}
