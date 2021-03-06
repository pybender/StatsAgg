package com.pearson.statsagg.controller.threads;

import com.pearson.statsagg.utilities.InvokerThread;
import com.pearson.statsagg.utilities.Threads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeffrey Schmidt
 */
public class InternalStatsInvokerThread extends InvokerThread implements Runnable {
    
    private static final Logger logger = LoggerFactory.getLogger(InternalStatsInvokerThread.class.getName());
    
    private final long threadExecutorShutdownWaitTime_;
    
    public InternalStatsInvokerThread() {
        threadExecutorShutdownWaitTime_ = 1500;
    }
    
    @Override
    public void run() {

        synchronized (lockObject_) {
            while (continueRunning_) {
                threadExecutor_.execute(new InternalStatsThread());

                try {
                    lockObject_.wait(15000);
                }
                catch (Exception e) {}
            }
        }
        
        while (!threadExecutor_.isTerminated()) {
            Threads.sleepMilliseconds(100);
        }
        
        isShutdown_ = true;
    }
    
    @Override
    public long getThreadExecutorShutdownWaitTime() {
        return threadExecutorShutdownWaitTime_;
    }
    
}