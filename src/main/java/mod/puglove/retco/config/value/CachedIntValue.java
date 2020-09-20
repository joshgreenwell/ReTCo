package mod.puglove.retco.config.value;

import java.util.function.IntSupplier;
import mod.puglove.retco.config.IReTCoConfig;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class CachedIntValue extends CachedPrimitiveValue<Integer> implements IntSupplier {

    private int cachedValue;

    private CachedIntValue(IReTCoConfig config, ConfigValue<Integer> internal) {
        super(config, internal);
    }

    public static CachedIntValue wrap(IReTCoConfig config, ConfigValue<Integer> internal) {
        return new CachedIntValue(config, internal);
    }

    public int get() {
        if (!resolved) {
            //If we don't have a cached value or need to resolve it again, get it from the actual ConfigValue
            cachedValue = internal.get();
            resolved = true;
        }
        return cachedValue;
    }

    @Override
    public int getAsInt() {
        return get();
    }

    public void set(int value) {
        internal.set(value);
        cachedValue = value;
    }
}