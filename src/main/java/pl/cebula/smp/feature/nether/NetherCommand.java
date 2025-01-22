package pl.cebula.smp.feature.nether;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.cebula.smp.configuration.implementation.NetherConfiguration;
import pl.cebula.smp.util.MessageUtil;


@Command(name = "nether")
@Permission("cebulasmp.nether.admin")
public class NetherCommand {

    private final NetherManager netherManager;
    private final NetherConfiguration netherConfiguration;

    public NetherCommand(NetherManager netherManager, NetherConfiguration netherConfiguration) {
        this.netherManager = netherManager;
        this.netherConfiguration = netherConfiguration;
    }

    @Execute(name = "status")
    @Permission("cebulasmp.nether.admin")
    void toggleNetherStatus(@Context CommandSender player, @Arg boolean b) {
        this.netherConfiguration.NetherJoinStatus = b;
        this.netherConfiguration.save();
        this.netherManager.toggleNetherBossBar();
        MessageUtil.sendMessage(player, "&fUstawiono nether na: " + (b ? "&awłączony" : "&cwyłączony"));
    }

    @Execute(name = "tp")
    @Permission("cebulasmp.nether.admin")
    void tpNether(@Context Player player, @OptionalArg Player target) {
        if (this.netherConfiguration.netherSpawnLocation == null) {
            MessageUtil.sendMessage(player, "&cLokalizacja spawnu w nether nie jest ustawiona");
            return;
        }
        Location netherSpawnLocation = this.netherConfiguration.getNetherSpawnLocation();
        Player toTeleport = target != null ? target : player;
        toTeleport.teleport(netherSpawnLocation);
        MessageUtil.sendMessage(toTeleport, "Zostałeś teleportowany do spawn Nether!");
        if (target != null && !target.equals(player)) {
            MessageUtil.sendMessage(player, "Teleportowano gracza " + target.getName() + " do spawn Nether.");
        }
    }

    @Execute(name = "setspawn")
    @Permission("cebulasmp.nether.admin")
    void setNetherSpawn(@Context Player player) {
        if (!player.getWorld().getName().equalsIgnoreCase("world_nether")) {
            MessageUtil.sendMessage(player, "&cMusisz być w świecie Nether, aby ustawić spawn Nether!");
            return;
        }
        this.netherConfiguration.setNetherSpawnLocation(player.getLocation());
        this.netherConfiguration.save();
        MessageUtil.sendMessage(player, "&aSpawn Nether został ustawiony na twoją aktualną lokalizację!");
    }
}