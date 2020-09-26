/**
 * Copyright (c) 2020 QingLang, Inc. <baisui@qlangtech.com>
 *
 * This program is free software: you can use, redistribute, and/or modify
 * it under the terms of the GNU Affero General Public License, version 3
 * or later ("AGPL"), as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.qlangtech.tis.rpc.grpc.log.stream;

/**
 * Protobuf type {@code stream.PIndexBackFlowPhaseStatus}
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2020/09/25
 */
public final class PIndexBackFlowPhaseStatus extends com.google.protobuf.GeneratedMessageV3 implements // @@protoc_insertion_point(message_implements:stream.PIndexBackFlowPhaseStatus)
PIndexBackFlowPhaseStatusOrBuilder {

    private static final long serialVersionUID = 0L;

    // Use PIndexBackFlowPhaseStatus.newBuilder() to construct.
    private PIndexBackFlowPhaseStatus(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private PIndexBackFlowPhaseStatus() {
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private PIndexBackFlowPhaseStatus(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new java.lang.NullPointerException();
        }
        int mutable_bitField0_ = 0;
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder();
        try {
            boolean done = false;
            while (!done) {
                int tag = input.readTag();
                switch(tag) {
                    case 0:
                        done = true;
                        break;
                    case 10:
                        {
                            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                                nodesStatus_ = com.google.protobuf.MapField.newMapField(NodesStatusDefaultEntryHolder.defaultEntry);
                                mutable_bitField0_ |= 0x00000001;
                            }
                            com.google.protobuf.MapEntry<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> nodesStatus__ = input.readMessage(NodesStatusDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                            nodesStatus_.getMutableMap().put(nodesStatus__.getKey(), nodesStatus__.getValue());
                            break;
                        }
                    default:
                        {
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                }
            }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
        } finally {
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        return com.qlangtech.tis.rpc.grpc.log.stream.LogCollectorProto.internal_static_stream_PIndexBackFlowPhaseStatus_descriptor;
    }

    @SuppressWarnings({ "rawtypes" })
    @java.lang.Override
    protected com.google.protobuf.MapField internalGetMapField(int number) {
        switch(number) {
            case 1:
                return internalGetNodesStatus();
            default:
                throw new RuntimeException("Invalid map field number: " + number);
        }
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return com.qlangtech.tis.rpc.grpc.log.stream.LogCollectorProto.internal_static_stream_PIndexBackFlowPhaseStatus_fieldAccessorTable.ensureFieldAccessorsInitialized(com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus.class, com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus.Builder.class);
    }

    public static final int NODESSTATUS_FIELD_NUMBER = 1;

    private static final class NodesStatusDefaultEntryHolder {

        static final com.google.protobuf.MapEntry<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> defaultEntry = com.google.protobuf.MapEntry.<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus>newDefaultInstance(com.qlangtech.tis.rpc.grpc.log.stream.LogCollectorProto.internal_static_stream_PIndexBackFlowPhaseStatus_NodesStatusEntry_descriptor, com.google.protobuf.WireFormat.FieldType.STRING, "", com.google.protobuf.WireFormat.FieldType.MESSAGE, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus.getDefaultInstance());
    }

    private com.google.protobuf.MapField<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> nodesStatus_;

    private com.google.protobuf.MapField<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> internalGetNodesStatus() {
        if (nodesStatus_ == null) {
            return com.google.protobuf.MapField.emptyMapField(NodesStatusDefaultEntryHolder.defaultEntry);
        }
        return nodesStatus_;
    }

    public int getNodesStatusCount() {
        return internalGetNodesStatus().getMap().size();
    }

    /**
     * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
     */
    public boolean containsNodesStatus(java.lang.String key) {
        if (key == null) {
            throw new java.lang.NullPointerException();
        }
        return internalGetNodesStatus().getMap().containsKey(key);
    }

    /**
     * Use {@link #getNodesStatusMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> getNodesStatus() {
        return getNodesStatusMap();
    }

    /**
     * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
     */
    public java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> getNodesStatusMap() {
        return internalGetNodesStatus().getMap();
    }

    /**
     * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
     */
    public com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus getNodesStatusOrDefault(java.lang.String key, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus defaultValue) {
        if (key == null) {
            throw new java.lang.NullPointerException();
        }
        java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> map = internalGetNodesStatus().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    /**
     * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
     */
    public com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus getNodesStatusOrThrow(java.lang.String key) {
        if (key == null) {
            throw new java.lang.NullPointerException();
        }
        java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> map = internalGetNodesStatus().getMap();
        if (!map.containsKey(key)) {
            throw new java.lang.IllegalArgumentException();
        }
        return map.get(key);
    }

    private byte memoizedIsInitialized = -1;

    @java.lang.Override
    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1)
            return true;
        if (isInitialized == 0)
            return false;
        memoizedIsInitialized = 1;
        return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
        com.google.protobuf.GeneratedMessageV3.serializeStringMapTo(output, internalGetNodesStatus(), NodesStatusDefaultEntryHolder.defaultEntry, 1);
        unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1)
            return size;
        size = 0;
        for (java.util.Map.Entry<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> entry : internalGetNodesStatus().getMap().entrySet()) {
            com.google.protobuf.MapEntry<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> nodesStatus__ = NodesStatusDefaultEntryHolder.defaultEntry.newBuilderForType().setKey(entry.getKey()).setValue(entry.getValue()).build();
            size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, nodesStatus__);
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus)) {
            return super.equals(obj);
        }
        com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus other = (com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus) obj;
        if (!internalGetNodesStatus().equals(other.internalGetNodesStatus()))
            return false;
        if (!unknownFields.equals(other.unknownFields))
            return false;
        return true;
    }

    @java.lang.Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        if (!internalGetNodesStatus().getMap().isEmpty()) {
            hash = (37 * hash) + NODESSTATUS_FIELD_NUMBER;
            hash = (53 * hash) + internalGetNodesStatus().hashCode();
        }
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(java.io.InputStream input) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }

    /**
     * Protobuf type {@code stream.PIndexBackFlowPhaseStatus}
     */
    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements // @@protoc_insertion_point(builder_implements:stream.PIndexBackFlowPhaseStatus)
    com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatusOrBuilder {

        public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
            return com.qlangtech.tis.rpc.grpc.log.stream.LogCollectorProto.internal_static_stream_PIndexBackFlowPhaseStatus_descriptor;
        }

        @SuppressWarnings({ "rawtypes" })
        protected com.google.protobuf.MapField internalGetMapField(int number) {
            switch(number) {
                case 1:
                    return internalGetNodesStatus();
                default:
                    throw new RuntimeException("Invalid map field number: " + number);
            }
        }

        @SuppressWarnings({ "rawtypes" })
        protected com.google.protobuf.MapField internalGetMutableMapField(int number) {
            switch(number) {
                case 1:
                    return internalGetMutableNodesStatus();
                default:
                    throw new RuntimeException("Invalid map field number: " + number);
            }
        }

        @java.lang.Override
        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return com.qlangtech.tis.rpc.grpc.log.stream.LogCollectorProto.internal_static_stream_PIndexBackFlowPhaseStatus_fieldAccessorTable.ensureFieldAccessorsInitialized(com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus.class, com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus.Builder.class);
        }

        // Construct using com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus.newBuilder()
        private Builder() {
            maybeForceBuilderInitialization();
        }

        private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
            }
        }

        @java.lang.Override
        public Builder clear() {
            super.clear();
            internalGetMutableNodesStatus().clear();
            return this;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
            return com.qlangtech.tis.rpc.grpc.log.stream.LogCollectorProto.internal_static_stream_PIndexBackFlowPhaseStatus_descriptor;
        }

        @java.lang.Override
        public com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus getDefaultInstanceForType() {
            return com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus.getDefaultInstance();
        }

        @java.lang.Override
        public com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus build() {
            com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        @java.lang.Override
        public com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus buildPartial() {
            com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus result = new com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus(this);
            int from_bitField0_ = bitField0_;
            result.nodesStatus_ = internalGetNodesStatus();
            result.nodesStatus_.makeImmutable();
            onBuilt();
            return result;
        }

        @java.lang.Override
        public Builder clone() {
            return super.clone();
        }

        @java.lang.Override
        public Builder setField(com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
            return super.setField(field, value);
        }

        @java.lang.Override
        public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
            return super.clearField(field);
        }

        @java.lang.Override
        public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
            return super.clearOneof(oneof);
        }

        @java.lang.Override
        public Builder setRepeatedField(com.google.protobuf.Descriptors.FieldDescriptor field, int index, java.lang.Object value) {
            return super.setRepeatedField(field, index, value);
        }

        @java.lang.Override
        public Builder addRepeatedField(com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
            return super.addRepeatedField(field, value);
        }

        @java.lang.Override
        public Builder mergeFrom(com.google.protobuf.Message other) {
            if (other instanceof com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus) {
                return mergeFrom((com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus other) {
            if (other == com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus.getDefaultInstance())
                return this;
            internalGetMutableNodesStatus().mergeFrom(other.internalGetNodesStatus());
            this.mergeUnknownFields(other.unknownFields);
            onChanged();
            return this;
        }

        @java.lang.Override
        public final boolean isInitialized() {
            return true;
        }

        @java.lang.Override
        public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
            com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage = (com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        private int bitField0_;

        private com.google.protobuf.MapField<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> nodesStatus_;

        private com.google.protobuf.MapField<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> internalGetNodesStatus() {
            if (nodesStatus_ == null) {
                return com.google.protobuf.MapField.emptyMapField(NodesStatusDefaultEntryHolder.defaultEntry);
            }
            return nodesStatus_;
        }

        private com.google.protobuf.MapField<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> internalGetMutableNodesStatus() {
            onChanged();
            ;
            if (nodesStatus_ == null) {
                nodesStatus_ = com.google.protobuf.MapField.newMapField(NodesStatusDefaultEntryHolder.defaultEntry);
            }
            if (!nodesStatus_.isMutable()) {
                nodesStatus_ = nodesStatus_.copy();
            }
            return nodesStatus_;
        }

        public int getNodesStatusCount() {
            return internalGetNodesStatus().getMap().size();
        }

        /**
         * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
         */
        public boolean containsNodesStatus(java.lang.String key) {
            if (key == null) {
                throw new java.lang.NullPointerException();
            }
            return internalGetNodesStatus().getMap().containsKey(key);
        }

        /**
         * Use {@link #getNodesStatusMap()} instead.
         */
        @java.lang.Deprecated
        public java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> getNodesStatus() {
            return getNodesStatusMap();
        }

        /**
         * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
         */
        public java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> getNodesStatusMap() {
            return internalGetNodesStatus().getMap();
        }

        /**
         * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
         */
        public com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus getNodesStatusOrDefault(java.lang.String key, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus defaultValue) {
            if (key == null) {
                throw new java.lang.NullPointerException();
            }
            java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> map = internalGetNodesStatus().getMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        /**
         * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
         */
        public com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus getNodesStatusOrThrow(java.lang.String key) {
            if (key == null) {
                throw new java.lang.NullPointerException();
            }
            java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> map = internalGetNodesStatus().getMap();
            if (!map.containsKey(key)) {
                throw new java.lang.IllegalArgumentException();
            }
            return map.get(key);
        }

        public Builder clearNodesStatus() {
            internalGetMutableNodesStatus().getMutableMap().clear();
            return this;
        }

        /**
         * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
         */
        public Builder removeNodesStatus(java.lang.String key) {
            if (key == null) {
                throw new java.lang.NullPointerException();
            }
            internalGetMutableNodesStatus().getMutableMap().remove(key);
            return this;
        }

        /**
         * Use alternate mutation accessors instead.
         */
        @java.lang.Deprecated
        public java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> getMutableNodesStatus() {
            return internalGetMutableNodesStatus().getMutableMap();
        }

        /**
         * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
         */
        public Builder putNodesStatus(java.lang.String key, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus value) {
            if (key == null) {
                throw new java.lang.NullPointerException();
            }
            if (value == null) {
                throw new java.lang.NullPointerException();
            }
            internalGetMutableNodesStatus().getMutableMap().put(key, value);
            return this;
        }

        /**
         * <code>map&lt;string, .NodeBackflowStatus&gt; nodesStatus = 1;</code>
         */
        public Builder putAllNodesStatus(java.util.Map<java.lang.String, com.qlangtech.tis.rpc.grpc.log.common.NodeBackflowStatus> values) {
            internalGetMutableNodesStatus().getMutableMap().putAll(values);
            return this;
        }

        @java.lang.Override
        public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.setUnknownFields(unknownFields);
        }

        @java.lang.Override
        public final Builder mergeUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }
        // @@protoc_insertion_point(builder_scope:stream.PIndexBackFlowPhaseStatus)
    }

    // @@protoc_insertion_point(class_scope:stream.PIndexBackFlowPhaseStatus)
    private static final com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus DEFAULT_INSTANCE;

    static {
        DEFAULT_INSTANCE = new com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus();
    }

    public static com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PIndexBackFlowPhaseStatus> PARSER = new com.google.protobuf.AbstractParser<PIndexBackFlowPhaseStatus>() {

        @java.lang.Override
        public PIndexBackFlowPhaseStatus parsePartialFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
            return new PIndexBackFlowPhaseStatus(input, extensionRegistry);
        }
    };

    public static com.google.protobuf.Parser<PIndexBackFlowPhaseStatus> parser() {
        return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PIndexBackFlowPhaseStatus> getParserForType() {
        return PARSER;
    }

    @java.lang.Override
    public com.qlangtech.tis.rpc.grpc.log.stream.PIndexBackFlowPhaseStatus getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
