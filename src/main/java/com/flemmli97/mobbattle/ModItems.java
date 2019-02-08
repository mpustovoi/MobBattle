package com.flemmli97.mobbattle;

import com.flemmli97.mobbattle.items.ItemExtendedSpawnEgg;
import com.flemmli97.mobbattle.items.MobArmor;
import com.flemmli97.mobbattle.items.MobArmy;
import com.flemmli97.mobbattle.items.MobEffect;
import com.flemmli97.mobbattle.items.MobEffectGive;
import com.flemmli97.mobbattle.items.MobEquip;
import com.flemmli97.mobbattle.items.MobGroup;
import com.flemmli97.mobbattle.items.MobHeal;
import com.flemmli97.mobbattle.items.MobKill;
import com.flemmli97.mobbattle.items.MobMount;
import com.flemmli97.mobbattle.items.MobStick;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = MobBattle.MODID)
public class ModItems {

	public static ToolMaterial mob_mat = EnumHelper.addToolMaterial("mob_mat", 0, -1, 1.0F, -5.0F, 0);
	public static Item mobStick = new MobStick();
	public static Item mobKill = new MobKill();
	public static Item mobHeal = new MobHeal();
	public static Item mobEffect = new MobEffect();
	public static Item mobGroup = new MobGroup();
	public static Item mobArmor = new MobArmor();
	public static Item mobMount = new MobMount();
	public static Item mobArmy = new MobArmy();
	public static Item mobEquip = new MobEquip();
	public static Item mobEffectGiver = new MobEffectGive();
	public static Item spawner = new ItemExtendedSpawnEgg();
	
	@SubscribeEvent
	public static final void registerItems(RegistryEvent.Register<Item> event) {
	    event.getRegistry().register(mobStick);
	    event.getRegistry().register(mobKill);
	    event.getRegistry().register(mobHeal);
	    event.getRegistry().register(mobEffect);
	    event.getRegistry().register(mobGroup);
	    event.getRegistry().register(mobArmor);
	    event.getRegistry().register(mobArmy);
	    event.getRegistry().register(mobMount);
	    event.getRegistry().register(mobEquip);
	    event.getRegistry().register(mobEffectGiver);
	    event.getRegistry().register(spawner);
	    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.spawner, new BehaviorDefaultDispenseItem()
        {
	        @Override
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
                double x = source.getX() + (double)enumfacing.getFrontOffsetX();
                double y = (double)(source.getBlockPos().getY() + enumfacing.getFrontOffsetY() + 0.2F);
                double z = source.getZ() + (double)enumfacing.getFrontOffsetZ();
                BlockPos blockpos = new BlockPos(x,y,z);
                Entity entity = ItemExtendedSpawnEgg.spawnEntity(source.getWorld(), stack, blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D);
                if (entity != null)
                {
                    stack.shrink(1);
                    ItemExtendedSpawnEgg.applyEntityNBT(entity, stack);
                }
                return stack;
            }
        });
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static final void initModels(ModelRegistryEvent event)
	{
		((MobStick) mobStick).initModel();
		((MobEffect) mobEffect).initModel();
		((MobKill) mobKill).initModel();
		((MobHeal) mobHeal).initModel();
		((MobGroup) mobGroup).initModel();
		((MobArmor) mobArmor).initModel();
		((MobMount) mobMount).initModel();
		((MobArmy) mobArmy).initModel();
		((MobEquip) mobEquip).initModel();
		((MobEffectGive) mobEffectGiver).initModel();
		((ItemExtendedSpawnEgg)spawner).initModel();
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static final void registerTextureSprite(TextureStitchEvent.Pre event)
	{
		ResourceLocation res = new ResourceLocation(MobBattle.MODID + ":gui/armor_slot_sword");
		event.getMap().registerSprite(res);
	}
}
