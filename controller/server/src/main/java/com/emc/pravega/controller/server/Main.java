/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.emc.pravega.controller.server;

import static com.emc.pravega.controller.util.Config.HOST_STORE_TYPE;
import static com.emc.pravega.controller.util.Config.STREAM_STORE_TYPE;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.emc.pravega.controller.server.rpc.RPCServer;
import com.emc.pravega.controller.server.rpc.v1.ControllerServiceImpl;
import com.emc.pravega.controller.store.host.Host;
import com.emc.pravega.controller.store.host.HostControllerStore;
import com.emc.pravega.controller.store.host.HostStoreFactory;
import com.emc.pravega.controller.store.host.InMemoryHostControllerStoreConfig;
import com.emc.pravega.controller.store.stream.StreamMetadataStore;
import com.emc.pravega.controller.store.stream.StreamStoreFactory;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * Entry point of controller server.
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        // TODO: Will use hard-coded host to container mapping for this sprint
        // Read from a config file. This same information will be present on pravega hosts
        // TODO: remove temporary hard coding for the cluster and segment
        Map<Host, Set<Integer>> hostContainerMap = new HashMap<>();
        hostContainerMap.put(new Host("localhost", 12345), Sets.newHashSet(0));

        //1) LOAD configuration.
        log.info("Creating in-memory stream store");
        StreamMetadataStore streamStore = StreamStoreFactory.createStore(
                StreamStoreFactory.StoreType.valueOf(STREAM_STORE_TYPE), null);
        log.info("Creating in-memory host store");
        HostControllerStore hostStore = HostStoreFactory.createStore(HostStoreFactory.StoreType.valueOf(HOST_STORE_TYPE),
                new InMemoryHostControllerStoreConfig(hostContainerMap));

        //2) start the Server implementations.
        //2.1) start RPC server with v1 implementation. Enable other versions if required.
        log.info("Starting RPC server");
        RPCServer.start(new ControllerServiceImpl(streamStore, hostStore));
    }
}