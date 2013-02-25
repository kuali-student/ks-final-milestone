/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Daniel on 8/8/12
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KSThreadRunnerAfterTransactionSynchronization implements TransactionSynchronization {
    final static Logger LOGGER = Logger.getLogger(KSThreadRunnerAfterTransactionSynchronization.class);

    public static final String THREAD_LIST_KEY ="org.kuali.student.ThreadList";

    private static final KSThreadRunnerAfterTransactionSynchronization instance = new KSThreadRunnerAfterTransactionSynchronization();

    @Override
    public void suspend() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void flush() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void beforeCommit(boolean readOnly) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void beforeCompletion() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterCommit() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    //After the transaction has ended, run the threads that have been added to the ThreadList
    public void afterCompletion(int status) {
        List<Runnable> threads = (List<Runnable>) TransactionSynchronizationManager.getResource(THREAD_LIST_KEY);
        if(threads == null){
            LOGGER.error("The list of threads has not been initialized.");
        }else{
            //Execute each of the runners
            for(Iterator<Runnable> iter = threads.iterator(); iter.hasNext(); ){
                Runnable runner = iter.next();
                iter.remove();
                Thread thread = new Thread(runner);
                thread.start();
            }
        }
    }

    public static void runAfterTransactionCompletes(Runnable runnable){
        instance._runAfterTransactionCompletes(runnable);
    }

    private void _runAfterTransactionCompletes(Runnable runnable){
        if(!TransactionSynchronizationManager.isSynchronizationActive()){
            //If there is no transaction then just run normally although there might be a race condition
            //It is possible the thread will start before the transaction is complete so that persisted entities are not
            //in the DB in time for the thread to use
            Thread thread = new Thread(runnable);
            thread.start();
        }else{

            //Register this synchronization manager after the application context has started
            TransactionSynchronizationManager.registerSynchronization(this);

            List<Runnable> runnables = (List<Runnable>) TransactionSynchronizationManager.getResource(THREAD_LIST_KEY);

            if(runnables == null){
                runnables = new ArrayList<Runnable>();
                //Register the list of threads as a transactional resource
                TransactionSynchronizationManager.bindResource(THREAD_LIST_KEY, runnables);
            }

            runnables.add(runnable);
        }
    }
}
