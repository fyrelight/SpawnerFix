package me.sudura.spawnerfix;

import co.aikar.commands.PaperCommandManager;
import me.sudura.spawnerfix.commands.MainCommand;
import me.sudura.spawnerfix.listeners.BlockListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SpawnerFix extends JavaPlugin {
    private File messagesFile;
    private FileConfiguration messages;
    public void onEnable() {
        PaperCommandManager manager = new PaperCommandManager(this);
        this.saveDefaultMessages();
        this.saveDefaultConfig();
        manager.registerCommand(new MainCommand(this));
        this.getServer().getPluginManager().registerEvents(new BlockListener(this), this);
    }

    public FileConfiguration getMessages() {
        return this.messages;
    }

    public void reloadMessages() {
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private void saveDefaultMessages() {
        messagesFile = new File(getDataFolder(), "messages.yml");

        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }

        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }
}
