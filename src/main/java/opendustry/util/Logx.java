package opendustry.util;

import arc.Core;
import arc.util.ArcRuntimeException;
import arc.util.Log;
import arc.util.Log.LogLevel;
import arc.util.Strings;
import arc.util.OS;
import mindustry.Vars;
import mindustry.ui.dialogs.BaseDialog;


/**
 * Central logging utility for the OpenDustry mod.
 *
 * <p>
 * Provides structured, context-aware logging on top of Mindustry's {@link Log}.
 * Every log entry is automatically tagged with the mod identifier and a
 * subsystem context (e.g. {@code world}, {@code net}, {@code ai}).
 * </p>
 *
 * <p>
 * Supports:
 * <ul>
 *   <li>Debug, info, warning, and error levels</li>
 *   <li>Formatted messages via {@link Strings#format}</li>
 *   <li>Throwable logging with clean stack traces</li>
 *   <li>Fatal errors that safely terminate the game</li>
 * </ul>
 * </p>
 *
 * <h2>Example</h2>
 * <pre>{@code
 * Logx.info("world", "Loaded region {}", id);
 * Logx.error("net", e, "Failed to connect to {}", address);
 * Logx.fatal("init", "Mod initialization failed");
 * }</pre>
 *
 * <p>
 * This class is static-only and must not be instantiated.
 * </p>
 */
public final class Logx {

    /**
     * Root tag used for all log entries from this mod.
     */
    public static final String MOD_TAG = Reference.MOD_NAME;

    /**
     * Prevent instantiation.
     */
    private Logx() {
    }

    /**
     * Emits a log entry with the given severity and context.
     *
     * @param level   the log severity level
     * @param context logical subsystem emitting the log (e.g. {@code net}, {@code world})
     * @param error   optional throwable associated with the log, or {@code null}
     * @param format  format string compatible with {@link Strings#format}
     * @param args    arguments referenced by the format specifiers
     */
    private static void log(
            LogLevel level,
            String context,
            Throwable error,
            String format,
            Object... args
    ) {
        String message = Strings.format(format, args);
        String prefix = "[" + MOD_TAG + "/" + context + "] ";

        Log.log(level, prefix + message);

        if (error != null) {
            logThrowable(level, error);
        }
    }

    /**
     * Logs a throwable and its causes in a readable, indented format.
     *
     * <p>
     * This avoids dumping raw JVM stack traces and keeps output consistent
     * with Mindustry's logging system.
     * </p>
     *
     * @param level the severity level to log the stack trace at
     * @param t     the throwable to log
     */
    private static void logThrowable(LogLevel level, Throwable t) {
        Log.log(level, "Caused by:");

        for (StackTraceElement e : t.getStackTrace()) {
            Log.log(level, "  at " + e);
        }

        Throwable cause = t.getCause();

        if (cause != null && cause != t) {
            Log.log(level, "Caused by:");
            logThrowable(level, cause);
        }
    }

    /**
     * Logs a debug-level message.
     *
     * @param ctx  logical subsystem emitting the log
     * @param fmt  format string
     * @param args format arguments
     */
    public static void debug(String ctx, String fmt, Object... args) {
        log(LogLevel.debug, ctx, null, fmt, args);
    }

    /**
     * Logs an info-level message.
     *
     * @param ctx  logical subsystem emitting the log
     * @param fmt  format string
     * @param args format arguments
     */
    public static void info(String ctx, String fmt, Object... args) {
        log(LogLevel.info, ctx, null, fmt, args);
    }

    /**
     * Logs a warning-level message.
     *
     * @param ctx  logical subsystem emitting the log
     * @param fmt  format string
     * @param args format arguments
     */
    public static void warn(String ctx, String fmt, Object... args) {
        log(LogLevel.warn, ctx, null, fmt, args);
    }

    /**
     * Logs an error-level message.
     *
     * @param ctx  logical subsystem emitting the log
     * @param fmt  format string
     * @param args format arguments
     */
    public static void error(String ctx, String fmt, Object... args) {
        log(LogLevel.err, ctx, null, fmt, args);
    }

    /**
     * Logs an error-level message with an associated throwable.
     *
     * @param ctx  logical subsystem emitting the log
     * @param t    throwable describing the error
     * @param fmt  format string
     * @param args format arguments
     */
    public static void error(String ctx, Throwable t, String fmt, Object... args) {
        log(LogLevel.err, ctx, t, fmt, args);
    }

    /**
     * Logs a fatal error and terminates the game.
     *
     * <p>
     * Intended for unrecoverable errors during initialization or critical
     * runtime failures.
     * </p>
     *
     * @param ctx  logical subsystem emitting the log
     * @param fmt  format string
     * @param args format arguments
     */
    public static void fatal(String ctx, String fmt, Object... args) {
        fatal(ctx, null, fmt, args);
    }

    /**
     * Logs a fatal error with a throwable and terminates the game.
     *
     * <p>
     * On desktop platforms this will exit the JVM. On Android, a runtime
     * exception is thrown to allow Mindustry to handle the crash gracefully.
     * </p>
     *
     * @param ctx  logical subsystem emitting the log
     * @param t    throwable describing the fatal error
     * @param fmt  format string
     * @param args format arguments
     */
    public static void fatal(String ctx, Throwable t, String fmt, Object... args) {
        log(LogLevel.err, ctx, t, fmt, args);

        log(LogLevel.err, "Main Thread", null, "Terminating...");

        if (Vars.ui != null) {
            BaseDialog dialog = new BaseDialog("Encountered an unrecoverable error!");
            dialog.cont.add(t.toString()).row();
            dialog.cont.button("Exit", Logx::exit).size(100f, 50f);
            dialog.show();
        } else {
            exit();
        }
    }

    private static void exit() {
        Core.app.post(() -> {
            Core.app.exit();
        });
    }
}
