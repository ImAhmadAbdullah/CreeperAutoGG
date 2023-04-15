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

package creeper.mrspeedy35.creeperautogg.events;

import creeper.mrspeedy35.creeperautogg.CreeperAutoGG;
import creeper.mrspeedy35.creeperautogg.utilities.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CreeperAutoGGTriggerEvents {

    private Pattern hypixelChatPattern = Pattern.compile("(?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern hypixelTeamPattern = Pattern.compile("\\.get(TEAM) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern hypixelGuildPattern = Pattern.compile("Guild > (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern hypixelPartyPattern = Pattern.compile("Party > (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern hypixelShoutPattern = Pattern.compile("\\.get(SHOUT) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private Pattern hypixelSpectatorPattern = Pattern.compile("\\.get(SPECTATOR) (?<rank>\\.get(.+) )?(?<player>\\S{1,16}): (?<message>.*)");
    private final List<String> hypixelEndingStrings = Arrays.asList(
            "1st Killer - ",
            "1st Place - ",
            "Winner: ",
            " - Damage Dealt - ",
            "Winning Team - ",
            "1st - ",
            "Winners: ",
            "Winner: ",
            "Winning Team: ",
            " won the game!",
            "Top Seeker: ",
            "1st Place: ",
            "Last team standing!",
            "Winner #1 (",
            "Top Survivors",
            "Winners - ");
    private Pattern craftiChatPattern = Pattern.compile("(?<rank>(?:RED|BLUE|YELLOW|GREEN) )?(?:(?:<|\\[)\\w+(?:>|\\])) (?<player>\\S{1,16}): (?<message>.*)");
    private Pattern craftiTeamPattern = Pattern.compile("\\.get(TEAM) (?<rank>(?:<|\\[)\\w+(?:>|\\])) (?<player>\\S{1,16}): (?<message>.*)");
    private Pattern craftiGuildPattern = Pattern.compile("Guilds \\u258F (?<rank>(?:<|\\[)\\w+(?:>|\\])) (?<player>\\S{1,16}): (?<message>.*)");
    private Pattern craftiClanPattern = Pattern.compile("Clans \\u258F (?<rank>(?:<|\\[)\\w+(?:>|\\])) (?<player>\\S{1,16}): (?<message>.*)");
    private Pattern craftiPartyPattern = Pattern.compile("Party \\u258F (?<rank>\\w+) (?<player>\\S{1,16}): (?<message>.*)");
    private Pattern craftiShoutPattern = Pattern.compile("\\.get(SHOUT) (?<rank>(?:COLOR )?(?:<|\\[)\\w+(?:>|\\])) (?<player>\\S{1,16}): (?<message>.*)");
    private Pattern craftiSpectatorPattern = Pattern.compile("(?:<|\\[)(Spectator|SPEC)(?:>|\\]) (?<player>\\S{1,16}): (?<message>.*)");
    private final List<String> craftiEndingStrings = Arrays.asList("Rewards:", "WINNER");
    private Pattern acentraPracticePattern = Pattern.compile("\"(?<rank>[^\\n]*?) \\u2503 (?<player>\\S{1,16}): (?<message>.*)");
    private Pattern acentraRBWPattern = Pattern.compile("\\\\[(?<number>\\d+)\\u272B\\\\] (?<player>\\S{1,16}): (?<message>.*)");

    private final List<String> acentraColdEndingStrings = Arrays.asList("Match Details (click name to view)", "Post-Match Inventories (click to view)");
    private int tick = -1;

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onGameEndEvent(ClientChatReceivedEvent event) {
        if (event.isCanceled()) {
            return;
        }

        String message = ChatColor.stripColor(event.message.getUnformattedText());

        if (message.isEmpty()) {
            return;
        }

        try {
            if (!CreeperAutoGG.getInstance().isOn() || isNormalMessage(message)) {
                return;
            }

            if (isEndOfGame(message)) {
                this.tick = CreeperAutoGG.getInstance().getTickDelay();
            }
        } catch (Exception ex) {
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onGameTick(TickEvent.ClientTickEvent event) {
        if (this.tick == 0) {
            if (CreeperAutoGG.getInstance().isOn())  {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("Good Game <3");
            }
            this.tick = -1;
        } else {
            if (this.tick > 0) {
                this.tick--;
            }
        }
    }

    private boolean isNormalMessage(String message) {
        return this.hypixelChatPattern.matcher(message).matches() ||
                this.hypixelTeamPattern.matcher(message).matches() ||
                this.hypixelGuildPattern.matcher(message).matches() ||
                this.hypixelPartyPattern.matcher(message).matches() ||
                this.hypixelShoutPattern.matcher(message).matches() ||
                this.hypixelSpectatorPattern.matcher(message).matches() ||
                this.craftiChatPattern.matcher(message).matches() ||
                this.craftiTeamPattern.matcher(message).matches() ||
                this.craftiGuildPattern.matcher(message).matches() ||
                this.craftiClanPattern.matcher(message).matches() ||
                this.craftiPartyPattern.matcher(message).matches() ||
                this.craftiShoutPattern.matcher(message).matches() ||
                this.craftiSpectatorPattern.matcher(message).matches() ||
                this.acentraPracticePattern.matcher(message).matches() ||
                this.acentraRBWPattern.matcher(message).matches();
    }

    private boolean isEndOfGame(String message) {
        return this.hypixelEndingStrings.stream().anyMatch(message::contains) ||
                this.craftiEndingStrings.stream().anyMatch(message::contains) ||
                this.acentraColdEndingStrings.stream().anyMatch(message::contains);
    }
}
