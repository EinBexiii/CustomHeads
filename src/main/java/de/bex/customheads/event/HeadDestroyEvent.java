package de.bex.customheads.event;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import de.bex.customheads.entity.EntityHead;
import lombok.Getter;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */

public class HeadDestroyEvent extends Event implements Cancellable {

    @Getter
    private final static HandlerList handlers = new HandlerList();

    @Getter
    private Player player;

    @Getter
    private EntityHead head;

    @Getter
    private String HeadName;

    public HeadDestroyEvent ( Player player , EntityHead head, String headName ) {
        this.player = player;
        this.head = head;
        this.HeadName = headName;
    }


}
