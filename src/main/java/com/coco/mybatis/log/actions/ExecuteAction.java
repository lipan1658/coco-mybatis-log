package com.coco.mybatis.log.actions;

import com.coco.mybatis.log.toolwindow.ToolWindowFactoryImpl;
import com.coco.mybatis.log.ui.SqlPanel;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.testframework.AbstractTestProxy;
import com.intellij.execution.testframework.TestTreeView;
import com.intellij.execution.testframework.sm.runner.ui.SMTRunnerConsoleView;
import com.intellij.execution.testframework.sm.runner.ui.SMTestRunnerResultsForm;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.execution.ui.ExecutionConsole;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.impl.ContentImpl;
import com.intellij.util.keyFMap.KeyFMap;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ExecuteAction
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/13 12:10
 */
public class ExecuteAction extends DumbAwareAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        String text = "";
        ToolWindow sqlToolWindow = ToolWindowFactoryImpl.getWindow(project);
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.getToolWindow("Debug");
        if(toolWindow == null || !toolWindow.isAvailable()){
            toolWindow = toolWindowManager.getToolWindow("Run");
            if(toolWindow != null){
                text = getText(toolWindow);
            }
        }else{
            text = getText(toolWindow);
        }
        SqlPanel sqlPanel = (SqlPanel) sqlToolWindow.getContentManager().getContent(0).getComponent();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(text));
        ConsoleView consoleView = sqlPanel.getConsoleView();
        consoleView.clear();

        List<String> sqlStringList= new ArrayList<>();
        List<String> parametersList= new ArrayList<>();
        //==>  Preparing:
        //==> Parameters:
        bufferedReader.lines().forEach(o->{
            if(o.contains("==>  Preparing:")){
                sqlStringList.add(o);
            }
            if(o.contains("==> Parameters:")){
                parametersList.add(o);
            }
        });
        List<String> sqlStringListNew;
        List<String> parametersListNew;
        if(sqlStringList.size() > 30){
            sqlStringListNew = sqlStringList.subList(0,30);
            parametersListNew = parametersList.subList(0,30);
        }else{
            sqlStringListNew = sqlStringList;
            parametersListNew = parametersList;
        }
        print(consoleView, sqlStringListNew, parametersListNew);
    }

    private void print(ConsoleView consoleView, List<String> sqlStringList, List<String> parametersList) {
        String sqlString;
        String parameters;
        for (int i = 0; i < sqlStringList.size(); i++) {
            sqlString = sqlStringList.get(i);
            parameters = parametersList.get(i);
            consoleView.print((i+1)+"、================================================\n", ConsoleViewContentType.LOG_INFO_OUTPUT);
            consoleView.print(sqlString+"\n", ConsoleViewContentType.LOG_WARNING_OUTPUT);
            consoleView.print(parameters+"\n", ConsoleViewContentType.LOG_WARNING_OUTPUT);
            sqlString = sqlString.replace("==>  Preparing:", "");
            parameters = parameters.replace("==> Parameters:", "");
            String regex = "\\?";
            String[] split = parameters.split(",");
            int count = count(sqlString, "?");
            for(int j=0;j<count;j++){
                int begin = split[j].indexOf("(");
                int end = split[j].indexOf(")");
//                split[j] = split[j].replace(split[j].substring(begin, end+1),"");
                if(split[j].contains("Long") || split[j].contains("Integer") || split[j].contains("BigDecimal")
                        || split[j].contains("Float") || split[j].contains("Double")){
                    split[j] = split[j].replace(split[j].substring(begin, end+1),"");
                    sqlString = sqlString.replaceFirst(regex, split[j].trim());
                }else{
                    split[j] = split[j].replace(split[j].substring(begin, end+1),"");
                    sqlString = sqlString.replaceFirst(regex, "'"+split[j].trim()+"'");
                }
            }
            consoleView.print("sql: "+sqlString+"\n\n", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        }
    }

    private int count(String str, String regex){
        int oldLength = str.length();
        int newLength = str.replace(regex, "").length();
        return oldLength - newLength;
    }

    @NotNull
    private static String getText(ToolWindow toolWindow) {
        Content content = toolWindow.getContentManager().getContent(0);
        KeyFMap plain = ((ContentImpl) content).getPlain();
        @NotNull Key<?>[] keys = plain.getKeys();
        RunContentDescriptor descriptor = (RunContentDescriptor) content.getUserData(keys[3]);
        ExecutionConsole executionConsole = descriptor.getExecutionConsole();
        if(executionConsole instanceof ConsoleViewImpl){
            return ((ConsoleViewImpl) executionConsole).getEditor().getDocument().getText();
        }
        return "";
    }


}
