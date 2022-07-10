package net.plazmix.gungame.state;

import com.comphenix.protocol.utility.MinecraftReflection;
import lombok.NonNull;
import net.plazmix.game.GamePlugin;
import net.plazmix.game.setting.GameSetting;
import net.plazmix.game.state.GameState;
import net.plazmix.game.user.GameUser;
import net.plazmix.game.utility.GameSchedulers;
import net.plazmix.gungame.PlazmixGunGamePlugin;
import net.plazmix.gungame.scoreboard.GunGameScoreboard;
import net.plazmix.gungame.util.EnumGameMap;
import net.plazmix.gungame.util.GameConstants;
import net.plazmix.gungame.util.GameKit;
import net.plazmix.protocollib.team.ProtocolTeam;
import net.plazmix.utility.PlayerUtil;
import net.plazmix.utility.player.PlazmixUser;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class IngameState extends GameState {

    private final GameKit gameKit = new GameKit();

    public IngameState(GamePlugin plugin) {
        super(plugin, "Идет игра", true);
    }

    private void resetPlayer(@NonNull Player player) {
        // Create spawn location.
        player.teleport(getPlugin().getServer().getWorld(EnumGameMap.getCurrentMap().getWorldName()).getSpawnLocation().clone().add(0.5, 0, 0.5));

        // Play sounds.
        player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);

        // Reset hotbar.
        player.getInventory().clear();

        // Выдаем игроку кит по его уровню
        gameKit.giveLevelKit(player);
    }

    @Override
    protected void onStart() {
        GameSetting.PLAYER_DAMAGE.set(plugin.getService(), true);
        GameSetting.ENTITY_DAMAGE.set(plugin.getService(), true);

        // Run map changer
        //GameSchedulers.runTimer(0, 20, () -> {
        //    EnumGameMap.SECONDS_TO_MAP_CHANGE = EnumGameMap.SECONDS_TO_MAP_CHANGE - 1;
//
        //    if (EnumGameMap.SECONDS_TO_MAP_CHANGE <= 0) {
        //        EnumGameMap currentMap = EnumGameMap.nextMap();
//
        //        for (Player player : Bukkit.getOnlinePlayers()) {
        //            player.sendTitle("§lСмена карты §c§lGunGame", "§b" + currentMap.getTitle());
//
        //            resetPlayer(player);
//
        //            player.setLevel(1);
//
        //            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
        //            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
        //        }
//
        //        EnumGameMap.SECONDS_TO_MAP_CHANGE = EnumGameMap.MAX_SECONDS_TO_MAP_CHANGE;
//
        //    } else if (EnumGameMap.SECONDS_TO_MAP_CHANGE <= 5) {
        //        for (Player player : Bukkit.getOnlinePlayers()) {
//
        //            player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
        //            player.sendTitle(ChatColor.GREEN.toString() + EnumGameMap.SECONDS_TO_MAP_CHANGE, "");
        //        }
        //    }
        //});
    }

    @Override
    protected void onShutdown() {
    }

    @EventHandler
    public void onDamageFall(EntityDamageEvent event) {

        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        resetPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        GameUser playerUser = GameUser.from(player);

        int playerLevel = player.getLevel();

        event.setKeepInventory(true);
        event.setKeepLevel(true);

        event.setDeathMessage(null);

        // Если игрок сдох сам по себе
        if (killer == null) {
            playerUser.getCache().add(GameConstants.DEATHS_PLAYER_DATA, 1);

            plugin.broadcastMessage("§d§lGunGame §8:: §fИгрок " + PlayerUtil.getDisplayName(player) + " §fумер");

            if (playerLevel <= 1) {
                player.sendMessage("§d§lGunGame §8:: §fВаш уровень был восстановлен до начального.");

            } else {
                player.setLevel(playerLevel - 1);
            }

            ProtocolTeam playerTeam = ProtocolTeam.findEntry(player);
            playerTeam.setSuffix(" §7" + player.getLevel() + "✫");

            resetPlayer(player);

        } else {
            GameUser killerUser = GameUser.from(killer);

            playerUser.getCache().add(GameConstants.DEATHS_PLAYER_DATA, 1);
            killerUser.getCache().add(GameConstants.KILLS_PLAYER_DATA, 1);

            // Если игрока все-таки кто-то убил
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if (otherPlayer.equals(player)) {
                    otherPlayer.sendMessage("§d§lGunGame §8:: §fВас убил " + PlayerUtil.getDisplayName(killer));
                    continue;
                }

                if (otherPlayer.equals(killer)) {
                    otherPlayer.sendMessage("§d§lGunGame §8:: §fВы убили игрока " + PlayerUtil.getDisplayName(player));
                    continue;
                }

                otherPlayer.sendMessage("§d§lGunGame §8:: §fИгрок " + PlayerUtil.getDisplayName(player) + " §fбыл убит " + PlayerUtil.getDisplayName(killer));
            }

            // Награждаем киллера, за то что он такой крутой
            int killerlevel = killer.getLevel();
            int maxLevel = 30;

            PlazmixUser plazmixUser = PlazmixUser.of(killer);
            plazmixUser.addExperience(5);

            if (killerlevel >= maxLevel) {
                killer.sendMessage("§d§lGunGame §8:: §cОшибка, Ваш уровень максимален!");
                killer.sendMessage("+5 XP");

            } else {

                killer.setLevel(killerlevel + 1);
                killer.updateInventory();

                ProtocolTeam killerTeam = ProtocolTeam.findEntry(killer);
                killerTeam.setSuffix(" §7" + killer.getLevel() + "✫");

                killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                gameKit.giveLevelKit(killer); // Выдаем игроку кит по его уровню

                killer.sendMessage("§d§lGunGame §8:: §fВаш уровень был повышен! Теперь он равен §a" + killer.getLevel());
                killer.sendMessage("+5 XP");


                if (playerLevel <= 1) {
                    player.sendMessage("§d§lGunGame §8:: §fВаш уровень был восстановлен до начального.");

                } else {
                    player.sendMessage("§d§lGunGame §8:: §fЗа смерть у Вас была снят §c1 §fуровень!");
                    player.setLevel(playerLevel - 1);
                }

                ProtocolTeam playerTeam = ProtocolTeam.findEntry(player);
                playerTeam.setSuffix(" §7" + player.getLevel() + "✫");


                player.updateInventory();

                player.setHealth(20);
                player.setFoodLevel(20);

                player.setVelocity(player.getVelocity());

                try {
                    MinecraftReflection.getCraftPlayerClass().getMethod("updateScaledHealth").invoke(player);
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
                }

                resetPlayer(player);
            }
        }

        player.spigot().respawn();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

        player.setPlayerWeather(WeatherType.CLEAR);
        player.setPlayerTime(1200, true);

        player.setGameMode(GameMode.ADVENTURE);

        player.teleport(getPlugin().getServer().getWorld(EnumGameMap.getCurrentMap().getWorldName()).getSpawnLocation().clone().add(0.5, 0, 0.5));
        player.sendMessage("§d§lGunGame §8:: §fСейчас идет игра на карте - §a" + EnumGameMap.getCurrentMap().getTitle());

        player.setLevel(1);
        player.setExp(0);

        GameSchedulers.runLater(20, () -> {

            ProtocolTeam protocolTeam = ProtocolTeam.findEntry(player);
            protocolTeam.setSuffix(" §7" + player.getLevel() + "✫");

            gameKit.giveLevelKit(player);
        });

        new GunGameScoreboard(player);
    }

    @EventHandler
    public void onMoveWater(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getTo().getBlock().getType();

        if (blockType.equals(Material.WATER) || blockType.equals(Material.STATIONARY_WATER)) {

            GameUser playerUser = GameUser.from(player);
            int playerLevel = player.getLevel();

            // Возвращаем все к дефортным значениям и добавляем смерть
            playerUser.getCache().increment(GameConstants.DEATHS_PLAYER_DATA);

            // Отправляем игрока на спавн арены
            resetPlayer(player);

            // Сообщаем игроку что он умер
            player.sendMessage("§d§lGunGame §8:: §fВы умерли от воды 0_о");

            // Убавляем ему уровень если есть
            if (playerLevel <= 1) {
                player.sendMessage("§d§lGunGame §8:: §fВаш уровень был восстановлен до начального");

            } else {

                player.setLevel(playerLevel - 1);
                player.sendMessage("§d§lGunGame §8:: §fЗа смерть у Вас была снят §c1 §fуровень");
            }

        }
    }

    @EventHandler
    public void onMoveSpawn(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

            Player damager = (Player) event.getDamager();
            Player player = (Player) event.getEntity();

            if (damager.getLocation().distance(PlazmixGunGamePlugin.getInstance().getService().getMapWorld().getSpawnLocation().clone().add(0.5, 0, 0.5)) <= 7.0D) {
                event.setCancelled(true);
                damager.sendMessage("§d§lGunGame §8:: §cОшибка, драться на локации спавна нельзя!");

            } else if (player.getLocation().distance(PlazmixGunGamePlugin.getInstance().getService().getMapWorld().getSpawnLocation().clone().add(0.5, 0, 0.5)) <= 7.0D) {
                event.setCancelled(true);
                damager.sendMessage("§d§lGunGame §8:: §cОшибка, драться с игроками, которые на локации спавна нельзя!");
            }

        } else {
            // Если вдруг это не игроки, то мы нахуй отменяем урон (Еще зоофилов тут не хватало)
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

}
