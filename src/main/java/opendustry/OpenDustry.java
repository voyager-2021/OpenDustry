package opendustry;

import arc.Core;
import arc.Events;
import arc.util.Log;
import arc.util.Log.LogLevel;
import arc.util.Strings;
import arc.util.Time;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;
import opendustry.content.ODPlanets;
import opendustry.util.Logx;


public class OpenDustry extends Mod {
    public static final String MODID = "opendustry";

    public OpenDustry() {
        Log.info("Constructing OpenDustry...");

        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("throw a fatal");
                dialog.cont.add("fatal error").row();
                dialog.cont.button("throw", this::crash).size(100f, 50f);
                dialog.show();
            });
        });
    }

    public void crash() {
        Logx.fatal("Main Thread:OpenDustry", new NullPointerException("something went wrong"), "fatal error!");
    }

    @Override
    public void loadContent() {
        Log.info("Loading content...");
        ODPlanets.load();
    }

    public static void logDebug(String string, Object... args) {
        Log.infoTag("opendustry", Strings.format(string, args));
    }

    public static void logInfo(String string, Object... args) {
        Log.infoTag("opendustry", Strings.format(string, args));
    }

    public static void logWarn(String string, Object... args) {
        warnTag("opendustry", Strings.format(string, args));
    }

    public static void logError(String string, Object... args) {
        Log.errTag("opendustry", Strings.format(string, args));
    }

    private static void debugTag(String tag, String text) {
        Log.log(LogLevel.debug, "[" + tag + "] " + text);
    }

    private static void infoTag(String tag, String text) {
        Log.log(LogLevel.info, "[" + tag + "] " + text);
    }

    private static void warnTag(String tag, String text) {
        Log.log(LogLevel.warn, "[" + tag + "] " + text);
    }

    private static void errTag(String tag, String text) {
        Log.log(LogLevel.err, "[" + tag + "] " + text);
    }
}
