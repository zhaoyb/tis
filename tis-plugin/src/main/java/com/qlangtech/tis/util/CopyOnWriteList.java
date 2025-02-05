/**
 * Copyright (c) 2020 QingLang, Inc. <baisui@qlangtech.com>
 * <p>
 *   This program is free software: you can use, redistribute, and/or modify
 *   it under the terms of the GNU Affero General Public License, version 3
 *   or later ("AGPL"), as published by the Free Software Foundation.
 * <p>
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *   FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.qlangtech.tis.util;

/**
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2021-05-09 10:33
 **/
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;




/**
 * {@link List}-like implementation that has copy-on-write semantics.
 *
 * <p>
 * This class is suitable where highly concurrent access is needed, yet
 * the write operation is relatively uncommon.
 *
 * @author Kohsuke Kawaguchi
 */
public class CopyOnWriteList<E> implements Iterable<E> {
    private volatile List<? extends E> core;

    public CopyOnWriteList(List<E> core) {
        this(core,false);
    }

    private CopyOnWriteList(List<E> core, boolean noCopy) {
        this.core = noCopy ? core : new ArrayList<>(core);
    }

    public CopyOnWriteList() {
        this.core = Collections.emptyList();
    }

    public synchronized void add(E e) {
        List<E> n = new ArrayList<>(core);
        n.add(e);
        core = n;
    }

    public synchronized void addAll(Collection<? extends E> items) {
        List<E> n = new ArrayList<>(core);
        n.addAll(items);
        core = n;
    }

    /**
     * Removes an item from the list.
     *
     * @return
     *      true if the list contained the item. False if it didn't,
     *      in which case there's no change.
     */
    public synchronized boolean remove(E e) {
        List<E> n = new ArrayList<>(core);
        boolean r = n.remove(e);
        core = n;
        return r;
    }

    /**
     * Returns an iterator.
     */
    @Override
    public Iterator<E> iterator() {
        final Iterator<? extends E> itr = core.iterator();
        return new Iterator<E>() {
            private E last;
            @Override
            public boolean hasNext() {
                return itr.hasNext();
            }

            @Override
            public E next() {
                return last=itr.next();
            }

            @Override
            public void remove() {
                CopyOnWriteList.this.remove(last);
            }
        };
    }

    /**
     * Completely replaces this list by the contents of the given list.
     */
    public void replaceBy(CopyOnWriteList<? extends E> that) {
        this.core = that.core;
    }

    /**
     * Completely replaces this list by the contents of the given list.
     */
    public void replaceBy(Collection<? extends E> that) {
        this.core = new ArrayList<E>(that);
    }

    /**
     * Completely replaces this list by the contents of the given list.
     */
    public void replaceBy(E... that) {
        replaceBy(Arrays.asList(that));
    }

    public void clear() {
        this.core = new ArrayList<>();
    }

    public <E> E[] toArray(E[] array) {
        return core.toArray(array);
    }

    public List<E> getView() {
        return Collections.unmodifiableList(core);
    }

    public void addAllTo(Collection<? super E> dst) {
        dst.addAll(core);
    }

    public E get(int index) {
        return core.get(index);
    }

    public boolean isEmpty() {
        return core.isEmpty();
    }

    public int size() {
        return core.size();
    }

    public boolean contains(Object item) {
        return core.contains(item);
    }

    @Override public String toString() {
        return core.toString();
    }

    /**
     * {@link Converter} implementation for XStream.
     */
    public static final class ConverterImpl extends AbstractCollectionConverter {
        public ConverterImpl(Mapper mapper) {
            super(mapper);
        }

        @Override
        public boolean canConvert(Class type) {
            return type==CopyOnWriteList.class;
        }

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
            for (Object o : (CopyOnWriteList) source)
                writeItem(o, context, writer);
        }

        @Override
        @SuppressWarnings("unchecked")
        public CopyOnWriteList unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            // read the items from xml into a list
            List items = new ArrayList();
            while (reader.hasMoreChildren()) {
                reader.moveDown();
                try {
                    Object item = readItem(reader, context, items);
                    items.add(item);
                } catch (CriticalXStreamException e) {
                    throw e;
                } catch (XStreamException | LinkageError e) {
                    RobustReflectionConverter.addErrorInContext(context, e);
                }
                reader.moveUp();
            }

            return new CopyOnWriteList(items,true);
        }
    }

}
