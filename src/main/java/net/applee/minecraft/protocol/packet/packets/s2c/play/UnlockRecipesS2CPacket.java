package net.applee.minecraft.protocol.packet.packets.s2c.play;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

import javax.annotation.Nullable;

public class UnlockRecipesS2CPacket implements S2CPacket<IEventHandler> {

    Action action;
    String[] recipeIDS;

    @Nullable
    String[] initRecipeIDS;

    boolean craftingRecipeBookOpen;
    boolean craftingRecipeBookFilter;
    boolean smeltingRecipeBookOpen;
    boolean smeltingRecipeBookFilter;
    boolean blastFurnaceRecipeBookOpen;
    boolean blastFurnaceRecipeBookFilter;
    boolean smokerRecipeBookOpenBoolean;
    boolean smokerRecipeBookFilter;

    @Override
    public void apply(IEventHandler handler) {
        handler.onRecipesUnlock(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
        action = buffer.readEnumConstant(Action.class);

        craftingRecipeBookOpen = buffer.readBoolean();
        craftingRecipeBookFilter = buffer.readBoolean();
        smeltingRecipeBookOpen = buffer.readBoolean();
        smeltingRecipeBookFilter = buffer.readBoolean();
        blastFurnaceRecipeBookOpen = buffer.readBoolean();
        blastFurnaceRecipeBookFilter = buffer.readBoolean();
        smokerRecipeBookOpenBoolean = buffer.readBoolean();
        smokerRecipeBookFilter = buffer.readBoolean();

        int size = buffer.readVarInt();
        recipeIDS = new String[size];

        for (int i = 0; i < size; i++)
            recipeIDS[i] = buffer.readString();

        if (action == Action.Init) {
            size = buffer.readVarInt();
            initRecipeIDS = new String[size];

            for (int i = 0; i < size; i++)
                initRecipeIDS[i] = buffer.readString();
        }


    }

    public enum Action {
        Init,
        Add,
        Remove
    }
}
