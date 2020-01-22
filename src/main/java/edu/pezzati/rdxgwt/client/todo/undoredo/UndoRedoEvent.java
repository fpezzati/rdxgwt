package edu.pezzati.rdxgwt.client.todo.undoredo;

import com.google.gwt.event.shared.GwtEvent;

public abstract class UndoRedoEvent extends GwtEvent<UndoRedoEventHandler> {
	
	public static final Type<UndoRedoEventHandler> TYPE = new Type<UndoRedoEventHandler>();


	@Override
	public Type<UndoRedoEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UndoRedoEventHandler handler) {
		handler.onChange(this);
	}

	public abstract int steps();
}
