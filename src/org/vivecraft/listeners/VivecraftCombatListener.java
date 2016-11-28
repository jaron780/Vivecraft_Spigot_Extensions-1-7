package org.vivecraft.listeners;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.vivecraft.VSE;
import org.vivecraft.VivePlayer;


public class VivecraftCombatListener implements Listener{

	private VSE vse;
	
	public VivecraftCombatListener(VSE plugin){
		this.vse = plugin;
	}
	
	   @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	   public void onProjectileLaunch(ProjectileLaunchEvent event) {
		   //position all projectiles correctly.
		   
		   final Projectile proj = event.getEntity();
		   if (!(proj.getShooter() instanceof Player) || !VSE.isVive((Player) proj.getShooter()))
			   return;

		    Player pl = (Player)proj.getShooter();
		    VivePlayer vp = (VivePlayer)VSE.vivePlayers.get(pl.getUniqueId());
		   
		   int hand = 0;
		   if (proj instanceof CraftArrow) hand = 1;
		   //TODO: check for seated mode.
		   
		   if ((vp == null) && (this.vse.getConfig().getBoolean("debug.enabled"))) {
			   vse.getLogger().warning(" Error on projectile launch!");
		   }
		   
		   //this only works if the incoming speed is at max (based! on draw time)
		   //TODO: properly scale in all cases.
		   
		   
		   proj.teleport(vp.getControllerPos(hand));
		   if(proj.getType() == EntityType.ARROW && vp.getDraw() != 0) {
			   proj.setVelocity(proj.getVelocity().multiply(vp.getDraw()));  
		   }
		   
	   }
	
	   @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	    public void onProjectileHit(EntityDamageByEntityEvent event) {
	        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof LivingEntity) {
	            final Arrow arrow = (Arrow) event.getDamager();
	            if (!(arrow.getShooter() instanceof Player) || !VSE.isVive((Player) arrow.getShooter()))
	                return;
	            
			    Player pl = (Player)arrow.getShooter();
			    VivePlayer vp = (VivePlayer)VSE.vivePlayers.get(pl.getUniqueId());
	 			
	            if(!vp.isSeated())
	 				event.setDamage(event.getDamage()*vse.getConfig().getDouble("bow.multiplier"));

	            //TODO: configurable Vive player arrow damage
	            
	            
	            //TODO: re-do Possi's headshot and longbow code?
	            
//	            final LivingEntity target = (LivingEntity) event.getEntity();               
//	            final Location eyeloc = target.getEyeLocation();
//	            if (eyeloc != null) {
//	                if (isDebug()) {
//	                    debugParticleLocation(eyeloc, Effect.FLAME);
//	                    debugParticleLocation(eyeloc, Effect.HEART);
//	                    debugParticleLocation(arrow.getLocation(), Effect.FLAME);
//	                    debugParticleLocation(arrow.getLocation(), Effect.LAVA_POP);
//	                    getLog().debug("HEADSHOT FROM: " + arrow.getLocation().toString());
//	                    getLog().debug("HEADSHOT TO  : " + eyeloc.toString() + "   eh: " + target.getEyeHeight());
//	                    //getLog().debug("HEADSHOT DIST: "+distance + "  sqrt:" + eyeloc.distanceSquared(arrow.getLocation()) + " t:"+target.getType());
//	                }
//	                if (doesPierceHead(target, arrow.getLocation(), arrow.getVelocity())) {
//	                    if (isDebug()) {
//	                        getLog().debug("HEADSHOT!!!");
//	                        ((Player) arrow.getShooter()).sendMessage("HEADSHOT!!!");
//	                    }
//	                    final ItemStack helmet = target.getEquipment().getHelmet();
//	                    if (helmet != null && !helmet.getType().equals(Material.AIR)) {
//	                        getLog().debug("HEADSHOT: HAS HELMET: " + helmet.getType());
//	                        String s = plugin.getConfig().getString("bow.helmet");
//	                        if (config.getHelmet().equals(LongbowConfig.RewardType.IGNORE)) {
//	                            return;
//	                        } else if (config.getHelmet().equals(LongbowConfig.RewardType.DROP)) {
//	                            getLog().debug("HEADSHOT: HELMET DROP");
//	                            helmet.setDurability((short) (helmet.getDurability() - 1));
//	                            if (helmet.getDurability() > 0)
//	                                target.getWorld().dropItemNaturally(event.getEntity().getLocation(), helmet);
//	                            target.getEquipment().setHelmet(null);
//	                            return;
//	                        }
//	                    }
//
//	                    switch (config.getHeadshot()) {
//	                        case CRITICAL:
//	                            getLog().debug("HEADSHOT: CRITICAL");
//	                            arrow.setCritical(true);
//	                            break;
//	                        case KILL:
//	                            Double d = Math.max(event.getDamage(), target.getHealth());
//	                            getLog().debug("HEADSHOT: INSTANT KILL");
//	                            getLog().debug("HEADSHOT: INSTANT KILL x" + event.getDamage() + " => " + d + "    (target health: "+target.getHealth()+"/"+target.getMaxHealth()+")");
//	                            event.setDamage(d);
//	                            break;
//	                        case MULTIPLICATOR:
//	                            d = config.getMultiplicator();
//	                            getLog().debug("HEADSHOT: DMG x" + d.toString() + "  -> " + event.getDamage() + " => " + (event.getDamage() * d) + "    (target health: "+target.getHealth()+"/"+target.getMaxHealth()+")");
//	                            if (d > 0) {
//	                                event.setDamage(event.getDamage() * d);
//	                            }
//	                            break;
//	                    }
//	                }
//	            } else {
//	                getLog().debug("NOOO no eyeloc :(");
//	            }
	        }
	    }
}