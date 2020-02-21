package logic.queue;


import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 * Наша спроектированная очередь
 * Author: Farrukh Karimov
 * Modification Date: 12.02.2020
 */
public class EngineeredQueue<T> {
    private int taskExecutionDelayMs = 1 * 30 * 1000;
    private int count = 0;
    @NotNull
    private String queueName;
    private final Timer releasingTimer;
    private final List<Integer> customersIdsList = new ArrayList<>();
    private final Map<T, Integer> idByCustomer = new HashMap<>();
    private final Map<Integer, T> customerById = new HashMap<>();

    /**
     * Конструктор - создание новой очереди с определенным именем и дефолтной задержкой на обслуживании
     * @param queueName - принимает имя очереди
     */
    public EngineeredQueue(@NotNull final String queueName){
        this.queueName = queueName;
        releasingTimer = new Timer(taskExecutionDelayMs, actionEvent -> {
            synchronized (customersIdsList){
                if(customersIdsList.size() > 0){
                    synchronized (idByCustomer){
                        synchronized (customerById) {
                            remove(customerById.get(customersIdsList.get(0)));
                            try {
                                System.out.println(" thread going to sleep :: ");
                                Thread.sleep(60 * 1000);
                                System.out.println(" thread woke UP :: ");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Конструктор - создание новой очереди с заданным именем и задержкой на обслуживании
     * @param queueName - принимает имя очереди
     */
    public EngineeredQueue(@NotNull final String queueName, final int taskExecutionDelayMs){
        this.queueName = queueName;
        this.taskExecutionDelayMs = taskExecutionDelayMs;
        releasingTimer = new Timer(taskExecutionDelayMs, actionEvent -> {
            synchronized (customersIdsList){
                if(customersIdsList.size() > 0){
                    synchronized (idByCustomer){
                        synchronized (customerById) {
                            remove(customerById.get(customersIdsList.get(0)));
                            try {
                                System.out.println(" thread going to sleep :: ");
                                Thread.sleep(60 * 1000);
                                System.out.println(" thread woke UP :: ");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
        if(!idByCustomer.containsKey(var)){
            count++;
            idByCustomer.put(var, count);
            customerById.put(count, var);
            customersIdsList.add(count);
            if(customersIdsList.size() == 1){
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
        if(idByCustomer.containsKey(var)){
            final int varId = idByCustomer.get(var);
            final int varIndex = customerPositionInQueue(varId);
            customersIdsList.remove(varIndex);
            idByCustomer.remove(var);
            customerById.remove(varId);
            if(customersIdsList.size() == 0){
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
        if(idByCustomer.containsKey(var)){
            final int customerId = idByCustomer.get(var);
            return customerPositionInQueue(customerId);
        }
        return -1;
    }

    private int customerPositionInQueue(final int customerId){
        int left = 0;
        int right = customersIdsList.size() - 1;
        while(left + 1 < right){
            int mid = (left + right) / 2;
            if(customersIdsList.get(mid) < customerId){
                left = mid;
            } else {
                right = mid;
            }
        }
        if(customersIdsList.get(left) == customerId){
            return left;
        }
        return right;
    }

    /**
     * @return возвращает количество элементов в очереди
     */
    public int size(){
        return customersIdsList.size();
    }

    /**
     * Метод для очистки очереди
     */
    public void clear(){
        count = 0;
        customersIdsList.clear();
        idByCustomer.clear();
    }
}