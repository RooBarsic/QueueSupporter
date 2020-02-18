package logic.queue;

import org.jetbrains.annotations.NotNull;

public interface EngineeredQueue<T> {
    @NotNull
    String getQueueName();

    /**
     * Метод для добавления обекта в очередь
     * @param var принимает на вход обект
     * @return возвращает true если элемент успешно добавлен, false если такой елемент уже существует
     */
    boolean add(@NotNull final T var);

    /**
     * :TODO оптимизировать время работы, сейчай O(N)
     * Метод для удаления обекта из очереди
     * @param var принимает на вход обект
     * @return возвращает true если такой обект был в очереди и мы только что его удалили, false иначе
     */
    boolean remove(@NotNull final T var);

    /**
     * :TODO оптимизировать время работы, сейчай O(N)
     * Метод для поиска позиции обекта в очереди
     * @param var принимает на вход обект
     * @return возвращает позицию обекта в очереди - если в очереди есть такой обект ( иначе возвращает -1 )
     */
    int findIndex(@NotNull final T var);

    /**
     * @return возвращает количество элементов в очереди
     */
    int size();

    /**
     * Метод для очистки очереди
     */
    void clear();
}
