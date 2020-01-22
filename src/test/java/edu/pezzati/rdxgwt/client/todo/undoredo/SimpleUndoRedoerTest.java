package edu.pezzati.rdxgwt.client.todo.undoredo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.pezzati.rdxgwt.client.todo.Todo;
import edu.pezzati.rdxgwt.client.todo.TodoListEvent;
import edu.pezzati.rdxgwt.client.todo.TodoListModel;
import edu.pezzati.rdxgwt.client.todo.reducer.AddTodo;
import edu.pezzati.rdxgwt.client.todo.reducer.ClearList;
import edu.pezzati.rdxgwt.client.todo.reducer.RemoveTodo;
import edu.pezzati.rdxgwt.client.todo.reducer.TodoReducer;
import edu.pezzati.rdxgwt.client.todo.reducer.UpdateTodo;
import edu.pezzati.rdxgwt.client.todo.reducer.exception.TodoListException;
import edu.pezzati.rdxgwt.client.todo.undoredo.SimpleUndoRedoer;
import edu.pezzati.rdxgwt.client.todo.undoredo.UndoRedoer;

public class SimpleUndoRedoerTest {

	private UndoRedoer unRe;

	@Before
	public void initEach() {
		unRe = new SimpleUndoRedoer();
		unRe.setEventQueueMaxSize(10);
	}

	@Test
	public void undoRedoerDoNothingWhenGivenModelIsNull() {
		unRe.setModel(new TodoListModel());
		unRe.setModel(null);
		Assert.assertNotNull(unRe.getModel());
	}

	@Test
	public void undoRedoerMakesCopyOfTheGivenModel() {
		TodoListModel notExpected = getSimpleTodoListModel();
		unRe.setModel(notExpected);
		Assert.assertEquals(notExpected, unRe.getModel());
		Assert.assertTrue(notExpected != unRe.getModel());
	}
	
	@Test
	public void undoRedoerDoesNotStoreNullEventsInQueue() throws TodoListException {
		unRe.addEventToQueue(getTodoListEvents().get(0));
		unRe.addEventToQueue(null);
		unRe.addEventToQueue(getTodoListEvents().get(2));
		Assert.assertEquals(2, unRe.getEventQueue().size());
	}

	@Test
	public void undoRedoerStoreEventsInAQueue() throws TodoListException {
		unRe.addEventToQueue(getTodoListEvents().get(0));
		unRe.addEventToQueue(getTodoListEvents().get(1));
		unRe.addEventToQueue(getTodoListEvents().get(2));
		Assert.assertEquals(3, unRe.getEventQueue().size());
	}
	
	@Test
	public void whenEventsInQueueExceedTheMaxEventSizeUndoRedoerPopAnEventAndApplyItToModel() throws TodoListException {
		unRe.setEventQueueMaxSize(3);
		unRe.getReducers().putAll(getReducers());
		unRe.setModel(getSimpleTodoListModel());
		unRe.getEventQueue().addAll(getTodoListEvents());
		unRe.addEventToQueue(new TodoListEvent() {
			@Override
			public String getType() {
				return "add-todo";
			}
			@Override
			public Object getData() {
				return new Todo().setUuid("10").setMemento("One more!");
			}
		});
		Collection<TodoListEvent> expectedQueue = getTodoListUpdatedEvents();
		TodoListModel expectedModel = getUndoRedoUpdatedModel();
		Assert.assertTrue(checkEventsAreTheSame(expectedQueue, unRe.getEventQueue()));
		Assert.assertEquals(expectedModel, unRe.getModel());
	}
	
	@Test
	public void whenAskedUndoStepsNumberIsLesserThanZeroUndoRedoerDoesNothing() throws TodoListException {
		unRe.setEventQueueMaxSize(5);
		unRe.getReducers().putAll(getReducers());
		unRe.setModel(getSimpleTodoListModel());
		unRe.getEventQueue().addAll(getTodoListEvents());
		TodoListModel expected = new TodoListModel(getSimpleTodoListModel());
		expected = applyEventToModel(applyEventToModel(applyEventToModel(expected, getReducers(), getTodoListEvents().get(0)), getReducers(), getTodoListEvents().get(1)), getReducers(), getTodoListEvents().get(2));
		TodoListModel actual = unRe.undoAboutSteps(-2);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void whenAskedUndoStepsNumberIsGreaterThanEventQueueSizeUndoRedoerStepsAsQueueHasEvents() throws TodoListException {
		unRe.setEventQueueMaxSize(5);
		unRe.getReducers().putAll(getReducers());
		unRe.setModel(getSimpleTodoListModel());
		unRe.getEventQueue().addAll(getTodoListEvents());
		TodoListModel expected = getSimpleTodoListModel();
		TodoListModel actual = unRe.undoAboutSteps(8);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void whenAskedUndoRedoerStepsAsUndoStepsNumberAsksFor() throws TodoListException {
		unRe.setEventQueueMaxSize(5);
		unRe.getReducers().putAll(getReducers());
		unRe.setModel(getSimpleTodoListModel());
		unRe.getEventQueue().addAll(getTodoListEvents());
		TodoListModel expected = new TodoListModel(getSimpleTodoListModel());
		expected = applyEventToModel(expected, getReducers(), getTodoListEvents().get(0));
		TodoListModel actual = unRe.undoAboutSteps(2);
		Assert.assertEquals(expected, actual);
	}
	
	private boolean checkEventsAreTheSame(Collection<TodoListEvent> expected, Collection<TodoListEvent> actual) {
		if(expected.size() != actual.size()) return false;
		List<TodoListEvent> expectedQueueAsList = new ArrayList<TodoListEvent>(expected);
		List<TodoListEvent> actualQueueAsList = new ArrayList<TodoListEvent>(actual);
		for(int i=0; i<expectedQueueAsList.size(); i++) {
			if(!(expectedQueueAsList.get(i).getType().equals(actualQueueAsList.get(i).getType()))) return false;
			if(expectedQueueAsList.get(i).getType().equals(new UpdateTodo().getActionType())) {
				if(!Arrays.equals((Todo[])expectedQueueAsList.get(i).getData(), (Todo[])actualQueueAsList.get(i).getData())) return false;
			} else {
				if(!(expectedQueueAsList.get(i).getData().equals(actualQueueAsList.get(i).getData()))) return false;
			}
		}
		return true;
	}

	private TodoListModel getSimpleTodoListModel() {
		TodoListModel tlm = new TodoListModel();
		tlm.getTodoList().add(new Todo().setUuid("1").setMemento("something 1."));
		tlm.getTodoList().add(new Todo().setUuid("2").setMemento("something 2."));
		tlm.getTodoList().add(new Todo().setUuid("3").setMemento("something 3."));
		return tlm;
	}
	
	private TodoListModel getUndoRedoUpdatedModel() {
		TodoListModel tlm = getSimpleTodoListModel();
		tlm.getTodoList().add(new Todo().setMemento("add 1."));
		return tlm;
	}
	
	private TodoListModel applyEventToModel(TodoListModel model, Map<String, TodoReducer> reducers, TodoListEvent event) throws TodoListException {
		return reducers.get(event.getType()).reduce(model, event.getData());
	}
	
	private List<TodoListEvent> getTodoListEvents() {
		List<TodoListEvent> events = new ArrayList<TodoListEvent>();
		events.add(new TodoListEvent() {
			@Override
			public String getType() {
				return "add-todo";
			}

			@Override
			public Object getData() {
				return new Todo().setMemento("add 1.");
			}
		});
		events.add(new TodoListEvent() {
			@Override
			public String getType() {
				return "add-todo";
			}

			@Override
			public Object getData() {
				return new Todo().setMemento("add 2.");
			}
		});
		events.add(new TodoListEvent() {
			@Override
			public String getType() {
				return "update-todo";
			}

			@Override
			public Object getData() {
				Todo todo1 = new Todo().setMemento("foo");
				Todo todo2 = new Todo().setMemento("bar");
				return new Todo[] { todo1, todo2 };
			}
		});
		return events;
	}
	
	private List<TodoListEvent> getTodoListUpdatedEvents() {
		List<TodoListEvent> events = getTodoListEvents();
		events.remove(0);
		events.add(new TodoListEvent() {
			@Override
			public String getType() {
				return "add-todo";
			}
			@Override
			public Object getData() {
				return new Todo().setUuid("10").setMemento("One more!");
			}
		});
		return events;
	}
	
	private Map<String, TodoReducer> getReducers() {
		Map<String, TodoReducer> reducers = new HashMap<String, TodoReducer>();
		TodoReducer addTodo = new AddTodo();
		TodoReducer clearList = new ClearList();
		TodoReducer removeTodo = new RemoveTodo();
		TodoReducer updateTodo = new UpdateTodo();
		reducers.put(addTodo.getActionType(), addTodo);
		reducers.put(clearList.getActionType(), clearList);
		reducers.put(removeTodo.getActionType(), removeTodo);
		reducers.put(updateTodo.getActionType(), updateTodo);
		return reducers;
	}
}
