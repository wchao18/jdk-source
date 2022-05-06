/*
 * Copyright (c) 2012, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.graalvm.compiler.hotspot.stubs;

import static org.graalvm.compiler.hotspot.GraalHotSpotVMConfig.INJECTED_VMCONFIG;
import static org.graalvm.compiler.hotspot.nodes.JumpToExceptionHandlerInCallerNode.jumpToExceptionHandlerInCaller;
import static org.graalvm.compiler.hotspot.replacements.HotSpotReplacementsUtil.registerAsWord;
import static org.graalvm.compiler.hotspot.stubs.ExceptionHandlerStub.checkExceptionNotNull;
import static org.graalvm.compiler.hotspot.stubs.ExceptionHandlerStub.checkNoExceptionInThread;
import static org.graalvm.compiler.hotspot.stubs.StubUtil.cAssertionsEnabled;
import static org.graalvm.compiler.hotspot.stubs.StubUtil.decipher;
import static org.graalvm.compiler.hotspot.stubs.StubUtil.newDescriptor;
import static org.graalvm.compiler.hotspot.stubs.StubUtil.printf;

import org.graalvm.compiler.api.replacements.Fold;
import org.graalvm.compiler.api.replacements.Fold.InjectedParameter;
import org.graalvm.compiler.api.replacements.Snippet;
import org.graalvm.compiler.api.replacements.Snippet.ConstantParameter;
import org.graalvm.compiler.core.common.spi.ForeignCallDescriptor;
import org.graalvm.compiler.debug.Assertions;
import org.graalvm.compiler.graph.Node.ConstantNodeParameter;
import org.graalvm.compiler.graph.Node.NodeIntrinsic;
import org.graalvm.compiler.hotspot.GraalHotSpotVMConfig;
import org.graalvm.compiler.hotspot.HotSpotForeignCallLinkage;
import org.graalvm.compiler.hotspot.meta.HotSpotProviders;
import org.graalvm.compiler.hotspot.nodes.StubForeignCallNode;
import org.graalvm.compiler.nodes.UnwindNode;
import org.graalvm.compiler.options.OptionValues;
import org.graalvm.compiler.word.Word;
import org.graalvm.word.Pointer;

import jdk.vm.ci.code.Register;

/**
 * Stub called by an {@link UnwindNode}. This stub executes in the frame of the method throwing an
 * exception and completes by jumping to the exception handler in the calling frame.
 */
public class UnwindExceptionToCallerStub extends SnippetStub {

    public UnwindExceptionToCallerStub(OptionValues options, HotSpotProviders providers, HotSpotForeignCallLinkage linkage) {
        super("unwindExceptionToCaller", options, providers, linkage);
    }

    /**
     * The current frame is unwound by this stub. Therefore, it does not need to save any registers
     * as HotSpot uses a caller save convention.
     */
    @Override
    public boolean preservesRegisters() {
        return false;
    }

    @Override
    protected Object getConstantParameterValue(int index, String name) {
        if (index == 2) {
            return providers.getRegisters().getThreadRegister();
        }
        assert index == 3;
        return options;
    }

    @Snippet
    private static void unwindExceptionToCaller(Object exception, Word returnAddress, @ConstantParameter Register threadRegister, @ConstantParameter OptionValues options) {
        Pointer exceptionOop = Word.objectToTrackedPointer(exception);
        if (logging(options)) {
            printf("unwinding exception %p (", exceptionOop.rawValue());
            decipher(exceptionOop.rawValue());
            printf(") at %p (", returnAddress.rawValue());
            decipher(returnAddress.rawValue());
            printf(")\n");
        }
        Word thread = registerAsWord(threadRegister);
        checkNoExceptionInThread(thread, assertionsEnabled(INJECTED_VMCONFIG));
        checkExceptionNotNull(assertionsEnabled(INJECTED_VMCONFIG), exception);

        Word handlerInCallerPc = exceptionHandlerForReturnAddress(EXCEPTION_HANDLER_FOR_RETURN_ADDRESS, thread, returnAddress);

        if (logging(options)) {
            printf("handler for exception %p at return address %p is at %p (", exceptionOop.rawValue(), returnAddress.rawValue(), handlerInCallerPc.rawValue());
            decipher(handlerInCallerPc.rawValue());
            printf(")\n");
        }

        jumpToExceptionHandlerInCaller(handlerInCallerPc, exception, returnAddress);
    }

    @Fold
    static boolean logging(OptionValues options) {
        return StubOptions.TraceUnwindStub.getValue(options);
    }

    /**
     * Determines if either Java assertions are enabled for Graal or if this is a HotSpot build
     * where the ASSERT mechanism is enabled.
     */
    @Fold
    @SuppressWarnings("all")
    static boolean assertionsEnabled(@InjectedParameter GraalHotSpotVMConfig config) {
        return Assertions.assertionsEnabled() || cAssertionsEnabled(config);
    }

    public static final ForeignCallDescriptor EXCEPTION_HANDLER_FOR_RETURN_ADDRESS = newDescriptor(UnwindExceptionToCallerStub.class, "exceptionHandlerForReturnAddress", Word.class, Word.class,
                    Word.class);

    @NodeIntrinsic(value = StubForeignCallNode.class)
    public static native Word exceptionHandlerForReturnAddress(@ConstantNodeParameter ForeignCallDescriptor exceptionHandlerForReturnAddress, Word thread, Word returnAddress);
}
