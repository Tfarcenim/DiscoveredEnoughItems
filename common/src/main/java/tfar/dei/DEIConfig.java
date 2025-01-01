package tfar.dei;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DEIConfig {

    public static final Client CLIENT;
    public static final ModConfigSpec CLIENT_SPEC;

    public static final Server SERVER;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        final Pair<Client, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
        final Pair<Server, ModConfigSpec> specPair2 = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specPair2.getRight();
        SERVER = specPair2.getLeft();
    }

    public static class Client {

        public static ModConfigSpec.BooleanValue hide_models;
        public static ModConfigSpec.BooleanValue show_items_outside_of_world;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("general");
            hide_models = builder.define("hide_models", true);
            show_items_outside_of_world = builder.define("show_items_outside_of_world",true);
            builder.pop();
        }
    }

    public static class Server {
        public Server(ModConfigSpec.Builder builder) {
            builder.push("general");
            builder.pop();
        }
    }
}
