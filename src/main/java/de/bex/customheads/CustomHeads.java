package de.bex.customheads;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.plugin.PluginBase;
import de.bex.customheads.commands.CommandHead;
import de.bex.customheads.entity.EntityHead;
import de.bex.customheads.listener.EntityDamageListener;
import de.bex.customheads.listener.LevelBlockListener;
import de.bex.customheads.listener.PlayerJoinListener;
import de.bex.customheads.utils.Updater;
import lombok.Getter;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
public class CustomHeads extends PluginBase {

    public final static String PREFIX = "§5Custom§cHeads §f> ";

    @Getter
    private Map<String, Skin> skins = new HashMap<>(  );


    @Override
    public void onEnable() {
        super.onEnable();

        this.registerListener();

        this.registerCommand();

        Entity.registerEntity( EntityHead.class.getSimpleName(), EntityHead.class);

        this.getLogger().info( "§ahas been activated!" );

        try {
            Updater updater = new Updater( new URL( "https://raw.githubusercontent.com/EinBexiii/CustomHeads/master/src/main/resources/plugin.yml" ), this );

            updater.check();
        } catch ( MalformedURLException e ) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerListener() {
        this.getServer().getPluginManager().registerEvents( new PlayerJoinListener(this), this );
        this.getServer().getPluginManager().registerEvents( new LevelBlockListener(this), this );
        this.getServer().getPluginManager().registerEvents( new EntityDamageListener(this), this );
    }

    private void registerCommand() {
        this.getServer().getCommandMap().register( "help", new CommandHead( "head", this ) );
    }


}
