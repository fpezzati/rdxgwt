package edu.pezzati.rdxgwt.client.todo;

import com.google.gwt.event.shared.GwtEvent;

public abstract class TodoListEvent extends GwtEvent<TodoListEventHandler> {
	

	public static final Type<TodoListEventHandler> TYPE = new Type<TodoListEventHandler>();

	@Override
	public Type<TodoListEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TodoListEventHandler handler) {
		handler.onChange(this);
	}
	
	public abstract String getType();
	
	public abstract Object getData();
}
