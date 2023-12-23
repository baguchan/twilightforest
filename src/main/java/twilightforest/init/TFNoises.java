package twilightforest.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import twilightforest.TwilightForestMod;

public class TFNoises {
    public static final ResourceKey<NormalNoise.NoiseParameters> DARK_FOREST_LEAVES = createKey("dark_forest_leaves");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(TwilightForestMod.ID, name));
    }

    public static void bootstrap(BootstapContext<NormalNoise.NoiseParameters> ctx) {
        register(ctx, DARK_FOREST_LEAVES, -8, 1.0D, 1.0D, 1.0D);
    }

    public static void register(BootstapContext<NormalNoise.NoiseParameters> ctx, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
        ctx.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
    }
}