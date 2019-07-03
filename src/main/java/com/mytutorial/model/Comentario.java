package com.mytutorial.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.wicket.util.io.IClusterable;

@Entity
@Table(name = "comentario")
public class Comentario implements IClusterable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String text;
	private Date data = new Date();

	@ManyToOne
	@JoinColumn(name = "idTutorial")
	private Tutorial tutorial;

	public Comentario() {

	}

	public Comentario(final Comentario comment) {
		this.text = comment.text;
		this.data = comment.data;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "[Comentario data = " + data + "text = " + text + "]";
	}

	public Tutorial getTutorial() {
		return tutorial;
	}

	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
