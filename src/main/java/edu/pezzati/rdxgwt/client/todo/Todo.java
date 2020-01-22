package edu.pezzati.rdxgwt.client.todo;

import java.io.Serializable;


public class Todo implements Serializable {

	private static final long serialVersionUID = -5520325283377072191L;
	private String uuid;
	private String memento;
	
	public Todo() {
		
	}

	public Todo(Todo todo) {
		this.setUuid(todo.getUuid());
		this.memento = todo.memento;
	}

	public String getUuid() {
		return uuid;
	}

	public Todo setUuid(String id) {
		this.uuid = id;
		return this;
	}

	public String getMemento() {
		return memento;
	}

	public Todo setMemento(String memento) {
		this.memento = memento;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
		result = prime * result + ((memento == null) ? 0 : memento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (getUuid() == null) {
			if (other.getUuid() != null)
				return false;
		} else if (!getUuid().equals(other.getUuid()))
			return false;
		if (memento == null) {
			if (other.memento != null)
				return false;
		} else if (!memento.equals(other.memento))
			return false;
		return true;
	}

	
}
