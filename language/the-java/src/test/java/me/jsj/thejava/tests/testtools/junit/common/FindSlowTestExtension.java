package me.jsj.thejava.tests.testtools.junit.common;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.annotation.Annotation;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
//    private static final long THRESHOLD = 1000L;

    private long THRESHOLD;

    public FindSlowTestExtension(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        Long startTime = store.remove("START_TIME", long.class);

        String testMethodName = context.getRequiredTestMethod().getName();
        Annotation annotation = context.getRequiredTestMethod().getAnnotation(SlowTest.class);

        long duration = System.currentTimeMillis() - startTime;
        if (duration > THRESHOLD && annotation == null) {
            System.out.printf("Please consider mark method [%s] with @SlowTest. \n", testMethodName);
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }
}
