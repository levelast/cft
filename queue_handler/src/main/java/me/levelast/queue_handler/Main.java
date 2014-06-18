package me.levelast.queue_handler;

import me.levelast.cft.create_queue.IQueueServer;
import me.levelast.cft.elements.CustomElement;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry("localhost", 2099);
        final IQueueServer queueServer = (IQueueServer) registry.lookup("queue_server");

        final Queue<CustomElement> queue = queueServer.createQueue();

        if (!queue.isEmpty()) {
            while (true) {
                System.out.print("Введите количество потоков: ");
                Scanner scanner = new Scanner(System.in);
                int numberOfThreads = scanner.nextInt();
                List<Queue<CustomElement>> queues = QueueHandler.createQueues(queue, queueServer.getGroupIds());
                if (numberOfThreads <= queues.size()) {
                    startThreads(numberOfThreads, queues);
                    break;
                }
                System.out.println("Количество потоков должно быть меньше числа групп (число групп: " + queues.size() + ") ");
            }
        } else {
            System.out.println("Очередь пуста.");
        }
    }

    private static void startThreads(int numberOfThreads, List<Queue<CustomElement>> queues) {
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        for (Queue<CustomElement> queue : queues) {
            service.submit(new QueueHandler(queue));
        }
        service.shutdown();
    }
}