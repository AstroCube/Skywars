package net.astrocube.skywars.chest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.skywars.api.chest.ChestFiller;
import net.astrocube.skywars.api.chest.tier.ChestTier;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.custom.ItemSerializer;
import net.astrocube.skywars.api.map.MapConfiguration;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.Random;

@Singleton
public class CoreChestFiller implements ChestFiller {

    private @Inject CustomItemRepository<ChestTier> tierRepository;

    private static final int MAX_FILL_ATTEMPTS = 50;

    @Override
    public void fillChest(MapConfiguration.Chest chest, Chest mapChest) {

        Optional<ChestTier> tierOptional = tierRepository.getRegisteredItems().stream().filter(t -> t.getTier() == chest.getTier())
                .findFirst();

        tierOptional.ifPresent(t -> {

            Random generator = new Random(System.currentTimeMillis());
            Inventory inventory = mapChest.getInventory();

            for (ChestTier.Item item : t.getProbabilities()) {

                ItemStack itemStack = ItemSerializer.serialize(item.getItem());
                int amount = generator.nextInt(item.getMaximum() - (item.getMinimum() - 1)) + item.getMinimum();
                itemStack.setAmount(amount);

                if (
                        item.getChance() >= 100 ||
                        generator.nextInt(100) < item.getChance()
                ) {
                    int randomSlot = generator.nextInt(inventory.getSize());
                    int currentAttempts = 0;
                    while (!isEmpty(mapChest.getInventory(), randomSlot) && currentAttempts <= MAX_FILL_ATTEMPTS) {
                        randomSlot = generator.nextInt(inventory.getSize());

                        currentAttempts++;
                    }
                    inventory.setItem(randomSlot, itemStack);
                }
            }

        });


    }

    private boolean isEmpty(Inventory inventory, int slot) {
        return inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR;
    }

}
