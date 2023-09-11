package net.applee.minecraft.utils;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.client.PlayerInventory;
import net.applee.minecraft.enums.GameMode;
import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.enums.SlotActionType;
import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.packets.c2s.play.*;
import net.applee.minecraft.utils.hit.BlockHitResult;
import net.applee.minecraft.utils.hit.EntityHitResult;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.Direction;
import net.applee.minecraft.utils.math.Vec3d;
import net.applee.minecraft.world.entity.Entity;

import java.util.ArrayList;

public class InteractionUtils {

    static MinecraftClient mc = MinecraftClient.getInstance();

    public static void attackEntity(Entity entity) {
        attackEntity(entity.getId());
    }

    public static void attackEntity(int id) {
        sendPacket(InteractEntityC2SPacket.attack(id, mc.input.sneaking));
        mc.player.swingHand(Hand.MAIN_HAND);

        if (!mc.player.getGamemode().isSpectator()) {
            mc.player.resetLastAttackedTicks();
        }
    }

    public void interactEntity(Entity entity, Hand hand) {
        syncSelectedSlot();
        sendPacket(InteractEntityC2SPacket.interact(entity, mc.player.isSneaking(), hand));
    }

    public void interactEntityAtLocation(Entity entity, EntityHitResult hitResult, Hand hand) {
        syncSelectedSlot();

        Vec3d vec3d = hitResult.getPos().subtract(entity.getX(), entity.getY(), entity.getZ());
        sendPacket(InteractEntityC2SPacket.interactAt(entity, mc.player.isSneaking(), hand, vec3d));
    }

    public static void swapSlots(int from, int to) {
        sendPacket(new ContainerClickC2SPacket(0, 0, from, SlotActionType.PICKUP, 0, mc.player.inventory.getInventory(), mc.player.inventory.getCursor()));
        sendPacket(new ContainerClickC2SPacket(0, 0, to, SlotActionType.PICKUP, 0, mc.player.inventory.getInventory(), mc.player.inventory.getCursor()));
    }

    public static void swapOffhand(int from) {
        if (!PlayerInventory.isValidIndex(from)) return;
        sendPacket(new ContainerClickC2SPacket(0, 0, from, SlotActionType.SWAP, 40, new ArrayList<>(), ItemStack.EMPTY));
    }

    public static void shiftSwapLeft(int from) {
        if (!PlayerInventory.isValidIndex(from)) return;
        sendPacket(new ContainerClickC2SPacket(0, 0, from, SlotActionType.QUICK_MOVE, 0, new ArrayList<>(), ItemStack.EMPTY));
    }

    public static void shiftSwapRight(int from) {
        if (!PlayerInventory.isValidIndex(from)) return;
        sendPacket(new ContainerClickC2SPacket(0, 0, from, SlotActionType.QUICK_MOVE, 1, new ArrayList<>(), ItemStack.EMPTY));
    }

    private static void syncSelectedSlot() {
        mc.player.inventory.updateSelectedSlot(mc.player.inventory.getSelectedSlot());
    }

    public static void updateHotbarSlot(int slot) {
        sendPacket(new UpdateHotbarSlotC2SPacket(slot));
        mc.player.inventory.setSelectedSlot(slot);
    }

    public static ActionResult interactBlock(Hand hand, BlockHitResult hitResult) {
        syncSelectedSlot();

        if (mc.world.properties.worldBorder.contains(hitResult.getBlockPos())) {
            return ActionResult.FAIL;
        }

        ActionResult result = null;

        ItemStack itemStack = mc.player.inventory.getStackInHand(hand);

        if (mc.player.getGamemode().isSpectator()) result = ActionResult.SUCCESS;
        if (itemStack.isEmpty()) result = ActionResult.PASS;

        sendSequencedPacket((seq) -> new InteractBlockC2SPacket(hand, hitResult, seq));

        return result;
    }

    public static ActionResult interactItem(Hand hand) {
        if (mc.player.getGamemode() == GameMode.SPECTATOR) return ActionResult.PASS;

        syncSelectedSlot();

        sendPacket(new PlayerMoveC2SPacket.Full(mc.player.getPosition(), mc.player.getRotation(), mc.player.isOnGround()));
        sendSequencedPacket((seq) -> new InteractItemC2SPacket(hand, seq));


        return ActionResult.SUCCESS;
    }

    public static void stopUsingItem() {
        syncSelectedSlot();
        sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN, 0));
    }

    private static int sequence = 0;
    private static void sendSequencedPacket(SequencedPacket sequencedPacket) {
        sequence++;
        C2SPacket packet = sequencedPacket.packet(sequence);
        sendPacket(packet);
    }

    private static void sendPacket(C2SPacket packet) {
        mc.networkHandler.sendPacket(packet);
    }

//    public void clickSlot(int windowId, int slotId, int button, SlotActionType actionType, PlayerEntity player) {
//        ScreenHandler screenHandler = player.currentScreenHandler;
//
//        if (windowId == screenHandler.windowId) {
//            List<Slot> slots = screenHandler.slots;
//
//            List<ItemStack> list = new ArrayList<>();
//
//            for (Slot slot : slots) {
//                list.add(slot.getStack().copy());
//            }
//
//            screenHandler.onSlotClick(slotId, button, actionType, player);
//            List<ItemStack> stacks = new ArrayList<>();
//
//            for(int i = 0; i < screenHandler.slots.size(); ++i) {
//                ItemStack itemStack = list.get(i);
//                ItemStack itemStack2 = (slots.get(i)).getStack();
//                if (!ItemStack.areEqual(itemStack, itemStack2)) {
//                    slots.add(itemStack2);
//                }
//            }
//
//
//            this.networkHandler.sendPacket(new ContainerClickC2SPacket(windowId, screenHandler.revision, slotId, actionType, button, slots, screenHandler.cursorStack));
//        }
//    }

    private interface SequencedPacket {
        C2SPacket packet(int sequence);
    }
}
