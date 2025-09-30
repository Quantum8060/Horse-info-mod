package xyz.qmc.horseinfo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.HorseEntity;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import xyz.qmc.horseinfo.config.HorseinfoConfig;


public class Horseinfo implements ModInitializer   {
    public static final String MOD_ID = "horseinfo";

    public static HorseinfoConfig CONFIG;

    @Override
    public void onInitialize() {

        AutoConfig.register(HorseinfoConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(HorseinfoConfig.class).getConfig();

        HudRenderCallback.EVENT.register((context, tickDelta) -> onHudRender(context, tickDelta));

    }


    private void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (client.player.getVehicle() instanceof HorseEntity horse) {

            if (!CONFIG.showHud) return;

            double health = horse.getHealth();
            double maxHealth = horse.getMaxHealth();
            double jump = horse.getJumpStrength();
            double speed = horse.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getValue();

            String healthText = String.format("Health: %.1f / %.1f", health, maxHealth);
            String jumpText   = String.format("Jump: %.2f", jump);
            String speedText  = String.format("Speed: %.2f", speed);

            int x = client.getWindow().getScaledWidth() / 10;
            int y = client.getWindow().getScaledHeight() - 40;

            RenderSystem.enableBlend();

            context.drawText(client.textRenderer, healthText, x, y, 0xFF5555, false);
            context.drawText(client.textRenderer, jumpText, x, y + 10, 0xFFAA00, false);
            context.drawText(client.textRenderer, speedText, x, y + 20, 0x55FFFF, false);

            RenderSystem.disableBlend();
        }
    }
}