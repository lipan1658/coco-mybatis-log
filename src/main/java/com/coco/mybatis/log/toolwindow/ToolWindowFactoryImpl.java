package com.coco.mybatis.log.toolwindow;

import com.coco.mybatis.log.ui.SqlPanel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ToolWindowFactoryImpl
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/13 8:58
 */
public class ToolWindowFactoryImpl implements ToolWindowFactory {

    private JBPanel sqlPanel;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        sqlPanel = new SqlPanel(project);
        toolWindow.getContentManager().addContent(ContentFactory.getInstance().createContent(sqlPanel, "sql", false));
    }

    @Nullable
    public static ToolWindow getWindow(@NotNull final Project project) {
        return ToolWindowManager.getInstance(project).getToolWindow("CocoMybatisLog");

    }

    public static void showWindow(@NotNull final ToolWindow toolWindow) {
        if (!toolWindow.isActive() && toolWindow.isAvailable()) {
            toolWindow.show(null);
        }
    }

    public static void showWindowContent(@NotNull final ToolWindow toolWindow, int contentIndex) {
        toolWindow.show(null);
        Content content = toolWindow.getContentManager().getContent(contentIndex);
        toolWindow.getContentManager().setSelectedContent(content);
    }
}