package pl.cebula.smp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.gson.Gson;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import dev.rollczi.litecommands.message.LiteMessages;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import pl.cebula.smp.feature.backup.BackupCommand;
import pl.cebula.smp.feature.backup.BackupController;
import pl.cebula.smp.feature.backup.BackupInventory;
import pl.cebula.smp.feature.economy.EconomyCommand;
import pl.cebula.smp.feature.economy.EconomyHolder;
import pl.cebula.smp.configuration.ConfigService;
import pl.cebula.smp.configuration.implementation.MessagesConfiguration;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.database.MongoDatabaseService;
import pl.cebula.smp.feature.command.TrashCommand;
import pl.cebula.smp.feature.help.HelpCommand;
import pl.cebula.smp.feature.help.HelpInventory;
import pl.cebula.smp.feature.job.JobCommand;
import pl.cebula.smp.feature.job.JobController;
import pl.cebula.smp.feature.job.JobInventory;
import pl.cebula.smp.feature.kit.KitCommand;
import pl.cebula.smp.feature.kit.KitInventory;
import pl.cebula.smp.feature.kit.KitPreviewInventory;
import pl.cebula.smp.feature.shop.controller.ShopNpcController;
import pl.cebula.smp.feature.shop.inventory.ShopInventory;
import pl.cebula.smp.feature.top.TopCitizenTask;
import pl.cebula.smp.feature.top.TopManager;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.feature.user.repository.UserRepository;
import pl.cebula.smp.feature.user.task.SpentTimeTask;
import pl.cebula.smp.feature.user.task.UsersSaveTask;
import pl.cebula.smp.listener.JoinQuitListener;

import java.io.File;
import java.util.Random;
import java.util.stream.Stream;

@Getter
public final class SurvivalPlugin extends JavaPlugin {

    @Getter
    public static SurvivalPlugin instance;

    public static final Gson GSON = GsonHolder.GSON;
    private final MongoDatabaseService mongoDatabaseService = new MongoDatabaseService();
    private final UserRepository userRepository = new UserRepository();
    public Economy economy;
    private PluginConfiguration pluginConfiguration;
    private MessagesConfiguration messagesConfiguration;
    private UserService userService;
    private ProtocolManager protocolManager;
    private TopManager topManager;

    private final Random random = new Random();


    private LiteCommands<CommandSender> liteCommands;

    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.userService = new UserService(this.userRepository);

        Bukkit.getServicesManager().register(Economy.class, new EconomyHolder(this.userService), this, ServicePriority.Highest);


        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            this.getLogger().severe("Nie wykryto pluginu Valut");
            this.getServer().shutdown();
            return;
        }
        this.economy = rsp.getProvider();
    }


    @Override
    public void onEnable() {
        Server server = getServer();
        instance = this;

        //load placeholderApi
        new Placeholder(this.userService).register();

        //load configs files
        ConfigService configService = new ConfigService();
        File dataFolder = this.getDataFolder();
        this.pluginConfiguration = configService.create(PluginConfiguration.class, new File(dataFolder, "config.yml"));
        this.messagesConfiguration = configService.create(MessagesConfiguration.class, new File(dataFolder, "messages.yml"));

        // topki
        this.topManager = new TopManager(this.userService);

        // help menu
        HelpInventory helpInventory = new HelpInventory(this);

        // shop Menu
        ShopInventory shopInventory = new ShopInventory(this, userService);

        // job Menu
        JobInventory jobInventory = new JobInventory(this, this.userService);

        // kit
        KitPreviewInventory kitPreviewInventory = new KitPreviewInventory(this.userService, this);
        KitInventory kitInventory = new KitInventory(this, this.pluginConfiguration, kitPreviewInventory);

        // backup
        BackupInventory backupInventory = new BackupInventory(this, this.userService);

        // load users
        this.userRepository.findAll().forEach(this.userService::addUser);

        // load commands
        this.liteCommands = LiteCommandsBukkit.builder()

                .settings(settings -> settings
                        .fallbackPrefix("cebula-survival")
                        .nativePermissions(false)
                )
                .commands(
                        new HelpCommand(this, helpInventory),
                        new TrashCommand(this),
                        new EconomyCommand(this.userService),
                        new JobCommand(jobInventory),
                        new KitCommand(kitInventory),
                        new BackupCommand(backupInventory)
                )
                .message(LiteMessages.MISSING_PERMISSIONS, permissions -> "&4ɴɪᴇ ᴘᴏꜱɪᴀᴅᴀꜱᴢ ᴡʏᴍᴀɢᴀɴᴇᴊ ᴘᴇʀᴍɪꜱᴊɪ&c: " + permissions.asJoinedText())
                .invalidUsage(
                        new InvalidCommandHandle()
                )
                .build();

        // load Listeners
        Stream.of(
                new JoinQuitListener(this.userService),
                new ShopNpcController(this.pluginConfiguration, shopInventory),
                new JobController(this.userService, this.random),
                new BackupController(this.userService)
        ).forEach(listener -> server.getPluginManager().registerEvents(listener, this));

        // load Tasks
        new UsersSaveTask(this,this.userService);
        new TopCitizenTask(this, this.topManager);
        new SpentTimeTask(this, this.userService);
    }

    @Override
    public void onDisable() {

        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

}
