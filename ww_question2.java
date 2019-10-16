package ww;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



class Main {

  static WebDriver driver;
  public static void main(String[] args) {
	  
	// Initialize browser
	System.out.println("Initializing browser");
    System.setProperty("webdriver.chrome.driver", "C://Users//Harini Shreedhar//Downloads//chromedriver//chromedriver.exe");    
    driver=new ChromeDriver();
    
    // CASE 1: Navigate to WW website
    System.out.println("\nCASE 1: Navigate to WW website");
    driver.get("https://www.weightwatchers.com/us/");  
    System.out.println("Reading title: "+ driver.getTitle());

    // CASE 2: Verify loaded page title matches “WW (Weight Watchers): Weight Loss & Wellness Help”
    System.out.println("\nCASE 2: Verify loaded page title matches “WW (Weight Watchers): Weight Loss & Wellness Help”");
    if(driver.getTitle().contains("WW (Weight Watchers): Weight Loss & Wellness Help")){
      System.out.println("Page title matches");
    }else{
      System.out.println("Page title does not match"); 
    }
    
    // CASE 3: On the right hand corner of the page, click on "Find a studio"
    System.out.println("\nCASE 3: On the right hand corner of the page, click on “Find a studio”");
    driver.findElement(By.className("find-a-meeting")).click();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // adding an implicit wait time
    String meeting_title = driver.getTitle();
    System.out.println(meeting_title);
    
    // CASE 4: Verify loaded page title contains "Find WW Studios & Meetings Near you | WW USA"
    System.out.println("\nCASE 4: Verify loaded page title");
    if(meeting_title.equals("Find WW Studios & Meetings Near You | WW USA")){
      System.out.println("Find a meeting page title matches");
    }
    else {
      System.out.println("Find a meeting page title does not match");   
    }
    
    // CASE 5: In the search field, search for meetings for zip code: 10011
    System.out.println("\nCASE 5: Search for meeting zipcode: 10011");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.findElement(By.id("meetingSearch")).sendKeys("10011");
    List <WebElement> spiceCls = driver.findElements(By.className("spice-translated"));
    Iterator <WebElement> spiceIter = spiceCls.iterator(); // iterator to find the search box
    while(spiceIter.hasNext()){
    	WebElement spiceEl = spiceIter.next();
    	if(spiceEl.getAttribute("spice").equals("SEARCH_BUTTON")){
    		System.out.println("Clicking on the search button");
    		spiceEl.click();
    		break;
    	}
    }
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
    // CASE 6: Print the title of the 1st result and distance
    System.out.println("\nPrint the title of the 1st result and distance");
    WebElement firstAddrResult = driver.findElements(By.className("meeting-locations-list__item")).get(0);
    
    // Finding location name
    WebElement name1 = firstAddrResult.findElement(By.className("location__name"));
    String pg1Name = name1.findElement(By.tagName("span")).getText();
    
    // Finding location address
    WebElement addr1 = firstAddrResult.findElement(By.className("location__address"));
    String pg1Addr = addr1.getText();
    WebElement city1 = driver.findElement(By.className("location__city-state-zip"));
    String pg1City = city1.getText();
    
    // CASE 7: Click on the first search result and then, verify displayed location name/title \
    //	matches with the name of the first searched result that was clicked
    System.out.println("\nCASE 7: Click 1st result and verify displayed name/title matches with the 1st result clicked");
    addr1.click();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
    WebElement name2 = driver.findElement(By.className("location__name"));
    String pg2Name = name2.findElement(By.tagName("span")).getText(); 
    WebElement addr2 = driver.findElement(By.className("location__address")); 
    String pg2Addr = addr2.getText();
    WebElement city2 = driver.findElement(By.className("location__city-state-zip"));
    String pg2City = city2.getText();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
    System.out.println("Page1:"+pg1Name+"\n"+pg1Addr+"\n"+pg1City);
    System.out.println("Page2:"+ pg2Name+"\n"+pg2Addr+"\n"+pg2City);
    
    // verifying if the results are same 
    if (pg1Name.equals(pg2Name) && pg1Addr.equals(pg2Addr) && pg1City.equals(pg2City)){
      System.out.println("Verified address on the search page against schedule page");
    }
    else{
      System.out.println("Error");
    }
    
    // CASE 8: From this location page, print TODAY'S hours of Operation
    System.out.println("\nCASE 8: From this location page, print TODAY'S hours of Operation");
    Date date = Calendar.getInstance().getTime();
    String today = new SimpleDateFormat("EE",Locale.ENGLISH).format(date.getTime()).toUpperCase();
    System.out.println("Today: "+today);
    List <WebElement> opHours = driver.findElements(By.className("hours-list-item-wrapper"));
    Iterator <WebElement> opHoursIter = opHours.iterator();
    while(opHoursIter.hasNext()){
    	WebElement opHour = opHoursIter.next();
    	String day = opHour.findElement(By.className("hours-list-item-day")).getText();
    	if(day.equals(today)){
    		String hour = opHour.findElement(By.className("hours-list-item-hours")).getText();
    		System.out.println("Found Operating Hours for "+day);
    		System.out.println(hour);
    		break;
    	}
    }
    
    // CASE 9: Create a method to print the number of meeting each person has on a particular day
    System.out.println("\nCASE 9: Print number of meetings each person has on a particular day");
    printMeeting("Mon");
  
  }
  
/** Prints the number of meeting for each person
	on a particular day
**/
  public static void printMeeting(String day){
	  
	   if(driver != null){
		   List <WebElement> schedules = driver.findElements(By.className("schedule-detailed-day"));
		   Iterator <WebElement> scheduleIter = schedules.iterator();
		   
		   while(scheduleIter.hasNext()){
			   WebElement scheduleDay = scheduleIter.next();
			   String dayLabel = scheduleDay.findElement(By.className("schedule-detailed-day-label")).getText();
			   if(dayLabel.equals(day.toUpperCase())){
				  HashMap <String,Integer> numMeetings = new HashMap<>();
				  List <WebElement> meetingItems = scheduleDay.findElements(By.className("schedule-detailed-day-meetings-item"));
				  Iterator <WebElement> meetingItemIter = meetingItems.iterator();
				  
				  while(meetingItemIter.hasNext()){
					  WebElement meetingItem = meetingItemIter.next();
					  String person = meetingItem.findElement(By.className("schedule-detailed-day-meetings-item-leader")).getText();
					  if(!numMeetings.containsKey(person)){
						  numMeetings.put(person, 1);
					  }
					  else{
						  int count = numMeetings.get(person);
						  numMeetings.put(person, count+1);
					  }
					  
				  }
				  System.out.println("\nMeeting stats");
				  for(Map.Entry<String,Integer> entry: numMeetings.entrySet()){
					System.out.println(entry.getKey()+ " "+ entry.getValue());   
				  }
				  break;
			   }
		   }
	   }
  }
}