package net.sf.timecut.ui.swt;

public class MissingSelectionObjectException extends Exception {

    public MissingSelectionObjectException(Object o) {
        super(o.toString());
    }
}
