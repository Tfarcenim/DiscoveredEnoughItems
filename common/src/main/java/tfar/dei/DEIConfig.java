package tfar.dei;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DEIConfig {

    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
        final Pair<Server, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specPair2.getRight();
        SERVER = specPair2.getLeft();
    }

    public static class Client {

        public static ForgeConfigSpec.BooleanValue hide_models;
        public static ForgeConfigSpec.BooleanValue show_items_outside_of_world;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            hide_models = builder.define("hide_models", true);
            show_items_outside_of_world = builder.define("show_items_outside_of_world",true);
            builder.pop();
        }
    }

    public static class Server {
        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            builder.pop();
        }
    }
}
