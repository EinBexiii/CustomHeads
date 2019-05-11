package de.bex.customheads.event;


import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import lombok.Getter;

/**
 * @author Bex | EinBexiii
 * @version 1.0
 */
public class HeadPlaceEvent extends Event implements Cancellable {

    @Getter
    private final static HandlerList handlers = new HandlerList();

    @Getter
    private Player player;

    @Getter
    private String HeadName;


    public HeadPlaceEvent ( Player player , String headName) {
        this.player = player;
        this.HeadName = headName;
    }
}
