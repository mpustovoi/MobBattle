package com.flemmli97.mobbattle;



import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EquipMessage  implements IMessage{

	public ItemStack equipment;
	public String uuid;
	public int slot;
	
	public EquipMessage(){}
	
	public EquipMessage(ItemStack stack, String entityUUID, int slot)
	{
		this.equipment = stack;
		this.uuid = entityUUID;
		this.slot = slot;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		this.equipment = compound.hasKey("Stack") ? ItemStack.loadItemStackFromNBT((NBTTagCompound) compound.getTag("Stack")) : null;
		this.uuid = compound.getString("UUID");
		this.slot = compound.getInteger("Slot");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		compound.setString("UUID", uuid);
		if(equipment!=null)
		compound.setTag("Stack", equipment.writeToNBT(tag));
		compound.setInteger("Slot", slot);
		ByteBufUtils.writeTag(buf, compound);
	}
	
	public static class Handler implements IMessageHandler<EquipMessage, IMessage> {

        @Override
        public IMessage onMessage(EquipMessage msg, MessageContext ctx) {
        	World world = ctx.getServerHandler().playerEntity.worldObj;
        	for (final Object obj : world.loadedEntityList)
			{
        		if(obj instanceof EntityLiving)
        		{
        			EntityLiving entity = (EntityLiving) obj;
					if (entity.getUniqueID().equals(UUID.fromString(msg.uuid)))
					{
						entity.setItemStackToSlot(CommonProxy.slot[msg.slot], msg.equipment);
					}
				}
			}
            return null;
        }
    }
}