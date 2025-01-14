package twilightforest.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTFSlimeProjectile extends EntityThrowable {

	public EntityTFSlimeProjectile(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	public EntityTFSlimeProjectile(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

        makeTrail();
	}
	
	/**
	 * How much this entity falls each tick
	 */
	@Override
    protected float getGravityVelocity()
    {
		return 0.006F;
    }
	
	/**
	 * Make slimy trail
	 */
	public void makeTrail() {
		for (int i = 0; i < 2; i++) {
			double dx = posX + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			double dy = posY + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			double dz = posZ + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			worldObj.spawnParticle("slime", dx, dy, dz, 0.0D, 0.0D, 0.0D);
		}
	}
	
	/**
	 * Reflect!
	 */
	@Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
		
        setBeenAttacked();
        
		pop();

        return true;
     }

	/**
	 * What happens when we hit something?
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		// only damage living things
		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLivingBase)
		{
			if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 200))
			{
				// damage armor?
				//TODO:
			}
		}

		pop();

	}

	/**
	 * Yeah, do that
	 */
	protected void pop() {
		for (int i = 0; i < 8; ++i)
		{
			this.worldObj.spawnParticle("slime", this.posX, this.posY, this.posZ, rand.nextGaussian() * 0.05D, rand.nextDouble() * 0.2D, rand.nextGaussian() * 0.05D);
		}
		
		// noise
		this.worldObj.playSoundAtEntity(this, "mob.slime.big", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));


		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}


}
