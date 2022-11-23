package me.sudura.spawnerfix.listeners;

import me.sudura.spawnerfix.SpawnerFix;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.List;

public class BlockListener implements Listener {
    SpawnerFix plugin;
    public BlockListener(SpawnerFix instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onChangeSpawner (PlayerInteractEvent event) {
        if (event.hasItem() && event.getItem().getType().name().endsWith("_SPAWN_EGG") && event.hasBlock() && event.getClickedBlock().getType() == Material.SPAWNER) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getMessages().getString("prevent.change"));
        }
    }

    @EventHandler
    public void onBreakBlock (BlockBreakEvent event) {
        for (String mat : plugin.getConfig().getStringList("material")){
            Material mat2 = Material.getMaterial(mat);
            if (event.getBlock().getType() == mat2) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(plugin.getMessages().getString("prevent.break").replace("%block%", mat.toLowerCase()));
                return;
            }
        }
    }

    @EventHandler
    public void onExplodeBlock(BlockExplodeEvent event) {
        List<Block> blockList = event.blockList();
        for (Block block : blockList) {
            for (String mat : plugin.getConfig().getStringList("material")){
                Material mat2 = Material.getMaterial(mat);
                if (block.getType() == mat2) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onExplodeEntity(EntityExplodeEvent event) {
        List<Block> blockList = event.blockList();
        for (Block block : blockList) {
            for (String mat : plugin.getConfig().getStringList("material")){
                Material mat2 = Material.getMaterial(mat);
                if (block.getType() == mat2) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onGrow(StructureGrowEvent event) {
        if (event.getSpecies() == TreeType.RED_MUSHROOM) {
            Location loc = event.getLocation();
            for(int x = -2; x <= 2; ++x) {
                for(int y = 1; y <= 13; ++y) {
                    for(int z = -2; z <= 2; ++z) {
                        Material blockMat = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z).getType();
                        for (String mat : plugin.getConfig().getStringList("material")){
                            Material mat2 = Material.getMaterial(mat);
                            if (blockMat == mat2) {
                                event.setCancelled(true);
                                event.getPlayer().sendMessage(plugin.getMessages().getString("prevent.grow").replace("%block%", mat.toLowerCase()));
                                return;
                            }
                        }
                    }
                }
            }
        }

    }
}
