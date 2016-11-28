package org.vivecraft.entities;

import org.vivecraft.VSE;

import net.minecraft.server.v1_7_R4.EntityCreeper;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.PathfinderGoal;

public class CustomGoalSwell extends PathfinderGoal {

	EntityCreeper a;
	EntityLiving b;
	
	public CustomGoalSwell(EntityCreeper arg0) 
	{
		a = arg0;
		a(1);
	}
	
	public double creeperBlowyUppyRadius = 5.0f; //VIVE default is 3
	
	@Override
	public boolean a(){
		VSE vse = (VSE.getPlugin(VSE.class));
		EntityLiving localEntityLiving = this.a.getGoalTarget();
		 if(vse.getConfig().getBoolean("CreeperRadius.enabled") == true){
			 //if((VSE.vivePlayers != null && localEntityLiving != null) && VSE.vivePlayers.containsKey(localEntityLiving.getUniqueID()))
			 if(localEntityLiving != null){
				 creeperBlowyUppyRadius = vse.getConfig().getDouble("CreeperRadius.radius");
			 }
		 }
		return (this.a.cb() > 0) || ((localEntityLiving != null) && (this.a.f(localEntityLiving) < (creeperBlowyUppyRadius*creeperBlowyUppyRadius)));
	}
	
	  public void c()
	  {
	    this.a.getNavigation().h();
	    this.b = this.a.getGoalTarget();
	  }
	  
	  public void d()
	  {
	    this.b = null;
	  }
	  
	  public void e()
	  {
	    if (this.b == null)
	    {
	      this.a.a(-1);
	      return;
	    }
	    if (this.a.f(this.b) > 49.0D)
	    {
	      this.a.a(-1);
	      return;
	    }
	    if (!this.a.getEntitySenses().canSee(this.b))
	    {
	      this.a.a(-1);
	      return;
	    }
	    this.a.a(1);
	  }
}
