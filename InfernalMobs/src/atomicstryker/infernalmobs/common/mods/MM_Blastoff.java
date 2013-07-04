package atomicstryker.infernalmobs.common.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import atomicstryker.infernalmobs.common.InfernalMobsCore;
import atomicstryker.infernalmobs.common.MobModifier;

public class MM_Blastoff extends MobModifier
{
    public MM_Blastoff(EntityLivingBase mob)
    {
        this.modName = "Blastoff";
    }
    
    public MM_Blastoff(EntityLivingBase mob, MobModifier prevMod)
    {
        this.modName = "Blastoff";
        this.nextMod = prevMod;
    }
    
    private long nextAbilityUse = 0L;
    private final static long coolDown = 15000L;
    
    @Override
    public boolean onUpdate(EntityLivingBase mob)
    {
        if (getMobTarget() != null
        && getMobTarget() instanceof EntityPlayer)
        {
            tryAbility(mob, getMobTarget());
        }
        
        return super.onUpdate(mob);
    }
    
    @Override
    public float onHurt(EntityLivingBase mob, DamageSource source, float damage)
    {
        if (source.getEntity() != null
        && source.getEntity() instanceof EntityLivingBase)
        {
            tryAbility(mob, (EntityLivingBase) source.getEntity());
        }
        
        return super.onHurt(mob, source, damage);
    }

    private void tryAbility(EntityLivingBase mob, EntityLivingBase target)
    {
        if (target == null || !mob.canEntityBeSeen(target))
        {
            return;
        }
        
        long time = System.currentTimeMillis();
        if (time > nextAbilityUse)
        {
            nextAbilityUse = time+coolDown;
            mob.worldObj.playSoundAtEntity(mob, "mob.slimeattack", 1.0F, (mob.worldObj.rand.nextFloat() - mob.worldObj.rand.nextFloat()) * 0.2F + 1.0F);
            
            if (target.worldObj.isRemote || !(target instanceof EntityPlayer))
            {
                target.addVelocity(0, 1.1D, 0);
            }
            else
            {
                InfernalMobsCore.instance().sendVelocityPacket((EntityPlayer)target, 0D, 1.1D, 0D);
            }
        }
    }
    
    @Override
    public Class<?>[] getModsNotToMixWith()
    {
        return modBans;
    }
    private static Class<?>[] modBans = { MM_Webber.class };
    
    @Override
    protected String[] getModNameSuffix()
    {
        return suffix;
    }
    private static String[] suffix = { " of Mission Control", " the NASA", " of WEEEEE" };
    
    @Override
    protected String[] getModNamePrefix()
    {
        return prefix;
    }
    private static String[] prefix = { " thumping ", " trolling ", " byebye " };
    
}