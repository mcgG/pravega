/**
 * Copyright (c) 2017 Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.client.stream.impl;

import io.pravega.client.stream.Serializer;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * An implementation of {@link Serializer} that converts byte arrays.
 */
public class ByteArraySerializer implements Serializer<byte[]>, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public ByteBuffer serialize(byte[] value) {
        return ByteBuffer.wrap(value);
    }

    @Override
    public byte[] deserialize(ByteBuffer serializedValue) {
        byte[] result = new byte[serializedValue.remaining()];
        serializedValue.get(result);
        return result;
    }
}
