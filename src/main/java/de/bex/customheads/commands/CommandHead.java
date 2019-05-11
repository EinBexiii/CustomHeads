package de.bex.customheads.commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import de.bex.customheads.CustomHeads;


/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
public class CommandHead extends Command {

    private final CustomHeads plugin;

    public CommandHead( String name, CustomHeads plugin ) {
        super( name );
        this.plugin = plugin;
        this.setPermission( "customheads.admin" );
        this.setDescription( "Get a head of a player" );
        this.setUsage( "/head <Name>" );
    }

    @Override
    public boolean execute( CommandSender commandSender, String s, String[] args ) {
        if(commandSender.isPlayer()) {
            if ( commandSender.hasPermission( "customheads.admin" ) ) {
                if ( args.length == 1 ) {
                    Player player = (Player) commandSender;
                    if( Server.getInstance().getPlayer( args[0] ) != null ) {

                        player.getInventory().addItem( Item.get( ItemID.SKULL, 0, 1 ).setCustomName( "[§5Head§f] §e" + args[0] ) );

                        player.sendMessage( CustomHeads.PREFIX + "§aYou get the head from §e" + args[0] );
                    }else if( this.plugin.getSkins().containsKey( args[0].toLowerCase() ) ) {
                        player.getInventory().addItem( Item.get( ItemID.SKULL, 0, 1 ).setCustomName( "[§5Head§f] §e" + args[0] ) );
                        player.sendMessage( CustomHeads.PREFIX + "§aYou get the head from §e" + args[0] );
                    }else {
                        player.sendMessage( CustomHeads.PREFIX + "§cHead could not be loaded!" );
                    }
                }else{
                    commandSender.sendMessage( CustomHeads.PREFIX + this.getUsage() );
                }
            }
        }
        return false;
    }
}
