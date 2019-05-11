package de.bex.customheads.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import de.bex.customheads.CustomHeads;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final CustomHeads plugin;

    @EventHandler
    public void onJoin( PlayerJoinEvent e ) {
        Player player = e.getPlayer();


        if(this.plugin.getSkins().containsKey( player.getName().toLowerCase() )) this.plugin.getSkins().replace( player.getName().toLowerCase(), player.getSkin() );
        else this.plugin.getSkins().put( player.getName().toLowerCase(), player.getSkin() );


    }
}
