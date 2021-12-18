package twilightforest.entity;

import com.dunk.tfc.ItemSetup;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.entity.ai.EntityAITFRedcapPlantTNT;
import twilightforest.item.TFItems;

public class EntityTFRedcapSapper extends EntityTFRedcap {
	

	public EntityTFRedcapSapper(World world) {
		super(world);

		this.tasks.addTask(4, new EntityAITFRedcapPlantTNT(this)); // plant TNT
		
        //texture = TwilightForestMod.MODEL_DIR + "redcapsapper.png";
        
        this.setTntLeft(3);

        this.setCurrentItemOrArmor(1, new ItemStack(ItemSetup.wroughtIronBoots));
        this.setCurrentItemOrArmor(0, new ItemStack(ItemSetup.wroughtIronPick, 1));
	}
	
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1500.0D); // max health
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    @Override
	public int getTotalArmorValue()
    {
        int var1 = super.getTotalArmorValue() + 2;

        if (var1 > 20)
        {
            var1 = 20;
        }

        return var1;
    }
    
	
	@Override
	public ItemStack getPick()
	{
		return new ItemStack(ItemSetup.wroughtIronPick);
	}

    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
			// are we in a level 2 hill?
			int chunkX = MathHelper.floor_double(posX) >> 4;
			int chunkZ = MathHelper.floor_double(posZ) >> 4;
			if (TFFeature.getNearestFeature(chunkX, chunkZ, worldObj) == TFFeature.hill2) {
				// award level 2 hill cheevo
				((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHill2);
			}

		}
	}
}
