package logic.queue;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ScheduledEngineeredQueueImpl<T> extends TimerTask implements EngineeredQueue<T>{
    private int taskExecutionDelayMs = 30000;
    @NotNull
    private final String queueName;
    private final Timer timer = new Timer();
    private final List<T> customersList = new ArrayList<>();
    private final Set<T> customersSet = new HashSet<>();

    public ScheduledEngineeredQueueImpl(@NotNull final String queueName){
        this.queueName = queueName;
        timer.schedule(this, 0, taskExecutionDelayMs);
    }

    ScheduledEngineeredQueueImpl(@NotNull final String queueName, int taskExecutionDelayMs ){
        this.queueName = queueName;
        this.taskExecutionDelayMs = taskExecutionDelayMs;
        timer.schedule(this, 0, taskExecutionDelayMs);
    }

    public void resetDelay(int taskExecutionDelayMs){
        this.taskExecutionDelayMs = taskExecutionDelayMs;
        timer.cancel();
        timer.schedule(this, 0, taskExecutionDelayMs);
    }

    @Override
    public void run() {
        synchronized (customersList){
            if(customersList.size() > 0) {
                synchronized (customersSet) {
                    T servedItem = customersList.remove(0);
                    customersSet.remove(servedItem);
                    System.out.println("!!!~ served customer : " + servedItem.toString());
                }
            }
        }
    }

    @Override
    public @NotNull String getQueueName() {
        return queueName;
    }

    @Override
    public boolean add(@NotNull T var) {
        if(customersSet.add(var)){
            customersList.add(var);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(@NotNull T var) {
        if(customersSet.remove(var)){
            return customersList.remove(var);
        }
        return false;
    }

    @Override
    public int findIndex(@NotNull T var) {
        if(customersSet.contains(var)){
            return customersList.indexOf(var) + 1;
        }
        return -1;
    }

    @Override
    public int size() {
        return customersList.size();
    }

    @Override
    public void clear() {
        customersList.clear();
        customersSet.clear();
    }
}
