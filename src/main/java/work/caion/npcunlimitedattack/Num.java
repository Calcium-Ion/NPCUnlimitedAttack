package work.caion.npcunlimitedattack;

import com.pixelmonmod.pixelmon.storage.TrainerPartyStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.battles.AttackEvent;

@Mod(modid = Num.MODID, name = Num.NAME, version = Num.VERSION, acceptableRemoteVersions = "*")
public class Num
{
    public static final String MODID = "npcunlimitedattack";
    public static final String NAME = "NPC Unlimited Attack";
    public static final String VERSION = "1.0";

    final String path = "./config/npcunlimitedattack";
    boolean enable = true;

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
        Sponge.getEventManager().registerListeners(this, this);
        logger.info(MODID + "event registed");
        load();
    }



    private void load(){
        Properties properties = new Properties();
        File pathF = new File(path);
        File config = new File(pathF,"config.cfg");
        try {
            if(!pathF.exists()) pathF.mkdirs();
            if(!config.exists()) config.createNewFile();
            FileInputStream fileInputStream = new FileInputStream(config);
            properties.load(fileInputStream);
            if(properties.get("enable") == null) properties.setProperty("enable", "true");
            enable = Boolean.parseBoolean(properties.getProperty("enable"));
            properties.store(new FileOutputStream(config),"NPC Unlimited Attack");
        } catch (IOException e) {
            System.out.println("配置文件加载失败");
            e.printStackTrace();
        }
    }

    @Listener
    public void onReload(GameReloadEvent event) {

    }

    @SubscribeEvent
    public void onAttackUse(AttackEvent.Use event) {
        if (event.user.pokemon.getStorage() instanceof TrainerPartyStorage) {
            event.user.attack.pp = event.user.attack.getMaxPP();
        }
    }
}
