package com.coco.mybatis.log.actions;

import com.coco.mybatis.log.toolwindow.ToolWindowFactoryImpl;
import com.coco.mybatis.log.ui.SqlPanel;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * ClearAction
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/14 7:24
 */
public class ClearAction extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        String text = "";
        ToolWindow sqlToolWindow = ToolWindowFactoryImpl.getWindow(project);
        SqlPanel sqlPanel = (SqlPanel) sqlToolWindow.getContentManager().getContent(0).getComponent();
        ConsoleView consoleView = sqlPanel.getConsoleView();
        consoleView.clear();
        consoleView.print(text+"\n", ConsoleViewContentType.LOG_INFO_OUTPUT);
    }
}
