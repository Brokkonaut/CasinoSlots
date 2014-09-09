[CasinoSlots](http://ci.graywolf336.com/job/CasinoSlots/) - [JavaDoc](http://ci.graywolf336.com/job/CasinoSlots/javadoc/)
====

A majorly updated version of AnCasino started by Darazo

[![Build Status](http://ci.graywolf336.com/job/CasinoSlots/badge/icon)](http://ci.graywolf336.com/job/CasinoSlots/)

### Update: 2.5.7 ###
* Fixed [player] sometimes not being replaced in broadcast and command actions
* Fixed variables not being replaced if there was a color code in front of them
* Fixed the console not being able to do __/casino remove__
* Play a sound at location and not just for the playing player

### Update: 2.5.6 ###
* Load the plugin after the worlds

### Update: 2.5.5 ###
* Using the logger provided with Bukkit
* Added support for withdrawing money from a player's account, if the reward money is negative it withdraws the money from the player
* Added a check to see if the config exists or not with the config-version property. If the config-version does not equal 1.0, the config will copy over it's default options.
* Added config option to display the information when we kept a chunk loaded, this defaults to false. (see: http://dev.bukkit.org/server-mods/casino-slots/pages/info/config/)
* Added an option to allow types to __also__ cost items
* Added another /casino set option, this time to set the __additional__ item the type costs.
* Added an option to check Towny
** If enabled, will check if the player is a mayor, has a town, and is the resident of a town
** Option to allow only mayors to create them
** Option to allow only players who are part of a town to create them
** Configurable messages for only mayors creating them, only players with towns, and the messages they see whenever they don't have ownership of the plot where the things would be.
* Added an option to check World Guard if the player has permission to build where the slot will be
* Added an alias to the adds, you can now do __/casino create__
* Added an alias to remove, can now do __/casino delete__
* Added an option to smite the player more than once with that action reward (see: http://dev.bukkit.org/server-mods/casino-slots/pages/info/actions)
* Added more variables for the broadcast message
** **[type]** The name of the type of the slot machine.
** **[moneyown]** The amount of money won!
* Added __/casino set debug__ to toggle if we're in debugging or not, note: This isn't in /casino set as debugging is very spamming to the console and it is only temporary.
* Fixed the chunk listener from testing the same chunk over and over.
* Fixed the broadcast action not including the latest colors.
* Fixed the action list not being acting like a list
* Fixed the console not being able to do __/casino list__
* Fixed the console not being able to do __/casino reload__
* Fixed the console not being able to do __/casino stats__
* Fixed the console not being able to do __/casino toggle__
* Fixed the console not being able to do __/casino version__
* Fixed the console not being able to do __/casino__
* Fixed the console __/casino__ to only display what the console can do
* Fixed players with create managed permissions see the commands they are allowed
* Fixed players being able to toggle if a slot is enabled or not even if they didn't have permission
* Fixed __/casino set__ being open to all players.
* Fixed a NPE when an Explosion had no blocks
* Fixed permissions not working correctly, now you can give players casinoslots.create.managed.<type> and they'll be able to create that type (http://dev.bukkit.org/server-mods/casino-slots/pages/info/permissions/)
* Fixed sounds not playing, now using the new API for sounds
** Note to people using tekkit, you will need to change the __bukkit-version__ to something smaller than 2377 or else you will get errors.

### Update: 2.5.2 ###
* Fixed the stats.yml file being over wrote each restart/reload
* Fixed the stats.yml displaying incorrect data
* Fixed the slots.yml not being saved when something was done to it, mostly noticeable when using managed slots (thanks LlmDl for noticing this)
* Fixed the prefix, colors, and all that from not changing when doing a /casino reload
* Fixed an NPE with the 'give' action.
* Added support for the new color codes
* Added new stats, wins and losts. This way you can keep track of how many people won it and how many people lost.
* Added a debugging option, I would advise as to not use this...it tends to spam the console a lot.
* Simplified the permissions check
* Made reloading the config and what happens during that a little better.

### Update: 2.5.1 ###
* Made sure the stats.yml file is being saved upon disabling of the plugin

### Update: 2.5.1 ###
* Fixed the slots.yml file not saving when being changed

### Update: 2.5.0 ###
* Fixed the reels displaying duplicate blocks
* Fixed the stats.yml not being saved
* Re-added the reload command
* Added the ability to give out enchanted items as rewards.

### Update: 2.4.9 ###
* Added /casino set sign <slot>
** This makes it so  you can punch and sign and that sign will update with the latest winner
* Added /casino set type <slotname> <type>
** This allows you to change the type of a slot machine to something different
* Enhanced the way the PlayerListner is handled
* Made the plugin check the chunks for both the controller and one of the reel blocks
* Started working on getting managed slots able to be made with permissions
* Created a check when loading for the seemingly random NPE on getBlock, tells you what to do now