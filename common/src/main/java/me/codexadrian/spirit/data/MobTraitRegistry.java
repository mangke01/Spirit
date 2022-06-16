package me.codexadrian.spirit.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import me.codexadrian.spirit.data.entitytraits.DamageTrait;
import me.codexadrian.spirit.data.entitytraits.ExplosionTrait;
import me.codexadrian.spirit.data.entitytraits.FireTrait;
import me.codexadrian.spirit.data.entitytraits.PotionTrait;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MobTraitRegistry {
    public static final Codec<MobTraitSerializer<?>> TRAIT_CODEC = ResourceLocation.CODEC.comapFlatMap(MobTraitRegistry::decode, MobTraitSerializer::id);
    public static final Codec<MobTrait<?>> CODEC = TRAIT_CODEC.dispatch(MobTrait::serializer, MobTraitSerializer::codec);
    private static final Map<ResourceLocation, MobTraitSerializer<?>> SERIALIZERS = new HashMap<>();

    static {
        add(ExplosionTrait.SERIALIZER);
        add(PotionTrait.SERIALIZER);
        add(FireTrait.SERIALIZER);
        add(DamageTrait.SERIALIZER);
    }

    private static DataResult<? extends MobTraitSerializer<?>> decode(ResourceLocation id) {
        return Optional.ofNullable(SERIALIZERS.get(id)).map(DataResult::success).orElse(DataResult.error("No trait type found."));
    }

    public static void add(MobTraitSerializer<?> serializer) {
        SERIALIZERS.put(serializer.id(), serializer);
    }
}
