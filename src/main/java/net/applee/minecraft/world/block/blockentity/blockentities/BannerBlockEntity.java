package net.applee.minecraft.world.block.blockentity.blockentities;

import net.applee.minecraft.enums.DyeColor;
import net.applee.minecraft.utils.Nbt;
import net.applee.minecraft.utils.math.BlockPos;
import net.applee.minecraft.utils.string.Text;
import net.applee.minecraft.world.block.blockentity.BlockEntity;
import net.applee.minecraft.world.block.blockentity.BlockEntityData;
import net.applee.minecraft.world.block.blockentity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BannerBlockEntity extends BlockEntity {
	public static final int field_31296 = 6;
	public static final String PATTERNS_KEY = "Patterns";
	public static final String PATTERN_KEY = "Pattern";
	public static final String COLOR_KEY = "Color";
	@Nullable
	private Text customName;
	private DyeColor baseColor;
	@Nullable
	private List patternListNbt;

	public BannerBlockEntity(BlockEntityType blockEntityType, BlockPos pos, Nbt nbtData) {
		super(blockEntityType, pos, nbtData);
	}

	public BannerBlockEntity(BlockEntityData data, BlockPos pos) {
		super(data, pos);
	}
//	@Nullable
//	private List<Pair<RegistryEntry<BannerPattern>, DyeColor>> patterns;
//
//	public BannerBlockEntity(BlockPos pos, BlockState state, DyeColor baseColor) {
//		this(pos, state);
//		this.baseColor = baseColor;
//	}
//
//	public BannerBlockEntity(BlockPos pos, BlockState state) {
//		super(BlockEntityType.BANNER, pos, state);
//		this.baseColor = ((AbstractBannerBlock) state.getBlock()).getColor();
//	}
//
//	@Nullable
//	public static List getPatternListNbt(ItemStack stack) {
//		List list = null;
//		Nbt nbtCompound = BlockItem.getBlockEntityNbt(stack);
//		if (nbtCompound != null && nbtCompound.contains(PATTERNS_KEY, 9)) {
//			list = nbtCompound.getList(PATTERNS_KEY, 10).copy();
//		}
//		return list;
//	}
//
//	public static int getPatternCount(ItemStack stack) {
//		NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
//		if (nbtCompound != null && nbtCompound.contains(PATTERNS_KEY)) {
//			return nbtCompound.getList(PATTERNS_KEY, 10).size();
//		}
//		return 0;
//	}

//	public static List<Pair<RegistryEntry<BannerPattern>, DyeColor>> getPatternsFromNbt(DyeColor baseColor, @Nullable NbtList patternListNbt) {
//		ArrayList list = Lists.newArrayList();
//		list.add(Pair.of(Registry.BANNER_PATTERN.entryOf(BannerPatterns.BASE), (Object) baseColor));
//		if (patternListNbt != null) {
//			for (int i = 0; i < patternListNbt.size(); ++i) {
//				NbtCompound nbtCompound = patternListNbt.getCompound(i);
//				RegistryEntry<BannerPattern> registryEntry = BannerPattern.byId(nbtCompound.getString(PATTERN_KEY));
//				if (registryEntry == null) continue;
//				int j = nbtCompound.getInt(COLOR_KEY);
//				list.add(Pair.of(registryEntry, (Object) DyeColor.byId(j)));
//			}
//		}
//		return list;
//	}
//
//	public static void loadFromItemStack(ItemStack stack) {
//		NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
//		if (nbtCompound == null || !nbtCompound.contains(PATTERNS_KEY, 9)) {
//			return;
//		}
//		NbtList nbtList = nbtCompound.getList(PATTERNS_KEY, 10);
//		if (nbtList.isEmpty()) {
//			return;
//		}
//		nbtList.remove(nbtList.size() - 1);
//		if (nbtList.isEmpty()) {
//			nbtCompound.remove(PATTERNS_KEY);
//		}
//		BlockItem.setBlockEntityNbt(stack, BlockEntityType.BANNER, nbtCompound);
//	}
//
//	public void readFrom(ItemStack stack, DyeColor baseColor) {
//		this.baseColor = baseColor;
//		this.readFrom(stack);
//	}
//
//	public void readFrom(ItemStack stack) {
//		this.patternListNbt = BannerBlockEntity.getPatternListNbt(stack);
//		this.patterns = null;
//		this.customName = stack.hasCustomName() ? stack.getName() : null;
//	}

	public Text getName() {
		if (this.customName != null) {
			return this.customName;
		}

		return Text.translatable("block.minecraft.banner");
	}

	public boolean hasCustomName() {
		return this.getCustomName() != null;
	}

	@Nullable
	public Text getCustomName() {
		return this.customName;
	}

	public void setCustomName(Text customName) {
		this.customName = customName;
	}

//	protected void writeNbt(NbtCompound nbt) {
//		super.writeNbt(nbt);
//		if (this.patternListNbt != null) {
//			nbt.put(PATTERNS_KEY, this.patternListNbt);
//		}
//		if (this.customName != null) {
//			nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
//		}
//	}
//
//	public void readNbt(NbtCompound nbt) {
//		super.readNbt(nbt);
//		if (nbt.contains("CustomName", 8)) {
//			this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
//		}
//		this.patternListNbt = nbt.getList(PATTERNS_KEY, 10);
//		this.patterns = null;
//	}
//
//	public NbtCompound toInitialChunkDataNbt() {
//		return this.createNbt();
//	}
//
//	public List<Pair<RegistryEntry<BannerPattern>, DyeColor>> getPatterns() {
//		if (this.patterns == null) {
//			this.patterns = BannerBlockEntity.getPatternsFromNbt(this.baseColor, this.patternListNbt);
//		}
//		return this.patterns;
//	}
//
//	public ItemStack getPickStack() {
//		ItemStack itemStack = new ItemStack(BannerBlock.getForColor(this.baseColor));
//		if (this.patternListNbt != null && !this.patternListNbt.isEmpty()) {
//			NbtCompound nbtCompound = new NbtCompound();
//			nbtCompound.put(PATTERNS_KEY, this.patternListNbt.copy());
//			BlockItem.setBlockEntityNbt(itemStack, this.getType(), nbtCompound);
//		}
//		if (this.customName != null) {
//			itemStack.setCustomName(this.customName);
//		}
//		return itemStack;
//	}

	public DyeColor getColorForState() {
		return this.baseColor;
	}
}
