package rhythm.analysis.view;

public class ArcSlider {
	private Arc_viewer arcViewer;
	
	private int slider;// = 0;
	private int leftMin;
	private  int rightMax;
	
	private int sliderxPixels;
	private int sliderWidth;
	private int sliderRadius;
	
	private float slider1offset;
	private boolean overSlider;
	private boolean sliderlocked;
	
	public ArcSlider(Arc_viewer arcViewer, int sliderWidth, int leftMin, int rightMax, int startX){
		this.arcViewer = arcViewer;
		
		this.leftMin = leftMin;
		this.rightMax = rightMax;
		
		this.sliderWidth = sliderWidth;
		this.sliderRadius = sliderWidth / 2;
		this.sliderxPixels = startX;
		setSlider();
		this.slider1offset = (float) 0.0;
		overSlider = false;
		sliderlocked = false;
	
	}
	
	public void setSlider(int position){
		this.slider = (int) ((this.sliderxPixels - arcViewer.screenBorder) / arcViewer.getLineSubdivision());
	}
	
	public void checkSetOverSlider(int mouseX, int mouseY){
		if(mouseY >= arcViewer.screenMidY - sliderRadius  && 
			mouseY <= arcViewer.screenMidY + sliderRadius  && 
		    mouseX >=  sliderxPixels - sliderRadius  && 
			mouseX <=  sliderxPixels + sliderRadius){
			overSlider = true;
			} else {
			overSlider = false;
		}
	 }
	 
	 public void setOffsetIfLocked(int mouseX){
		 if(overSlider){
			 //System.out.println("click");
			 sliderlocked = true; 
			 slider1offset = mouseX - sliderxPixels;   
		 } else {
			 sliderlocked = false;
		 }
	 }
	 
	 public void setSliderPixels(int mouseX, int leftMin, int rightMax){
		 if(sliderlocked) {
			 if(mouseX >= rightMax){
				 sliderxPixels = rightMax - sliderWidth;
			 } else if(mouseX <= leftMin){
				 sliderxPixels = leftMin + sliderWidth;
			 } else {
				 sliderxPixels = mouseX;
			 }
			 setSlider();
		 } 
	 }
	 
	 public void setSlider(){
		 this.slider = getXPosition(sliderxPixels); 
	 }
	 
	 private int getXPosition(int xPixels){
		 return (int) ((xPixels - arcViewer.screenBorder) / arcViewer.getLineSubdivision());		 
	 }
	 
	 public int getXPixels(){
		 return this.sliderxPixels;
	 }
	 
	 public int getWidth(){
		 return this.sliderWidth;
	 }
	 
	 public int getSlider(){
		 return this.slider;
	 }
	 
	 public void lockSlider(){
		 this.sliderlocked = false;
	 }
	 
	  
	 public int getLeftMin(){
		 return this.leftMin;
	 }
	 
	 public int getRightMax(){
		 return this.rightMax;
	 }

}