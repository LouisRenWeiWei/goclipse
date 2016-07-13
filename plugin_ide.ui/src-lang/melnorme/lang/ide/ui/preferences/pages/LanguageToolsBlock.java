/*******************************************************************************
 * Copyright (c) 2016 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package melnorme.lang.ide.ui.preferences.pages;

import melnorme.lang.ide.core.LangCore;
import melnorme.lang.ide.core.LangCore_Actual;
import melnorme.lang.ide.core.engine.LanguageServerHandler;
import melnorme.lang.ide.core.operations.ToolchainPreferences;
import melnorme.lang.ide.ui.preferences.AbstractCompositePreferencesBlock;
import melnorme.lang.ide.ui.preferences.common.PreferencesPageContext;
import melnorme.util.swt.components.AbstractGroupWidget;
import melnorme.util.swt.components.fields.ButtonTextField;
import melnorme.util.swt.components.fields.FileTextField;

public class LanguageToolsBlock extends AbstractCompositePreferencesBlock {
	
	protected final LanguageServerHandler<?> languageServerHandler = LangCore.getLanguageServerHandler();
	
	public LanguageToolsBlock(PreferencesPageContext prefContext) {
		super(prefContext);
		
		addChildWidget(init_createEngineToolGroup());
	}
	
	protected EngineToolGroup init_createEngineToolGroup() {
		return new EngineToolGroup();
	}
	
	protected String getEngineToolName() {
		return LangCore_Actual.LANGUAGE_SERVER_Name;
	}
	
	public class EngineToolGroup extends AbstractGroupWidget {
		
		public final ButtonTextField toolLocationField;
		
		public EngineToolGroup() {
			super(getEngineToolName() + ": ", 3);
			
			this.toolLocationField = initToolLocationField();
			addChildWidget(toolLocationField);
			if(toolLocationField instanceof DownloadToolTextField) {
				layoutColumns = 4;
			}
			
			toolLocationField.addFieldValidator(false, languageServerHandler.getLanguageToolPathValidator());
			
			prefContext.bindToPreference(toolLocationField, ToolchainPreferences.LANGUAGE_SERVER_PATH);
		}
		
		protected ButtonTextField initToolLocationField() {
			return new FileTextField(getEngineToolName());
		}
		
	}
	
}