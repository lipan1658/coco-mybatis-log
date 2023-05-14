package com.coco.mybatis.log.ui;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * LeftToolbarPanel
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/13 13:18
 */
public class LeftToolbarPanel extends JBPanel {

    public LeftToolbarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        init();
    }

    private void init() {
        ActionGroup actionGroupLeft = (ActionGroup) ActionManager.getInstance().getAction("CocoMybatisLog.ToolBarActions.left");
        ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar("left", actionGroupLeft, false);
        actionToolbar.setTargetComponent(this);
        add(actionToolbar.getComponent(), BorderLayout.CENTER);
    }
}
