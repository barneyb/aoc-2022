package com.barneyb.aoc.pry;

import lombok.val;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Model {

    public static void main(String[] args) {
        val database = new HashMap<>(Map.of("a", 6, "b", 7));
        val api = new API(database);
        val alice = new Client(api);
        val bob = new Client(api);
        bob.update("a", 9);
        System.out.println(bob.render("c"));
        System.out.println(alice.render("c"));
    }

    private static class API {
        private final Map<String, Integer> database;

        public API(Map<String, Integer> database) {
            this.database = database;
        }

        public Map<String, Integer> initialLoad() {
            return Collections.unmodifiableMap(database);
        }

        public void update(String key, Integer value) {
            database.put(key, value);
        }
    }

    private static class Client {
        private final API api;
        private final Map<String, Integer> redux;

        private void thunk() {
            this.redux.put("c", this.redux.get("a") * this.redux.get("b"));
        }

        public Client(API api) {
            this.api = api;
            this.redux = new HashMap<>(this.api.initialLoad());
            thunk();
        }

        public void update(String key, Integer value) {
            redux.put(key, value);
            thunk();
            api.update(key, value);
        }

        public Integer render(String key) {
            return redux.get(key);
        }
    }
}
