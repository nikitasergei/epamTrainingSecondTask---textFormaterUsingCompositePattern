package by.epam.nikitaSergei.entity;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Operator extends Thread {
    final static Logger logger = Logger.getLogger(Operator.class);

    private static AtomicInteger count = new AtomicInteger(0);
    private int orderNum;
    private CallCenter callCenter;
    private Lock lock = new ReentrantLock();


    public Operator(CallCenter callCenter) {
        this.callCenter = callCenter;
        this.orderNum = count.incrementAndGet();
        logger.info("Operator" + this.orderNum + " came to work");
        this.start();
    }

    /**
     * Operator checks client's queue and if it's empty he wait for new client, else he try to get not null client from queue
     * and solve his problem (method solveClientProblem)
     */
    @Override
    public void run() {
        while (true) {
            if (!this.callCenter.getClientQueue().isEmpty()) {
                Client client = this.callCenter.getClientQueue().poll();
                if (client != null) {
                    solveClientProblem(client);
                }
            }
            this.lock.lock();
        }
    }

    /**
     * Operator make {@param client} locked and call method sleep() for 5 seconds (emulates that they are both busy
     * by solving a client's problem). After that client will be unlocked.
     *
     * @param client - object of Client.class, which call to CallCenter
     */
    private void solveClientProblem(Client client) {
        client.getLock().lock();
        logger.info("Client" + client.getOrderNum() + " is locked by operator" + this.orderNum);
        try {
            sleep(5000);
        } catch (InterruptedException exception) {
            logger.error(exception);
        }
        client.getLock().unlock();
        logger.info("Client's" + client.getOrderNum() + " problem is resolved!");
    }


    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public CallCenter getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(CallCenter callCenter) {
        this.callCenter = callCenter;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}

