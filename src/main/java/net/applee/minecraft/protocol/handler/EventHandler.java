package net.applee.minecraft.protocol.handler;

import net.applee.minecraft.client.ClientPlayerEntity;
import net.applee.minecraft.client.ClientSettings;
import net.applee.minecraft.client.MinecraftClient;
import net.applee.minecraft.enums.GameEvent;
import net.applee.minecraft.enums.Hand;
import net.applee.minecraft.enums.MessageType;
import net.applee.minecraft.item.EquipmentSlot;
import net.applee.minecraft.item.ItemStack;
import net.applee.minecraft.item.map.MapState;
import net.applee.minecraft.protocol.packet.C2SPacket;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;
import net.applee.minecraft.protocol.packet.packets.c2s.play.*;
import net.applee.minecraft.protocol.packet.packets.s2c.login.*;
import net.applee.minecraft.protocol.packet.packets.s2c.play.*;
import net.applee.minecraft.protocol.packet.packets.s2c.play.todo.*;
import net.applee.minecraft.utils.JsonHelper;
import net.applee.minecraft.utils.Utils;
import net.applee.minecraft.utils.math.*;
import net.applee.minecraft.utils.string.Text;
import net.applee.minecraft.world.Dimension;
import net.applee.minecraft.world.World;
import net.applee.minecraft.world.chunk.Chunk;
import net.applee.minecraft.world.chunk.ChunkData;
import net.applee.minecraft.world.entity.Entity;
import net.applee.minecraft.world.entity.EntityType;
import net.applee.minecraft.world.entity.TrackedPosition;
import net.applee.minecraft.world.entity.attributes.AttributeData;
import net.applee.minecraft.world.entity.attributes.AttributesContainer;
import net.applee.minecraft.world.entity.misc.ExpOrbEntity;
import net.applee.minecraft.world.entity.player.PlayerEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class EventHandler implements IEventHandler {
	private static final int NO_ANSWER_KICK_DELAY = 700;

	private MinecraftClient mc;
	boolean keepAliveSended = false;
	int keepAliveTimer = 0;

	private String loginNickname;
	private UUID loginUUID;

	@Override
	public void init() {

	}

	@Override
	public void setClient(MinecraftClient client) {
		mc = client;
	}

	@Override
	public void resetKeepAliveTimer() {
		keepAliveTimer = 0;
	}

	@Override
	public void onLoginDisconnect(LoginDisconnectS2CPacket packet) {

		JsonHelper json;
		try {
			json = new JsonHelper(packet.getReason());
		} catch (Exception e) {
            mc.logger.exception(e);
			return;
		}

		if (mc.pinging) {
			mc.pinging = false;
			mc.serverMetadata.previewsChat = json.getBoolean("previewsChat");
			mc.serverMetadata.enforcesSecureChat = json.getBoolean("enforcesSecureChat");
			setServerIcon(json.getString("favicon"), 1);

			JsonHelper players;
			JsonHelper version;
			if ((players = json.getJSONObject("players")) != null) {
				mc.serverMetadata.maxPlayers = players.getInt("max");
				mc.serverMetadata.currentOnline = players.getInt("online");
				JSONArray onlinePlayers = players.getJSONArray("sample");

				if (onlinePlayers != null) {
					for (Object playerJson : onlinePlayers) {
						try {
							mc.serverMetadata.players.add(new JSONObject(String.valueOf(playerJson)));
						} catch (Exception e) {
						}
					}
				}
			}

			if ((version = json.getJSONObject("version")) != null) {
				mc.serverMetadata.versionProtocol = version.getInt("protocol");
				mc.serverMetadata.versionName = version.getString("name");
			}

			mc.serverMetadata.description = Text.of(json.getJSONObject("description"));
			mc.logger.info("Pinged:", "\"%s\"".formatted(mc.serverMetadata.description.getStyledText()), "%s/%s".formatted(mc.serverMetadata.currentOnline, mc.serverMetadata.maxPlayers));
			mc.networkHandler.disconnect();
		} else {
            Text message = Text.of(json);
			mc.logger.info(message.getStyledText());
            onDisconnect(message);
		}
	}

	@Override
	public void onLoginEncryptRequest(LoginRequestS2CPacket packet) {

	}

	@Override
	public void onLoginSuccess(LoginSuccessS2CPacket packet) {
		mc.networkHandler.isPlaying = true;
		loginNickname = packet.getNickname();
		loginUUID = packet.getUuid();
	}

	@Override
	public void onLoginCompressionSet(LoginCompressionS2CPacket packet) {
		mc.networkHandler.packetCompressThreshold = packet.getCompressThreshold();
	}

	@Override
	public void onLoginPluginRequest(LoginPluginRequestS2CPacket packet) {

	}


	// Play

	int gcTimer = 0;

	@Override
	public void onTick() {
		if (gcTimer < 100) {
			gcTimer++;
		} else {
			gcTimer = 0;
			System.gc();
		}
		mc.world.tick();
		if (mc.networkHandler.isClosed() || !mc.networkHandler.isConnected()) {
			onDisconnect(Text.literal("Socket closed"));
			return;
		}
		if (keepAliveSended) {
			keepAliveSended = false;
			keepAliveTimer = 0;
		} else keepAliveTimer++;
		if (keepAliveTimer >= NO_ANSWER_KICK_DELAY) {
			keepAliveTimer = 0;
			onDisconnect(Text.literal("No answer from server"));
		}
	}

	@Override
	public void onPacketReceived(PacketByteBuffer buffer, S2CPacket<IEventHandler> packet) {
		packet.load(buffer);
		packet.apply(this);
	}

	@Override
	public boolean onPacketSend(C2SPacket packet) {
		return false;
	}

	@Override
	public void onPacketSent(C2SPacket packet) {

	}

	@Override
	public void onMessage(String message, MessageType type) {

	}

	@Override
	public void onDisconnect(Text reason) {
		onMessage(reason.getStyledText(), MessageType.DISCONNECT);
		mc.logger.warning("Disconnected:", reason);
		mc.networkHandler.disconnect();
	}

	@Override
	public void onTerminate() {
	}

	// Entities
	@Override
	public void onSpawnEntity(SpawnEntityS2CPacket packet) {
		Entity entity = packet.getType().createEntity();
		entity.setType(packet.getType());
		entity.setPosition(packet.getPos());
		entity.setHeadYaw(packet.getHeadYaw());
		entity.setId(packet.getEntityID());
		entity.setUuid(packet.getEntityUuid());
		entity.setVelocity(packet.getVelocity());

		switch (packet.getType()) {
			case ITEM_FRAME, GLOW_ITEM_FRAME, PAINTING -> {
				entity.setLookDirection(Direction.byId(packet.getData()));
			}

			default -> entity.setRotation(new Rotation(packet.getYaw(), packet.getPitch()));
		}


		mc.world.addEntity(packet.getEntityID(), entity);
	}

	@Override
	public void onExpOrbSpawn(SpawnExpOrbS2CPacket packet) {
		ExpOrbEntity expOrb = new ExpOrbEntity();
		expOrb.setType(EntityType.EXPERIENCE_ORB);
		expOrb.setPosition(packet.getPos());
		expOrb.setId(packet.getEntityID());
		expOrb.setCount(packet.getCount());
		mc.world.addEntity(packet.getEntityID(), expOrb);
	}

	@Override
	public void onPlayerSpawn(SpawnPlayerS2CPacket packet) {
		PlayerEntity player = new PlayerEntity();
		player.setPosition(packet.getPos());
		player.setRotation(packet.getRotation());
		player.setUuid(packet.getEntityUuid());
		player.setId(packet.getEntityID());
		player.setType(EntityType.PLAYER);
		mc.world.addEntity(packet.getEntityID(), player);
	}

	@Override
	public void onEntityMove(EntityMoveS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		entity.setOnGround(packet.isOnGround());

		// On move
		if (packet.isMoved()) {
			TrackedPosition trackedPosition = entity.getTrackedPosition();
			Vec3d updated = trackedPosition.withDelta(packet.getDelta());
			trackedPosition.setPos(updated);
			entity.updateTrackedPosition(trackedPosition);
		}

		// On rotate
		if (packet.isRotated()) {
			entity.setRotation(packet.getRotation());
		}

		mc.world.addEntity(packet.getEntityID(), entity);
	}

	@Override
	public void onEntityTeleported(EntityTeleportedS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		entity.setOnGround(packet.isOnGround());
		entity.setRotation(packet.getRotation());
		TrackedPosition trackedPosition = entity.getTrackedPosition();
		trackedPosition.setPos(new Vec3d(0, 0, 0));
		entity.updateTrackedPosition(trackedPosition);
		entity.setPosition(packet.getPos());

		mc.world.addEntity(packet.getEntityID(), entity);
	}

	@Override
	public void onEntityHeadRotationUpdate(EntityHeadYawUpdateS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		entity.setHeadYaw(packet.getYaw());
	}

	@Override
	public void onEntityVelocityUpdate(EntityVelocityUpdateS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		Vec3i velPack = packet.getVelocity();
		Vec3d vel = new Vec3d((double) velPack.getX() / 8000.0, (double) velPack.getY() / 8000.0, (double) velPack.getZ() / 8000.0);

		if (entity instanceof ClientPlayerEntity player) player.setKnockbackVelocity(vel);
		else entity.setVelocity(vel);
	}


	@Override
	public void onEntityLeashed(EntityAttachS2CPacket packet) {
		Entity entity = getEntity(packet.getOwnerID());
		if (entity == null) return;
		Entity attached = getEntity(packet.getLeashedID());
		if (attached == null) return;
		entity.setHoldingEntity(attached);
	}

	@Override
	public void onEntityEffect(EntityStatusEffectS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;
		entity.addEffect(packet.getData());
	}

	@Override
	public void onEntityEffectClear(EntityEffectClearS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;
		entity.removeEffect(packet.getEffect());
	}

	@Override
	public void onEntityAttributesUpdate(EntityAttributesS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		AttributesContainer container = entity.getAttributesContainer();

		for (EntityAttributesS2CPacket.Property property : packet.getProperties()) {
			AttributeData attributeData = new AttributeData(property.getKey(), property.getValue(), property.getModifiers());
			container.updateAttribute(attributeData);
		}
	}

	@Override
	public void onEntityPassengersUpdate(EntityPassengersUpdateS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		for (int passengerID : packet.getPassengersIDS()) {
			Entity passenger = getEntity(passengerID);
			if (passenger == null) continue;
			passenger.startRiding(entity);
		}
	}

	@Override
	public void onEntityEquipmentUpdate(EntityEquipmentUpdateS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		for (EquipmentSlot slot : packet.getSlots().keySet())
			entity.updateEquipmentSlot(slot, packet.getSlots().get(slot));
	}

	@Override
	public void onEntityMetadataUpdate(EntityMetadataUpdateS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;
		entity.readMetadata(packet.getMetadata());
	}

	@Override
	public void onEntityStatus(EntityStatusS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		entity.handleStatus(packet.getStatus());
	}

	@Override
	public void onEntityAnimation(EntityAnimationS2CPacket packet) {
		Entity entity = getEntity(packet.getEntityID());
		if (entity == null) return;

		EntityAnimationS2CPacket.Animation animation = packet.getAnimation();

		switch (animation) {
			case SWING_MAIN_HAND, SWING_OFFHAND -> {
				if (entity instanceof PlayerEntity player)
					player.swingHand(animation == EntityAnimationS2CPacket.Animation.SWING_MAIN_HAND ? Hand.MAIN_HAND : Hand.OFF_HAND);
			}
		}
	}

	@Override
	public void onSoundEntity(SoundFromEntityS2CPacket packet) {
//        Entity entity = getEntity(packet.getEntityID());
//        if (entity == null) return;
	}

	@Override
	public void onEntitiesRemove(RemoveEntitiesS2CPacket packet) {
		for (int i : packet.getEntitiesID())
			mc.world.removeEntity(i);
	}

	@Override
	public void onPlayerAbilities(PlayerAbilitiesS2CPacket packet) {
		mc.player.setAbilities(packet.getAbilities());
	}

	// Screen
	@Override
	public void onSelectedSlotUpdate(SelectedSlotUpdateS2CPacket packet) {
		mc.player.inventory.setSelectedSlot(packet.getSlot());
	}

	@Override
	public void onScreenSlotsUpdateUpdate(ScreenSlotsUpdateS2CPacket packet) {
		if (packet.getWindowId() == 0) {
			mc.player.inventory.updateInventory(packet.getStacks(), packet.getCarriedStack(), packet.getStateId());
		}
//        else if (packet.getWindowId() == mc.player.currentScreenHandler.syncId) {
//            playerEntity.currentScreenHandler.updateSlotStacks(packet.getRevision(), packet.getContents(), packet.getCursorStack());
//        }
	}

	@Override
	public void onScreenSlotUpdate(ScreenSlotUpdateS2CPacket packet) {
		ItemStack itemStack = packet.getStack();
		int i = packet.getSlotId();
//        mc.getTutorialManager().onSlotUpdate(itemStack);
		if (packet.getWindowId() == ScreenSlotUpdateS2CPacket.UPDATE_CURSOR_SYNC_ID) {
//            if (!(mc.currentScreen instanceof CreativeInventoryScreen)) {
//                mc.player.currentScreenHandler.setCursorStack(itemStack);
//            }
		} else if (packet.getWindowId() == ScreenSlotUpdateS2CPacket.UPDATE_PLAYER_INVENTORY_SYNC_ID) {
			mc.player.inventory.updateSlot(i, itemStack);
		} else if (packet.getWindowId() == 0) {
			mc.player.inventory.updateSlot(i, packet.getStateId(), itemStack);
		}
//        else if (!(packet.getWindowId() != mc.player.currentScreenHandler.syncId || packet.getWindowId() == 0)) {
//            mc.player.currentScreenHandler.setStackInSlot(i, packet.getRevision(), itemStack);
//        }

	}

	@Override
	public void onScreenOpen(OpenScreenS2CPacket packet) {

	}

	@Override
	public void onCloseScreen(CloseScreenS2CPacket packet) {

	}

	@Override
	public void onScreenPropertyUpdate(ScreenPropertyUpdateS2CPacket packet) {

	}

	@Override
	public void onHorseScreenOpened(OpenHorseScreenS2CPacket packet) {

	}

	@Override
	public void onScreenShaderUpdate(ScreenShaderUpdateS2CPacket packet) {

	}

	@Override
	public void onAwardStatistic(AwardStatisticsS2CPacket packet) {

	}

	@Override
	public void onAcknowledgeBlockChange(BlockAcknowledgeChangeS2CPacket packet) {

	}

	@Override
	public void onBlockBreakingProgress(BlockBreakingStageUpdateS2CPacket packet) {

	}

	@Override
	public void onBlockEntityUpdate(BlockEntityDataS2CPacket packet) {

	}

	@Override
	public void onBlockEvent(BlockEventS2CPacket packet) {

	}

	@Override
	public void onBlockUpdate(BlockUpdateS2CPacket packet) {
		mc.world.updateBlock(packet.getPos(), packet.getBlockId());
	}

	@Override
	public void onBossBarUpdate(BossBarUpdateS2CPacket packet) {
	}

	@Override
	public void onChangeDifficulty(DifficultyUpdateS2CPacket packet) {
	}

	@Override
	public void onChatPreview(ChatPreviewS2CPacket packet) {
	}

	@Override
	public void onClearTitle(ClearTitleS2CPacket packet) {
	}

	@Override
	public void onCommandsSuggest(CommandSuggestionsS2CPacket packet) {
	}

	@Override
	public void onCommandsList(CommandTreeS2CPacket packet) {
	}

	@Override
	public void onCooldownUpdate(CooldownUpdateS2CPacket packet) {

	}

	@Override
	public void onChatSuggestions(ChatSuggestionsS2CPacket packet) {

	}

	@Override
	public void onPluginMessage(PluginMessageS2CPacket packet) {
		PacketByteBuffer data = new PacketByteBuffer(packet.getData());
		if ("minecraft:brand".equals(packet.getChannel())) {
			mc.serverMetadata.brand = data.readString();
		}

	}

	@Override
	public void onHideMessage(HideMessageS2CPacket packet) {

	}

	@Override
	public void onExplosion(ExplosionS2CPacket packet) {

	}

	@Override
	public void onChunkUnload(UnloadChunkS2CPacket packet) {
		mc.world.unloadChunk(packet.getChunkPos());
	}

	@Override
	public void onChunkData(ChunkDataS2CPacket packet) {
		if (!mc.settings.isChunkLoading()) return;
		ChunkData data = packet.getData();
		ChunkPos pos = data.getPos();
		Chunk chunk = new Chunk(pos, data);
		mc.world.addChunk(pos, chunk);
	}

	@Override
	public void onWorldEvent(WorldEventS2CPacket packet) {

	}

	@Override
	public void onParticle(ParticleS2CPacket packet) {

	}

	@Override
	public void onLightUpdate(LightUpdateS2CPacket packet) {

	}

	@Override
	public void onMapUpdate(MapUpdateS2CPacket packet) {
		int i = packet.getId();
		String string = MapState.getMapName(i);
		MapState mapState = mc.world.getMapState(string);
		if (mapState == null) {
			mapState = MapState.of(packet.getScale(), packet.isLocked(), mc.world.getCurrentDimension());
			mc.world.addMapState(string, mapState);
		}
		packet.apply(mapState);
	}

	@Override
	public void onMerchantOffersList(MerchantOffersListS2CPacket packet) {

	}


	@Override
	public void onTabListUpdate(TabListUpdateS2CPacket packet) {

	}

	@Override
	public void onKeepAlive(KeepAliveS2CPacket packet) {
		keepAliveSended = true;
		mc.networkHandler.sendPacket(new KeepAliveC2SPacket(packet.getKeepAliveId()));
	}

	@Override
	public void onCombatDeath(DeathMessageS2CPacket packet) {
		JSONObject json = new JSONObject(packet.getMessage().getStyledText());
		json.put("player", packet.getPlayer()).put("killer", packet.getKiller());
		this.onMessage(json.toString(), MessageType.DEATH);
	}

	@Override
	public void onGameEvent(GameEventS2CPacket packet) {
		if (packet.getEvent() == GameEvent.EnableRespawnScreen) mc.player.setHealth(0);
	}

	@Override
	public void onChatMessage(ChatMessageS2CPacket packet) {
		JSONObject json = new JSONObject();
		JSONObject unsignedContent;

		if (packet.getMessage().getUnsignedContent().isPresent()) {
			try {
				unsignedContent = new JSONObject(packet.getMessage().getUnsignedContent().get().toString());
			} catch (Exception e) {
				unsignedContent = null;
			}
		} else unsignedContent = null;

		json.put("message", packet.getMessage().getBody().getText())
				.put("formatted_message", packet.getMessage().getBody().getFormattedMessage())
				.put("timestamp", packet.getMessage().getBody().getInstant().getEpochSecond())
				.put("sender_uuid", packet.getMessage().getHeader().getSenderUuid())
				.put("unsigned_content", unsignedContent);

		this.onMessage(json.toString(), MessageType.CHAT);
	}

	@Override
	public void onRespawn(RespawnS2CPacket packet) {

	}

	@Override
	public void onSyncPlayerPos(SyncPositionS2CPacket packet) {
		double x, y, z, velX = 0, velY = 0, velZ = 0;

		ClientPlayerEntity clientPlayerEntity = mc.player;
		Vec3d syncPos = packet.getPos();
		Vec3d speed = clientPlayerEntity.getVelocity();
		Vec3d pos = clientPlayerEntity.getPosition();

		if (packet.isDismountVehicle()) {
			mc.player.stopRiding();
		}

		if (packet.getFlags().contains(SyncPositionS2CPacket.Flag.X)) {
			velX = speed.x;
			x = pos.x + syncPos.x;
		} else x = syncPos.x;
		if (packet.getFlags().contains(SyncPositionS2CPacket.Flag.Y)) {
			velY = speed.y;
			y = pos.y + syncPos.y;
		} else y = syncPos.y;
		if (packet.getFlags().contains(SyncPositionS2CPacket.Flag.Z)) {
			velZ = speed.z;
			z = pos.z + syncPos.z;
		} else z = syncPos.z;

		clientPlayerEntity.setPosAndPrevPos(x, y, z);
		clientPlayerEntity.setVelocity(velX, velY, velZ);

		Rotation rotation = packet.getRotation();

		if (packet.getFlags().contains(SyncPositionS2CPacket.Flag.X_ROT)) {
			rotation = rotation.addPitch(clientPlayerEntity.getRotation().getPitch());
		}
		if (packet.getFlags().contains(SyncPositionS2CPacket.Flag.Y_ROT)) {
			rotation = rotation.addYaw(clientPlayerEntity.getRotation().getYaw());
		}

		clientPlayerEntity.setPosAndRot(MathHelper.round(x, 4), MathHelper.round(y, 4), MathHelper.round(z, 4), rotation);
		mc.networkHandler.sendPacket(new TeleportConfirmC2SPacket(packet.getTpId()));
	}

	@Override
	public void onSpawnPointUpdate(PlayerSpawnPositionS2CPacket packet) {
		mc.world.properties.spawnPos = packet.getPos();
	}

	@Override
	public void onGameChatMessage(GameMessageS2CPacket packet) {
		onMessage(packet.getContent(), MessageType.SYSTEM);
	}

	@Override
	public void onPlayDisconnect(DisconnectS2CPacket packet) {
		onDisconnect(packet.getReason());
	}

	@Override
	public void onSubtitleTextUpdate(SubtitleUpdateS2CPacket packet) {
		onMessage(packet.getSubtitle(), MessageType.SUBTITLE);
	}

	@Override
	public void onGameJoin(GameJoinS2CPacket packet) {
		mc.networkHandler.killTickEvent();
		initSession();

		mc.player.setId(packet.getEntityID());
		mc.player.setSelfNickname(loginNickname);
		mc.player.setUuid(loginUUID);

		mc.world.addEntity(packet.getEntityID(), mc.player);

		ClientSettings settings = mc.settings;
		mc.networkHandler.sendPacket(new UpdateClientSettingsC2SPacket(
				settings.getLanguage(),
				(byte) settings.getChunkDistance(),
				UpdateClientSettingsC2SPacket.ChatVisibility.Enabled,
				true,
				settings.getSkinParts(),
				settings.getMainArm(),
				false,
				false
		));

		for (String dim : packet.getDimensions()) {
			mc.world.addDimension(dim);
		}

		mc.networkHandler.sendPacket(new SendPluginMessageC2SPacket(PluginMessageS2CPacket.BRAND, MinecraftClient.brand.getBytes(StandardCharsets.UTF_8)));
		mc.world.setCurrentDimension(Dimension.getByName(packet.getDimensionType()));
		mc.world.setDeathDimension(Dimension.getByName(packet.getDeathDimensionName()));

		mc.networkHandler.runTickEvent();
	}

	@Override
	public void onAdvancementUpdate(AdvancementUpdateS2CPacket packet) {

	}

	@Override
	public void onChatVisibilityUpdate(ChatVisibilityUpdateS2CPacket packet) {

	}

	@Override
	public void onChunkSectionUpdate(ChunkDeltaUpdateS2CPacket packet) {

	}

	@Override
	public void onChunkLoadDistanceUpdate(ChunkLoadDistanceS2CPacket packet) {

	}

	@Override
	public void onChunkCenterUpdate(ChunkRenderDistanceCenterS2CPacket packet) {

	}

	@Override
	public void onCraftFailed(CraftFailedResponseS2CPacket packet) {

	}

	@Override
	public void onCombatEnded(EndCombatS2CPacket packet) {

	}

	@Override
	public void onCombatStart(EnterCombatS2CPacket packet) {

	}

	@Override
	public void onExperienceBarUpdate(ExperienceBarUpdateS2CPacket packet) {
		mc.player.setExperience(packet.getTotalExp());
		mc.player.setExperienceLevel(packet.getLevel());
		mc.player.setExperienceBar(packet.getExpBar());
	}

	@Override
	public void onHealthUpdate(HealthUpdateS2CPacket packet) {
		mc.player.setHealth(packet.getHealth());
		mc.player.setFood(packet.getFood());
		mc.player.setSaturation(packet.getFoodSaturation());
	}

	@Override
	public void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet) {
	}

	@Override
	public void onLookAt(LookAtS2CPacket packet) {

	}

	@Override
	public void onMessageHeader(MessageHeaderS2CPacket packet) {

	}

	@Override
	public void onTagQueryResponse(NbtQueryResponseS2CPacket packet) {

	}

	@Override
	public void onSignedBookOpened(OpenSignedBookS2CPacket packet) {

	}

	@Override
	public void onOverlayMessage(OverlayMessageS2CPacket packet) {

	}

	@Override
	public void onPlayerListUpdate(PlayerListS2CPacket packet) {
	}

	@Override
	public void onPing(PlayPingS2CPacket packet) {
		mc.networkHandler.sendPacket(new PongC2SPacket(packet.getId()));
	}


	@Override
	public void onCustomSound(SoundPlayByIdS2CPacket packet) {

	}

	@Override
	public void onSound(SoundS2CPacket packet) {

	}

	@Override
	public void onResourcePackLoad(ResourcePackLoadS2CPacket packet) {

	}

	@Override
	public void onScoreboardDisplay(ScoreboardDisplayS2CPacket packet) {

	}

	@Override
	public void onObjectiveUpdate(ScoreboardObjectiveUpdateS2CPacket packet) {

	}

	@Override
	public void onScoreboardUpdate(ScoreboardUpdateS2CPacket packet) {

	}

	@Override
	public void onAdvancementTabSelect(SelectAdvancementTabS2CPacket packet) {

	}

	@Override
	public void onServerMetadata(ServerMetadataS2CPacket packet) {
		String motd = packet.getMotd();
		setServerIcon(packet.getIcon(), 0);
		if (motd != null && !motd.isBlank())
			mc.serverMetadata.motd = Text.of(new JsonHelper(motd));

		mc.serverMetadata.previewsChat = packet.isPreviewsChat();
		mc.serverMetadata.enforcesSecureChat = packet.isEnforcesSecureChat();
	}

	@Override
	public void onSignEditorOpen(SignEditorOpenS2CPacket packet) {

	}

	@Override
	public void onSimulationDistanceUpdate(SimulationDistanceUpdateS2CPacket packet) {

	}

	@Override
	public void onStopSound(StopSoundS2CPacket packet) {

	}

	@Override
	public void onRecipesUpdate(SynchronizeRecipesS2CPacket packet) {

	}

	@Override
	public void onTagsUpdate(SynchronizeTagsS2CPacket packet) {
	}

	@Override
	public void onTeamsUpdate(TeamS2CPacket packet) {

	}

	@Override
	public void onTitleAnimateUpdate(TitleFadeS2CPacket packet) {

	}

	@Override
	public void onTitleUpdate(TitleS2CPacket packet) {
		onMessage(packet.getText(), MessageType.TITLE);
	}

	@Override
	public void onRecipesUnlock(UnlockRecipesS2CPacket packet) {

	}

	@Override
	public void onVehicleMove(VehicleMoveS2CPacket packet) {
		mc.player.updateVehiclePos(packet.getPos());
		mc.player.updateVehicleRotation(packet.getRotation());
	}

	@Override
	public void onBorderInit(WorldBorderInitializeS2CPacket packet) {
		mc.world.properties.worldBorder.maxRadius = packet.getMaxRadius();
		mc.world.properties.worldBorder.worldSize = packet.getSize();
		mc.world.properties.worldBorder.warningTime = packet.getWarningTime();
		mc.world.properties.worldBorder.warningBlocks = packet.getWarningBlocks();
		mc.world.properties.worldBorder.centerX = packet.getCenterX();
		mc.world.properties.worldBorder.centerZ = packet.getCenterZ();
		mc.world.properties.worldBorder.sizeLerpTarget = packet.getSizeLerpTarget();
		mc.world.properties.worldBorder.sizeLerpTime = packet.getSizeLerpTime();
	}

	@Override
	public void onBorderCenterUpdate(WorldBorderCenterChangedS2CPacket packet) {
		mc.world.properties.worldBorder.centerX = packet.getCenterX();
		mc.world.properties.worldBorder.centerZ = packet.getCenterZ();
	}

	@Override
	public void onBorderInterpolSizeUpdate(WorldBorderInterpolateSizeS2CPacket packet) {
		mc.world.properties.worldBorder.sizeLerpTarget = packet.getNewDiameter();
		mc.world.properties.worldBorder.worldSize = packet.getOldDiameter();
		mc.world.properties.worldBorder.sizeLerpTime = packet.getSpeed();
	}

	@Override
	public void onBorderSizeUpdate(WorldBorderSizeChangedS2CPacket packet) {
		mc.world.properties.worldBorder.worldSize = packet.getDiameter();
		mc.world.properties.worldBorder.worldSize = packet.getDiameter();
	}

	@Override
	public void onBorderWarnBlockUpdate(WorldBorderWarningBlocksChangedS2CPacket packet) {
		mc.world.properties.worldBorder.warningBlocks = packet.getWarnBlocks();
	}

	@Override
	public void onBorderWarnTimeUpdate(WorldBorderWarningTimeChangedS2CPacket packet) {
		mc.world.properties.worldBorder.warningTime = packet.getWarnTime();
	}

	@Override
	public void onWorldTimeUpdate(WorldTimeUpdateS2CPacket packet) {
		mc.world.setTime(packet.getTimeOfDay());
		mc.world.setAge(packet.getWorldAge());
	}


	private Entity getEntity(int id) {
		Entity entity;
		if ((entity = mc.world.getEntity(id)) == null) {
			if ((entity = mc.world.getPlayer(id)) == null) {
				return null;
			}
		}
		return entity;
	}


	private void setServerIcon(String ico, int type) {
		BufferedImage icon = Utils.imageFromBase64(ico);
		if (icon == null) return;
		if (type == 0) mc.serverMetadata.icon = icon;
		else if (type == 1) mc.serverMetadata.favicon = icon;
	}

	private void initSession() {
		resetKeepAliveTimer();
		mc.world = new World();
		mc.player = new ClientPlayerEntity();
		mc.profileManager.clear();
	}

}
