
package org.mule.modules.google.contact.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/google-contacts</code>.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:46:32-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleContactsNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(GoogleContactsNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [google-contacts] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [google-contacts] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config-with-oauth", new GoogleContactsConnectorConfigDefinitionParser());
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
            this.registerBeanDefinitionParser("get-contacts", new GetContactsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-contacts", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-contact-by-id", new GetContactByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-contact-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("insert-contact", new InsertContactDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("insert-contact", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-contact", new UpdateContactDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-contact", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("add-group", new AddGroupDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("add-group", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-contact-by-id", new DeleteContactByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-contact-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-contact", new DeleteContactDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-contact", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("download-photo-by-id", new DownloadPhotoByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("download-photo-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("download-photo", new DownloadPhotoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("download-photo", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-contact-photo", new UpdateContactPhotoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-contact-photo", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-contact-photo-by-id", new DeleteContactPhotoByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-contact-photo-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-contact-photo", new DeleteContactPhotoDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-contact-photo", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-groups", new GetGroupsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-groups", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-group-by-name", new GetGroupByNameDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-group-by-name", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-group-by-id", new GetGroupByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-group-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("create-group", new CreateGroupDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create-group", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("update-group", new UpdateGroupDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("update-group", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-group-by-id", new DeleteGroupByIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-group-by-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("delete-group", new DeleteGroupDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("delete-group", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("batch-contacts", new BatchContactsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("batch-contacts", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("batch-groups", new BatchGroupsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("batch-groups", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("batch-insert", new BatchInsertDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("batch-insert", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("batch-update", new BatchUpdateDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("batch-update", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("batch-delete", new BatchDeleteDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("batch-delete", "@Processor", ex);
        }
    }

}
