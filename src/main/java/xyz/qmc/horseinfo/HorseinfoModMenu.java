package xyz.qmc.horseinfo;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;
import xyz.qmc.horseinfo.config.HorseinfoConfig;

public class HorseinfoModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        return parent -> AutoConfigClient.getConfigScreen(HorseinfoConfig.class, parent).get();
    }
}
