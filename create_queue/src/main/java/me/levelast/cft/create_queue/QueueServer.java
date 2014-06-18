package me.levelast.cft.create_queue;


import me.levelast.cft.elements.CustomElement;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class QueueServer implements IQueueServer {

    private static int number = 0;
    private Set<Long> groupIds = new HashSet<>();

    @Override
    public Queue<CustomElement> createQueue() throws RemoteException {
        Queue<CustomElement> queue = new LinkedList<>();
        addElements(queue);
        return queue;
    }

    public static void main(String[] args) throws InterruptedException, RemoteException, AlreadyBoundException {

        Registry registry = LocateRegistry.createRegistry(2099);
        QueueServer service = new QueueServer();
        IQueueServer queueServer = (IQueueServer) UnicastRemoteObject.exportObject(service, 0);
        registry.bind("queue_server", queueServer);
        System.out.println("Сервер генерации очереди запущен.");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите количество элементов (0 - остановка сервера): ");
            while (!scanner.hasNextInt()) {
                scanner.next();
                Thread.yield();
            }
            number = scanner.nextInt();
            if (number == 0) {
                System.exit(0);
            }
        }
    }

    private void addElements(Queue<CustomElement> queue) {
        for (int i = 0; i < number; i++) {
            Random random = new Random();
            Long groupId = (long) random.nextInt(100);
            groupIds.add(groupId);
            queue.add(new CustomElement(UUID.randomUUID(), groupId));
        }
    }

    public Set<Long> getGroupIds() {
        return groupIds;
    }
}
