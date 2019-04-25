package com.mytutorial.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mytutorial.HomePage;
import com.mytutorial.model.Usuario;
import com.mytutorial.service.UsuarioService;

public class TelaPrincipal extends HomePage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "usuarioService")
	private UsuarioService usuarioService;

	private Form<?> form = new Form<>("form");
	private ModalWindow modalWindow;

	public TelaPrincipal() {
		this(new Usuario());
	}

	public TelaPrincipal(Usuario usuario) {
		modalWindow = new ModalWindow("modalWindow");
		// Tamanho do Modal
		modalWindow.setInitialHeight(500);
		modalWindow.setInitialWidth(900);
		modalWindow.setOutputMarkupId(true);
		add(modalWindow);

		// Criando o botão para o modal
		add(new AjaxLink<String>("abrirModal") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				TutorialPanel tutorialPanel = new TutorialPanel(modalWindow.getContentId()) {
				};
				tutorialPanel.setOutputMarkupId(true);
				add(tutorialPanel);
				modalWindow.setContent(tutorialPanel);
				modalWindow.show(target);
			}

		});
	}

}
