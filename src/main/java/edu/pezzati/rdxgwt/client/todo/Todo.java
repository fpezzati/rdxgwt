package edu.pezzati.rdxgwt.client.todo;

import java.io.Serializable;


public class Todo implements Serializable {

	private static final long serialVersionUID = -5520325283377072191L;
	private String id;
	private String memento;
	
	public Todo() {
		
	}

	public Todo(Todo todo) {
		this.setId(todo.getId());
		this.memento = todo.memento;
	}

	public String getId() {
		return id;
	}

	public Todo setId(String id) {
		this.id = id;
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
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (memento == null) {
			if (other.memento != null)
				return false;
		} else if (!memento.equals(other.memento))
			return false;
		return true;
	}

	
}
