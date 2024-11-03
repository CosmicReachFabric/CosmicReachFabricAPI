package net.coolsimulations.cosmicreach.fabric.impl.block;

import com.badlogic.gdx.audio.Sound;
import finalforeach.cosmicreach.world.blocks.Block;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistryImpl {

    private static Map<Block, Sound> BLOCK_BREAK_SOUNDS = new HashMap<>();
    private static Map<Block, Sound> BLOCK_PLACE_SOUNDS = new HashMap<>();

    public static Block getByName(String name) {
        return Block.getInstance(name);
    }

    public static void registerBlockSound(Block block, Sound breakSound, Sound placeSound) {
        if (!BLOCK_BREAK_SOUNDS.containsKey(block))
            BLOCK_BREAK_SOUNDS.put(block, breakSound);
        if (!BLOCK_PLACE_SOUNDS.containsKey(block))
            BLOCK_PLACE_SOUNDS.put(block, placeSound);
    }

    @Nullable
    public static Sound getBreakSound(Block block) {
        return BLOCK_BREAK_SOUNDS.get(block);
    }

    @Nullable
    public static Sound getPlaceSound(Block block) {
        return BLOCK_PLACE_SOUNDS.get(block);
    }
}
