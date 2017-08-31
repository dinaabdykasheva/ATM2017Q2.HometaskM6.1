package test;

import app.business_objects.Mail;
import app.business_objects.User;
import app.pages.*;
import core.utils.MyLogger;
import core.utils.Screenshoter;
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
            Assert.assertTrue(isAccountIconPresent, "User isn't logged in");
            MyLogger.info("User is successfully logged in");
        } else {
            MyLogger.error("User wasn't logged in");
            Screenshoter.takeScreenshot();
        }

    }

    @Test(description = "SaveToDraftTest", dependsOnMethods = "loginToAccountTest")
    @Parameters({"recipient", "subject", "body"})
    public void saveToDraftTest(String recipient, String subject, String body) {
        mail = new Mail(recipient, subject, body);
        writeMail = accountPage.clickWriteMailButton().writeMailAndSaveToDraft(mail);
        boolean isDraftMailSaved = writeMail.isDraftMailDisplayed(mail);
        if (isDraftMailSaved) {
            Assert.assertTrue(isDraftMailSaved, "Mail wasn't saved to drafts");
            MyLogger.info("mail is save to drafts");
        } else {
            MyLogger.error("Mail wasn't saved to drafts");
            Screenshoter.takeScreenshot();
        }

    }

    @Test(description = "VerifySavedDraftReceiverTest", dependsOnMethods = "saveToDraftTest")
    @Parameters({"recipient"})
    public void verifySavedDraftReceiverTest(String recipient) {
        openSavedDraft = new DraftsFolderPage().openDraftMail();
        String receiver = openSavedDraft.getReceiver();
        if (receiver.equals(recipient)) {
            Assert.assertEquals(recipient, receiver, "");
            MyLogger.info("Receiver is valid");
        } else {
            MyLogger.error("Receiver isn't valid");
            Screenshoter.takeScreenshot();
        }

    }

    @Test(description = "VerifySavedDraftSubjectTest", dependsOnMethods = "verifySavedDraftReceiverTest")
    @Parameters({"subject"})
    public void verifySavedDraftSubjectTest(String subject) {
        String mailSubject = new WriteMailPage().getSubject();
        if (mailSubject.equals(subject)) {
            Assert.assertEquals(subject, mailSubject, "Subject isn't valid");
            MyLogger.info("Subject is valid");
        } else {
            MyLogger.error("Subject isn't valid");
            Screenshoter.takeScreenshot();
        }

    }

    @Test(description = "VerifySavedDraftBodyTest", dependsOnMethods = "verifySavedDraftSubjectTest")
    @Parameters({"body"})
    public void verifySavedDraftBodyTest(String body) {
        String mailBody = new WriteMailPage().getBody();
        if (mailBody.equals(body)) {
            Assert.assertEquals(body, mailBody, "Body isn't valid");
            MyLogger.info("Body is valid");
        } else {
            MyLogger.error("Body isn't valid");
            Screenshoter.takeScreenshot();
        }

    }

    @Test(description = "isMailSent", dependsOnMethods = {"verifySavedDraftReceiverTest", "verifySavedDraftSubjectTest", "verifySavedDraftBodyTest"})
    public void isMailSent() {
        sendMail = new WriteMailPage().sendMail().openSentMail();
        boolean isMailSent = sendMail.isMailSent(mail);
        if (isMailSent) {
            Assert.assertTrue(isMailSent, "Mail wasn't sent");
            MyLogger.info("Mail was sent");
        } else {
            MyLogger.error("Mail wasn't sent");
            Screenshoter.takeScreenshot();
        }

    }

    @Test(description = "MailIsDeletedFromDraftsTest")
    public void mailIsDeletedFromDraftsTest() {
        openDraftFolder = accountPage.openDrafts();
        boolean isMailDeletedFromDrafts = openDraftFolder.isDraftMailDisplayed(mail);
        if (isMailDeletedFromDrafts) {
            Assert.assertFalse(isMailDeletedFromDrafts, "Mail isn't deleted from drafts");
            MyLogger.info("Mail was deleted from drafts");
        } else {
            MyLogger.error("Mail wasn't deleted from drafts");
            Screenshoter.takeScreenshot();
        }

    }

    @Test(description = "ExitGMailTest", alwaysRun = true)
    public void exitGMailTest() {
        exitGMail = accountPage.exitGMail();
        boolean isUserLoggedOff = exitGMail.isUserLoggedOff();
        if (isUserLoggedOff) {
            Assert.assertTrue(isUserLoggedOff, "User wasn't logged off");
            MyLogger.info("User was logged off");
        } else {
            MyLogger.error("User wasn't logged off");
        }

    }
}
