package net.applee.minecraft.protocol.handler;

import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.enums.MessageType;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.protocol.packet.packets.s2c.login.*;
import net.applee.minecraft.protocol.packet.packets.s2c.play.*;
import net.applee.minecraft.protocol.packet.packets.s2c.play.todo.*;
import net.applee.minecraft.utils.string.Text;

public interface IEventHandler {

    void init();

    void setClient(MinecraftClient client);
    void resetKeepAliveTimer();

    // Login
    void onLoginDisconnect(LoginDisconnectS2CPacket packet);

    void onLoginEncryptRequest(LoginRequestS2CPacket packet);

    void onLoginSuccess(LoginSuccessS2CPacket packet);

    void onLoginCompressionSet(LoginCompressionS2CPacket packet);

    void onLoginPluginRequest(LoginPluginRequestS2CPacket packet);


    // Play
    void onTick();

    void onPacketReceived(PacketByteBuffer buffer, S2CPacket<IEventHandler> packet);
    boolean onPacketSend(C2SPacket packet);
    void onPacketSent(C2SPacket packet);

    void onMessage(String message, MessageType type);

    void onDisconnect(Text reason);

    void onTerminate();

    void onSpawnEntity(SpawnEntityS2CPacket packet);

    void onExpOrbSpawn(SpawnExpOrbS2CPacket packet);

    void onPlayerSpawn(SpawnPlayerS2CPacket packet);

    void onEntityMove(EntityMoveS2CPacket packet);

    void onEntityAnimation(EntityAnimationS2CPacket packet);

    void onEntityStatus(EntityStatusS2CPacket packet);

    void onEntityLeashed(EntityAttachS2CPacket packet);

    void onEntityAttributesUpdate(EntityAttributesS2CPacket packet);

    void onEntityEquipmentUpdate(EntityEquipmentUpdateS2CPacket packet);

    void onEntityPassengersUpdate(EntityPassengersUpdateS2CPacket packet);

    void onEntityTeleported(EntityTeleportedS2CPacket packet);

    void onEntityHeadRotationUpdate(EntityHeadYawUpdateS2CPacket packet);

    void onEntityEffect(EntityStatusEffectS2CPacket packet);

    void onEntityEffectClear(EntityEffectClearS2CPacket packet);

    void onEntityMetadataUpdate(EntityMetadataUpdateS2CPacket packet);

    void onEntityVelocityUpdate(EntityVelocityUpdateS2CPacket packet);

    void onSoundEntity(SoundFromEntityS2CPacket packet);

    void onPlayerAbilities(PlayerAbilitiesS2CPacket packet);

    void onSelectedSlotUpdate(SelectedSlotUpdateS2CPacket packet);

    void onScreenSlotsUpdateUpdate(ScreenSlotsUpdateS2CPacket packet);

    void onScreenSlotUpdate(ScreenSlotUpdateS2CPacket packet);

    void onScreenOpen(OpenScreenS2CPacket packet);

    void onCloseScreen(CloseScreenS2CPacket packet);

    void onScreenPropertyUpdate(ScreenPropertyUpdateS2CPacket packet);

    void onHorseScreenOpened(OpenHorseScreenS2CPacket packet);

    void onScreenShaderUpdate(ScreenShaderUpdateS2CPacket packet);

    void onAwardStatistic(AwardStatisticsS2CPacket packet);

    void onAcknowledgeBlockChange(BlockAcknowledgeChangeS2CPacket packet);

    void onBlockBreakingProgress(BlockBreakingStageUpdateS2CPacket packet);

    void onBlockEvent(BlockEventS2CPacket packet);

    void onBlockUpdate(BlockUpdateS2CPacket packet);

    void onBlockEntityUpdate(BlockEntityDataS2CPacket packet);

    void onBossBarUpdate(BossBarUpdateS2CPacket packet);

    void onChangeDifficulty(DifficultyUpdateS2CPacket packet);

    void onChatPreview(ChatPreviewS2CPacket packet);

    void onClearTitle(ClearTitleS2CPacket packet);

    void onCommandsSuggest(CommandSuggestionsS2CPacket packet);

    void onCommandsList(CommandTreeS2CPacket packet);

    void onCooldownUpdate(CooldownUpdateS2CPacket packet);

    void onChatSuggestions(ChatSuggestionsS2CPacket packet);

    void onPluginMessage(PluginMessageS2CPacket packet);

    void onHideMessage(HideMessageS2CPacket packet);

    void onExplosion(ExplosionS2CPacket packet);

    void onChunkUnload(UnloadChunkS2CPacket packet);

    void onChunkData(ChunkDataS2CPacket packet);

    void onWorldEvent(WorldEventS2CPacket packet);

    void onParticle(ParticleS2CPacket packet);

    void onLightUpdate(LightUpdateS2CPacket packet);

    void onMapUpdate(MapUpdateS2CPacket packet);

    void onMerchantOffersList(MerchantOffersListS2CPacket packet);


    void onTabListUpdate(TabListUpdateS2CPacket packet);

    void onKeepAlive(KeepAliveS2CPacket packet);

    void onCombatDeath(DeathMessageS2CPacket packet);

    void onGameEvent(GameEventS2CPacket packet);

    void onChatMessage(ChatMessageS2CPacket packet);

    void onRespawn(RespawnS2CPacket packet);

    void onSyncPlayerPos(SyncPositionS2CPacket packet);

    void onSpawnPointUpdate(PlayerSpawnPositionS2CPacket packet);

    void onGameChatMessage(GameMessageS2CPacket packet);

    void onPlayDisconnect(DisconnectS2CPacket packet);

    void onSubtitleTextUpdate(SubtitleUpdateS2CPacket packet);

    void onGameJoin(GameJoinS2CPacket packet);

    void onAdvancementUpdate(AdvancementUpdateS2CPacket packet);

    void onChatVisibilityUpdate(ChatVisibilityUpdateS2CPacket packet);

    void onChunkSectionUpdate(ChunkDeltaUpdateS2CPacket packet);

    void onChunkLoadDistanceUpdate(ChunkLoadDistanceS2CPacket packet);

    void onChunkCenterUpdate(ChunkRenderDistanceCenterS2CPacket packet);

    void onCraftFailed(CraftFailedResponseS2CPacket packet);

    void onCombatEnded(EndCombatS2CPacket packet);

    void onCombatStart(EnterCombatS2CPacket packet);

    void onEntitiesRemove(RemoveEntitiesS2CPacket packet);

    void onExperienceBarUpdate(ExperienceBarUpdateS2CPacket packet);

    void onHealthUpdate(HealthUpdateS2CPacket packet);

    void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet);

    void onLookAt(LookAtS2CPacket packet);

    void onMessageHeader(MessageHeaderS2CPacket packet);

    void onTagQueryResponse(NbtQueryResponseS2CPacket packet);

    void onSignedBookOpened(OpenSignedBookS2CPacket packet);

    void onOverlayMessage(OverlayMessageS2CPacket packet);

    void onPlayerListUpdate(PlayerListS2CPacket packet);

    void onPing(PlayPingS2CPacket packet);

    void onCustomSound(SoundPlayByIdS2CPacket packet);

    void onSound(SoundS2CPacket packet);

    void onResourcePackLoad(ResourcePackLoadS2CPacket packet);

    void onScoreboardDisplay(ScoreboardDisplayS2CPacket packet);

    void onObjectiveUpdate(ScoreboardObjectiveUpdateS2CPacket packet);

    void onScoreboardUpdate(ScoreboardUpdateS2CPacket packet);

    void onAdvancementTabSelect(SelectAdvancementTabS2CPacket packet);

    void onServerMetadata(ServerMetadataS2CPacket packet);

    void onSignEditorOpen(SignEditorOpenS2CPacket packet);

    void onSimulationDistanceUpdate(SimulationDistanceUpdateS2CPacket packet);

    void onStopSound(StopSoundS2CPacket packet);

    void onRecipesUpdate(SynchronizeRecipesS2CPacket packet);

    void onTagsUpdate(SynchronizeTagsS2CPacket packet);

    void onTeamsUpdate(TeamS2CPacket packet);

    void onTitleAnimateUpdate(TitleFadeS2CPacket packet);

    void onTitleUpdate(TitleS2CPacket packet);

    void onRecipesUnlock(UnlockRecipesS2CPacket packet);

    void onVehicleMove(VehicleMoveS2CPacket packet);

    void onBorderInit(WorldBorderInitializeS2CPacket packet);

    void onBorderCenterUpdate(WorldBorderCenterChangedS2CPacket packet);

    void onBorderInterpolSizeUpdate(WorldBorderInterpolateSizeS2CPacket packet);

    void onBorderSizeUpdate(WorldBorderSizeChangedS2CPacket packet);

    void onBorderWarnBlockUpdate(WorldBorderWarningBlocksChangedS2CPacket packet);

    void onBorderWarnTimeUpdate(WorldBorderWarningTimeChangedS2CPacket packet);

    void onWorldTimeUpdate(WorldTimeUpdateS2CPacket packet);

}
