package me.levelast.queue_handler;

import me.levelast.cft.elements.CustomElement;

import java.io.*;
import java.util.*;

public class QueueHandler implements Runnable {

    private Queue<CustomElement> queue;

    public QueueHandler(Queue<CustomElement> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
                CustomElement element = queue.poll();
            try {
                handle(element.getItemId(), element.getGroupId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(UUID itemId, Long groupId) throws IOException {
        System.out.println("ItemId: " + itemId + ", GroupId: " + groupId);
    }

    public static List<Queue<CustomElement>> createQueues(Queue<CustomElement> queue, Set<Long> groupIds) {

        List<Queue<CustomElement>> queues = new ArrayList<>();
        for (Long groupId : groupIds) {
            Queue<CustomElement> queueWithGroupId = new PriorityQueue<>();

            for (CustomElement element : queue) {
                if (element.getGroupId().equals(groupId)) {
                    queueWithGroupId.add(element);
                }
            }

            queues.add(queueWithGroupId);
        }
        return queues;
    }
}