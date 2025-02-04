package tfar.dei.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.io.IOUtils;
import tfar.dei.DEIConfig;
import tfar.dei.DiscoveredEnoughItems;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DiscoveredItems {

    public static final ResourceLocation ID = DiscoveredEnoughItems.id("hidden");
    private static final Set<Item> discovered = new HashSet<>();
    private static final Map<Item, Component> tooltips = new HashMap<>();
    private static String worldName;

    static final File PRE_DISCOVERED = new File("config/dei_pre_discovered.json");
    static final File TOOLTIPS = new File("config/dei_tooltips.json");

    public static BakedModel HIDDEN;


    public static void setWorldName(String worldName) {
        DiscoveredItems.worldName = worldName;
    }

    public static void clear() {
        discovered.clear();
        tooltips.clear();
    }

    public static boolean discovered(ItemStack stack) {
        if (Minecraft.getInstance().level == null) {
            return DEIConfig.Client.show_items_outside_of_world.get();
        }
        return discovered.contains(stack.getItem());
    }

    public static Component tooltip(ItemStack stack) {
        return tooltips.get(stack.getItem());
    }

    public static void addDiscovered(ItemStack stack) {
        boolean changed = discovered.add(stack.getItem());
        if (changed) saveToDisk();
    }

    public static int overlay() {
        return 0xff000000;
    }

    public static int packed() {
        return 0;
    }

    static Gson gson = new Gson();

    public static void loadFromDisk() {
        clear();

        new File("dei/").mkdirs();//make sure the folder exists
        File worldDiscovered = new File("dei/" + worldName + ".json");

        if (!worldDiscovered.exists() && PRE_DISCOVERED.exists()) {//add pre discovered entries
            try {
                Path path = worldDiscovered.toPath();
                Files.copy(PRE_DISCOVERED.toPath(), path, StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (worldDiscovered.exists()) {// load existing discoveries
            Reader reader = null;
            try {
                reader = new FileReader(worldDiscovered);
                JsonReader jsonReader = new JsonReader(reader);
                DiscoveredEnoughItems.LOG.info("Loading existing discoveries");
                JsonArray json = gson.fromJson(jsonReader, JsonArray.class);

                for (JsonElement element : json) {
                    discovered.add(BuiltInRegistries.ITEM.get(new ResourceLocation(element.getAsString())));
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }

        if (TOOLTIPS.exists()) {
            Reader reader = null;
            try {
                reader = new FileReader(TOOLTIPS);
                JsonReader jsonReader = new JsonReader(reader);
                DiscoveredEnoughItems.LOG.info("Loading tooltips");
                JsonObject json = gson.fromJson(jsonReader, JsonObject.class);

                for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                    Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(entry.getKey()));
                    Component component = Component.Serializer.fromJson(entry.getValue().getAsString());
                    tooltips.put(item, component);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
    }

    static JsonArray discoveredToJson() {
        JsonArray array = new JsonArray();
        for (Item item : discovered) {
            array.add(BuiltInRegistries.ITEM.getKey(item).toString());
        }
        return array;
    }

    public static void saveToDisk() {
        File worldDiscovered = new File("dei/" + worldName + ".json");
        JsonWriter writer = null;
        try {
            writer = gson.newJsonWriter(new FileWriter(worldDiscovered));
            writer.setIndent("    ");
            gson.toJson(discoveredToJson(), writer);
        } catch (Exception e) {
            DiscoveredEnoughItems.LOG.error("Couldn't save discovered");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public static String getWorldName() {
        Minecraft minecraft = Minecraft.getInstance();
        ServerData serverData = minecraft.getCurrentServer();
        if (serverData != null) {
            return serverData.name;
        } else {
            IntegratedServer integratedServer = minecraft.getSingleplayerServer();
            return integratedServer.getMotd();
        }
    }
}
