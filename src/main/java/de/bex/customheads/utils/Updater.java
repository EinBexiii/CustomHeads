package de.bex.customheads.utils;

import de.bex.customheads.CustomHeads;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
@RequiredArgsConstructor
public class Updater {

    private final URL url;
    private final CustomHeads plugin;

    public void check() {
        this.plugin.getLogger().info( "§bChecking for update..." );

        String version = "canNotCheck";
        String versionLine;

        try {
            URLConnection connection = url.openConnection();
            connection.setUseCaches( false );

            BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );

            while((versionLine = reader.readLine()) != null) {
                if(versionLine.contains( "version:" )) {
                    version = versionLine.substring( 9 );
                    break;
                }
            }
            reader.close();

            if(!version.equalsIgnoreCase( this.plugin.getDescription().getVersion() )) {
                this.plugin.getLogger().info( "§aNew update is avaible!" );

            }else {
                this.plugin.getLogger().info( "§cNo new updates avaible..." );
            }

        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
