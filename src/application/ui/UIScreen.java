package application.ui;

public enum UIScreen 
{
	LOGIN("UILogin", "Fantastic Memory - Login"), 
	REGISTER("UIRegister", "Fantastic Memory - Registration"), 
	GDPR("UIGDPR", "Fantastic Memory - GDPR"), 
	LISTSMENU("UIMyLists", "Fantastic Memory - My Lists ");
	
	private String path;
	private String stageTitle;
	
	private UIScreen(String path, String stageTitle)
	{
		this.path = path;
		this.stageTitle = stageTitle;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public String getTitle()
	{
		return stageTitle;
	}
}
