package by.epam.nikitaSergei.entity;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client extends Thread {
    final static Logger logger = Logger.getLogger(Client.class);

    private static AtomicInteger count = new AtomicInteger(0);
    private int orderNum;
    private CallCenter callCenter;
    private Lock lock = new ReentrantLock();

    public Client(CallCenter callCenter) {
        this.callCenter = callCenter;
        this.orderNum = count.incrementAndGet();
        logger.info("Client" + this.orderNum + " created");
        this.start();
    }

    /**
     * Method starts to run when new client call to callCenter. CallCenter try to add client to queue and checks
     * is queue empty? If queue is empty callCenter take free operator from operatorsQueue and give him a task
     * to solve client's problem.
     */
    @Override
    public void run() {
        logger.info("Client" + this.orderNum + " call to Call Center");
        if (addClientToQueue()) {
            logger.info("Client" + this.orderNum + " added to clientQueue!");
            if (!isOperatorsQueueEmpty()) {
                Operator operator = this.callCenter.getOperatorsQueue().peek();
            }
        } else {
            logger.error("The clientQueue is full of clients. Please, call back later");
        }
    }

    /**
     * @return true if client was added to callCenter's clientQueue successfully, else {@return false}
     */
    private boolean addClientToQueue() {
        return this.callCenter.getClientQueue().add(this);
    }

    /**
     * Method checks is free operators in operatorsQueue present
     *
     * @return true if present, {@return false} - if not present
     */
    private boolean isOperatorsQueueEmpty() {
        return this.callCenter.getOperatorsQueue().isEmpty();
    }

    public int getOrderNum() {
        return orderNum;
    }

    private void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public CallCenter getCallCenter() {
        return callCenter;
    }

    private void setCallCenter(CallCenter callCenter) {
        this.callCenter = callCenter;
    }

    public Lock getLock() {
        return lock;
    }

    private void setLock(Lock lock) {
        this.lock = lock;
    }
}
