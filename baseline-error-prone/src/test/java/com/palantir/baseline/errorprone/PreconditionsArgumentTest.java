/*
 * (c) Copyright 2020 Palantir Technologies Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.baseline.errorprone;

import com.google.errorprone.CompilationTestHelper;
import org.junit.jupiter.api.Test;

class PreconditionsArgumentTest {

    @Test
    void testSafe() {
        helper().addSourceLines(
                        "Test.java",
                        "import com.google.common.base.Preconditions;",
                        "import com.palantir.logsafe.SafeArg;",
                        "class Test {",
                        "    void f() {",
                        "        Exception e = new Exception();",
                        "        // BUG: Diagnostic contains: Arg was passed to",
                        "       Preconditions.checkArgument(true, \"msg\", SafeArg.of(\"name\", null));",
                        "    }",
                        "}")
                .doTest();
    }

    @Test
    void testUnsafe() {
        helper().addSourceLines(
                        "Test.java",
                        "import com.google.common.base.Preconditions;",
                        "import com.palantir.logsafe.UnsafeArg;",
                        "class Test {",
                        "    void f() {",
                        "        // BUG: Diagnostic contains: Arg was passed to",
                        "       Preconditions.checkArgument(true, \"msg\", UnsafeArg.of(\"name\", null));",
                        "    }",
                        "}")
                .doTest();
    }

    private CompilationTestHelper helper() {
        return CompilationTestHelper.newInstance(PreconditionsArgument.class, getClass());
    }
}
