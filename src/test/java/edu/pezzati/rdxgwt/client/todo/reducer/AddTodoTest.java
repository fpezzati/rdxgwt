package edu.pezzati.rdxgwt.client.todo.reducer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListInvalidData;

import java.util.HashMap;

import org.junit.Assert;

public class AddTodoTest {

	private TodoReducer addTodo;
	@Rule
	public ExpectedException expEx = ExpectedException.none();
	
	@Before
	public void initEach() {
		addTodo = new AddTodo();
	}
	
	@Test
	public void addTodoHandleAddTodoActionType() {
		Assert.assertEquals("add-todo", addTodo.getActionType());
	}
	
	@Test
	public void whenModelIsNullAddTodoDoesNothing() throws TodoListException {
		Object data = new Todo().setMemento("something to remember.");
		TodoListModel actual = addTodo.reduce(null, data);
		Assert.assertNull(actual);
	}
	
	@Test
	public void whenDataIsNullAddTodoDoesNothing() throws TodoListException {
		TodoListModel expected = getSomeTodoList();
		TodoListModel actual = addTodo.reduce(getSomeTodoList(), null);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void addTodoExpectATodoListAsDataOtherwiseWillRaiseAnError() throws TodoListException {
		Object data = new HashMap<String, String>();
		expEx.expect(TodoListInvalidData.class);
		addTodo.reduce(getSomeTodoList(), data);
		Assert.fail();
	}
	
	@Test
	public void addTodoAddNewTodoToTheModelsTodoList() throws TodoListException {
		TodoListModel expected = getSomeEnrichedTodoList();
		Object data = new Todo().setUuid("4").setMemento("New one to remember!");
		TodoListModel actual = addTodo.reduce(getSomeTodoList(), data);
		Assert.assertEquals(expected, actual);
	}

	private TodoListModel getSomeTodoList() {
		TodoListModel tlm = new TodoListModel();
		tlm.getTodoList().add(new Todo().setUuid("1").setMemento("remember this."));
		tlm.getTodoList().add(new Todo().setUuid("2").setMemento("and this."));
		tlm.getTodoList().add(new Todo().setUuid("3").setMemento("and this too."));
		return tlm;
	}
	
	private TodoListModel getSomeEnrichedTodoList() {
		TodoListModel tlm = getSomeTodoList();
		tlm.getTodoList().add(new Todo().setUuid("4").setMemento("New one to remember!"));
		return tlm;
	}
}
