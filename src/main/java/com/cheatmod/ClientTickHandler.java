package com.cheatmod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ClientTickHandler {
    // Fly
    private static boolean flyEnabled = false;
    private static boolean lastFPressed = false;
    
    // Speed - einstellbar
    private static float currentSpeed = 1.0f;
    private static final float MIN_SPEED = 0.5f;
    private static final float MAX_SPEED = 5.0f;
    private static final float SPEED_STEP = 0.5f;
    private static boolean lastVPressed = false;
    private static boolean lastCPressed = false;
    
    // XRay
    private static boolean xrayEnabled = false;
    private static boolean lastXPressed = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;

        EntityPlayer player = mc.thePlayer;

        // F - Fly Toggle
        boolean fPressed = Keyboard.isKeyDown(Keyboard.KEY_F);
        if (fPressed && !lastFPressed) {
            toggleFly(player);
        }
        lastFPressed = fPressed;

        // V - Speed erhöhen
        boolean vPressed = Keyboard.isKeyDown(Keyboard.KEY_V);
        if (vPressed && !lastVPressed) {
            increaseSpeed(player);
        }
        lastVPressed = vPressed;

        // C - Speed senken
        boolean cPressed = Keyboard.isKeyDown(Keyboard.KEY_C);
        if (cPressed && !lastCPressed) {
            decreaseSpeed(player);
        }
        lastCPressed = cPressed;

        // X - XRay Toggle
        boolean xPressed = Keyboard.isKeyDown(Keyboard.KEY_X);
        if (xPressed && !lastXPressed) {
            toggleXRay(player);
        }
        lastXPressed = xPressed;

        // Fly anwenden
        if (flyEnabled) {
            player.capabilities.allowFlying = true;
            player.capabilities.isFlying = true;
            player.sendPlayerAbilities();
            
            // Speed bei Fly anwenden
            player.capabilities.flySpeed = 0.05f * currentSpeed;
            player.sendPlayerAbilities();
        } else {
            // Fly deaktivieren
            if (!player.capabilities.isCreative) {
                player.capabilities.isFlying = false;
                player.capabilities.allowFlying = false;
                player.sendPlayerAbilities();
            }
        }
    }

    private void toggleFly(EntityPlayer player) {
        flyEnabled = !flyEnabled;
        if (flyEnabled) {
            player.addChatMessage("\u00a76[CheatMod] \u00a7aFly: ON");
        } else {
            player.addChatMessage("\u00a76[CheatMod] \u00a7cFly: OFF");
        }
    }

    private void increaseSpeed(EntityPlayer player) {
        if (currentSpeed < MAX_SPEED) {
            currentSpeed += SPEED_STEP;
            if (currentSpeed > MAX_SPEED) {
                currentSpeed = MAX_SPEED;
            }
            player.addChatMessage("\u00a76[CheatMod] \u00a7eSpeed: " + String.format("%.1f", currentSpeed) + "x");
        }
    }

    private void decreaseSpeed(EntityPlayer player) {
        if (currentSpeed > MIN_SPEED) {
            currentSpeed -= SPEED_STEP;
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED;
            }
            player.addChatMessage("\u00a76[CheatMod] \u00a7eSpeed: " + String.format("%.1f", currentSpeed) + "x");
        }
    }

    private void toggleXRay(EntityPlayer player) {
        xrayEnabled = !xrayEnabled;
        if (xrayEnabled) {
            player.addChatMessage("\u00a76[CheatMod] \u00a7aXRay: ON");
        } else {
            player.addChatMessage("\u00a76[CheatMod] \u00a7cXRay: OFF");
        }
    }
}
