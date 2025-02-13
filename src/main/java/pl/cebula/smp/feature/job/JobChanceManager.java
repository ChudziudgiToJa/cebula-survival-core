package pl.cebula.smp.feature.job;

import pl.cebula.smp.feature.lootcase.LootCaseChance;

import java.util.List;
import java.util.Random;

public class JobChanceManager {

    public static JobDropChance pickRandomItem(List<JobDropChance> items) {
        double totalChance = items.stream().mapToDouble(JobDropChance::getChance).sum();

        double randomValue = new Random().nextDouble() * totalChance;

        double cumulativeChance = 0.0;
        for (JobDropChance item : items) {
            cumulativeChance += item.getChance();
            if (randomValue <= cumulativeChance) {
                return item;
            }
        }
        throw new IllegalStateException("Nie udało się wylosować żadnego przedmiotu job.");
    }
}
