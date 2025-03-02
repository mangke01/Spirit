package earth.terrarium.spirit.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.spirit.Spirit;
import earth.terrarium.spirit.api.elements.BaseElements;
import earth.terrarium.spirit.common.block.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SpiritBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, Spirit.MODID);

    public static final RegistryEntry<Block> PEDESTAL = BLOCKS.register("pedestal", () -> new PedestalBlock(Block.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryEntry<Block> INFUSION_PEDESTAL = BLOCKS.register("infusion_pedestal", () -> new InfusionPedestalBlock(Block.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryEntry<Block> SOUL_BASIN = BLOCKS.register("soul_basin", () -> new SoulBasinBlock(Block.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));

    public static final RegistryEntry<Block> RAGING_SOUL_FIRE = BLOCKS.register("raging_soul_fire", () -> new RagingSoulFireBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(arg -> 15).sound(SoundType.WOOL)));
    public static final RegistryEntry<Block> FLOO_FIRE = BLOCKS.register("floo_fire", () -> new FlooFireBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(arg -> 15).sound(SoundType.WOOL)));

    public static final RegistryEntry<Block> FROSTED_LAVA = BLOCKS.register("frosted_lava", () -> new FrostedLavaBlock(Block.Properties.of().lightLevel(arg -> 10).sound(SoundType.NETHERRACK)));

    //Normal Blocks
    public static final RegistryEntry<Block> SOUL_STEEL_BLOCK = BLOCKS.register("soul_steel_block", () -> new Block(Block.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));
    public static final RegistryEntry<Block> SOUL_GLASS = BLOCKS.register("soul_glass", () -> new Block(Block.Properties.of().strength(0.3F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryEntry<Block> SOUL_SLATE = BLOCKS.register("soul_slate", () -> new Block(Block.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).noOcclusion()));
}
