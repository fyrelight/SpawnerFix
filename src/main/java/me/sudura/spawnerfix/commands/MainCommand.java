package me.sudura.spawnerfix.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.sudura.spawnerfix.SpawnerFix;
import org.bukkit.command.CommandSender;

@CommandAlias("spawner|spawners|spawnerfix")
public class MainCommand extends BaseCommand {
    SpawnerFix plugin;

    public MainCommand(SpawnerFix instance){
        plugin = instance;
    }

    @Subcommand("reload")
    @Description("Reloads the config and message files")
    public void onReload(CommandSender sender) {
        plugin.reloadMessages();
        plugin.reloadConfig();
        sender.sendMessage(plugin.getMessages().getString("command.reload"));
    }

    @Default
    @CatchUnknown
    public void onUnknown(CommandSender sender) {
        sender.sendMessage(plugin.getMessages().getString("command.unknown"));
    }
}
