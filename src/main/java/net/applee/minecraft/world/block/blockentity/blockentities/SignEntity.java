package net.applee.minecraft.world.block.blockentity.blockentities;

import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.string.AnsiColor;
import net.applee.minecraft.world.block.blockentity.BlockEntity;
import net.applee.minecraft.world.block.blockentity.BlockEntityData;

public class SignEntity extends BlockEntity {
    public static final int STRINGS_LENGTH = 4;
    private static final String[] TEXT_KEYS = new String[]{"Text1", "Text2", "Text3", "Text4"};

    private Nbt[] texts;
    private AnsiColor textColor;
    private boolean glowingText;


    public SignEntity(BlockEntityData data, BlockPos pos) {
        super(data, pos);

        Nbt nbtData = data.getData();
        if (nbtData == null) return;

        texts = new Nbt[STRINGS_LENGTH];
        for (int i = 0; i < STRINGS_LENGTH; i++)
            texts[i] = nbtData.getNbt(TEXT_KEYS[i]);

        textColor = AnsiColor.ofName(nbtData.getString("Color"));
        glowingText = nbtData.getBool("GlowingText");

    }

    public Nbt[] getTexts() {
        return texts;
    }

    public AnsiColor getTextColor() {
        return textColor;
    }

    public boolean isGlowing() {
        return glowingText;
    }
}
