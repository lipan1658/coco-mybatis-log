package com.coco.mybatis.log.ui;

import com.coco.mybatis.log.ui.LeftToolbarPanel;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.components.JBPanel;

import java.awt.*;

/**
 * SqlPanel
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/13 9:01
 */
public class SqlPanel extends JBPanel {

    private Project project;
    private ConsoleView consoleView;

    private LeftToolbarPanel leftToolbarPanel;

    private JBPanel bodyPanel;

    public SqlPanel(Project project) {
        this.project = project;
        setLayout(new BorderLayout());
        init();
    }

    private void init() {
        leftToolbarPanel = new LeftToolbarPanel();
        add(leftToolbarPanel, BorderLayout.WEST);

        bodyPanel = new JBPanel();
        bodyPanel.setLayout(new BorderLayout());
        add(bodyPanel, BorderLayout.CENTER);
        consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        bodyPanel.add(consoleView.getComponent(), BorderLayout.CENTER);
        Disposer.register(project, consoleView);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ConsoleView getConsoleView() {
        return consoleView;
    }

    public void setConsoleView(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public LeftToolbarPanel getLeftToolbarPanel() {
        return leftToolbarPanel;
    }

    public void setLeftToolbarPanel(LeftToolbarPanel leftToolbarPanel) {
        this.leftToolbarPanel = leftToolbarPanel;
    }

    public JBPanel getBodyPanel() {
        return bodyPanel;
    }

    public void setBodyPanel(JBPanel bodyPanel) {
        this.bodyPanel = bodyPanel;
    }
}
