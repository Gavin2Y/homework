package gavin.test.homework.infra.common.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class LRUCache<K, V> {

    @Data
    class Node {
        K key;
        V value;
        Node prev;
        Node next;
        long expireTime;

        Node(K key, V value, long expireTime) {
            this.key = key;
            this.value = value;
            this.expireTime = expireTime;
        }
    }

    private final int capacity;
    private final Map<K, Node> cache;
    private final Node head;
    private final Node tail;
    private final BiConsumer<K, V> exceedProcessor;
    private final long ttl;

    public LRUCache(int capacity, long ttl, BiConsumer<K, V> exceedProcessor) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(null, null, -1);
        this.tail = new Node(null, null, -1);
        this.exceedProcessor = exceedProcessor;
        this.ttl = ttl;
        head.next = tail;
        tail.prev = head;
    }

    public LRUCache(int capacity, long ttl) {
        this(capacity, ttl, null);
    }

    public synchronized V get(K key) {
        Node node = cache.get(key);
        if (node == null) return null;

        long currentTime = System.currentTimeMillis();
        if (node.expireTime < currentTime) {
            removeNode(node);
            cache.remove(key);
            return null;
        }

        // 未过期，移动到链表头部，并更新expiretime
        moveToHead(node);
        node.expireTime = currentTime + ttl;
        return node.value;
    }

    // 插入值
    public synchronized void put(K key, V value) {
        long currentTime = System.currentTimeMillis();
        evictExpiredNodes(currentTime);

        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            node.expireTime = currentTime + ttl;
            moveToHead(node);
        } else {
            Node newNode = new Node(key, value, currentTime + ttl);
            cache.put(key, newNode);
            addToHead(newNode);

            if (cache.size() > capacity) {
                Node tailNode = removeTail();
                cache.remove(tailNode.key);

                if (this.exceedProcessor != null) {
                    this.exceedProcessor.accept(tailNode.key, tailNode.value);
                }
            }
        }
    }

    private void evictExpiredNodes(long currentTime) {
        Node current = tail.prev; // 从尾部向前遍历
        while (current != head) {
            Node prevNode = current.prev; // 保存前驱节点
            if (current.expireTime < currentTime) {
                removeNode(current);
                cache.remove(current.key);
            } else {
                break; // 遇到未过期节点，终止遍历
            }
            current = prevNode;
        }
    }

    // 将节点移动到链表头部
    private void moveToHead(Node node) {
        removeNode(node); // 从链表中移除节点
        addToHead(node); // 将节点添加到链表头部
    }

    // 将节点添加到链表头部
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    // 从链表中移除节点
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // 移除链表尾部的节点
    private Node removeTail() {
        Node tailNode = tail.prev;
        removeNode(tailNode);
        return tailNode;
    }

}
