package me.fadingfog.hideandseek.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.fadingfog.hideandseek.HideAndSeek;
import org.bukkit.OfflinePlayer;

@SuppressWarnings("NullableProblems")
public class HnsExpansion extends PlaceholderExpansion {
    private final HideAndSeek plugin;

    public HnsExpansion(HideAndSeek plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "FadingFog";
    }

    @Override
    public String getIdentifier() {
        return "hns";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        switch (identifier) {
            case "test":
                return "aboba";
        }

        return null;
    }
}
