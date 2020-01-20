package edu.pezzati.rdxgwt.client.todo;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.event.shared.Event.Type;

public interface TodoListEventHandler extends EventHandler {

	public static Type<TodoListEventHandler> Type = new Type<TodoListEventHandler>();

	public void onChange(TodoListEvent event);
}
