package xyz.qmc.horseinfo;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import xyz.qmc.horseinfo.config.HorseinfoConfig;

public class HorseinfoModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return parent -> AutoConfig.getConfigScreen(HorseinfoConfig.class, parent).get();
    }
}
