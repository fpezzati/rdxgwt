package edu.pezzati.rdxgwt.client.todo.reducer;

import org.junit.Test;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;

import org.junit.Assert;
import org.junit.Before;

public class ClearListTest {
	
	private TodoReducer clearList;
	private Object data;
	
	@Before
	public void initEach() {
		clearList = new ClearList();
		data = null;
	}

	@Test
	public void clearListCanHandleClearListActionType() {
		Assert.assertEquals("clear-list", clearList.getActionType());
	}
	
	@Test
	public void whenModelIsNullClearListDoesNothing() throws TodoListException {
		TodoListModel expected = null;
		TodoListModel actual = clearList.reduce(null, data);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void clearListReducesModelWhateverDataIs() throws TodoListException {
		TodoListModel expected = new TodoListModel();
		TodoListModel actual = clearList.reduce(getThreeTodoModel() , data);
		Assert.assertEquals(expected, actual);
	}

	private TodoListModel getThreeTodoModel() {
		TodoListModel tlm = new TodoListModel();
		tlm.getTodoList().add(new Todo().setMemento("foo"));
		tlm.getTodoList().add(new Todo().setMemento("bar"));
		tlm.getTodoList().add(new Todo().setMemento("zip"));
		return tlm;
	}
}
