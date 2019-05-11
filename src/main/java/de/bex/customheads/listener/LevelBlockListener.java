package de.bex.customheads.listener;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemSkull;
import de.bex.customheads.CustomHeads;
import de.bex.customheads.event.HeadPlaceEvent;
import de.bex.customheads.manager.HeadManager;
import lombok.RequiredArgsConstructor;


/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
@RequiredArgsConstructor
public class LevelBlockListener implements Listener {

    private HeadManager headManager;

    private final CustomHeads plugin;


    @EventHandler
    public void onBlockPlace ( BlockPlaceEvent e ) {
        Player player = e.getPlayer();

        Item item = player.getInventory().getItemInHand();


        if(item instanceof ItemSkull && item.getCustomName().startsWith( "[§5Head§f]" )) {
            String name = item.getCustomName().replace( "[§5Head§f]", "" ).replace( "§e", "" ).replace( " ", "" );

            HeadPlaceEvent headPlaceEvent = new HeadPlaceEvent( e.getPlayer(), name );

            this.plugin.getServer().getPluginManager().callEvent( headPlaceEvent );

            if(!headPlaceEvent.isCancelled()) {
                if(this.plugin.getServer().getPlayer( name ) != null){
                    this.headManager = new HeadManager( this.plugin.getServer().getPlayer( name ).getSkin(), e.getBlock().getLocation(), player, this.plugin.getServer().getPlayer( name ).getName() );
                }else{
                    if(this.plugin.getSkins().containsKey( name )) {
                        this.headManager = new HeadManager( this.plugin.getSkins().get( name ) , e.getBlock().getLocation(), player, name );
                    }else{
                        player.sendMessage( CustomHeads.PREFIX + "§cHead could not be loaded!" );
                        player.getInventory().setItemInHand( Item.get( item.getId(), 0, item.getCount() +1 ).setCustomName( item.getCustomName() ) );
                    }

                }
                this.headManager.spawnEntity();



                if(player.isSurvival() || player.isAdventure()) {
                    if(item.getCount() == 1) {
                        player.getInventory().setItemInHand( Item.get( BlockID.AIR, 0, 1 ) );
                    }else {
                        player.getInventory().setItemInHand( Item.get( item.getId(), 0, item.getCount() -1 ).setCustomName( item.getCustomName() ) );
                    }

                }
            }
            e.setCancelled( true );
        }
    }
}
