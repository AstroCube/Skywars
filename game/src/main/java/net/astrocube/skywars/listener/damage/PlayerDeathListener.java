package net.astrocube.skywars.listener.damage;

import net.astrocube.skywars.api.event.PlayerDisqualificationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {

		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		double damage = event.getDamage();
		Player player = (Player) event.getEntity();

		if (event.getCause() == EntityDamageEvent.DamageCause.VOID
				|| (player.getHealth() - damage) < 1) {

			if (!(event instanceof EntityDamageByEntityEvent)) {
				EntityDamageEvent lastDamageCause = player.getLastDamageCause();
				if (lastDamageCause instanceof EntityDamageByEntityEvent) {
					event = lastDamageCause;
				} else {
					event.setCancelled(true);
					Bukkit.getPluginManager().callEvent(new PlayerDisqualificationEvent(player, null));
					return;
				}
			}

			EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
			Entity damager = damageEvent.getDamager();

			if (damager instanceof Projectile) {
				ProjectileSource shooter = ((Projectile) damager).getShooter();
				if (shooter instanceof Entity) {
					damager = (Entity) shooter;
				}
			}

			event.setCancelled(true);
			Player killer = null;

			if (damager instanceof Player) {
				killer = (Player) damager;
			}

			Bukkit.getPluginManager().callEvent(new PlayerDisqualificationEvent(player, killer));
		}
	}
}