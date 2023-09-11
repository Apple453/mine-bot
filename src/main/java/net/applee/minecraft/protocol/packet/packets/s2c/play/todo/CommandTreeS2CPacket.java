package net.applee.minecraft.protocol.packet.packets.s2c.play.todo;

import net.applee.minecraft.protocol.handler.IEventHandler;
import net.applee.minecraft.protocol.packet.PacketByteBuffer;
import net.applee.minecraft.protocol.packet.S2CPacket;

public class CommandTreeS2CPacket implements S2CPacket<IEventHandler> {
    // Todo: make a packet...

    private static final byte field_33321 = 0;
    private static final byte field_33322 = 1;
    private static final byte field_33323 = 2;
    private static final byte field_33317 = 3;
    private static final byte field_33318 = 4;
    private static final byte field_33319 = 8;
    private static final byte field_33320 = 16;
    private int rootSize;
//    private final List<CommandNodeData> nodes;

//    public CommandTreeS2CPacket(RootCommandNode<CommandSource> rootNode) {
//        Object2IntMap<CommandNode<CommandSource>> object2IntMap = traverse(rootNode);
//        this.nodes = collectNodes(object2IntMap);
//        this.rootSize = object2IntMap.getInt(rootNode);
//    }


    @Override
    public void apply(IEventHandler handler) {
        handler.onCommandsList(this);
    }

    @Override
    public void load(PacketByteBuffer buffer) {
//        this.nodes = buf.readList(CommandTreeS2CPacket::readCommandNode);
//        this.rootSize = buf.readVarInt();
//        validate(this.nodes);
    }
//
//    private static void validate(List<CommandNodeData> nodeDatas, BiPredicate<CommandNodeData, IntSet> validator) {
//        IntSet intSet = new IntOpenHashSet(IntSets.fromTo(0, nodeDatas.size()));
//
//        boolean bl;
//        do {
//            if (intSet.isEmpty()) {
//                return;
//            }
//
//            bl = intSet.removeIf((i) -> {
//                return validator.test((CommandNodeData)nodeDatas.get(i), intSet);
//            });
//        } while(bl);
//
//        throw new IllegalStateException("Server sent an impossible command tree");
//    }
//
//    private static void validate(List<CommandNodeData> nodeDatas) {
//        validate(nodeDatas, CommandNodeData::validateRedirectNodeIndex);
//        validate(nodeDatas, CommandNodeData::validateChildNodeIndices);
//    }
//
//    private static Object2IntMap<CommandNode<CommandSource>> traverse(RootCommandNode<CommandSource> commandTree) {
//        Object2IntMap<CommandNode<CommandSource>> object2IntMap = new Object2IntOpenHashMap();
//        Queue<CommandNode<CommandSource>> queue = Queues.newArrayDeque();
//        queue.add(commandTree);
//
//        CommandNode commandNode;
//        while((commandNode = (CommandNode)queue.poll()) != null) {
//            if (!object2IntMap.containsKey(commandNode)) {
//                int i = object2IntMap.size();
//                object2IntMap.put(commandNode, i);
//                queue.addAll(commandNode.getChildren());
//                if (commandNode.getRedirect() != null) {
//                    queue.add(commandNode.getRedirect());
//                }
//            }
//        }
//
//        return object2IntMap;
//    }
//
//    private static List<CommandNodeData> collectNodes(Object2IntMap<CommandNode<CommandSource>> nodes) {
//        ObjectArrayList<CommandNodeData> objectArrayList = new ObjectArrayList(nodes.size());
//        objectArrayList.size(nodes.size());
//        ObjectIterator var2 = Object2IntMaps.fastIterable(nodes).iterator();
//
//        while(var2.hasNext()) {
//            Object2IntMap.Entry<CommandNode<CommandSource>> entry = (Object2IntMap.Entry)var2.next();
//            objectArrayList.set(entry.getIntValue(), createNodeData((CommandNode)entry.getKey(), nodes));
//        }
//
//        return objectArrayList;
//    }
//
//    private static CommandNodeData readCommandNode(PacketByteBuf buf) {
//        byte b = buf.readByte();
//        int[] is = buf.readIntArray();
//        int i = (b & 8) != 0 ? buf.readVarInt() : 0;
//        SuggestableNode suggestableNode = readArgumentBuilder(buf, b);
//        return new CommandNodeData(suggestableNode, b, i, is);
//    }
//
//    @Nullable
//    private static SuggestableNode readArgumentBuilder(PacketByteBuf buf, byte flags) {
//        int i = flags & 3;
//        String string;
//        if (i == 2) {
//            string = buf.readString();
//            int j = buf.readVarInt();
//            ArgumentSerializer<?, ?> argumentSerializer = (ArgumentSerializer)Registry.COMMAND_ARGUMENT_TYPE.get(j);
//            if (argumentSerializer == null) {
//                return null;
//            } else {
//                ArgumentSerializer.ArgumentTypeProperties<?> argumentTypeProperties = argumentSerializer.fromPacket(buf);
//                Identifier identifier = (flags & 16) != 0 ? buf.readIdentifier() : null;
//                return new ArgumentNode(string, argumentTypeProperties, identifier);
//            }
//        } else if (i == 1) {
//            string = buf.readString();
//            return new LiteralNode(string);
//        } else {
//            return null;
//        }
//    }
//
//    private static CommandNodeData createNodeData(CommandNode<CommandSource> node, Object2IntMap<CommandNode<CommandSource>> nodes) {
//        int i = 0;
//        int j;
//        if (node.getRedirect() != null) {
//            i |= 8;
//            j = nodes.getInt(node.getRedirect());
//        } else {
//            j = 0;
//        }
//
//        if (node.getCommand() != null) {
//            i |= 4;
//        }
//
//        Object suggestableNode;
//        if (node instanceof RootCommandNode) {
//            i |= 0;
//            suggestableNode = null;
//        } else if (node instanceof ArgumentCommandNode) {
//            ArgumentCommandNode<CommandSource, ?> argumentCommandNode = (ArgumentCommandNode)node;
//            suggestableNode = new ArgumentNode(argumentCommandNode);
//            i |= 2;
//            if (argumentCommandNode.getCustomSuggestions() != null) {
//                i |= 16;
//            }
//        } else {
//            if (!(node instanceof LiteralCommandNode)) {
//                throw new UnsupportedOperationException("Unknown node type " + node);
//            }
//
//            LiteralCommandNode literalCommandNode = (LiteralCommandNode)node;
//            suggestableNode = new LiteralNode(literalCommandNode.getLiteral());
//            i |= 1;
//        }
//
//        Stream var10000 = node.getChildren().stream();
//        Objects.requireNonNull(nodes);
//        int[] is = var10000.mapToInt(nodes::getInt).toArray();
//        return new CommandNodeData((SuggestableNode)suggestableNode, i, j, is);
//    }
//
//    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
//        clientPlayPacketListener.onCommandTree(this);
//    }
//
//    public RootCommandNode<CommandSource> getCommandTree(CommandRegistryAccess commandRegistryAccess) {
//        return (RootCommandNode)(new CommandTree(commandRegistryAccess, this.nodes)).getNode(this.rootSize);
//    }
//
//    private static class CommandNodeData {
//        @Nullable
//        final SuggestableNode suggestableNode;
//        final int flags;
//        final int redirectNodeIndex;
//        final int[] childNodeIndices;
//
//        CommandNodeData(@Nullable SuggestableNode suggestableNode, int flags, int redirectNodeIndex, int[] childNodeIndices) {
//            this.suggestableNode = suggestableNode;
//            this.flags = flags;
//            this.redirectNodeIndex = redirectNodeIndex;
//            this.childNodeIndices = childNodeIndices;
//        }
//
//        public void write(PacketByteBuf buf) {
//            buf.writeByte(this.flags);
//            buf.writeIntArray(this.childNodeIndices);
//            if ((this.flags & 8) != 0) {
//                buf.writeVarInt(this.redirectNodeIndex);
//            }
//
//            if (this.suggestableNode != null) {
//                this.suggestableNode.write(buf);
//            }
//
//        }
//
//        public boolean validateRedirectNodeIndex(IntSet indices) {
//            if ((this.flags & 8) != 0) {
//                return !indices.contains(this.redirectNodeIndex);
//            } else {
//                return true;
//            }
//        }
//
//        public boolean validateChildNodeIndices(IntSet indices) {
//            int[] var2 = this.childNodeIndices;
//            int var3 = var2.length;
//
//            for(int var4 = 0; var4 < var3; ++var4) {
//                int i = var2[var4];
//                if (indices.contains(i)) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }
//
//    private interface SuggestableNode {
//        ArgumentBuilder<CommandSource, ?> createArgumentBuilder(CommandRegistryAccess commandRegistryAccess);
//
//        void write(PacketByteBuf buf);
//    }
//
//    private static class ArgumentNode implements SuggestableNode {
//        private final String name;
//        private final ArgumentSerializer.ArgumentTypeProperties<?> properties;
//        @Nullable
//        private final Identifier id;
//
//        @Nullable
//        private static Identifier computeId(@Nullable SuggestionProvider<CommandSource> provider) {
//            return provider != null ? SuggestionProviders.computeId(provider) : null;
//        }
//
//        ArgumentNode(String name, ArgumentSerializer.ArgumentTypeProperties<?> properties, @Nullable Identifier id) {
//            this.name = name;
//            this.properties = properties;
//            this.id = id;
//        }
//
//        public ArgumentNode(ArgumentCommandNode<CommandSource, ?> node) {
//            this(node.getName(), ArgumentTypes.getArgumentTypeProperties(node.getType()), computeId(node.getCustomSuggestions()));
//        }
//
//        public ArgumentBuilder<CommandSource, ?> createArgumentBuilder(CommandRegistryAccess commandRegistryAccess) {
//            ArgumentType<?> argumentType = this.properties.createType(commandRegistryAccess);
//            RequiredArgumentBuilder<CommandSource, ?> requiredArgumentBuilder = RequiredArgumentBuilder.argument(this.name, argumentType);
//            if (this.id != null) {
//                requiredArgumentBuilder.suggests(SuggestionProviders.byId(this.id));
//            }
//
//            return requiredArgumentBuilder;
//        }
//
//        public void write(PacketByteBuf buf) {
//            buf.writeString(this.name);
//            write(buf, this.properties);
//            if (this.id != null) {
//                buf.writeIdentifier(this.id);
//            }
//
//        }
//
//        private static <A extends ArgumentType<?>> void write(PacketByteBuf buf, ArgumentSerializer.ArgumentTypeProperties<A> properties) {
//            write(buf, properties.getSerializer(), properties);
//        }
//
//        private static <A extends ArgumentType<?>, T extends ArgumentSerializer.ArgumentTypeProperties<A>> void write(PacketByteBuf buf, ArgumentSerializer<A, T> serializer, ArgumentSerializer.ArgumentTypeProperties<A> properties) {
//            buf.writeVarInt(Registry.COMMAND_ARGUMENT_TYPE.getRawId(serializer));
//            serializer.writePacket(properties, buf);
//        }
//    }
//
//    private static class LiteralNode implements SuggestableNode {
//        private final String literal;
//
//        LiteralNode(String literal) {
//            this.literal = literal;
//        }
//
//        public ArgumentBuilder<CommandSource, ?> createArgumentBuilder(CommandRegistryAccess commandRegistryAccess) {
//            return LiteralArgumentBuilder.literal(this.literal);
//        }
//
//        public void write(PacketByteBuf buf) {
//            buf.writeString(this.literal);
//        }
//    }
//
//    private static class CommandTree {
//        private final CommandRegistryAccess commandRegistryAccess;
//        private final List<CommandNodeData> nodeDatas;
//        private final List<CommandNode<CommandSource>> nodes;
//
//        CommandTree(CommandRegistryAccess commandRegistryAccess, List<CommandNodeData> nodeDatas) {
//            this.commandRegistryAccess = commandRegistryAccess;
//            this.nodeDatas = nodeDatas;
//            ObjectArrayList<CommandNode<CommandSource>> objectArrayList = new ObjectArrayList();
//            objectArrayList.size(nodeDatas.size());
//            this.nodes = objectArrayList;
//        }
//
//        public CommandNode<CommandSource> getNode(int index) {
//            CommandNode<CommandSource> commandNode = (CommandNode)this.nodes.get(index);
//            if (commandNode != null) {
//                return commandNode;
//            } else {
//                CommandNodeData commandNodeData = (CommandNodeData)this.nodeDatas.get(index);
//                Object commandNode2;
//                if (commandNodeData.suggestableNode == null) {
//                    commandNode2 = new RootCommandNode();
//                } else {
//                    ArgumentBuilder<CommandSource, ?> argumentBuilder = commandNodeData.suggestableNode.createArgumentBuilder(this.commandRegistryAccess);
//                    if ((commandNodeData.flags & 8) != 0) {
//                        argumentBuilder.redirect(this.getNode(commandNodeData.redirectNodeIndex));
//                    }
//
//                    if ((commandNodeData.flags & 4) != 0) {
//                        argumentBuilder.executes((context) -> {
//                            return 0;
//                        });
//                    }
//
//                    commandNode2 = argumentBuilder.build();
//                }
//
//                this.nodes.set(index, commandNode2);
//                int[] var10 = commandNodeData.childNodeIndices;
//                int var6 = var10.length;
//
//                for(int var7 = 0; var7 < var6; ++var7) {
//                    int i = var10[var7];
//                    CommandNode<CommandSource> commandNode3 = this.getNode(i);
//                    if (!(commandNode3 instanceof RootCommandNode)) {
//                        ((CommandNode)commandNode2).addChild(commandNode3);
//                    }
//                }
//
//                return (CommandNode)commandNode2;
//            }
//        }
//    }

}
