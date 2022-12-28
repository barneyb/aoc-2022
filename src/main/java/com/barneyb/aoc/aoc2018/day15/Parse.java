package com.barneyb.aoc.aoc2018.day15;

import com.barneyb.util.Vec2;
import lombok.Value;
import lombok.val;

import java.util.ArrayList;
import java.util.Collection;

@Value
class Parse {
    // @formatter:off
    Collection<Vec2> elves   = new ArrayList<>();
    Collection<Vec2> goblins = new ArrayList<>();
    Collection<Vec2> spaces  = new ArrayList<>();

    void addElf   (int x, int y) { elves  .add(new Vec2(x, y)); }
    void addGoblin(int x, int y) { goblins.add(new Vec2(x, y)); }
    void addSpace (int x, int y) { spaces .add(new Vec2(x, y)); }

    Battlefield toBattlefield() {
        val bf = new Battlefield();
        for (val e : elves  ) bf.addElf   (e);
        for (val e : goblins) bf.addGoblin(e);
        for (val e : spaces ) bf.addSpace (e);
        return bf;
    }
    // @formatter:on
}
