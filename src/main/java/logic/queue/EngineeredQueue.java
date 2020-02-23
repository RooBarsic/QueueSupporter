package logic.queue;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import javax.swing.Timer;

/**
 * Наша спроектированная очередь
 * Author: Farrukh Karimov
 * Modification Date: 12.02.2020
 */
public class EngineeredQueue<T>{
    private int idCounter = 0;
    private int taskExecutionDelayMs = 3 * 60 * 1000;
    private Timer releasingTimer;
    @NotNull
    private final String queueName;
    private final ConcurrentHashMap<T, Integer> customerIdByCustomer = new ConcurrentHashMap<>();
    private final ConcurrentSkipListSet<Integer> orderedIds = new ConcurrentSkipListSet<>();

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
            orderedIds.remove(
                    orderedIds.first()
            );
        });
    }

    @NotNull
    public String getQueueName(){
        return queueName;
    }

    public boolean contains(T var){
        return orderedIds.contains(
                customerIdByCustomer.getOrDefault(var, 0)
        );
    }

    /**
     * Метод для добавления обекта в очередь
     * @param var принимает на вход обект
     * @return возвращает true если элемент успешно добавлен, false если такой елемент уже существует
     */
    public boolean add(@NotNull final T var){
        if(!contains(var)){
            idCounter++;
            customerIdByCustomer.put(var, idCounter);
            orderedIds.add(idCounter);
            if(orderedIds.size() == 1){
                releasingTimer.start();
            }
            return true;
        }
        return false;
    }

    /**
     * Метод для удаления обекта из очереди
     * @param var принимает на вход обект
     * @return возвращает true если такой обект был в очереди и мы только что его удалили, false иначе
     */
    public boolean remove(@NotNull final T var){
        if(contains(var)){
            final int varIndex = orderedIds
                    .headSet(
                            customerIdByCustomer.get(var),
                            true
                    ).size();
            orderedIds.remove(customerIdByCustomer.get(var));
            if(orderedIds.size() == 0){
                releasingTimer.stop();
            } else if(varIndex == 1){
                releasingTimer.restart();
            }
            return true;
        }
        return false;
    }

    /**
     * Метод для поиска позиции обекта в очереди
     * @param var принимает на вход обект
     * @return возвращает позицию обекта в очереди - если в очереди есть такой обект ( иначе возвращает -1 )
     */
    public int findIndex(@NotNull final T var){
        return orderedIds
                .headSet(
                        customerIdByCustomer.getOrDefault(var, 0),
                        true
                ).size() - 1;
    }

    /**
     * @return возвращает количество элементов в очереди
     */
    public int size(){
        return orderedIds.size();
    }

    /**
     * Метод для очистки очереди
     */
    public void clear(){
        orderedIds.clear();
    }
}