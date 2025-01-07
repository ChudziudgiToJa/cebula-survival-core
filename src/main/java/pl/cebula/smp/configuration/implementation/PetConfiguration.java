package pl.cebula.smp.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.potion.PotionEffectType;
import pl.cebula.smp.feature.pet.object.PetData;

import java.util.List;

public class PetConfiguration extends OkaeriConfig {

    @Comment("numer to id effektu")
    public List<PetData> petDataDataList = List.of(
            new PetData(
                    "Królik",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2QxZDJiYmMzZDc3YWIwZDExOWMwMzFlYTc0ZDg3ODFjOTMyODVjZjczNmFiYzQyMmJhZWMxZWIxNTYwZThjYSJ9fX0=",
                    8,
                    " &fNadaje efekt wysokiego skoku I"
            ),
            new PetData(
                    "Kret",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRiNTRmN2Y1NTkzYTMyM2I2NTUyMWU2MTA2MTZmZGM5OTEwZjI5ZTI3YWUzMTkxNTExNjIzZTgxOGQ4ODM0OCJ9fX0=",
                    3,
                    "&fNadaje efekt szybkiego kopania I"
            ),
            new PetData(
                    "Kot",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDViY2Y4OTdmYTYwNmFiZGIxZmEyYTFlODhhMDdkOGZiYmI1YmJkNDQ1ZTBhZmE1NzYyYTQ2NTA5NDYyNThmMiJ9fX0=",
                    1,
                    "&fNadaje efekt szybkiego biegania I"
            ),
            new PetData(
                    "Nietoperz",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODFjNWNjMWY0MDAwNWEzMzEyNGM2MDM4NGEwZjE3YTM2YTdiMTlhZTkwZjFjMzJkY2RhMTdiNWI1NjI4MGE0MyJ9fX0=",
                    16,
                    "&fNadaje efekt widzenia w ciemności"
            )
    );
}
