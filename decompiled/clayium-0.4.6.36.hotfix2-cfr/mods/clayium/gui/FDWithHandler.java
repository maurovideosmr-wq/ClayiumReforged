/*
 * Decompiled with CFR 0.152.
 */
package mods.clayium.gui;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import mods.clayium.gui.IFunctionalDrawer;

public abstract class FDWithHandler<S, T>
implements IFunctionalDrawer<T> {
    protected IFunctionalIterable<IFDHandler<S, T>, T> defaultHandlers = null;

    public FDWithHandler(IFunctionalIterable<IFDHandler<S, T>, T> defaultHandlers) {
        this.defaultHandlers = defaultHandlers;
    }

    public FDWithHandler(List<IFDHandler<S, T>> defaultHandlers) {
        this(new FunctionalList(defaultHandlers));
    }

    public FDWithHandler(IFDHandler<S, T> defaultHandler) {
        this(Arrays.asList(defaultHandler));
    }

    public FDWithHandler() {
        this((IFDHandler)null);
    }

    @Override
    public T draw(T param) {
        return this.render(this.applyHandler(param));
    }

    public abstract T render(T var1);

    public T applyHandler(T param) {
        IFunctionalIterable<IFDHandler<S, T>, T> iterable = this.getHandlerIterator(param);
        if (iterable == null) {
            return param;
        }
        IFunctionalIterator<IFDHandler<S, T>, T> iterator = iterable.iterator(param);
        if (iterator == null) {
            return param;
        }
        while (iterator.hasNext(param)) {
            IFDHandler<S, T> handler = iterator.next(param);
            param = handler.apply(this.preApplyHandler(param), param);
        }
        return param;
    }

    public abstract S preApplyHandler(T var1);

    public IFunctionalIterable<IFDHandler<S, T>, T> getHandlerIterator(T param) {
        return this.defaultHandlers;
    }

    public static class FunctionalIterator<S, T>
    implements IFunctionalIterator<S, T> {
        Iterator<S> iterator;

        public FunctionalIterator(Iterator<S> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext(T param) {
            return this.iterator.hasNext();
        }

        @Override
        public S next(T param) {
            return this.iterator.next();
        }
    }

    public static class FunctionalList<S, T>
    implements IFunctionalIterable<S, T> {
        Iterable<S> iterable;

        public FunctionalList(Iterable<S> iterable) {
            this.iterable = iterable;
        }

        @Override
        public IFunctionalIterator<S, T> iterator(T param) {
            return new FunctionalIterator(this.iterable.iterator());
        }
    }

    public static interface IFunctionalIterator<S, T> {
        public boolean hasNext(T var1);

        public S next(T var1);
    }

    public static interface IFunctionalIterable<S, T> {
        public IFunctionalIterator<S, T> iterator(T var1);
    }

    public static interface IFDHandler<S, T> {
        public T apply(S var1, T var2);
    }
}

