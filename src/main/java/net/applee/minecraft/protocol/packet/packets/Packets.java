package net.applee.minecraft.protocol.packet.packets;

import net.applee.minecraft.protocol.exceptions.PacketNotFoundException;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.packets.c2s.play.*;
import net.applee.minecraft.protocol.packet.packets.c2s.play.test.UpdateCreativeSlotC2SPacket;
import net.applee.minecraft.protocol.packet.packets.s2c.login.LoginCompressionS2CPacket;
import net.applee.minecraft.protocol.packet.packets.s2c.login.LoginDisconnectS2CPacket;
import net.applee.minecraft.protocol.packet.packets.s2c.login.LoginRequestS2CPacket;
import net.applee.minecraft.protocol.packet.packets.s2c.login.LoginSuccessS2CPacket;
import net.applee.minecraft.protocol.packet.packets.s2c.play.*;
import net.applee.minecraft.protocol.packet.packets.s2c.play.todo.*;
import net.applee.minecraft.utils.Utils;

public class Packets {

    private static final Class<?>[] C2SPackets = new Class<?>[]{
            TeleportConfirmC2SPacket.class,
            GetBlockEntityTagC2SPacket.class,
            ChangeDifficultyC2SPacket.class,
            MessageAcknowledgmentC2SPacket.class,
            ChatCommandC2SPacket.class,
            ChatMessageC2SPacket.class,
            ChatPreviewC2SPacket.class,
            ClientCommandC2SPacket.class,
            UpdateClientSettingsC2SPacket.class,
            CommandsRequestC2SPacket.class,
            ContainerButtonClickC2SPacket.class,
            ContainerClickC2SPacket.class,
            ContainerCloseC2SPacket.class,
            SendPluginMessageC2SPacket.class,
            EditBookC2SPacket.class,
            GetEntityTagC2SPacket.class,
            InteractEntityC2SPacket.class,
            JigsawGenerateC2SPacket.class,
            KeepAliveC2SPacket.class,
            LockDifficultyC2SPacket.class,
            PlayerMoveC2SPacket.Move.class,
            PlayerMoveC2SPacket.Full.class,
            PlayerMoveC2SPacket.Look.class,
            PlayerMoveC2SPacket.Ground.class,
            VehicleMoveC2SPacket.class,
            UpdateBoatPaddlesC2SPacket.class,
            PickItemC2SPacket.class,
            RecipeLoadC2SPacket.class,
            UpdateAbilitiesC2SPacket.class,
            PlayerActionC2SPacket.class,
            PlayerCommandC2SPacket.class,
            PlayerInputC2SPacket.class,
            PongC2SPacket.class,
            UpdateBookRecipeC2SPacket.class,
            RecipeCheckedC2SPacket.class,
            RenameItemC2SPacket.class,
            ResourcePackStateC2SPacket.class,
            AdvancementsSeenC2SPacket.class,
            SelectTradeC2SPacket.class,
            UpdateBeaconEffectsC2SPacket.class,
            UpdateHotbarSlotC2SPacket.class,
            UpdateCommandBlockC2SPacket.class,
            UpdateCommandBlockMinecartC2SPacket.class,
            UpdateCreativeSlotC2SPacket.class,
            UpdateJigsawBlockC2SPacket.class,
            UpdateStructureBlockC2SPacket.class,
            UpdateSignC2SPacket.class,
            SwingHandC2SPacket.class,
            TeleportToEntityC2SPacket.class,
            InteractBlockC2SPacket.class,
            InteractItemC2SPacket.class
    };

    private static final Class<?>[] S2CLoginPackets = new Class<?>[]{
            LoginDisconnectS2CPacket.class,
            LoginRequestS2CPacket.class,
            LoginSuccessS2CPacket.class,
            LoginCompressionS2CPacket.class
    };

    private static final Class<?>[] S2CPlayPackets = new Class<?>[]{
            SpawnEntityS2CPacket.class,
            SpawnExpOrbS2CPacket.class,
            SpawnPlayerS2CPacket.class,
            EntityAnimationS2CPacket.class,
            AwardStatisticsS2CPacket.class,
            BlockAcknowledgeChangeS2CPacket.class,
            BlockBreakingStageUpdateS2CPacket.class,
            BlockEntityDataS2CPacket.class,
            BlockEventS2CPacket.class,
            BlockUpdateS2CPacket.class,
            BossBarUpdateS2CPacket.class,
            DifficultyUpdateS2CPacket.class,
            ChatPreviewS2CPacket.class,
            ClearTitleS2CPacket.class,
            CommandSuggestionsS2CPacket.class,
            CommandTreeS2CPacket.class,
            CloseScreenS2CPacket.class,
            ScreenSlotsUpdateS2CPacket.class,
            ScreenPropertyUpdateS2CPacket.class,
            ScreenSlotUpdateS2CPacket.class,
            CooldownUpdateS2CPacket.class,
            ChatSuggestionsS2CPacket.class,
            PluginMessageS2CPacket.class,
            SoundPlayByIdS2CPacket.class,
            HideMessageS2CPacket.class,
            DisconnectS2CPacket.class,
            EntityStatusS2CPacket.class,
            ExplosionS2CPacket.class,
            UnloadChunkS2CPacket.class,
            GameEventS2CPacket.class,
            OpenHorseScreenS2CPacket.class,
            WorldBorderInitializeS2CPacket.class,
            KeepAliveS2CPacket.class,
            ChunkDataS2CPacket.class,
            WorldEventS2CPacket.class,
            ParticleS2CPacket.class,
            LightUpdateS2CPacket.class,
            GameJoinS2CPacket.class,
            MapUpdateS2CPacket.class,
            MerchantOffersListS2CPacket.class,
            EntityMoveS2CPacket.Move.class,
            EntityMoveS2CPacket.RotateAndMove.class,
            EntityMoveS2CPacket.Rotate.class,
            VehicleMoveS2CPacket.class,
            OpenSignedBookS2CPacket.class,
            OpenScreenS2CPacket.class,
            SignEditorOpenS2CPacket.class,
            PlayPingS2CPacket.class,
            CraftFailedResponseS2CPacket.class,
            PlayerAbilitiesS2CPacket.class,
            MessageHeaderS2CPacket.class,
            ChatMessageS2CPacket.class,
            EndCombatS2CPacket.class,
            EnterCombatS2CPacket.class,
            DeathMessageS2CPacket.class,
            PlayerListS2CPacket.class,
            LookAtS2CPacket.class,
            SyncPositionS2CPacket.class,
            UnlockRecipesS2CPacket.class,
            RemoveEntitiesS2CPacket.class,
            EntityEffectClearS2CPacket.class,
            ResourcePackLoadS2CPacket.class,
            RespawnS2CPacket.class,
            EntityHeadYawUpdateS2CPacket.class,
            ChunkDeltaUpdateS2CPacket.class,
            SelectAdvancementTabS2CPacket.class,
            ServerMetadataS2CPacket.class,
            OverlayMessageS2CPacket.class,
            WorldBorderCenterChangedS2CPacket.class,
            WorldBorderInterpolateSizeS2CPacket.class,
            WorldBorderSizeChangedS2CPacket.class,
            WorldBorderWarningTimeChangedS2CPacket.class,
            WorldBorderWarningBlocksChangedS2CPacket.class,
            ScreenShaderUpdateS2CPacket.class,
            SelectedSlotUpdateS2CPacket.class,
            ChunkRenderDistanceCenterS2CPacket.class,
            ChunkLoadDistanceS2CPacket.class,
            PlayerSpawnPositionS2CPacket.class,
            ChatVisibilityUpdateS2CPacket.class,
            ScoreboardDisplayS2CPacket.class,
            EntityMetadataUpdateS2CPacket.class,
            EntityAttachS2CPacket.class,
            EntityVelocityUpdateS2CPacket.class,
            EntityEquipmentUpdateS2CPacket.class,
            ExperienceBarUpdateS2CPacket.class,
            HealthUpdateS2CPacket.class,
            ScoreboardObjectiveUpdateS2CPacket.class,
            EntityPassengersUpdateS2CPacket.class,
            TeamS2CPacket.class,
            ScoreboardUpdateS2CPacket.class,
            SimulationDistanceUpdateS2CPacket.class,
            SubtitleUpdateS2CPacket.class,
            WorldTimeUpdateS2CPacket.class,
            TitleS2CPacket.class,
            TitleFadeS2CPacket.class,
            SoundFromEntityS2CPacket.class,
            SoundS2CPacket.class,
            StopSoundS2CPacket.class,
            GameMessageS2CPacket.class,
            TabListUpdateS2CPacket.class,
            NbtQueryResponseS2CPacket.class,
            ItemPickupAnimationS2CPacket.class,
            EntityTeleportedS2CPacket.class,
            AdvancementUpdateS2CPacket.class,
            EntityAttributesS2CPacket.class,
            EntityStatusEffectS2CPacket.class,
            SynchronizeRecipesS2CPacket.class,
            SynchronizeTagsS2CPacket.class
    };

    public static int getId(C2SPacket packet) {
        return Utils.findElementIndex(C2SPackets, packet.getClass());
    }

    public static Class<?> getS2CPacket(int packetId, boolean play) {
        Class<?>[] packets = play ? S2CPlayPackets : S2CLoginPackets;
        try {
            return packets[packetId];
        } catch (Exception e) {
            throw new PacketNotFoundException(packetId);
        }

    }

    public static int getS2CId(Class<?> packet, boolean play) {
        Class<?>[] packets = play ? S2CPlayPackets : S2CLoginPackets;
        try {
            return Utils.findElementIndex(packets, packet);
        } catch (Exception e) {
            throw new PacketNotFoundException(packet);
        }

    }
}
