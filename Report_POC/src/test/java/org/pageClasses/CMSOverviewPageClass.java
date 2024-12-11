package org.pageClasses;

import com.microsoft.playwright.*;
import org.junit.Assert;
import org.utilities.MyLogger;

import java.util.Arrays;
import java.util.List;

public class CMSOverviewPageClass {

    private Page page;
    ElementHandle elementHandle;

    public CMSOverviewPageClass(Page lPage){
        this.page = lPage;
    }

    private final String cmsLogoXpath = "//*[@id='block-cms-evo-sitebranding']//img[@alt='CMS.gov']";
    private final String medicareButtonXpath = "//*[@region='cms_header_navigation']//*[@role='button' and text()='Medicare']";
    private final String marketPlaceButtonXpath = "//*[@region='cms_header_navigation']//*[@role='button' and text()='Marketplace & Private Insurance']";
    private final String prioritiesButtonXpath = "//*[@region='cms_header_navigation']//*[@role='button' and text()='Priorities']";
    private final String trainingButtonXpath = "//*[@region='cms_header_navigation']//*[@role='button' and text()='Training & Education']";

    private final String googleAboutLink = "//a[text()='About']";
    private final String googleStoreLink = "//a[text()='Store']";
    private final String googleLogo = "//picture/img";
    private final String googleGmailLink = "//a[text()='Gmail']";
    private final String googleImagesLink = "//a[text()='Image']";//this will fail

    private List<String> selectors = Arrays.asList(
            cmsLogoXpath,
            medicareButtonXpath,
            marketPlaceButtonXpath,
            prioritiesButtonXpath,
            trainingButtonXpath
    );

    private List<String> selectors2 = Arrays.asList(
            googleAboutLink,
            googleStoreLink,
            googleLogo,
            googleGmailLink,
            googleImagesLink
    );
    public void validateHeaders(){

        selectors.forEach(c -> {
            page.setDefaultTimeout(1000);
            MyLogger.info(page.textContent(c));
            elementHandle  = page.waitForSelector(c);
            Assert.assertTrue(elementHandle.isEnabled());
        });
    }

    public void validateTitle(String title){
        Assert.assertEquals(title,page.title());
    }

    public void validateHeaders2(){
        selectors2.forEach(c -> {
            page.setDefaultTimeout(1000);
            MyLogger.info(page.textContent(c));
            elementHandle  = page.waitForSelector(c);
            Assert.assertTrue(elementHandle.isEnabled());
        });
    }
}
