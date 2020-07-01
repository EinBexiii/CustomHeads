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
import cn.nukkit.utils.SkinAnimation;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
		
		/**
		 * NEW Skintag to set with new Skindata
		 * @author depascaldc
		 * @since 01.01.2020
		 */
		CompoundTag skinTag = new CompoundTag()
                .putByteArray("Data", head.getSkinData().data)
                .putInt("SkinImageWidth", head.getSkinData().width)
                .putInt("SkinImageHeight", head.getSkinData().height)
                .putString("ModelId", head.getSkinId())
                .putByteArray("SkinResourcePatch", head.getSkinResourcePatch().getBytes(StandardCharsets.UTF_8))
                .putByteArray("GeometryData", head.getGeometryData().getBytes(StandardCharsets.UTF_8))
                .putByteArray("AnimationData", head.getAnimationData().getBytes(StandardCharsets.UTF_8))
                .putBoolean("PremiumSkin", head.isPremium())
                .putBoolean("PersonaSkin", head.isPersona())
                .putBoolean("CapeOnClassicSkin", head.isCapeOnClassic());
        List<SkinAnimation> animations = head.getAnimations();
        if (!animations.isEmpty()) {
            ListTag<CompoundTag> animationsTag = new ListTag<>("AnimationImageData");
            for (SkinAnimation animation : animations) {
                animationsTag.add(new CompoundTag()
                        .putFloat("Frames", animation.frames)
                        .putInt("Type", animation.type)
                        .putInt("ImageWidth", animation.image.width)
                        .putInt("ImageHeight", animation.image.height)
                        .putByteArray("Image", animation.image.data));
            }
            skinTag.putList(animationsTag);
        }
        
		return new CompoundTag()
				.putList(new ListTag<>("Pos").add(new DoubleTag("", loc.x + 0.5)).add(new DoubleTag("", loc.y))
						.add(new DoubleTag("", loc.z + 0.5)))
				.putList(new ListTag<DoubleTag>("Motion").add(new DoubleTag("", 0)).add(new DoubleTag("", 0))
						.add(new DoubleTag("", 0)))
				.putList(new ListTag<FloatTag>("Rotation").add(new FloatTag("", (float) this.getYaw()))
						.add(new FloatTag("", (float) loc.getPitch())))
				.putBoolean("Invulnerable", true).putString("NameTag", "").putString("name", name)
				.putBoolean("npc", true).putFloat("scale", 1)
				.putCompound("Skin", skinTag);
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
