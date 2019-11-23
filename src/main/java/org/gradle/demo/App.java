package org.gradle.demo;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class App
{
    public static void main(String[] args)
    {
        Resource sharedResource = new Resource(1);

        ReentrantLock lock = new ReentrantLock();

        // Создаем 5 нитей, каждая из которых инкрементит поле 4 раза, используя sharedResource.i++;
        // Т.о, макс значение поля м.б. 5*4=20
        // В реальности, значение получалось меньше либо больше. Напр, 17 или 21, причем , в конце не обязательно максимальное число
        // Волатильность ( volatile int i;) ничего не меняла
        // Использование синхронизированного метода ничего не дало: synchronized void increment()
        // Оборачивание вызова инкремента в synchronized также не помогло: synchronized (sharedResource) {}
        // Использование ReentrantLock дало корректный вывод (i в диапазоне от 2 до 21. 21 - в самом конце);
        // причём, из вывода ясно, что i последовательно инкрементится РАЗНЫМИ потоками

        for (int i = 0; i < 5; i++)
        {
            new Thread(() -> {
                for (int j = 0; j < 4; j++)
                {
                    // [A]
                    // sharedResource.i++;

                    // [B]
                    // synchronized (sharedResource) {
                    //     sharedResource.increment();
                    // }

                    // [C]
                    lock.lock();
                    sharedResource.i++;
                    System.out.println("[" + Thread.currentThread().getName() + "] " + sharedResource.i );
                    lock.unlock();

                    try
                    {
                        sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}

class Resource {
    int i;

    Resource(int i)
    {
        this.i = i;
    }

    void increment(){
        this.i++;
    }
}
