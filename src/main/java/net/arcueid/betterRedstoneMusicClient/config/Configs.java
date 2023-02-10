package net.arcueid.betterRedstoneMusicClient.config;

import com.google.common.collect.Lists;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.arcueid.betterRedstoneMusicClient.ModInfo;
import net.arcueid.betterRedstoneMusicClient.gui.GuiConfigs;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import top.hendrixshen.magiclib.config.ConfigManager;
import top.hendrixshen.magiclib.config.annotation.Config;
import top.hendrixshen.magiclib.config.annotation.Hotkey;

import java.util.ArrayList;

public class Configs {
  // GENERIC
  @Config(category = ConfigCategory.GENERIC)
  public static boolean debug = false;

  @Hotkey(hotkey = "O,C")
  @Config(category = ConfigCategory.GENERIC)
  public static ConfigHotkey openConfigGui;

  // FEATURE_TOGGLE
  @Hotkey
  @Config(category = ConfigCategory.FEATURE_TOGGLE)
  public static boolean disableDifferentBlockUsage = false;

  // LISTS
  @Config(category = ConfigCategory.LISTS)
  public static ArrayList<String> differentBlockUsageBlackList =
      Lists.newArrayList("minecraft:redstone_repeater");

  public static void init(ConfigManager cm) {
    // GENERIC
    cm.setValueChangeCallback("debug", option -> {
      if (debug) {
        Configurator.setLevel(ModInfo.MOD_ID, Level.toLevel("DEBUG"));
      } else {
        Configurator.setLevel(ModInfo.MOD_ID, Level.toLevel("INFO"));
      }
      GuiConfigs.getInstance().reDraw();
    });

    openConfigGui.getKeybind().setCallback((keyAction, iKeybind) -> {
      GuiConfigs screen = GuiConfigs.getInstance();
      screen.setParentGui(Minecraft.getInstance().screen);
      Minecraft.getInstance().setScreen(screen);
      return true;
    });
  }

  public static class ConfigCategory {
    public static final String GENERIC = "generic";
    public static final String FEATURE_TOGGLE = "feature_toggle";
    public static final String LISTS = "lists";
  }
}
