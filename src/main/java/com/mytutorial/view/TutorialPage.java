package com.mytutorial.view;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.mytutorial.HomePage;

public class TutorialPage extends HomePage {

	private static final long serialVersionUID = 1L;

	public TutorialPage(PageParameters parameters) {
		
		add(new Label("title", parameters.get("title")));
		add(new Label("editor", parameters.get("editor")));
		
	}

}
