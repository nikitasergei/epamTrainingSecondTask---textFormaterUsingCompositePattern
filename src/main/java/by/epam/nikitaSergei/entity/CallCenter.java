package by.epam.nikitaSergei.entity;

import org.apache.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class CallCenter {
    final static Logger logger = Logger.getLogger(CallCenter.class);

    //it's a queue of clients. When new client call to callCenter, he gets into clientQueue if this queue is empty
    private Queue<Client> clientQueue;

    //it's a queue of operators. When operator is added to operatorsQueue, and callCenter can to check is free operator present.
    private Queue<Operator> operatorsQueue;
    private int clientQueueQuantity;
    private int numberOfOperators;


    public CallCenter(int numberOfOperators, int clientQueueQuantity) {
        this.clientQueueQuantity = clientQueueQuantity;
        this.numberOfOperators = numberOfOperators;
        this.clientQueue = new ArrayBlockingQueue<>(clientQueueQuantity);
        this.operatorsQueue = new ArrayBlockingQueue<>(numberOfOperators);
        for (int index = 0; index < numberOfOperators; index++)
            this.createOperators();
        logger.info("Call Center is ready to work");
    }

    public Client createClient() {
        return new Client(this);
    }

    public void createOperators() {
        Operator operator = new Operator(this);
        this.getOperatorsQueue().add(operator);

    }

    public Queue<Client> getClientQueue() {
        return clientQueue;
    }

    private void setClientQueue(Queue<Client> clientQueue) {
        this.clientQueue = clientQueue;
    }

    public Queue<Operator> getOperatorsQueue() {
        return operatorsQueue;
    }

    private void setOperatorsQueue(Queue<Operator> operatorsQueue) {
        this.operatorsQueue = operatorsQueue;
    }

    public int getNumberOfOperators() {
        return numberOfOperators;
    }

    public void setNumberOfOperators(int numberOfOperators) {
        this.numberOfOperators = numberOfOperators;
    }
}


