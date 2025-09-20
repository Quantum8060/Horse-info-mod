package xyz.qmc.horseinfo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.HorseBaseEntity;


public class Horseinfo implements ModInitializer   {
	public static final String MOD_ID = "horseinfo";

    @Override
    public void onInitialize() {
        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    private void onHudRender(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (client.player.getVehicle() instanceof HorseBaseEntity horse) {
            TextRenderer textRenderer = client.textRenderer;

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

            textRenderer.drawWithShadow(matrices, healthText, x, y, 0xFF5555);
            textRenderer.drawWithShadow(matrices, jumpText, x, y + 10, 0xFFAA00);
            textRenderer.drawWithShadow(matrices, speedText, x, y + 20, 0x55FFFF);

            RenderSystem.disableBlend();
        }
    }
}