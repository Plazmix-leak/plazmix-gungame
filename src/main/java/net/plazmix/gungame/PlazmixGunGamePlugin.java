package net.plazmix.gungame;

import net.plazmix.core.PlazmixCoreApi;
import net.plazmix.game.GamePlugin;
import net.plazmix.game.installer.GameInstallerTask;
import net.plazmix.game.utility.GameSchedulers;
import net.plazmix.gungame.mysql.GameStatsMysqlDatabase;
import net.plazmix.gungame.state.IngameState;
import net.plazmix.gungame.util.EnumGameMap;
import org.bukkit.ChatColor;
import org.bukkit.World;


/*  Leaked by https://t.me/leak_mine
    - Все слитые материалы вы используете на свой страх и риск.

    - Мы настоятельно рекомендуем проверять код плагинов на хаки!
    - Список софта для декопиляции плагинов:
    1. Luyten (последнюю версию можно скачать можно тут https://github.com/deathmarine/Luyten/releases);
    2. Bytecode-Viewer (последнюю версию можно скачать можно тут https://github.com/Konloch/bytecode-viewer/releases);
    3. Онлайн декомпиляторы https://jdec.app или http://www.javadecompilers.com/

    - Предложить свой слив вы можете по ссылке @leakmine_send_bot или https://t.me/leakmine_send_bot
*/

public class PlazmixGunGamePlugin extends GamePlugin {

    @Override
    public GameInstallerTask getInstallerTask() {
        return null;
    }

    @Override
    protected void handleEnable() {

        // Setup game info.
        service.setMapName(EnumGameMap.getCurrentMap().getWorldName());
        service.setGameName("GunGame");
        service.setServerMode("GunGame");
        service.setMaxPlayers(50);

        // Add game databases.
        service.addGameDatabase(new GameStatsMysqlDatabase());

        // Register game states.
        //service.registerState(new WaitingState(this));
        service.registerState(new IngameState(this));

        // Run world ticker
        GameSchedulers.runTimer(0, 10, () -> {

            for (World world : getServer().getWorlds()) {

                world.setStorm(false);
                world.setThundering(false);

                world.setWeatherDuration(0);
                world.setTime(1200);
            }
        });
    }

    @Override
    protected void handleDisable() {
        broadcastMessage(ChatColor.RED + "Арена " + PlazmixCoreApi.getCurrentServerName() + " перезапускается!");
    }

}

