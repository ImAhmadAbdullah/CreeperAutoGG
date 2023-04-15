/*
 *     Copyright (C) 2023 Mr. Speedy
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
package creeper.mrspeedy35.creeperautogg;

import creeper.mrspeedy35.creeperautogg.commands.SlashCGG;
import creeper.mrspeedy35.creeperautogg.configuration.FileUtils;
import creeper.mrspeedy35.creeperautogg.events.CreeperAutoGGTriggerEvents;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CreeperAutoGG.MODID, version = CreeperAutoGG.VERSION, acceptedMinecraftVersions="[1.8.9]")
public class CreeperAutoGG {

    public static final String MODID = "creeperautogg";
    public static final String VERSION = "1.0";

    @Mod.Instance
    private static CreeperAutoGG instance;

    private FileUtils fileUtils;

    private boolean isOn = true;
    private int tickDelay = 20;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.fileUtils = new FileUtils(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.fileUtils.loadConfig();

        ClientCommandHandler.instance.registerCommand(new SlashCGG());
        MinecraftForge.EVENT_BUS.register(new CreeperAutoGGTriggerEvents());
    }

    public FileUtils getFileUtils() {
        return this.fileUtils;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean on) {
        this.isOn = on;
    }

    public int getTickDelay() {
        return this.tickDelay;
    }

    public void setTickDelay(int delay) {
        this.tickDelay = delay;
    }

    public static CreeperAutoGG getInstance() {
        return instance;
    }
}
