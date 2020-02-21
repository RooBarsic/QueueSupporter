package logic.queue;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.Timer;

/**
 * Наша спроектированная очередь
 * Author: Farrukh Karimov
 * Modification Date: 12.02.2020
 */
public class EngineeredQueue<T>{
    private int taskExecutionDelayMs = 3 * 60 * 1000;
    @NotNull
    private final String queueName;
    int[] aarr = new int[235235];
    private Timer releasingTimer = null;
    private final List<T> customersList = new ArrayList<>();
    private final Set<T> customersSet = new HashSet<>();
    private AtomicBoolean autoRemovingIsRunning = new AtomicBoolean();

    /**
     * Конструктор - создание новой очереди с определенным именем и дефолтной задержкой на обслуживании
     * @param queueName - принимает имя очереди
     */
    public EngineeredQueue(@NotNull final String queueName){
        this.queueName = queueName;
        initTimer();
    }

    /**
     * Конструктор - создание новой очереди с заданным именем и задержкой на обслуживании
     * @param queueName - принимает имя очереди
     */
    public EngineeredQueue(@NotNull final String queueName, final int taskExecutionDelayMs){
        this.queueName = queueName;
        this.taskExecutionDelayMs = taskExecutionDelayMs;
        initTimer();
    }

    private void initTimer(){
        releasingTimer = new Timer(taskExecutionDelayMs, actionEvent -> {
            synchronized (customersList){
                if(customersList.size() > 0){
                    synchronized (customersSet){
                        boolean flag = autoRemovingIsRunning.get();
                        while(!autoRemovingIsRunning.compareAndSet(flag, true)){
                            flag = autoRemovingIsRunning.get();
                        }

                        customersSet.remove(customersList.remove(0));
                        if(customersSet.size() == 0) {
                            releasingTimer.stop();
                        }

                        flag = autoRemovingIsRunning.get();
                        while(!autoRemovingIsRunning.compareAndSet(flag, false)){
                            flag = autoRemovingIsRunning.get();
                        }
                    }
                }
            }
        });
    }

    @NotNull
    public String getQueueName(){
        return queueName;
    }

    /**
     * Метод для добавления обекта в очередь
     * @param var принимает на вход обект
     * @return возвращает true если элемент успешно добавлен, false если такой елемент уже существует
     */
    public boolean add(@NotNull final T var){
        while(autoRemovingIsRunning.get()){

        }
        if(customersSet.add(var)){
            customersList.add(var);
            if(customersList.size() == 1){
                releasingTimer.start();
            }
            return true;
        }
        return false;
    }

    /**
     * :TODO оптимизировать время работы, сейчай O(N)
     * Метод для удаления обекта из очереди
     * @param var принимает на вход обект
     * @return возвращает true если такой обект был в очереди и мы только что его удалили, false иначе
     */
    public boolean remove(@NotNull final T var){
        while(autoRemovingIsRunning.get()){

        }
        if(customersSet.remove(var)){
            final int varIndex = customersList.indexOf(var);
            customersList.remove(varIndex);
            if(customersSet.size() == 0){
                releasingTimer.stop();
            } else if(varIndex == 0){
                releasingTimer.restart();
            }
            return true;
        }
        return false;
    }

    /**
     * :TODO оптимизировать время работы, сейчай O(N)
     * Метод для поиска позиции обекта в очереди
     * @param var принимает на вход обект
     * @return возвращает позицию обекта в очереди - если в очереди есть такой обект ( иначе возвращает -1 )
     */
    public int findIndex(@NotNull final T var){
        while(autoRemovingIsRunning.get()){

        }
        if(customersSet.contains(var)){
            return customersList.indexOf(var) + 1;
        }
        return -1;
    }

    /**
     * @return возвращает количество элементов в очереди
     */
    public int size(){
        while(autoRemovingIsRunning.get()){

        }
        return customersList.size();
    }

    /**
     * Метод для очистки очереди
     */
    public void clear(){
        while(autoRemovingIsRunning.get()){

        }
        customersList.clear();
        customersSet.clear();
    }
}