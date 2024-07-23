package pl.danielo535.pvpswordlobby.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    private static final Pattern HEX_PATTERN = Pattern.compile("(&#[0-9a-fA-F]{6})");

    private TextUtil() {
    }

    public static String colorize(String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(1).substring(1);
            matcher.appendReplacement(sb, "" + ChatColor.of(hex));
        }
        matcher.appendTail(sb);

        String hexColored = sb.toString();

        return ChatColor.translateAlternateColorCodes('&', hexColored);
    }

    public static List<String> colorize(List<String> text) {
        List<String> messages = new ArrayList<>();
        text.forEach(message -> messages.add(colorize(message)));
        return messages;
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(colorize(message));
    }

    public static void sendMessage(Player sender, String message) {
        sender.sendMessage(colorize(message));
    }

    public static void sendMessage(CommandSender sender, List<String> messages) {
        messages.forEach((message) -> {
            sendMessage(sender, message);
        });
    }

    public static void sendMessage(CommandSender sender, String... messages) {
        Arrays.asList(messages).forEach((message) -> {
            sendMessage(sender, message);
        });
    }

    public static void broadcastMessage(String... messages) {
        Bukkit.getOnlinePlayers().forEach((player) -> {
            sendMessage(player, messages);
        });
    }

    public static void broadcastMessage(List<String> messages) {
        Bukkit.getOnlinePlayers().forEach((player) -> {
            sendMessage(player, messages);
        });
    }

    public static void sendTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(colorize(title), colorize(subTitle), fadeIn, stay, fadeOut);
    }

    public static void sendTitle(Player player, String title, String subTitle) {
        player.sendTitle(colorize(title), colorize(subTitle), 30, 30, 30);
    }
}
