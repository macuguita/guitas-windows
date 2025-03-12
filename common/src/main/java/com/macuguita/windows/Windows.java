package com.macuguita.windows;

import com.macuguita.windows.reg.WindowsBlocks;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Windows {
    public static final String MOD_ID = "windows";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.
        WindowsBlocks.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}
