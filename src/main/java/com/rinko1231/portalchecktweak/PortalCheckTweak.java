package com.rinko1231.portalchecktweak;

import com.rinko1231.portalchecktweak.config.PortalCheckTweakConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("portalchecktweak")
public class PortalCheckTweak {
    public PortalCheckTweak()
    {
        MinecraftForge.EVENT_BUS.register(this);
        PortalCheckTweakConfig.setup();
    }
}
