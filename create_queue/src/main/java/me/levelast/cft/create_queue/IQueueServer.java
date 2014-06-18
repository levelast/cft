package me.levelast.cft.create_queue;


import me.levelast.cft.elements.CustomElement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Queue;
import java.util.Set;

public interface IQueueServer extends Remote {
    Queue<CustomElement> createQueue() throws RemoteException;

    Set<Long> getGroupIds() throws RemoteException;
}