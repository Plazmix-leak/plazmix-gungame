package net.plazmix.gungame.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Getter
public enum EnumGameMap {

    APOCALYPSE("Апокалипсис", "Apocalypse"),
    MUSHROOM("Грибная долина", "Mushroom"),
    FARM("Ферма", "Farm"),
    ;

    public static long MAX_SECONDS_TO_MAP_CHANGE = TimeUnit.MINUTES.toSeconds(1);
    public static long SECONDS_TO_MAP_CHANGE = MAX_SECONDS_TO_MAP_CHANGE;

    private static EnumGameMap CURRENT_GAME_MAP = EnumGameMap.APOCALYPSE;


    public static EnumGameMap getCurrentMap() {
        return CURRENT_GAME_MAP;
    }

    //public static EnumGameMap nextMap() {
    //    return CURRENT_GAME_MAP = EnumGameMap.values()[CURRENT_GAME_MAP.ordinal() + 1 >= values().length ? 0 : CURRENT_GAME_MAP.ordinal() + 1];
    //}

    private final String title;
    private final String worldName;
}
