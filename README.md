# CustomHeads
Customheads is a plugin that allows you to place player heads on your [Nukkit](https://github.com/NukkitX/Nukkit) server.

## Installation
1. Download the Jar [here](https://nukkitx.com/resources/customheads.245/).
2. Put the Jar into your plugins folder.
3. Start your nukkit server, and have fun.

## Features to be added soon
- [ ] Load head from a png file.
- [ ] Get head from a player who's offline.
- [ ] Config
- [x] Update checker

## Commands
- /head < name >

## Permissions
- For command: customheads.admin
  
## Events
There are two events, so you can use them:



#### 1. HeadDestroyEvent
```
@EventHandler
public void onDestroy( HeadDestroyEvent e ) {
  Player player = e.getPlayer();
  
  if(!player.hasPermission("example.test") {
    e.setCancelled( true );
  }
}
```


#### 2. HeadPlaceEvent
```
 @EventHandler
 public void onPlace( HeadPlaceEvent e ) {
   Player player = e.getPlayer();
  
   if(!player.hasPermission("example.test") {
     e.setCancelled( true );
   }
 }
```

## Contact
- Discord: Bex#5272
- Twitter: [@weirdBEXI](https://twitter.com/weirdBEXI?s=09)
