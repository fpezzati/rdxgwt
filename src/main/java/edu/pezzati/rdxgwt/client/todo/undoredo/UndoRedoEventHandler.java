package edu.pezzati.rdxgwt.client.todo.undoredo;

import com.google.gwt.event.shared.EventHandler;

public interface UndoRedoEventHandler extends EventHandler {

	public void onChange(UndoRedoEvent event);
}
