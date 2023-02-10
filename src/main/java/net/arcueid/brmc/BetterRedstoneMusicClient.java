package net.arcueid.brmc;

import net.arcueid.brmc.config.Configs;
import net.fabricmc.api.ClientModInitializer;
import top.hendrixshen.magiclib.config.ConfigHandler;
import top.hendrixshen.magiclib.config.ConfigManager;

public class BetterRedstoneMusicClient implements ClientModInitializer {
    private static final int CONFIG_VERSION = 1;

    @Override
    public void onInitializeClient() {
        top.hendrixshen.magiclib.config.ConfigManager cm = ConfigManager.get(ModInfo.MOD_ID);
        cm.parseConfigClass(Configs.class);
        ModInfo.configHandler = new ConfigHandler(ModInfo.MOD_ID, cm, CONFIG_VERSION);
        ConfigHandler.register(ModInfo.configHandler);
        Configs.init(cm);
    }
}
