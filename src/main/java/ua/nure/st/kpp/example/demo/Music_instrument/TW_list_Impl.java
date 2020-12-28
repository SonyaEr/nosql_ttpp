package ua.nure.st.kpp.example.demo.Music_instrument;

import ua.nure.st.kpp.example.demo.Music_instrument.Music_Instrument;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TW_list_Impl implements TW_list
{
    private static class Node
    {
        Music_Instrument data;
        Node next;
        Node previous;

        Node(Music_Instrument data)
        {
            this.data = data;
            next = null;
            previous = null;
        }
    }

    private class IteratorImpl implements Iterator<Music_Instrument>
    {
        private Node current;
        boolean next_called;
        IteratorImpl()
        {
            current = null;
            next_called = false;
        }

        public boolean hasNext()
        {
            if (current == null)
            {
                return head != null;
            }
            else
            {
                return current.next != null;
            }
        }

        public Music_Instrument next()
        {
            if (len == 0)
            {
                throw new NoSuchElementException();
            }
            else
            {
                if (current == null)
                {
                    current = head;
                    next_called = true;
                    return current.data;
                }
                else
                {
                    current = current.next;
                    if (current == null)
                        throw new NoSuchElementException();
                    next_called = true;
                    return current.data;
                }
            }
        }

        public void remove()
        {
            if (!next_called)
            {
                throw new IllegalStateException();
            }
            else
            {
                next_called = false;
                if (current == head)
                {
                    head = head.next;
                    current = null;
                }
                else
                {
                    Node temp = current;
                    TW_list_Impl.this.remove(temp.data);
                    current = current.previous;
                }

            }
        }
    }

    @Override
    public Iterator<Music_Instrument> iterator()
    {
        return new IteratorImpl();
    }

    private Node head;
    private Node tail;
    private int len;

    public TW_list_Impl()
    {
        head = null;
        tail = null;
        len = 0;
    }

    public void add(Music_Instrument data)
    {
        if (head == null)
        {
            head = new Node(data);
            head.next = null;
            head.previous = null;
            tail = head;
        }
        else
        {
            if (len == 1)
            {
                tail = new Node(data);
                tail.previous = head;
                head.next = tail;
            }
            else
            {
                Node temp = new Node(data);
                Node temp_previous = tail;
                temp_previous.next = temp;
                temp.previous = temp_previous;
                tail = temp;
            }
        }
        len++;
    }

    public void print()
    {
        Node temp = head;
        while (temp != null)
        {
            System.out.println(temp.data.toString());
            temp = temp.next;
        }
    }

    public void rprint()
    {
        Node temp = tail;
        while (temp != null)
        {
            System.out.println(temp.data.toString());
            temp = temp.previous;
        }
    }

    public void clear()
    {
        Node temp = head;
        while (temp != null)
        {
            head = null;
            temp = temp.next;
            head = temp;
        }
        head = tail = null;
        len = 0;
    }

    public boolean remove(Music_Instrument element)
    {
        Node temp = head;
        while (temp != null)
        {
            if (temp.data.equals(element))
            {
                break;
            }
            else
            {
                temp = temp.next;
            }
        }
        if (temp != null)
        {
            if (temp == head)
            {
                if (temp.next == null)
                {
                    head = tail = null;
                }
                else
                {
                    head.next.previous = null;
                    head = head.next;
                }
            }
            else if (temp == tail)
            {
                if (temp.previous == null)
                {
                    head = tail = null;
                }
                else
                {
                    tail.previous.next = null;
                    tail = tail.next;
                }
            }
            else
            {
                temp.next.previous = temp.previous;
                temp.previous.next = temp.next;
                temp = null;
            }
            len--;
            return true;
        }
        else
        {
            return false;
        }
    }

    public Music_Instrument[] toArray()
    {
        Music_Instrument[] result = new Music_Instrument[len];
        int i = 0;
        Node temp = head;
        while (temp != null)
        {
            result[i++] = temp.data;
            temp = temp.next;
        }
        return result;
    }

    public int size()
    {
        return len;
    }

    public boolean contains(Music_Instrument element)
    {
        Node temp = head;
        while(temp != null)
        {
            if (temp.data.equals(element))
            {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public String toString()
    {
        String result = "{";
        Node temp = head;
        while(temp != null)
        {
            result += "["+temp.data.toString()+"]";
            temp = temp.next;
            if (temp != null)
            {
                result+=", ";
            }
        }
        return result + "}";
    }
}
