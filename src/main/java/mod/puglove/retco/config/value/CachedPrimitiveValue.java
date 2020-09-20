package mod.puglove.retco.config.value;

import mod.puglove.retco.config.IReTCoConfig;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class CachedPrimitiveValue<T> {

    protected final ConfigValue<T> internal;
    protected boolean resolved;

    protected CachedPrimitiveValue(IReTCoConfig config, ConfigValue<T> internal) {
        this.internal = internal;
        config.addCachedValue(this);
    }

    public void clearCache() {
        resolved = false;
    }
}
