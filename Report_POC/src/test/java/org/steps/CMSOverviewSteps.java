package org.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.pageClasses.CMSOverviewPageClass;
import org.utilities.PlayWrightFactory;

import static org.utilities.MyLogger.getCurrentLog;

public class CMSOverviewSteps {

    CMSOverviewPageClass cmsOverviewPageClass = new CMSOverviewPageClass(PlayWrightFactory.getPage());

    @Given("User Navigates to CMS Landing Page")
    public void user_navigates_to_cms_landing_page() {
        cmsOverviewPageClass.validateTitle("Home - Centers for Medicare & Medicaid Services | CMS");

    }
    @Then("User Validates CMS Headers")
    public void user_validates_cms_headers() {
        cmsOverviewPageClass.validateHeaders();
    }

    @Given("User Navigates to Google Landing Page")
    public void userNavigatesToGoogleLandingPage() {
        PlayWrightFactory.getPage().navigate("https://google.com");
        cmsOverviewPageClass.validateTitle("Google");
    }

    @Then("User Validates Google Headers")
    public void userValidatesGoogleHeaders() {
        cmsOverviewPageClass.validateHeaders2();
    }
}
