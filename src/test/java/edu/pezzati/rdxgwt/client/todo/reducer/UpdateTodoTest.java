package edu.pezzati.rdxgwt.client.todo.reducer;

import org.junit.Test;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;

import org.junit.Assert;

public class UpdateTodoTest {
	
	private TodoReducer updateTodo = new UpdateTodo();

	@Test
	public void updateTodoHandlesUpdateTodoActions() {
		Assert.assertEquals("update-todo", updateTodo.getActionType());
	}
	
	@Test
	public void updateTodoDoesNothingIfModelIsNull() throws TodoListException {
		TodoListModel tlm = null;
		Todo[] data = { new Todo().setMemento("before"), new Todo().setMemento("after") };
		updateTodo.reduce(tlm , data );
		Assert.assertNull(tlm);
	}
	
	@Test
	public void updateTodoDoesNothingIfDataIsNull() throws TodoListException {
		TodoListModel expected = getModelWithSomeTodo();
		Object data = null;
		TodoListModel actual = updateTodo.reduce(getModelWithSomeTodo(), data);
		Assert.assertEquals(expected, actual);
	}
	
	private TodoListModel getModelWithSomeTodo() {
		TodoListModel tlm = new TodoListModel();
		tlm.getTodoList().add(new Todo().setMemento("something 1."));
		tlm.getTodoList().add(new Todo().setMemento("something 2."));
		tlm.getTodoList().add(new Todo().setMemento("something 3."));
		return tlm;
	}
}
