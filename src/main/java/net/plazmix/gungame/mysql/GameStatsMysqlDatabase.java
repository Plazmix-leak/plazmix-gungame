package net.plazmix.gungame.mysql;

import lombok.NonNull;
import net.plazmix.game.GamePlugin;
import net.plazmix.game.mysql.GameMysqlDatabase;
import net.plazmix.game.mysql.RemoteDatabaseRowType;
import net.plazmix.game.user.GameUser;
import net.plazmix.gungame.util.GameConstants;

public final class GameStatsMysqlDatabase extends GameMysqlDatabase {

    public GameStatsMysqlDatabase() {
        super("GunGame", true);
    }

    @Override
    public void initialize() {
        addColumn(GameConstants.KILLS_PLAYER_DATA, RemoteDatabaseRowType.INT, gameUser -> gameUser.getCache().getInt(GameConstants.KILLS_PLAYER_DATA));
        addColumn(GameConstants.DEATHS_PLAYER_DATA, RemoteDatabaseRowType.INT, gameUser -> gameUser.getCache().getInt(GameConstants.DEATHS_PLAYER_DATA));
    }


    @Override
    public void onJoinLoad(@NonNull GamePlugin gamePlugin, @NonNull GameUser gameUser) {
        loadPrimary(false, gameUser, gameUser.getCache()::set);
    }

    @Override
    public void onQuitSave(@NonNull GamePlugin plugin, @NonNull GameUser gameUser) {
        insert(false, gameUser);
    }

}
