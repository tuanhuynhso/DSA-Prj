package utils;

import java.util.HashMap;
import java.util.Map;
import skills.Skill;

public class CooldownManager {
    private static CooldownManager instance = null;

    public static CooldownManager getInstance() {
        if (instance == null) {
            instance = new CooldownManager();
        }
        return instance;
    }

    private Map<Skill, Double> cooldowns = new HashMap<>();

    public boolean available(Skill s) {
        return currentTime() >= cooldowns.getOrDefault(s, 0d);
    }

    public void start(Skill s) {
        cooldowns.put(s, currentTime() + s.cooldown);
    }

    private double currentTime() {
        return System.currentTimeMillis() / 1000d; // Convert to seconds
    }
}
