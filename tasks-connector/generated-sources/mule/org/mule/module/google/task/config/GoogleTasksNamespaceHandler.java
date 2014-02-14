
package org.mule.module.google.task.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/google-tasks</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:47:49-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleTasksNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(GoogleTasksNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [google-tasks] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [google-tasks] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config-with-oauth", new GoogleTasksConnectorConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("authorize", new AuthorizeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("authorize", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("authorize", new AuthorizeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("unauthorize", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-task-lists", new GetTaskListsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-task-lists", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-task-list-by-id", new GetTaskListByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-task-list-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("insert-task-list", new InsertTaskListDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("insert-task-list", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-task-list", new UpdateTaskListDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-task-list", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-task-list", new DeleteTaskListDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-task-list", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-tasks", new GetTasksDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-tasks", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-task-by-id", new GetTaskByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-task-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("insert-task", new InsertTaskDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("insert-task", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-task", new UpdateTaskDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-task", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-task", new DeleteTaskDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-task", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("move", new MoveDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("move", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("clear-tasks", new ClearTasksDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("clear-tasks", "@Processor", ex);
        }
    }

}
