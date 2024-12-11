package org.utilities;

import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class PlayWrightFactory {
    private static volatile PlayWrightFactory instance;

    private static final ThreadLocal<Browser> browserTL = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextTL = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageTL = new ThreadLocal<>();
    private static final ThreadLocal<Playwright> playwrightTL = new ThreadLocal<>();

    private PlayWrightFactory(){}

    public static PlayWrightFactory getInstance() {
        if(instance == null){
            synchronized (PlayWrightFactory.class){
                if(instance == null){
                    instance = new PlayWrightFactory();
                }
            }
        }
        if(getPage() == null){
            instance.setBrowser(ConfigurationReader.getProperty("browser"),
                    ConfigurationReader.getProperty("headless").equals("true"));
        }


        return instance;
    }

    public static Browser getBrowser() {
        return browserTL.get();
    }

    public static BrowserContext getBrowserContext() {
        return browserContextTL.get();
    }

    public static Page getPage() {
        return pageTL.get();
    }

    public static Playwright getPlaywright() {
        return playwrightTL.get();
    }

    public void setBrowser(String browserType, boolean headless){
        playwrightTL.set(Playwright.create());

        switch (browserType){
            case "chrome":
                if(headless){
                    browserTL.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome")));
                }else{
                    browserTL.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));
                }
                break;
            case "chromium":
                if(headless){
                    browserTL.set(getPlaywright().chromium().launch());
                }else{
                    browserTL.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                }
                break;
            case "firefox":
                if(headless){
                    browserTL.set(getPlaywright().firefox().launch());
                }else{
                    browserTL.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                }
                break;
            case "edge":
                if(headless){
                    browserTL.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge")));
                }else{
                    browserTL.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false)));
                }
                break;
            case "safari":
                if(headless){
                    browserTL.set(getPlaywright().webkit().launch());
                }else{

                }browserTL.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
        }

        browserContextTL.set(getBrowser().newContext());
        pageTL.set(getBrowserContext().newPage());
        getPage().navigate(ConfigurationReader.getProperty("app_url"));
        getPage();
    }

    public byte[] takeScreenshot(){
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        return getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
    }
}
