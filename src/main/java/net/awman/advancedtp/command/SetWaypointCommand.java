package net.awman.advancedtp.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.awman.advancedtp.AdvancedTp;
import net.awman.advancedtp.util.IEntityDataSaver;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public class SetWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                .executes(SetWaypointCommand::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        IEntityDataSaver player = (IEntityDataSaver)context.getSource().getPlayer();
        BlockPos playerPos = context.getSource().getPlayer().getBlockPos();
        String pos = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";

        player.getPersistentData().putIntArray(AdvancedTp.MOD_ID + "waypoint_" + getString(context, "waypoint_id"),
                new int[] {playerPos.getX(), playerPos.getY(), playerPos.getZ() });

        context.getSource().sendFeedback(new LiteralText("Set new waypoint (" + getString(context, "waypoint_id") + ") at " + pos), true);
        return 1;
    }
}
