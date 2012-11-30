package atomicstryker.minefactoryreloaded.common.tileentities;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import atomicstryker.minefactoryreloaded.common.core.BlockPosition;
import atomicstryker.minefactoryreloaded.common.core.Util;

public class TileEntityBlockBreaker extends TileEntityFactoryPowered
{

	public TileEntityBlockBreaker()
	{
		super(25, 25);
	}

	@Override
	public void doWork()
	{
		if(!powerAvailable())
		{
			return;
		}
		
		float dropOffsetX = 0.0F;
		float dropOffsetZ = 0.0F;
		
		if(getDirectionFacing() == ForgeDirection.NORTH)
		{
			dropOffsetX = -0.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == ForgeDirection.EAST)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = -0.5F;
		}
		else if(getDirectionFacing() == ForgeDirection.SOUTH)
		{
			dropOffsetX = 1.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == ForgeDirection.WEST)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = 1.5F;
		}
		
		BlockPosition bp = BlockPosition.fromFactoryTile(this);
		bp.moveForwards(1);
		int blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		
		Block b = Block.blocksList[blockId];
		if(b != null && !b.isAirBlock(worldObj, bp.x, bp.y, bp.z) && !Util.isBlockUnbreakable(b) && b.getBlockHardness(worldObj, bp.x, bp.y, bp.z) >= 0)
		{
			List<ItemStack> drops = b.getBlockDropped(worldObj, bp.x, bp.y, bp.z, worldObj.getBlockMetadata(bp.x, bp.y, bp.z), 0);
			for(ItemStack s : drops)
			{
				dropStack(s, dropOffsetX, 0, dropOffsetZ);
			}
			worldObj.setBlockWithNotify(bp.x, bp.y, bp.z, 0);
		}
	}

}