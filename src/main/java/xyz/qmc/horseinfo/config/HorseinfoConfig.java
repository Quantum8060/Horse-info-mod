package xyz.qmc.horseinfo.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "horseinfo")
public class HorseinfoConfig implements ConfigData {

    public boolean showHud = true;
}
