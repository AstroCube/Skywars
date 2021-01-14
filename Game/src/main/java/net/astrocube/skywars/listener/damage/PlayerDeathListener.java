package net.astrocube.skywars.listener.damage;

import net.astrocube.skywars.api.event.PlayerDisqualificationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        double damage = event.getDamage();
        Player player = (Player) event.getEntity();

        if ((player.getHealth() - damage) < 1) {
            if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new PlayerDisqualificationEvent(player,null));
            } else if ((player.getHealth() - damage) < 1) {
                EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
                Player killer = (Player) damageEvent.getDamager();
                Bukkit.getPluginManager().callEvent(new PlayerDisqualificationEvent(player, killer));
            }
        }

    }

}
