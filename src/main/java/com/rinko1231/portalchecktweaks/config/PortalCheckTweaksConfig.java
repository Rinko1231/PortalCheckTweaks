package com.rinko1231.portalchecktweaks.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class PortalCheckTweaksConfig {

        public static ForgeConfigSpec SPEC;
        public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        public static ForgeConfigSpec.BooleanValue onlyForPlayer;
        public static ForgeConfigSpec.BooleanValue disableCollisionCheck;

        static
        {
            BUILDER.push("Config");

            onlyForPlayer = BUILDER
                    .define("Only Enable Portal Collision Position Check For Players",false);
            disableCollisionCheck = BUILDER
                    .define("Disable Portal Collision Position Check For All Entities",false);

            SPEC = BUILDER.build();
        }

        public static void setup()
        {
            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "PortalCheckTweaks.toml");
        }


    }