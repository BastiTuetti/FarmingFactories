package de.riskonia.farmingfactories.commands;

import de.riskonia.farmingfactories.main.Main;
import de.riskonia.farmingfactories.utils.invs.InventoryManager;
import de.riskonia.farmingfactories.utils.misc.Factory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class testCmd implements CommandExecutor {

    private Main plugin;
    public testCmd(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length>0){
                if (args.length==1) {
                    if (args[0].equalsIgnoreCase("savetest")) {
                        ItemStack diamond = new ItemStack(Material.DIAMOND);
                        ItemStack bricks = new ItemStack(Material.BRICK);
                        bricks.setAmount(20);

                        Inventory ininventory = Bukkit.getServer().createInventory(null, 9);
                        ininventory.setItem(1, diamond);
                        ininventory.setItem(8, bricks);
                        String inbase64 = plugin.invman.toBase64(ininventory);

                        Inventory outinventory = Bukkit.getServer().createInventory(null, 9);
                        outinventory.setItem(0, bricks);
                        outinventory.setItem(5, bricks);
                        String outbase64 = plugin.invman.toBase64(outinventory);

                        int currentCount = plugin.dataManager.getData().getInt("Count");
                        int inputInvID = currentCount + 1;
                        int outputInvID = currentCount + 2;
                        plugin.dataManager.getData().set("Count", currentCount + 2);
                        plugin.dataManager.getData().set("Inventories." + inputInvID, inbase64);
                        plugin.dataManager.getData().set("Inventories." + outputInvID, outbase64);
                        plugin.dataManager.saveData();
                        Location location = player.getLocation();

                        int factID = plugin.sqlm.createFactory("wood", inputInvID, outputInvID, location);
                        player.sendMessage("FactoryID: " + factID);
                        player.sendMessage("InputInv: " + inputInvID);
                        player.sendMessage("OutputInv: " + outputInvID);
                        player.sendMessage("Location: " + location);
                        return true;
                    }
                }
            }
            if (args.length>1){
                if (args[0].equalsIgnoreCase("loadfactinput")){
                    if (args.length==2){
                        int factID = Integer.parseInt(args[1]);
                        Factory factory = plugin.sqlm.getFactory(factID);
                        Inventory inventory = null;
                        try {
                            inventory = plugin.invman.fromBase64(plugin.dataManager.getData().getString("Inventories."+ factory.getInputid()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (inventory!=null) {
                            player.openInventory(inventory);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
