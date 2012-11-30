package atomicstryker.battletowers.common;

import java.util.Random;
import java.util.Set;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenHandler implements IWorldGenerator
{    
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.getBiomeGenForCoords(chunkX, chunkZ) != BiomeGenBase.hell)
        {
            generateSurface(world, random, chunkX*16, chunkZ*16);
        }
    }
    
    private void generateSurface(World world, Random random, int xActual, int zActual)
    {
        if (AS_BattleTowersCore.canTowerSpawnAt(xActual, zActual))
        {
            if (attemptToSpawnTower(world, random, xActual, zActual))
            {
                //System.out.println("Battle Tower spawned at [ "+xActual+" | "+zActual+" ]");
            }
            else
            {
                AS_BattleTowersCore.setTowerSpawnFailedAt(xActual, zActual);
            }
        }
    }
    
    private boolean attemptToSpawnTower(World world, Random random, int x, int z)
    {
        int y = getSurfaceBlockHeight(world, x, z);
        if (y == 49) return false;
        
        return ((new AS_WorldGenTower(this)).generate(world, random, x, y, z));
    }
    
    private int getSurfaceBlockHeight(World world, int x, int z)
    {
        int h = 50;
        
        do
        {
            h++;
        }
        while (world.getBlockId(x, h, z) != 0);
        
        return h-1;
    }
    
    public TowerStageItemManager getTowerStageManagerForFloor(int floor, Random rand)
    {
        floor--; // subtract 1 to match the floors to the array
        
        if (floor >= AS_BattleTowersCore.floorItemManagers.length)
        {
            floor = AS_BattleTowersCore.floorItemManagers.length-1;
        }
        if (floor < 1)
        {
            floor = 1;
        }
        
        return new TowerStageItemManager(AS_BattleTowersCore.floorItemManagers[floor]);
    }

}