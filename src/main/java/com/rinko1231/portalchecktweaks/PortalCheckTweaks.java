package com.rinko1231.portalchecktweaks;

import com.rinko1231.portalchecktweaks.config.PortalCheckTweaksConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("portalchecktweaks")
public class PortalCheckTweaks {
    public PortalCheckTweaks()
    {
        MinecraftForge.EVENT_BUS.register(this);
        PortalCheckTweaksConfig.setup();
    }
}
