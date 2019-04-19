package com.mytutorial.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public class AlertFeedback extends FeedbackPanel {

	private static final long serialVersionUID = 1L;

	public AlertFeedback(String id) {
		super(id);
	}

	@Override
	protected String getCSSClass(final FeedbackMessage message) {
		String css = "feedback";
		if (message.getLevel() == FeedbackMessage.ERROR || message.getLevel() == FeedbackMessage.FATAL) {
			css = "alert alert-danger alertMessage";
		}else if (message.getLevel() == FeedbackMessage.SUCCESS) {
			css = "alert alert-success alertMessage";
		}

		return css;
	}

}
