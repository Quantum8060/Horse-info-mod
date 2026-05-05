package xyz.qmc.horseinfo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.Identifier;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import xyz.qmc.horseinfo.config.HorseinfoConfig;


public class Horseinfo implements ClientModInitializer {
    public static final String MOD_ID = "horseinfo";
    private static final Identifier HUD_ELEMENT_ID = Identifier.of(MOD_ID, "horse_stats");

    public static HorseinfoConfig CONFIG;

    @Override
    public void onInitializeClient() {

        AutoConfig.register(HorseinfoConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(HorseinfoConfig.class).getConfig();

        HudElementRegistry.attachElementAfter(VanillaHudElements.MOUNT_HEALTH, HUD_ELEMENT_ID, this::onHudRender);

    }


    private void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (client.player.getVehicle() instanceof AbstractHorseEntity horse) {

            if (!CONFIG.showHud) return;

            double health = horse.getHealth();
            double maxHealth = horse.getMaxHealth();
            double jump = horse.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
            double speed = horse.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);

            String healthText = String.format("Health: %.1f / %.1f", health, maxHealth);
            String jumpText   = String.format("Jump: %.2f", jump);
            String speedText  = String.format("Speed: %.2f", speed);

            int x = client.getWindow().getScaledWidth() / 10;
            int y = client.getWindow().getScaledHeight() - 40;

            context.drawText(client.textRenderer, healthText, x, y, 0xFFFF5555, false);
            context.drawText(client.textRenderer, jumpText, x, y + 10, 0xFFFFAA00, false);
            context.drawText(client.textRenderer, speedText, x, y + 20, 0xFF55FFFF, false);
        }
    }
}
