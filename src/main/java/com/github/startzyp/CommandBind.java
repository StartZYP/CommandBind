package com.github.startzyp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommandBind extends JavaPlugin implements Listener {
    private HashMap<String,String> CmdMap = new HashMap<>();
    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(),"config.yml");
        if (!(file.exists())){
            saveDefaultConfig();
        }
        loadConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        super.onEnable();
    }

    public void loadConfig(){
        saveConfig();
        List<String> main = getConfig().getStringList("main");
        for (String s:main){
            String[] split = s.split("-");
            System.out.println(split[0]+"-"+split[1]);
            CmdMap.put(split[0],split[1]);
        }
    }

    @EventHandler
    public void PlayerUseItem(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand.hasItemMeta()){
            ItemMeta itemMeta = itemInHand.getItemMeta();
            if (itemMeta!=null&&itemMeta.hasLore()){
                String lore = Objects.requireNonNull(itemMeta.getLore()).toString();
                Set<String> keystring = CmdMap.keySet();
                for(String s:keystring){
                    if (lore.contains(s)){
                        String docmd = CmdMap.get(s);
                        String newcmd = docmd.replace("<player>", player.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),newcmd);
                        int amount = itemInHand.getAmount();
                        if (amount==1){
                            player.setItemInHand(new ItemStack(Material.AIR));
                        }else {
                            itemInHand.setAmount(amount-1);
                            player.setItemInHand(itemInHand);
                        }
                        player.updateInventory();
                    }
                }
            }
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()&&args.length==1&&args[0].equalsIgnoreCase("reload")){
            reloadConfig();
            sender.sendMessage("插件重载成功");
        }
        return super.onCommand(sender, command, label, args);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
