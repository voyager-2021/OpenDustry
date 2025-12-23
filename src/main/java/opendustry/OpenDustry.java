package opendustry;

import mindustry.mod.Mod;
import opendustry.content.ODPlanets;
import opendustry.util.Logx;


public class OpenDustry extends Mod {
    public static final String MODID = "opendustry";

    public OpenDustry() {
        Logx.info(null, "Constructing OpenDustry...");
    }

    @Override
    public void loadContent() {
        Logx.info(null, "Loading content...");
        ODPlanets.load();
        Logx.info(null, "Content loaded.");
    }
}
