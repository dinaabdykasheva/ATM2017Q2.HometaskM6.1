package test;

import app.business_objects.Mail;
import app.business_objects.User;
import app.pages.*;
import core.utils.MyLogger;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


/**
 * Created by Dina_Abdykasheva on 6/15/2017.
 */
public class GMailTestPO extends BaseTest {
    public DraftsFolderPage writeMail, openDraftFolder;
    public WriteMailPage openSavedDraft;
    public SentFolderPage sendMail;
    public LoginToGMailPage exitGMail;
    public AccountPage accountPage;
    public Mail mail;

    @Test(description = "loginToAccountTest", priority = 0)
    @Parameters({"username", "password"})
    public void loginToAccountTest(String username, String password) {
        accountPage = new LoginToGMailPage().loginToGMail(new User(username, password));
        boolean isAccountIconPresent = accountPage.isAccountIconPresent();
        if (isAccountIconPresent) {
            MyLogger.logger.info("User was successfully logged in");
        } else MyLogger.logger.error("User isn't logged in");
        Assert.assertTrue(isAccountIconPresent, "User isn't logged in");
    }

    @Test(description = "SaveToDraftTest", dependsOnMethods = "loginToAccountTest")
    @Parameters({"recipient", "subject", "body"})
    public void saveToDraftTest(String recipient, String subject, String body) {
        mail = new Mail(recipient, subject, body);
        writeMail = accountPage.clickWriteMailButton().writeMailAndSaveToDraft(mail);
        boolean isDraftMailSaved = writeMail.isDraftMailDisplayed(mail);
        if (isDraftMailSaved) {
            MyLogger.logger.info("mail is saved to drafts");
        } else MyLogger.logger.error("Mail wasn't saved to drafts");
        Assert.assertTrue(isDraftMailSaved, "Mail wasn't saved to drafts");
    }

    @Test(description = "VerifySavedDraftReceiverTest", dependsOnMethods = "saveToDraftTest")
    @Parameters({"recipient"})
    public void verifySavedDraftReceiverTest(String recipient) {
        openSavedDraft = new DraftsFolderPage().openDraftMail();
        String receiver = openSavedDraft.getReceiver();
        if (receiver.equals(recipient)) {
            MyLogger.logger.info("Receiver is valid");
        } else MyLogger.logger.error("Receiver isn't valid");
        Assert.assertEquals(recipient, receiver, "Receiver isn't valid");
    }

    @Test(description = "VerifySavedDraftSubjectTest", dependsOnMethods = "verifySavedDraftReceiverTest")
    @Parameters({"subject"})
    public void verifySavedDraftSubjectTest(String subject) {
        String mailSubject = new WriteMailPage().getSubject();
        if (mailSubject.equals(subject)) {
            MyLogger.logger.info("Subject is valid");
        } else MyLogger.logger.error("Subject isn't valid");
        Assert.assertEquals(subject, mailSubject, "Subject isn't valid");
    }

    @Test(description = "VerifySavedDraftBodyTest", dependsOnMethods = "verifySavedDraftSubjectTest")
    @Parameters({"body"})
    public void verifySavedDraftBodyTest(String body) {
        String mailBody = new WriteMailPage().getBody();
        if (mailBody.equals(body)) {
            MyLogger.logger.info("Body is valid");
        } else MyLogger.logger.error("Body isn't valid");
        Assert.assertEquals(body, mailBody, "Body isn't valid");
    }

    @Test(description = "isMailSent", dependsOnMethods = {"verifySavedDraftReceiverTest", "verifySavedDraftSubjectTest", "verifySavedDraftBodyTest"})
    public void isMailSent() {
        sendMail = new WriteMailPage().sendMail().openSentMail();
        boolean isMailSent = sendMail.isMailSent(mail);
        if (isMailSent) {
            MyLogger.logger.info("Mail was sent");
        } else MyLogger.logger.error("Mail wasn't sent");
        Assert.assertTrue(isMailSent, "Mail wasn't sent");
    }

    @Test(description = "MailIsDeletedFromDraftsTest")
    public void mailIsDeletedFromDraftsTest() {
        openDraftFolder = accountPage.openDrafts();
        boolean isMailDeletedFromDrafts = openDraftFolder.isDraftMailDisplayed(mail);
        if (isMailDeletedFromDrafts) {
            MyLogger.logger.info("Mail was deleted from drafts");
        } else MyLogger.logger.error("Mail wasn't deleted from drafts");
        Assert.assertFalse(isMailDeletedFromDrafts, "Mail isn't deleted from drafts");
    }

    @Test(description = "ExitGMailTest", alwaysRun = true)
    public void exitGMailTest() {
        exitGMail = accountPage.exitGMail();
        boolean isUserLoggedOff = exitGMail.isUserLoggedOff();
        if (isUserLoggedOff) {
            MyLogger.logger.info("User was logged off");
        } else  MyLogger.logger.error("User wasn't logged off");
        Assert.assertTrue(isUserLoggedOff, "User wasn't logged off");
    }
}
