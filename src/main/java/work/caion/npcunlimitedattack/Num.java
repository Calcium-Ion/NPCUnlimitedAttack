package work.caion.npcunlimitedattack;

import com.pixelmonmod.pixelmon.storage.TrainerPartyStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Logger;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;

@Mod(modid = Num.MODID, name = Num.NAME, version = Num.VERSION, acceptableRemoteVersions = "*")
public class Num
{
    public static final String MODID = "npcunlimitedattack";
    public static final String NAME = "NPC Unlimited Attack";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        Pixelmon.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAttackUse(AttackEvent.Use event) {
        if (event.user.pokemon.getStorage() instanceof TrainerPartyStorage) {
            event.user.attack.pp = event.user.attack.getMaxPP();
        }
    }
}
