package com.mytutorial;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import com.mytutorial.view.Apresentacao;
import com.mytutorial.view.TelaPrincipal;

public class Home extends WebPage{
	
	public Home() {
		
		add(telaPrincipal());
		
		add(new Link<Void>("sair") {

			private static final long serialVersionUID = 1L;

			public void onClick() {
				getSession().invalidate();
				setResponsePage(TelaPrincipal.class);
			}
		});


	}
	
	private AjaxLink<TelaPrincipal> telaPrincipal() {
		AjaxLink<TelaPrincipal> ajaxLink = new AjaxLink<TelaPrincipal>("telaPrincipal") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(Apresentacao.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

}
