package net.astrocube.skywars.listener.damage;

import com.google.inject.Inject;
import net.astrocube.skywars.api.game.SpawnProtectionChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerFallListener implements Listener {

    private @Inject SpawnProtectionChecker spawnProtectionChecker;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (
                    event.getCause() == EntityDamageEvent.DamageCause.FALL &&
                    spawnProtectionChecker.hasProtection(player.getDatabaseIdentifier())
            ) {
                spawnProtectionChecker.removeFromRegistry(player.getDatabaseIdentifier());
                event.setCancelled(true);
            }
        }

    }

}
