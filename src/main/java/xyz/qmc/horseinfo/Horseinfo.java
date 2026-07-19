package xyz.qmc.horseinfo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.Horse;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import xyz.qmc.horseinfo.config.HorseinfoConfig;


public class Horseinfo implements ClientModInitializer {
	public static final String MOD_ID = "horseinfo";

	public static HorseinfoConfig CONFIG;

	@Override
	public void onInitializeClient() {
		AutoConfig.register(HorseinfoConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(HorseinfoConfig.class).getConfig();

		HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(MOD_ID, "horse_stats"), this::onHudRender);
	}


	private void onHudRender(GuiGraphicsExtractor context, DeltaTracker tickCounter) {
		Minecraft client = Minecraft.getInstance();
		if (client.player == null) return;

		if (client.player.getVehicle() instanceof Horse horse) {

			if (!CONFIG.showHud) return;

			double health    = horse.getHealth();
			double maxHealth = horse.getMaxHealth();
			// getJumpStrength() は削除済み → JUMP_STRENGTH 属性から取得
			double jump  = horse.getAttributeValue(Attributes.JUMP_STRENGTH);
			double speed = horse.getAttributeValue(Attributes.MOVEMENT_SPEED);

			String healthText = String.format("Health: %.1f / %.1f", health, maxHealth);
			String jumpText   = String.format("Jump: %.2f", jump);
			String speedText  = String.format("Speed: %.2f", speed);

			int x = context.guiWidth() / 10;
			int y = context.guiHeight() - 40;

			// RenderSystem.enableBlend() / disableBlend() は 1.21 で削除済み
			context.text(client.font, healthText, x, y,      0xFFFF5555, false);
			context.text(client.font, jumpText,   x, y + 10, 0xFFFFAA00, false);
			context.text(client.font, speedText,  x, y + 20, 0xFF55FFFF, false);
		}
	}
}
