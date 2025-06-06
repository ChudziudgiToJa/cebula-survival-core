package pl.cebula.smp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.eternalcode.core.EternalCoreApi;
import com.eternalcode.core.EternalCoreApiProvider;
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
import pl.cebula.smp.configuration.ConfigService;
import pl.cebula.smp.configuration.implementation.*;
import pl.cebula.smp.database.MongoDatabaseService;
import pl.cebula.smp.feature.abyss.AbyssTask;
import pl.cebula.smp.feature.afkzone.AfkZoneManager;
import pl.cebula.smp.feature.afkzone.AfkZoneTask;
import pl.cebula.smp.feature.backup.BackupCommand;
import pl.cebula.smp.feature.backup.BackupController;
import pl.cebula.smp.feature.backup.BackupInventory;
import pl.cebula.smp.feature.blacksmith.BlacksmithController;
import pl.cebula.smp.feature.blacksmith.BlacksmithInventory;
import pl.cebula.smp.feature.blocker.BlockerController;
import pl.cebula.smp.feature.blocker.MobChunkLimitTask;
import pl.cebula.smp.feature.bordercollection.BorderCollectionController;
import pl.cebula.smp.feature.bordercollection.BorderCollectionInventory;
import pl.cebula.smp.feature.chat.ChatCharController;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.command.ClanCommand;
import pl.cebula.smp.feature.clan.command.ClanCommandArgument;
import pl.cebula.smp.feature.clan.feature.armor.ClanArmorTask;
import pl.cebula.smp.feature.clan.feature.cuboid.ClanCuboidController;
import pl.cebula.smp.feature.clan.feature.cuboid.blocker.ClanCuboidCommandBlocker;
import pl.cebula.smp.feature.clan.feature.cuboid.bossbar.ClanCuboidBossBarController;
import pl.cebula.smp.feature.clan.feature.cuboid.bossbar.ClanCuboidBossBarTak;
import pl.cebula.smp.feature.clan.feature.cuboid.heart.ClanCuboidHeartController;
import pl.cebula.smp.feature.clan.feature.cuboid.heart.ClanCuboidHeartHologramTask;
import pl.cebula.smp.feature.clan.feature.cuboid.heart.ClanCuboidHeartInventory;
import pl.cebula.smp.feature.clan.feature.cuboid.visual.ClanCuboidBorderParticleTask;
import pl.cebula.smp.feature.clan.feature.cuboid.visual.ClanCuboidAlertTask;
import pl.cebula.smp.feature.clan.feature.delete.ClanDeleteInventory;
import pl.cebula.smp.feature.clan.feature.invite.ClanInviteService;
import pl.cebula.smp.feature.clan.feature.pvp.ClanPvpController;
import pl.cebula.smp.feature.clan.feature.war.ClanWarController;
import pl.cebula.smp.feature.clan.feature.war.ClanWarManager;
import pl.cebula.smp.feature.clan.repository.ClanRepository;
import pl.cebula.smp.feature.clan.service.ClanService;
import pl.cebula.smp.feature.clan.task.ClanSaveTask;
import pl.cebula.smp.feature.command.*;
import pl.cebula.smp.feature.crafting.CraftingCommand;
import pl.cebula.smp.feature.crafting.CraftingInventory;
import pl.cebula.smp.feature.crafting.CraftingManager;
import pl.cebula.smp.feature.dailyvpln.DailyVplnController;
import pl.cebula.smp.feature.dailyvpln.DailyVplnManager;
import pl.cebula.smp.feature.disco.*;
import pl.cebula.smp.feature.economy.EconomyCommand;
import pl.cebula.smp.feature.economy.EconomyHolder;
import pl.cebula.smp.feature.economy.MoneyCommand;
import pl.cebula.smp.feature.economy.PayCommand;
import pl.cebula.smp.feature.end.EndCommand;
import pl.cebula.smp.feature.end.EndController;
import pl.cebula.smp.feature.end.EndManager;
import pl.cebula.smp.feature.end.EndTask;
import pl.cebula.smp.feature.help.HelpCommand;
import pl.cebula.smp.feature.help.HelpInventory;
import pl.cebula.smp.feature.itemshop.ItemShopCommand;
import pl.cebula.smp.feature.itemshop.ItemShopInventory;
import pl.cebula.smp.feature.itemshop.ItemShopManager;
import pl.cebula.smp.feature.job.JobCommand;
import pl.cebula.smp.feature.job.JobController;
import pl.cebula.smp.feature.job.JobInventory;
import pl.cebula.smp.feature.killcounter.KillCounterController;
import pl.cebula.smp.feature.kit.KitCommand;
import pl.cebula.smp.feature.kit.KitInventory;
import pl.cebula.smp.feature.lootcase.*;
import pl.cebula.smp.feature.nether.NetherCommand;
import pl.cebula.smp.feature.nether.NetherController;
import pl.cebula.smp.feature.nether.NetherManager;
import pl.cebula.smp.feature.nether.NetherTask;
import pl.cebula.smp.feature.pet.PetCommand;
import pl.cebula.smp.feature.pet.PetController;
import pl.cebula.smp.feature.pet.PetInventory;
import pl.cebula.smp.feature.pet.task.PetMoveTask;
import pl.cebula.smp.feature.pet.task.PetPotionEffectTask;
import pl.cebula.smp.feature.pet.task.PetRemoveBuggyPetsTask;
import pl.cebula.smp.feature.randomteleport.RandomTeleportCommand;
import pl.cebula.smp.feature.randomteleport.RandomTeleportController;
import pl.cebula.smp.feature.shop.ShopCommand;
import pl.cebula.smp.feature.shop.ShopInventory;
import pl.cebula.smp.feature.shop.npc.controller.NpcShopController;
import pl.cebula.smp.feature.shop.npc.inventory.NpcShopInventory;
import pl.cebula.smp.feature.statistic.StatisticCommand;
import pl.cebula.smp.feature.statistic.StatisticController;
import pl.cebula.smp.feature.statistic.StatisticInventory;
import pl.cebula.smp.feature.top.TopCitizenTask;
import pl.cebula.smp.feature.top.TopManager;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.feature.user.command.UserCommand;
import pl.cebula.smp.feature.user.command.UserCommandArgument;
import pl.cebula.smp.feature.user.controller.JoinQuitListener;
import pl.cebula.smp.feature.user.repository.UserRepository;
import pl.cebula.smp.feature.user.task.SpentTimeTask;
import pl.cebula.smp.feature.user.task.UsersSaveTask;
import pl.cebula.smp.feature.vanish.VanishCommand;
import pl.cebula.smp.feature.vanish.VanishController;
import pl.cebula.smp.feature.vanish.VanishHandler;
import pl.cebula.smp.feature.voucher.VoucherCommand;
import pl.cebula.smp.feature.voucher.VoucherController;
import pl.cebula.smp.feature.voucher.VoucherInventory;

import java.io.File;
import java.util.Random;
import java.util.stream.Stream;

@Getter
public final class SurvivalPlugin extends JavaPlugin {

    public static final Gson GSON = GsonHolder.GSON;
    @Getter
    public static SurvivalPlugin instance;
    private final MongoDatabaseService mongoDatabaseService = new MongoDatabaseService();
    private final UserRepository userRepository = new UserRepository();
    private final ClanRepository clanRepository = new ClanRepository();
    private final DiscoRepository discoRepository = new DiscoRepository();
    private final ItemShopManager itemShopManager = new ItemShopManager();
    private final ClanInviteService clanInviteService = new ClanInviteService(this);
    private final AfkZoneManager afkZoneManager = new AfkZoneManager();
    private final Random random = new Random();
    private final DailyVplnManager dailyVplnManager = new DailyVplnManager(this.random);
    private final VanishHandler vanishHandler = new VanishHandler();
    public Economy economy;
    private EternalCoreApi eternalCoreApi;
    private PluginConfiguration pluginConfiguration;
    private ClanConfiguration clanConfiguration;
    private LootCaseConfiguration lootCaseConfiguration;
    private KitConfiguration kitConfiguration;
    private ItemShopConfiguration itemShopConfiguration;
    private NpcShopConfiguration npcShopConfiguration;
    private CraftingConfiguration craftingConfiguration;
    private WorldsSettings worldsSettings;
    private PetConfiguration petconfiguration;
    private VoucherConfiguration voucherConfiguration;
    private BorderCollectionConfiguration borderCollectionConfiguration;
    private UserService userService;
    private ClanService clanService;
    private DiscoService discoService;
    private ProtocolManager protocolManager;
    private ClanWarManager clanWarManager;
    private NetherManager netherManager;
    private EndManager endManager;
    private TopManager topManager;
    private LiteCommands<CommandSender> liteCommands;

    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.userService = new UserService(this.userRepository);
        this.clanService = new ClanService(this.clanRepository);
        this.discoService = new DiscoService(this.discoRepository);

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
        this.eternalCoreApi = EternalCoreApiProvider.provide();

        //load configs files
        ConfigService configService = new ConfigService();
        File dataFolder = this.getDataFolder();
        this.pluginConfiguration = configService.create(PluginConfiguration.class, new File(dataFolder, "config.yml"));
        this.lootCaseConfiguration = configService.create(LootCaseConfiguration.class, new File(dataFolder, "lootcase.yml"));
        this.kitConfiguration = configService.create(KitConfiguration.class, new File(dataFolder, "kit.yml"));
        this.npcShopConfiguration = configService.create(NpcShopConfiguration.class, new File(dataFolder, "npcShop.yml"));
        this.itemShopConfiguration = configService.create(ItemShopConfiguration.class, new File(dataFolder, "itemshop.yml"));
        this.craftingConfiguration = configService.create(CraftingConfiguration.class, new File(dataFolder, "crafting.yml"));
        this.petconfiguration = configService.create(PetConfiguration.class, new File(dataFolder, "pet.yml"));
        this.clanConfiguration = configService.create(ClanConfiguration.class, new File(dataFolder, "klan.yml"));
        this.worldsSettings = configService.create(WorldsSettings.class, new File(dataFolder, "nether.yml"));
        this.borderCollectionConfiguration = configService.create(BorderCollectionConfiguration.class, new File(dataFolder, "border.yml"));
        this.voucherConfiguration = configService.create(VoucherConfiguration.class, new File(dataFolder, "voucher.yml"));

        new Placeholder(this.userService, this.clanService, this.worldsSettings).register();

        // topki

        this.topManager = new TopManager(this.userService);

        // help menu
        HelpInventory helpInventory = new HelpInventory(this);

        // shop Menu
        NpcShopInventory npcShopInventory = new NpcShopInventory(this, userService, this.npcShopConfiguration);
        ShopInventory shopInventory = new ShopInventory(this, npcShopInventory);

        // job Menu
        JobInventory jobInventory = new JobInventory(this, this.userService, this.pluginConfiguration);

        // kit
        KitInventory kitInventory = new KitInventory(this, this.kitConfiguration, this.userService);

        // backup
        BackupInventory backupInventory = new BackupInventory(this, this.userService);

        // ItemShop
        ItemShopInventory itemShopInventory = new ItemShopInventory(this, this.itemShopConfiguration, this.itemShopManager, this.userService);

        // lootCase
        LootCaseInventory lootCaseInventory = new LootCaseInventory(this);
        LootCaseHandler lootCaseHandler = new LootCaseHandler(this.lootCaseConfiguration);
        lootCaseHandler.createLootCaseHolograms();

        // Statistic
        StatisticInventory statisticInventory = new StatisticInventory(this, this.userService);

        //Clan
        ClanDeleteInventory clanDeleteInventory = new ClanDeleteInventory(this, this.clanService, this.protocolManager);
        this.clanWarManager = new ClanWarManager(this.clanConfiguration);
        ClanCuboidHeartInventory clanCuboidHeartInventory = new ClanCuboidHeartInventory(this);

        //Custom crafting
        CraftingManager craftingManager = new CraftingManager(this.craftingConfiguration);
        craftingManager.registerCraftings();

        //Pet
        PetInventory petInventory = new PetInventory();

        //Blacksmih
        BlacksmithInventory blacksmithInventory = new BlacksmithInventory(this, this.userService);

        //Crafting
        CraftingInventory craftingInventory = new CraftingInventory(this, this.craftingConfiguration);

        //Nether
        this.netherManager = new NetherManager(this.worldsSettings);

        //End
        this.endManager = new EndManager(this.worldsSettings);

        // Voucher
        VoucherInventory voucherInventory = new VoucherInventory(this, this.voucherConfiguration);

        //Disco
        DiscoInventory discoInventory = new DiscoInventory(this);

        BorderCollectionInventory borderCollectionInventory = new BorderCollectionInventory(this, this.borderCollectionConfiguration, this.userService);
        Bukkit.getWorlds().getFirst().getWorldBorder().setSize(this.borderCollectionConfiguration.getWorldSize());

        // load data
        this.userRepository.findAll().forEach(this.userService::addUser);
        this.clanRepository.findAll().forEach(this.clanService::addClan);
        this.discoRepository.findAll().forEach(this.discoService::add);


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
                        new JobCommand(jobInventory, this.pluginConfiguration),
                        new KitCommand(kitInventory, this.kitConfiguration, this.userService),
                        new BackupCommand(backupInventory, this.userService),
                        new VplnCommand(this.userService),
                        new ItemShopCommand(itemShopInventory),
                        new LootCaseCommand(this.lootCaseConfiguration),
                        new MoneyCommand(this.userService),
                        new StatisticCommand(statisticInventory),
                        new PayCommand(this.userService),
                        new ClanCommand(this.userService, this.clanService, clanDeleteInventory, this.clanInviteService, this.clanConfiguration, this.clanWarManager),
                        new VanishCommand(this.userService, this.vanishHandler, this),
                        new PetCommand(this.petconfiguration, petInventory, this.userService, this),
                        new CraftingCommand(craftingInventory),
                        new DiscordCommand(this.pluginConfiguration),
                        new NetherCommand(this.netherManager, this.worldsSettings),
                        new ReloadConfigurationCommand(configService),
                        new ShopCommand(shopInventory),
                        new RandomTeleportCommand(this.pluginConfiguration),
                        new LiveCommand(),
                        new UserCommand(this.userService),
                        new VoucherCommand(this.voucherConfiguration, voucherInventory),
                        new DiscoCommand(this.discoService, discoInventory),
                        new EndCommand(this.endManager, this.worldsSettings)

                )
                .message(LiteMessages.MISSING_PERMISSIONS, permissions -> "&4ɴɪᴇ ᴘᴏꜱɪᴀᴅᴀꜱᴢ ᴡʏᴍᴀɢᴀɴᴇᴊ ᴘᴇʀᴍɪꜱᴊɪ&c: " + permissions.asJoinedText())
                .argument(User.class, new UserCommandArgument(this.userService))
                .argument(Clan.class, new ClanCommandArgument(this.clanService))
                .argument(LootCase.class, new LootCaseCommandArgument(this.lootCaseConfiguration))
                .invalidUsage(
                        new InvalidCommandHandle()
                )
                .build();

        // load Listeners
        Stream.of(
                new JoinQuitListener(this.userService),
                new NpcShopController(this.npcShopConfiguration, npcShopInventory),
                new JobController(this.userService, this.random, this.pluginConfiguration),
                new BackupController(this.userService),
                new BlockerController(this.pluginConfiguration),
                new DailyVplnController(this.userService, this.pluginConfiguration, this.dailyVplnManager),
                new LootCaseController(this.lootCaseConfiguration, lootCaseInventory),
                new StatisticController(this.userService),
                new ClanPvpController(this.clanService),
                new VanishController(this.userService, this),
                new KillCounterController(this),
                new PetController(this.userService, this.petconfiguration, this),
                new ChatCharController(),
                new BlacksmithController(blacksmithInventory, this.pluginConfiguration),
                new ClanCuboidController(this.clanService, this.clanConfiguration),
                new ClanCuboidHeartController(this.clanService, this, this.clanConfiguration, clanCuboidHeartInventory, this.userService),
                new ClanCuboidBossBarController(this.clanService, this),
                new ClanWarController(this.clanConfiguration, this.clanWarManager),
                new ClanCuboidCommandBlocker(this.clanService, this.clanConfiguration),
                new NetherController(this.worldsSettings, this.netherManager),
                new BorderCollectionController(this.borderCollectionConfiguration, borderCollectionInventory),
                new RandomTeleportController(this.pluginConfiguration, this.eternalCoreApi),
                new VoucherController(this.voucherConfiguration),
                new DiscoController(this.discoService),
                new EndController(this.worldsSettings, this.endManager)
        ).forEach(listener -> server.getPluginManager().registerEvents(listener, this));
        // load Tasks
        new UsersSaveTask(this, this.userService);
        new ClanSaveTask(this, this.clanService);
        new TopCitizenTask(this, this.topManager);
        new SpentTimeTask(this, this.userService);
        new AbyssTask(this);
        new AfkZoneTask(this, afkZoneManager, this.lootCaseConfiguration, userService);
        new ClanArmorTask(this, this.clanService);
        new PetMoveTask(this, this.userService);
        new PetPotionEffectTask(this.userService, this);
        new ClanCuboidAlertTask(this.clanService, this, this.userService);
        new ClanCuboidBossBarTak(this.clanService, this);
        new ClanCuboidHeartHologramTask(this, this.clanService);
        new PetRemoveBuggyPetsTask(this, this.userService);
        new NetherTask(this, this.worldsSettings);
        new ClanCuboidBorderParticleTask(this.clanService, this);
        new MobChunkLimitTask(this);
        new DiscoTask(this, this.discoService, this.random, this.clanService);
        new DiscoSaveTask(this, this.discoService);
        new EndTask(this, this.worldsSettings);
    }

    @Override
    public void onDisable() {
        this.protocolManager.removePacketListeners(this);
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
        this.userService.saveAllUsers();
        this.discoService.saveAll();
        this.clanService.saveAllClans();
    }

}
