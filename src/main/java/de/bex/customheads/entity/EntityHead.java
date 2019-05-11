package de.bex.customheads.entity;

import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
public class EntityHead extends EntityHuman {


    public EntityHead( FullChunk chunk, CompoundTag nbt ) {
        super( chunk, nbt );
    }

    @Override
    public boolean isNameTagVisible() {
        return false;
    }

    @Override
    public boolean isSneaking() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }
}
