package logic.queue;


import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Наша спроектированная очередь
 * Author: Farrukh Karimov
 * Modification Date: 12.02.2020
 */
public class EngineeredQueue<T> {
    private int count = 0;
    @NotNull
    private String queueName;
    private List<Integer> customersIdsList = new ArrayList<>();
    private Set<T> customersSet = new HashSet<>();
    private Map<T, Integer> idByCustomer = new HashMap<>();

    /**
     * Конструктор - создание новой очереди с определенным именем
     * @param queueName - принимает имя очереди
     */
    public EngineeredQueue(@NotNull final String queueName){
        this.queueName = queueName;
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
            customersIdsList.add(count);
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
            int customerId = idByCustomer.remove(var);
            return customersIdsList.remove(new Integer(customerId));
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