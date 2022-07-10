package net.plazmix.gungame.scoreboard;

import lombok.NonNull;
import net.plazmix.core.PlazmixCoreApi;
import net.plazmix.game.user.GameUser;
import net.plazmix.gungame.util.EnumGameMap;
import net.plazmix.gungame.util.GameConstants;
import net.plazmix.scoreboard.BaseScoreboardBuilder;
import net.plazmix.scoreboard.BaseScoreboardScope;
import net.plazmix.scoreboard.animation.ScoreboardDisplayFlickAnimation;
import net.plazmix.utility.DateUtil;
import net.plazmix.utility.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GunGameScoreboard {

    public GunGameScoreboard(@NonNull Player player) {
        BaseScoreboardBuilder scoreboardBuilder = BaseScoreboardBuilder.newScoreboardBuilder();

        scoreboardBuilder.scoreboardScope(BaseScoreboardScope.PROTOTYPE);

        ScoreboardDisplayFlickAnimation displayFlickAnimation = new ScoreboardDisplayFlickAnimation();

        displayFlickAnimation.addColor(ChatColor.LIGHT_PURPLE);
        displayFlickAnimation.addColor(ChatColor.DARK_PURPLE);
        displayFlickAnimation.addColor(ChatColor.WHITE);
        displayFlickAnimation.addColor(ChatColor.DARK_PURPLE);

        displayFlickAnimation.addTextToAnimation("GUNGAME");
        scoreboardBuilder.scoreboardDisplay(displayFlickAnimation);

        scoreboardBuilder.scoreboardLine(14, ChatColor.GRAY + DateUtil.formatPattern(DateUtil.DEFAULT_DATE_PATTERN));
        scoreboardBuilder.scoreboardLine(13, "");
        scoreboardBuilder.scoreboardLine(12, " §fУровень: §e0/15");
        scoreboardBuilder.scoreboardLine(11, "");
        scoreboardBuilder.scoreboardLine(10, " §fУбийств: §a0");
        scoreboardBuilder.scoreboardLine(9, " §fСмертей: §c0");
        scoreboardBuilder.scoreboardLine(8, "");
        scoreboardBuilder.scoreboardLine(7, " §fСмена карты через:");
        scoreboardBuilder.scoreboardLine(6, "  §e...");
        scoreboardBuilder.scoreboardLine(5, "");
        scoreboardBuilder.scoreboardLine(4, "§fКарта: §a" + EnumGameMap.getCurrentMap().getTitle());
        scoreboardBuilder.scoreboardLine(3, "§fСервер: §a" + PlazmixCoreApi.getCurrentServerName());
        scoreboardBuilder.scoreboardLine(2, "");
        scoreboardBuilder.scoreboardLine(1, "§dwww.plazmix.net");

        scoreboardBuilder.scoreboardUpdater((baseScoreboard, player1) -> {
            baseScoreboard.setScoreboardDisplay(displayFlickAnimation);

            baseScoreboard.updateScoreboardLine(12, player, " §fУровень: §e" + player.getLevel() + "§7/§e15");
            baseScoreboard.updateScoreboardLine(10, player, " §fУбийств: §a" + GameUser.from(player).getCache().getInt(GameConstants.KILLS_PLAYER_DATA));
            baseScoreboard.updateScoreboardLine(9, player, " §fСмертей: §c" + GameUser.from(player).getCache().getInt(GameConstants.DEATHS_PLAYER_DATA));

            baseScoreboard.updateScoreboardLine(6, player, "  §e" + NumberUtil.getTime((int) EnumGameMap.SECONDS_TO_MAP_CHANGE));

            baseScoreboard.updateScoreboardLine(4, player, "§fКарта: §a" + EnumGameMap.getCurrentMap().getTitle());

        }, 10);

        scoreboardBuilder.build().setScoreboardToPlayer(player);
    }
}
