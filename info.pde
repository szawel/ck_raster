void info(){
	fill(0);
	// rect(ws_width,0,50,100);
	pushMatrix();
	translate(ws_width,0);
	text(" szerokość " + int(ws_width) + " px",0,8);
	text(" wysokość  " + int(ws_height) + " px",0,20);
	popMatrix();
}