/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.tasks.testing;

import org.gradle.api.NonNullApi;
import org.gradle.api.internal.tasks.testing.operations.TestListenerBuildOperationAdapter;
import org.gradle.api.tasks.testing.TestEventReporterFactory;
import org.gradle.internal.event.ListenerManager;
import org.gradle.internal.operations.BuildOperationIdFactory;
import org.gradle.internal.operations.BuildOperationListenerManager;
import org.gradle.internal.service.Provides;
import org.gradle.internal.service.ServiceRegistration;
import org.gradle.internal.service.ServiceRegistrationProvider;

@NonNullApi
public class TestingBuildSessionScopeServices implements ServiceRegistrationProvider {
    @Provides
    TestListenerBuildOperationAdapter createTestListenerBuildOperationAdapter(BuildOperationListenerManager listener, BuildOperationIdFactory buildOperationIdFactory) {
        return new TestListenerBuildOperationAdapter(listener.getBroadcaster(), buildOperationIdFactory);
    }

    @Provides
    TestEventReporterFactory createTestEventService(TestListenerBuildOperationAdapter testListenerBuildOperationAdapter) {
        return new DefaultTestEventReporterFactory(testListenerBuildOperationAdapter);
    }

    void configure(ServiceRegistration serviceRegistration, ListenerManager listenerManager, TestListenerBuildOperationAdapter testListenerBuildOperationAdapter) {
        listenerManager.addListener(testListenerBuildOperationAdapter);
    }
}
