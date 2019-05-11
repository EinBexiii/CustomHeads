package de.bex.customheads.manager;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.level.Location;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.PlayerSkinPacket;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
@RequiredArgsConstructor
public class HeadManager {

    private final Skin head;
    private final Location loc;
    private final Player player;
    private final String name;


    private CompoundTag getNbt() {
        return
                new CompoundTag(  )
                        .putList(new ListTag<>("Pos")
                                .add(new DoubleTag("", loc.x+0.5))
                                .add(new DoubleTag("", loc.y))
                                .add(new DoubleTag("", loc.z + 0.5)))

                        .putList(new ListTag<DoubleTag>("Motion")
                                .add(new DoubleTag("", 0))
                                .add(new DoubleTag("", 0))
                                .add(new DoubleTag("", 0)))

                        .putList(new ListTag<FloatTag>("Rotation")
                                .add(new FloatTag("", (float) this.getYaw() ))
                                .add(new FloatTag("", (float) loc.getPitch())))

                        .putBoolean("Invulnerable", true)
                        .putString("NameTag", "")
                        .putString( "name", name  )
                        .putBoolean("npc", true)
                        .putFloat("scale", 1)

                .putCompound( "Skin", new CompoundTag( )
                        .putString( "ModelId", "geometry." + this.getGeometryName() )
                        .putByteArray( "Data", head.getSkinData() )
                        .putString("GeometryName", "geometry" + this.getGeometryName())
                        .putByteArray("GeometryData", this.geometryContent().getBytes())
                 );
    }

    private double getYaw() {
        if(player.getYaw() >= 180) {
            return player.getYaw() - 180.0;
        }else{
            return player.getYaw() + 180;
        }
    }


    private String getGeometryName() {
        return "head";
    }

    public void spawnEntity() {
        FullChunk fullChunk = loc.getLevel().getChunk( (int) loc.getX() >> 4, (int) loc.getZ() >> 4 );

        EntityHuman entityHuman = (EntityHuman) Entity.createEntity( "EntityHead", fullChunk, this.getNbt() );

        entityHuman.setSkin( this.head );

        entityHuman.spawnToAll();

        entityHuman.setHealth( 1 );

        entityHuman.setNameTagAlwaysVisible( false );
        entityHuman.setNameTagVisible( false );

        entityHuman.setRotation( this.getYaw(), 0.0 );

        this.sendPacket( entityHuman );
    }

    private void sendPacket( EntityHuman entityHuman ) {
        Skin skin = new Skin();

        skin.setGeometryData( this.geometryContent() );
        skin.setGeometryName( "geometry." + this.getGeometryName() );
        skin.setSkinData( entityHuman.getSkin().getSkinData() );
        skin.setSkinId( this.getGeometryName() );

        entityHuman.setSkin( skin );


        PlayerSkinPacket skinPacket = new PlayerSkinPacket();

        skinPacket.skin = skin;
        skinPacket.newSkinName = this.getGeometryName();
        skinPacket.oldSkinName = entityHuman.getSkin().getSkinId();
        skinPacket.premium = true;

        skinPacket.uuid = entityHuman.getUniqueId();

        Server.broadcastPacket( Server.getInstance().getOnlinePlayers().values(), skinPacket );

    }

    private String geometryContent() {
        InputStream inputStream = getClass().getResourceAsStream("/head/geometry.json");

        Scanner s = new Scanner(inputStream).useDelimiter("\\A");

        return s.hasNext() ? s.next() : "";
    }
}
