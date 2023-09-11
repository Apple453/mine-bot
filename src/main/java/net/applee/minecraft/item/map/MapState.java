package net.applee.minecraft.item.map;

import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.math.MathHelper;
import net.applee.minecraft.utils.string.Text;
import net.applee.minecraft.world.Dimension;
import net.applee.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapState {
	public static final int field_31831 = 4;
	public static final int MAX_ICONS = 256;
	private static final int field_31832 = 128;
	private static final int field_31833 = 64;
	public final int centerX;
	public final int centerZ;
	public final Dimension dimension;
	public final byte scale;
	public final boolean locked;
	final Map<String, MapIcon> icons = new LinkedHashMap<>();
	private final boolean showIcons;
	private final boolean unlimitedTracking;
//	private final List<PlayerUpdateTracker> updateTrackers = new ArrayList<>();
//	private final Map<PlayerEntity, PlayerUpdateTracker> updateTrackersByPlayer = new HashMap<>();
	private final Map<String, MapBannerMarker> banners = new HashMap<>();
	private final Map<String, MapFrameMarker> frames = new HashMap<>();
	public byte[] colors = new byte[16384];
	private int iconCount;

	private MapState(int centerX, int centerZ, byte scale, boolean showIcons, boolean unlimitedTracking, boolean locked, Dimension dimension) {
		this.scale = scale;
		this.centerX = centerX;
		this.centerZ = centerZ;
		this.dimension = dimension;
		this.showIcons = showIcons;
		this.unlimitedTracking = unlimitedTracking;
		this.locked = locked;
	}

	public static String getMapName(int mapId) {
		return "map_" + mapId;
	}

	public static MapState of(double centerX, double centerZ, byte scale, boolean showIcons, boolean unlimitedTracking, Dimension dimension) {
		int i = 128 * (1 << scale);
		int j = MathHelper.floor((centerX + 64.0) / (double) i);
		int k = MathHelper.floor((centerZ + 64.0) / (double) i);
		int l = j * i + i / 2 - 64;
		int m = k * i + i / 2 - 64;
		return new MapState(l, m, scale, showIcons, unlimitedTracking, false, dimension);
	}

	public static MapState of(byte scale, boolean showIcons, Dimension dimension) {
		return new MapState(0, 0, scale, false, false, showIcons, dimension);
	}

	public MapState copy() {
		MapState mapState = new MapState(this.centerX, this.centerZ, this.scale, this.showIcons, this.unlimitedTracking, true, this.dimension);
		mapState.banners.putAll(this.banners);
		mapState.icons.putAll(this.icons);
		mapState.iconCount = this.iconCount;
		System.arraycopy(this.colors, 0, mapState.colors, 0, this.colors.length);
		return mapState;
	}

	public MapState zoomOut(int zoomOutScale) {
		return MapState.of(this.centerX, this.centerZ, (byte) MathHelper.clamp(this.scale + zoomOutScale, 0, 4), this.showIcons, this.unlimitedTracking, this.dimension);
	}

//	public void update(PlayerEntity player, ItemStack stack) {
//		NbtCompound nbtCompound;
//		if (!this.updateTrackersByPlayer.containsKey(player)) {
//			PlayerUpdateTracker playerUpdateTracker = new PlayerUpdateTracker(player);
//			this.updateTrackersByPlayer.put(player, playerUpdateTracker);
//			this.updateTrackers.add(playerUpdateTracker);
//		}
//		if (!player.getInventory().contains(stack)) {
//			this.removeIcon(player.getName().getString());
//		}
//		for (int i = 0; i < this.updateTrackers.size(); ++i) {
//			PlayerUpdateTracker playerUpdateTracker2 = this.updateTrackers.get(i);
//			String string = playerUpdateTracker2.player.getName().getString();
//			if (playerUpdateTracker2.player.isRemoved() || !playerUpdateTracker2.player.getInventory().contains(stack) && !stack.isInFrame()) {
//				this.updateTrackersByPlayer.remove(playerUpdateTracker2.player);
//				this.updateTrackers.remove(playerUpdateTracker2);
//				this.removeIcon(string);
//				continue;
//			}
//			if (stack.isInFrame() || playerUpdateTracker2.player.world.getRegistryKey() != this.dimension || !this.showIcons)
//				continue;
//			this.addIcon(MapIcon.Type.PLAYER, playerUpdateTracker2.player.world, string, playerUpdateTracker2.player.getX(), playerUpdateTracker2.player.getZ(), playerUpdateTracker2.player.getYaw(), null);
//		}
//		if (stack.isInFrame() && this.showIcons) {
//			ItemFrameEntity itemFrameEntity = stack.getFrame();
//			BlockPos blockPos = itemFrameEntity.getDecorationBlockPos();
//			MapFrameMarker mapFrameMarker = this.frames.get(MapFrameMarker.getKey(blockPos));
//			if (mapFrameMarker != null && itemFrameEntity.getId() != mapFrameMarker.getEntityId() && this.frames.containsKey(mapFrameMarker.getKey())) {
//				this.removeIcon("frame-" + mapFrameMarker.getEntityId());
//			}
//			MapFrameMarker mapFrameMarker2 = new MapFrameMarker(blockPos, itemFrameEntity.getHorizontalFacing().getHorizontal() * 90, itemFrameEntity.getId());
//			this.addIcon(MapIcon.Type.FRAME, player.world, "frame-" + itemFrameEntity.getId(), blockPos.getX(), blockPos.getZ(), itemFrameEntity.getHorizontalFacing().getHorizontal() * 90, null);
//			this.frames.put(mapFrameMarker2.getKey(), mapFrameMarker2);
//		}
//		if ((nbtCompound = stack.getNbt()) != null && nbtCompound.contains("Decorations", 9)) {
//			NbtList nbtList = nbtCompound.getList("Decorations", 10);
//			for (int j = 0; j < nbtList.size(); ++j) {
//				NbtCompound nbtCompound2 = nbtList.getCompound(j);
//				if (this.icons.containsKey(nbtCompound2.getString("id"))) continue;
//				this.addIcon(MapIcon.Type.byId(nbtCompound2.getByte("type")), player.world, nbtCompound2.getString("id"), nbtCompound2.getDouble("x"), nbtCompound2.getDouble("z"), nbtCompound2.getDouble("rot"), null);
//			}
//		}
//	}

	private void removeIcon(String id) {
		MapIcon mapIcon = this.icons.remove(id);
		if (mapIcon != null && mapIcon.getType().shouldUseIconCountLimit()) {
			--this.iconCount;
		}
	}

	private void addIcon(MapIcon.Type type, @Nullable World world, String key, double x, double z, double rotation, @Nullable Text text) {
		MapIcon mapIcon2;
		MapIcon mapIcon;
		byte d;
		int k;

		int i = 1 << this.scale;
		float f = (float) (x - (double) this.centerX) / (float) i;
		float g = (float) (z - (double) this.centerZ) / (float) i;
		byte b = (byte) ((double) (f * 2.0f) + 0.5);
		byte c = (byte) ((double) (g * 2.0f) + 0.5);

		if (f >= -63.0f && g >= -63.0f && f <= 63.0f && g <= 63.0f) {
			d = (byte) ((rotation + (rotation < 0.0 ? -8.0 : 8.0)) * 16.0 / 360.0);
			if (this.dimension == Dimension.NETHER && world != null) {
				k = (int) (world.getTime() / 10L);
				d = (byte) (k * k * 34187121 + k * 121 >> 15 & 0xF);
			}
		} else if (type == MapIcon.Type.PLAYER) {
			if (Math.abs(f) < 320.0f && Math.abs(g) < 320.0f) {
				type = MapIcon.Type.PLAYER_OFF_MAP;
			} else if (this.unlimitedTracking) {
				type = MapIcon.Type.PLAYER_OFF_LIMITS;
			} else {
				this.removeIcon(key);
				return;
			}
			d = 0;

			if (f <= -63.0f) b = -128;
			if (g <= -63.0f) c = -128;
			if (f >= 63.0f) b = 127;
			if (g >= 63.0f) c = 127;

		} else {
			this.removeIcon(key);
			return;
		}

		if (!(mapIcon = new MapIcon(type, b, c, d, text)).equals(mapIcon2 = this.icons.put(key, mapIcon))) {
			if (mapIcon2 != null && mapIcon2.getType().shouldUseIconCountLimit()) {
				--this.iconCount;
			}
			if (type.shouldUseIconCountLimit()) {
				++this.iconCount;
			}
		}
	}

//	@Nullable
//	public Packet<?> getPlayerMarkerPacket(int id, PlayerEntity player) {
//		PlayerUpdateTracker playerUpdateTracker = this.updateTrackersByPlayer.get(player);
//		if (playerUpdateTracker == null) {
//			return null;
//		}
//		return playerUpdateTracker.getPacket(id);
//	}
//
//	public PlayerUpdateTracker getPlayerSyncData(PlayerEntity player) {
//		PlayerUpdateTracker playerUpdateTracker = this.updateTrackersByPlayer.get(player);
//		if (playerUpdateTracker == null) {
//			playerUpdateTracker = new PlayerUpdateTracker(player);
//			this.updateTrackersByPlayer.put(player, playerUpdateTracker);
//			this.updateTrackers.add(playerUpdateTracker);
//		}
//		return playerUpdateTracker;
//	}

	public boolean addBanner(World world, BlockPos pos) {
		double d = (double) pos.getX() + 0.5;
		double e = (double) pos.getZ() + 0.5;
		int i = 1 << this.scale;
		double f = (d - (double) this.centerX) / (double) i;
		double g = (e - (double) this.centerZ) / (double) i;
		int j = 63;
		if (f >= -63.0 && g >= -63.0 && f <= 63.0 && g <= 63.0) {
			MapBannerMarker mapBannerMarker = MapBannerMarker.fromWorldBlock(world, pos);
			if (mapBannerMarker == null) {
				return false;
			}
			if (this.banners.remove(mapBannerMarker.getKey(), mapBannerMarker)) {
				this.removeIcon(mapBannerMarker.getKey());
				return true;
			}
			if (!this.iconCountNotLessThan(256)) {
				this.banners.put(mapBannerMarker.getKey(), mapBannerMarker);
				this.addIcon(mapBannerMarker.getIconType(), world, mapBannerMarker.getKey(), d, e, 180.0, mapBannerMarker.getName());
				return true;
			}
		}
		return false;
	}

	public void removeBanner(World world, int x, int z) {
		for (MapBannerMarker banner : banners.values()) {
			if (banner.getPos().getX() != x || banner.getPos().getZ() != z || banner.equals(MapBannerMarker.fromWorldBlock(world, banner.getPos())))
				continue;
			this.removeIcon(banner.getKey());
		}
	}

	public Collection<MapBannerMarker> getBanners() {
		return this.banners.values();
	}

	public void removeFrame(BlockPos pos, int id) {
		this.removeIcon("frame-" + id);
		this.frames.remove(MapFrameMarker.getKey(pos));
	}

	public boolean putColor(int x, int z, byte color) {
		byte b = this.colors[x + z * 128];
		if (b != color) {
			this.setColor(x, z, color);
			return true;
		}
		return false;
	}

	public void setColor(int x, int z, byte color) {
		this.colors[x + z * 128] = color;
	}

	public boolean hasMonumentIcon() {
		for (MapIcon mapIcon : this.icons.values()) {
			if (mapIcon.getType() != MapIcon.Type.MANSION && mapIcon.getType() != MapIcon.Type.MONUMENT) continue;
			return true;
		}
		return false;
	}

	public void replaceIcons(MapIcon[] icons) {
		this.icons.clear();
		this.iconCount = 0;
		for (int i = 0; i < icons.length; ++i) {
			MapIcon mapIcon = icons[i];
			this.icons.put("icon-" + i, mapIcon);
			if (!mapIcon.getType().shouldUseIconCountLimit()) continue;
			++this.iconCount;
		}
	}

	public Iterable<MapIcon> getIcons() {
		return this.icons.values();
	}

	public boolean iconCountNotLessThan(int iconCount) {
		return this.iconCount >= iconCount;
	}

	public record UpdateData(int startX, int startZ, int width, int height, byte[] colors) {

		public void setColorsTo(MapState mapState) {
			for (int i = 0; i < this.width; ++i) {
				for (int j = 0; j < this.height; ++j) {
					mapState.setColor(this.startX + i, this.startZ + j, this.colors[i + j * this.width]);
				}
			}
		}
	}

//	public class PlayerUpdateTracker {
//		public final PlayerEntity player;
//		public int field_131;
//		private boolean dirty = true;
//		private int startX;
//		private int startZ;
//		private int endX = 127;
//		private int endZ = 127;
//		private boolean iconsDirty = true;
//		private int emptyPacketsRequested;
//
//		PlayerUpdateTracker(PlayerEntity player) {
//			this.player = player;
//		}
//
//		private UpdateData getMapUpdateData() {
//			int i = this.startX;
//			int j = this.startZ;
//			int k = this.endX + 1 - this.startX;
//			int l = this.endZ + 1 - this.startZ;
//			byte[] bs = new byte[k * l];
//			for (int m = 0; m < k; ++m) {
//				for (int n = 0; n < l; ++n) {
//					bs[m + n * k] = MapState.this.colors[i + m + (j + n) * 128];
//				}
//			}
//			return new UpdateData(i, j, k, l, bs);
//		}
//
//		@Nullable
//		Packet<?> getPacket(int mapId) {
//			Collection<MapIcon> collection;
//			UpdateData updateData;
//			if (this.dirty) {
//				this.dirty = false;
//				updateData = this.getMapUpdateData();
//			} else {
//				updateData = null;
//			}
//			if (this.iconsDirty && this.emptyPacketsRequested++ % 5 == 0) {
//				this.iconsDirty = false;
//				collection = MapState.this.icons.values();
//			} else {
//				collection = null;
//			}
//			if (collection != null || updateData != null) {
//				return new MapUpdateS2CPacket(mapId, MapState.this.scale, MapState.this.locked, collection, updateData);
//			}
//			return null;
//		}
//
//		void markDirty(int startX, int startZ) {
//			if (this.dirty) {
//				this.startX = Math.min(this.startX, startX);
//				this.startZ = Math.min(this.startZ, startZ);
//				this.endX = Math.max(this.endX, startX);
//				this.endZ = Math.max(this.endZ, startZ);
//			} else {
//				this.dirty = true;
//				this.startX = startX;
//				this.startZ = startZ;
//				this.endX = startX;
//				this.endZ = startZ;
//			}
//		}
//
//		private void markIconsDirty() {
//			this.iconsDirty = true;
//		}
//	}
}

