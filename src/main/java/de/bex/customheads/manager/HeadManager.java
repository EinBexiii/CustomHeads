package de.bex.customheads.manager;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.level.Location;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
public class HeadManager {

	private final Skin head;
	private final Location loc;
	private final Player player;
	private final String name;
	
	/**
	 * Constructor HeadManager
	 * @author depascaldc
	 * @param head
	 * @param loc
	 * @param player
	 * @param name
	 */
	public HeadManager(Skin head, Location loc, Player player, String name) {
		this.name = name;
		this.player = player;
		this.head = head;
		this.loc = loc;
	}

	private CompoundTag getNbt() {
		return new CompoundTag()
				.putList(new ListTag<>("Pos").add(new DoubleTag("", loc.x + 0.5)).add(new DoubleTag("", loc.y))
						.add(new DoubleTag("", loc.z + 0.5)))
				.putList(new ListTag<DoubleTag>("Motion").add(new DoubleTag("", 0)).add(new DoubleTag("", 0))
						.add(new DoubleTag("", 0)))
				.putList(new ListTag<FloatTag>("Rotation").add(new FloatTag("", (float) this.getYaw()))
						.add(new FloatTag("", (float) loc.getPitch())))
				.putBoolean("Invulnerable", true).putString("NameTag", "").putString("name", name)
				.putBoolean("npc", true).putFloat("scale", 1)
				.putCompound("Skin",
						new CompoundTag().putString("ModelId", "geometry." + this.getGeometryName())
								.putByteArray("Data", head.getSkinData())
								.putString("GeometryName", "geometry" + this.getGeometryName())
								.putByteArray("GeometryData", this.geometryContent().getBytes()));
	}

	private double getYaw() {
		if (player.getYaw() >= 180) {
			return player.getYaw() - 180.0;
		} else {
			return player.getYaw() + 180;
		}
	}

	private String getGeometryName() {
		return "head";
	}

	/**
	 * Fix Client-Crash by spawning the head...
	 * 
	 * @author depascaldc https://depascaldc.de
	 */
	public void spawnEntity() {
		FullChunk fullChunk = loc.getLevel().getChunk((int) loc.getX() >> 4, (int) loc.getZ() >> 4);
		EntityHuman entityHuman = (EntityHuman) Entity.createEntity("EntityHead", fullChunk, this.getNbt());
		entityHuman.setHealth(1);
		entityHuman.setNameTagAlwaysVisible(false);
		entityHuman.setNameTagVisible(false);
		entityHuman.setRotation(this.getYaw(), 0.0);
		// create skin here and dont send data packet ( cuz of client crash )
		Skin skin = new Skin();
		skin.setGeometryData(this.geometryContent());
		skin.setGeometryName("geometry." + this.getGeometryName());
		skin.setSkinData(entityHuman.getSkin().getSkinData());
		skin.setSkinId(this.getGeometryName());
		entityHuman.setSkin(skin);
		// and spawn Entity with all attributes here when set...
		entityHuman.spawnToAll();
	}

	private String geometryContent() {
		InputStream inputStream = getClass().getResourceAsStream("/head/geometry.json");
		Scanner s = new Scanner(inputStream).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
