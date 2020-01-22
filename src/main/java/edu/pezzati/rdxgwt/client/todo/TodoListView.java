package edu.pezzati.rdxgwt.client.todo;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.pezzati.rdxgwt.client.todo.undoredo.UndoRedoEvent;

public class TodoListView implements IsWidget {

	private FlexTable todoTable;
	/**
	 * Helps finding which {@link Todo} object was edited in the {@link TextBox} widget.
	 */
	private Map<TextBox, Todo> todoTextField;
	private Map<Button, Todo> todoButton;
	private EventBus eventBus;
	private int steps;
	
	public TodoListView() {
		todoTextField = new HashMap<>();
		todoButton = new HashMap<>();
	}

	@Override
	public Widget asWidget() {
		HorizontalPanel basePanel = new HorizontalPanel();
		todoTable = new FlexTable();
		todoTable.setText(0, 0, "");
		TextBox mementoField = new TextBox();
		mementoField.addValueChangeHandler(getMementoFieldChangeHandler());
		Button removeButton = new Button("-");
		removeButton.addClickHandler(getRemoveButtonClickHandler());
		todoTable.setWidget(0, 0, mementoField);
		todoTable.setWidget(0, 1, removeButton);
		Button add = new Button("+");
		add.addClickHandler(getAddButtonClickHandler());
		Button clear = new Button("clear");
		clear.addClickHandler(getClearButtonClickHandler());
		Button stepBack = new Button("<");
		stepBack.addClickHandler(getStepBackClickHandler());
		Button stepForward = new Button(">");
		stepForward.addClickHandler(getStepForwardClickHandler());
		VerticalPanel leftPanel = new VerticalPanel();
		leftPanel.add(todoTable);
		leftPanel.addStyleName("leftPanel");
		VerticalPanel rightPanel = new VerticalPanel();
		rightPanel.add(add);
		rightPanel.add(clear);
		rightPanel.add(stepBack);
		rightPanel.add(stepForward);
		rightPanel.addStyleName("rightPanel");
		basePanel.add(leftPanel);
		basePanel.add(rightPanel);
		return basePanel;
	}
	
	public void refresh(TodoListModel model) {
		todoTable.clear();
		todoTable.removeAllRows();
		todoTextField.clear();
		for(Todo todo : model.getTodoList()) {
			TextBox mementoField = new TextBox();
			mementoField.addValueChangeHandler(getMementoFieldChangeHandler());
			Button removeButton = new Button("-");
			removeButton.addClickHandler(getRemoveButtonClickHandler());
			mementoField.setValue(todo.getMemento());
			todoTable.insertRow(todoTable.getRowCount());
			todoTable.setWidget(todoTable.getRowCount(), 0, mementoField);
			todoTable.setWidget(todoTable.getRowCount(), 1, removeButton);
			todoTextField.put(mementoField, todo);
			todoButton.put(removeButton, todo);
		}
	}
	
	private ClickHandler getAddButtonClickHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("click add");
				eventBus.fireEvent(new TodoListEvent() {
					@Override
					public String getType() {
						return "add-todo";
					}

					@Override
					public Object getData() {
						return getNewTodo();
					}
				});
			}
		};
	}

	private ClickHandler getClearButtonClickHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("click clear");
				eventBus.fireEvent(new TodoListEvent() {
					@Override
					public String getType() {
						return "clear-list";
					}

					@Override
					public Object getData() {
						return null;
					}
				});
			}
		};
	}
	
	private ClickHandler getStepBackClickHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				steps--;
				GWT.log("stepping back.");
				eventBus.fireEvent(new UndoRedoEvent() {
					@Override
					public int steps() {
						return steps;
					}
				});
			}
		};
	}

	private ClickHandler getStepForwardClickHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				steps++;
				GWT.log("stepping forward.");
				eventBus.fireEvent(new UndoRedoEvent() {
					@Override
					public int steps() {
						return steps;
					}
				});
			}
		};
	}

	private ValueChangeHandler<String> getMementoFieldChangeHandler() {
		return new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				Todo oldTodo = todoTextField.get(event.getSource());
				Todo newTodo = new Todo();
				newTodo.setUuid(DOM.createUniqueId());
				newTodo.setMemento(event.getValue());
				GWT.log("edit todo. New value: " + event.getValue() + ". It was: " + oldTodo.getMemento());
				eventBus.fireEvent(new TodoListEvent() {
					@Override
					public String getType() {
						return "update-todo";
					}

					@Override
					public Object getData() {
						return new Todo[] { oldTodo, newTodo };
					}
				});
			}
		};
	}
	
	private ClickHandler getRemoveButtonClickHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("click remove");
				eventBus.fireEvent(new TodoListEvent() {
					@Override
					public String getType() {
						return "remove-todo";
					}

					@Override
					public Object getData() {
						return todoButton.get(event.getSource());
					}
				});
			}
		};
	}
	
	private Todo getNewTodo() {
		Todo todo = new Todo();
		todo.setUuid(DOM.createUniqueId());
		todo.setMemento("new!");
		return todo;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
}
