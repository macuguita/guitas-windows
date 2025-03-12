package com.macuguita.windows.forge;

import net.minecraftforge.fml.common.Mod;

import com.macuguita.windows.Windows;

@Mod(Windows.MOD_ID)
public final class WindowsForge {
    public WindowsForge() {

        // Run our common setup.
        Windows.init();
    }
}
