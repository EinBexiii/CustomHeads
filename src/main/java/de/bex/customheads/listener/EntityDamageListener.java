package de.bex.customheads.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import de.bex.customheads.CustomHeads;
import de.bex.customheads.entity.EntityHead;
import de.bex.customheads.event.HeadDestroyEvent;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
@RequiredArgsConstructor
public class EntityDamageListener implements Listener {

    private final CustomHeads plugin;

    @EventHandler
    public void onEntityDamage ( EntityDamageByEntityEvent e) {

        if(e.getEntity() instanceof EntityHead ) {
            e.setCancelled( true );
            if(e.getDamager() instanceof Player ) {
                Player player = (Player) e.getDamager();
                HeadDestroyEvent headDestroyEvent = new HeadDestroyEvent( player, (EntityHead) e.getEntity(), e.getEntity().namedTag.getString( "name" ) );
                this.plugin.getServer().getPluginManager().callEvent( headDestroyEvent );
                if(player.isCreative()) {
                    if(!headDestroyEvent.isCancelled()) {
                        e.getEntity().close();
                    }
                }else {
                    if(!player.isSpectator()) {
                        if(!headDestroyEvent.isCancelled()) {
                            String name  = e.getEntity().namedTag.getString( "name" );

                            float locR = (float) ThreadLocalRandom.current().nextDouble(0.0, 1.0);

                            e.getEntity().getLocation().getLevel().dropItem( e.getEntity().getLocation().add( locR, locR, locR ), Item.get( ItemID.SKULL, 0, 1 ).setCustomName( "[§5Head§f] §e" + name ) );

                            if(!this.plugin.getSkins().containsKey( name.toLowerCase() )) this.plugin.getSkins().put( name.toLowerCase(), ( (EntityHead) e.getEntity() ).getSkin() );

                            e.getEntity().close();

                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage ( EntityDamageEvent e ) {
        if(e.getEntity() instanceof EntityHead) {
            e.setCancelled( true );
        }
    }

}
